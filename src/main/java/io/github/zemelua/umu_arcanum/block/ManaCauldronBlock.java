package io.github.zemelua.umu_arcanum.block;

import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Map;

public class ManaCauldronBlock extends LayeredCauldronBlock {
	private static final Map<Item, CauldronInteraction> INTERACTIONS = CauldronInteraction.newInteractionMap();
	private static final CauldronInteraction FILL_MANA;
	private static final CauldronInteraction PICK_MANA;
	private static final CauldronInteraction POUR_MANA;
	private static final CauldronInteraction SCOOP_MANA;
	private static final CauldronInteraction POUR_MANA_EMPTY;

	public ManaCauldronBlock(Properties properties) {
		super(properties, precipitation -> precipitation == Biome.Precipitation.NONE, ManaCauldronBlock.INTERACTIONS);
	}

	protected static void onFMLCommonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			CauldronInteraction.addDefaultInteractions(INTERACTIONS);
			ManaCauldronBlock.INTERACTIONS.put(ModItems.MANA_BUCKET.get(), ManaCauldronBlock.FILL_MANA);
			ManaCauldronBlock.INTERACTIONS.put(Items.BUCKET, ManaCauldronBlock.PICK_MANA);
			ManaCauldronBlock.INTERACTIONS.put(ModItems.MANA_BOTTLE.get(), ManaCauldronBlock.POUR_MANA);
			ManaCauldronBlock.INTERACTIONS.put(Items.GLASS_BOTTLE, ManaCauldronBlock.SCOOP_MANA);

			CauldronInteraction.EMPTY.put(ModItems.MANA_BUCKET.get(), ManaCauldronBlock.FILL_MANA);
			CauldronInteraction.WATER.put(ModItems.MANA_BUCKET.get(), ManaCauldronBlock.FILL_MANA);
			CauldronInteraction.LAVA.put(ModItems.MANA_BUCKET.get(), ManaCauldronBlock.FILL_MANA);
			CauldronInteraction.POWDER_SNOW.put(ModItems.MANA_BUCKET.get(), ManaCauldronBlock.FILL_MANA);
			CauldronInteraction.EMPTY.put(ModItems.MANA_BOTTLE.get(), ManaCauldronBlock.POUR_MANA_EMPTY);
		});
	}

	static {
		FILL_MANA = (state, level, pos, player, hand, itemStack)
				-> CauldronInteraction.emptyBucket(level, pos, player, hand, itemStack,
				ModBlocks.MANA_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
				SoundEvents.BUCKET_EMPTY)
		;
		PICK_MANA = (state, level, pos, player, hand, itemStack)
				-> CauldronInteraction.fillBucket(state, level, pos, player, hand, itemStack,
				new ItemStack(ModItems.MANA_BUCKET.get()), (stateArg)
						-> stateArg.getValue(LayeredCauldronBlock.LEVEL) == 3,
				SoundEvents.BUCKET_FILL)
		;
		POUR_MANA = (state, level, pos, player, hand, itemStack) -> {
			if (state.getValue(LayeredCauldronBlock.LEVEL) != 3) {
				if (!level.isClientSide()) {
					player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.awardStat(Stats.USE_CAULDRON);
					player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
					level.setBlockAndUpdate(pos, state.cycle(LayeredCauldronBlock.LEVEL));
					level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
					level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
				}

				return InteractionResult.sidedSuccess(level.isClientSide());
			} else {
				return InteractionResult.PASS;
			}
		};
		SCOOP_MANA = (state, level, pos, player, hand, itemStack) -> {
			if (!level.isClientSide()) {
				player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(ModItems.MANA_BOTTLE.get())));
				player.awardStat(Stats.USE_CAULDRON);
				player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
				LayeredCauldronBlock.lowerFillLevel(state, level, pos);
				level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
			}

			return InteractionResult.sidedSuccess(level.isClientSide());
		};
		POUR_MANA_EMPTY = (state, level, pos, player, hand, itemStack) -> {
			if (!level.isClientSide()) {
				player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.awardStat(Stats.USE_CAULDRON);
				player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
				level.setBlockAndUpdate(pos, ModBlocks.MANA_CAULDRON.get().defaultBlockState());
				level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
				level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
			}

			return InteractionResult.sidedSuccess(level.isClientSide());
		};
	}
}
