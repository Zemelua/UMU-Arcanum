package io.github.zemelua.umu_arcanum.block;

import io.github.zemelua.umu_arcanum.block.entity.PotionCauldronBlockEntity;
import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class PotionCauldronBlock extends LayeredCauldronBlock implements EntityBlock {
	private static final Map<Item, CauldronInteraction> INTERACTIONS = CauldronInteraction.newInteractionMap();
	private static final CauldronInteraction POUR_POTION;
	private static final CauldronInteraction POUR_POTION_EMPTY;
	private static final CauldronInteraction POUR_MANA;
	private static final CauldronInteraction SCOOP_POTION;

	public PotionCauldronBlock(Properties properties) {
		super(properties, precipitation -> precipitation == Biome.Precipitation.NONE, PotionCauldronBlock.INTERACTIONS);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity blockEntity) {
			ItemStack heldStack = player.getItemInHand(hand);
			Collection<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(pos));

			if (heldStack.is(Items.STICK) && !itemEntities.isEmpty()) {
				if (!level.isClientSide()) {
					level.playSound(player, pos, SoundEvents.PLAYER_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);

					Optional<ItemStack> result = blockEntity.stir(itemEntities.stream()
							.map(ItemEntity::getItem)
							.toArray(ItemStack[]::new)
					);

					result.ifPresentOrElse(arg -> {
						level.playSound(null, pos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F);
						ExperienceOrb.award((ServerLevel) level, Vec3.atCenterOf(pos), 22);
						Containers.dropItemStack(level, pos.getX(), pos.getY() + 0.8D, pos.getZ(), arg);
						itemEntities.forEach(ItemEntity::discard);
					}, () -> {
						level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 4.0F, Explosion.BlockInteraction.NONE);
						level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 4);
					});
				}

				return InteractionResult.sidedSuccess(level.isClientSide());
			}
		}

		return super.use(state, level, pos, player, hand, hitResult);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PotionCauldronBlockEntity(pos, state);
	}

	@Override
	@SuppressWarnings("deprecation")
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (entity.isOnFire()) {
			entity.clearFire();
		}

		if (!world.isClientSide() && this.isEntityInsideContent(state, pos, entity)) {
			if (entity instanceof LivingEntity entityLiving) {
				@Nullable PotionCauldronBlockEntity blockEntity = (PotionCauldronBlockEntity) world.getBlockEntity(pos);

				if (blockEntity != null) {
					blockEntity.getEffectInstances().stream()
							.map(effectInstance -> new MobEffectInstance(effectInstance.getEffect(), 200, effectInstance.getAmplifier()))
							.forEach(entityLiving::addEffect);
				}
			}
		}
	}

	protected static void onFMLCommonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			CauldronInteraction.addDefaultInteractions(PotionCauldronBlock.INTERACTIONS);
			PotionCauldronBlock.INTERACTIONS.put(Items.POTION, PotionCauldronBlock.POUR_POTION);
			PotionCauldronBlock.INTERACTIONS.put(ModItems.MANA_BOTTLE.get(), PotionCauldronBlock.POUR_MANA);
			PotionCauldronBlock.INTERACTIONS.put(Items.GLASS_BOTTLE, PotionCauldronBlock.SCOOP_POTION);

			CauldronInteraction.EMPTY.put(Items.POTION, PotionCauldronBlock.POUR_POTION_EMPTY);
		});
	}

	static {
		POUR_POTION = (state, level, pos, player, hand, itemStack) -> {
			if (state.getValue(LayeredCauldronBlock.LEVEL) != 3) {
				if (!level.isClientSide()) {
					if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity blockEntityPotionCauldron) {
						blockEntityPotionCauldron.pourEffect(PotionUtils.getMobEffects(itemStack));
					}
					player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.awardStat(Stats.USE_CAULDRON);
					player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
					level.setBlockAndUpdate(pos, state.cycle(LayeredCauldronBlock.LEVEL));
					level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
					level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return InteractionResult.sidedSuccess(level.isClientSide());
			} else {
				return InteractionResult.PASS;
			}
		};
		POUR_MANA = (state, level, pos, player, hand, itemStack) -> {
			if (state.getValue(LayeredCauldronBlock.LEVEL) != 3) {
				if (!level.isClientSide()) {
					if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity blockEntityPotionCauldron) {
						blockEntityPotionCauldron.pourMana();
					}
					player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.awardStat(Stats.USE_CAULDRON);
					player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
					level.setBlockAndUpdate(pos, state.cycle(LayeredCauldronBlock.LEVEL));
					level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
					level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return InteractionResult.sidedSuccess(level.isClientSide());
			} else {
				return InteractionResult.PASS;
			}
		};
		SCOOP_POTION = (state, level, pos, player, hand, itemStack) -> {
			if (!level.isClientSide()) {
				ItemStack potionStack = new ItemStack(Items.POTION);
				if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity blockEntityPotionCauldron) {
					Potion potion = Potions.EMPTY;
					PotionUtils.setPotion(potionStack, potion);
					PotionUtils.setCustomEffects(potionStack, blockEntityPotionCauldron.scoopEffect());
				}
				player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, potionStack));
				player.awardStat(Stats.USE_CAULDRON);
				player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
				if (!player.isCrouching()) {
					LayeredCauldronBlock.lowerFillLevel(state, level, pos);
				}
				level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
			}

			return InteractionResult.sidedSuccess(level.isClientSide());
		};
		POUR_POTION_EMPTY = ((state, level, pos, player, hand, itemStack) -> {
			if (!level.isClientSide()) {
				player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack.copy(), player, new ItemStack(Items.GLASS_BOTTLE)));
				player.awardStat(Stats.USE_CAULDRON);
				player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
				level.setBlockAndUpdate(pos, ModBlocks.POTION_CAULDRON.get().defaultBlockState());
				if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity blockEntityPotionCauldron) {
					blockEntityPotionCauldron.pourEffect(PotionUtils.getMobEffects(itemStack));
				}
				level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return InteractionResult.sidedSuccess(level.isClientSide());
		});
	}
}
