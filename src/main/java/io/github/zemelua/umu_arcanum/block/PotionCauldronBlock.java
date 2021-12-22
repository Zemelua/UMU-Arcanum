package io.github.zemelua.umu_arcanum.block;

import io.github.zemelua.umu_arcanum.block.entity.PotionCauldronBlockEntity;
import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;

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

			if (heldStack.getItem() instanceof ShovelItem) {
				if (!level.isClientSide()) {
					blockEntity.stir(level.getEntitiesOfClass(ItemEntity.class, new AABB(pos)).stream()
							.map(ItemEntity::getItem)
							.toArray(ItemStack[]::new)
					);
				} else {
					level.playSound(player, pos, SoundEvents.PLAYER_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
				}

				return InteractionResult.sidedSuccess(level.isClientSide());
			}
		}

		return super.use(state, level, pos, player, hand, hitResult);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
		if (PotionCauldronBlock.isHeated(level, pos)) {
			if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity blockEntity) {
				double xPos = pos.getX();
				double yPos = pos.getY();
				double zPos = pos.getZ();
				int height = 4 + 5 + (state.getValue(BlockStateProperties.LEVEL_CAULDRON) - 1) * 3;

				for (int i = 0; i < 3; i++) {
					level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE,
							xPos + random.nextFloat(), yPos + height / 16D, zPos + random.nextFloat(), 0.0D, 0.04D, 0.0D);
				}
				if (random.nextInt(200) == 0) {
					level.playLocalSound(xPos, yPos, zPos, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS,
							0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
				}
			}
		}
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

	public static boolean isHeated(Level level, BlockPos pos) {
		return isHeater(level.getBlockState(pos.below()));
	}

	public static boolean isSoulHeated(Level level, BlockPos pos) {
		return isSoulHeater(level.getBlockState(pos.below()));
	}

	public static boolean isHeater(BlockState state) {
		return (state.is(Blocks.CAMPFIRE) && state.getValue(BlockStateProperties.LIT))
				|| isSoulHeater(state);
	}

	public static boolean isSoulHeater(BlockState state) {
		return (state.is(Blocks.SOUL_CAMPFIRE) && state.getValue(BlockStateProperties.LIT));
	}

	static {
		POUR_POTION = (state, level, pos, player, hand, itemStack) -> {
			if (state.getValue(LayeredCauldronBlock.LEVEL) != 3) {
				if (!level.isClientSide()) {
					if (level.getBlockEntity(pos) instanceof PotionCauldronBlockEntity blockEntityPotionCauldron) {
						blockEntityPotionCauldron.pour(itemStack);
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
					@Nullable Potion potion = blockEntityPotionCauldron.getRoot();
					if (potion != null) {
						PotionUtils.setPotion(potionStack, potion);
					}
					PotionUtils.setCustomEffects(potionStack, blockEntityPotionCauldron.scoop());
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
					blockEntityPotionCauldron.pour(itemStack);
				}
				level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return InteractionResult.sidedSuccess(level.isClientSide());
		});
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
}
