package io.github.zemelua.umu_arcanum.datagen.loottable;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.stream.Collectors;

public class ModBlockLoots extends BlockLoot {
	@Override
	protected void addTables() {
		this.add(ModBlocks.MANDRAKE.get(), BlockLoot.applyExplosionDecay(ModBlocks.MANDRAKE.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModBlocks.MANDRAKE.get())))
				.withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition
								.hasBlockStateProperties(ModBlocks.MANDRAKE.get())
								.setProperties(StatePropertiesPredicate.Builder.properties()
										.hasProperty(CarrotBlock.AGE, 7)))
						.add(LootItem.lootTableItem(ModItems.MANDRAKE.get())
								.apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))))));
		this.dropOther(ModBlocks.MANA_CAULDRON.get(), Items.CAULDRON);
		this.dropOther(ModBlocks.POTION_CAULDRON.get(), Items.CAULDRON);
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ForgeRegistries.BLOCKS.getEntries().stream()
				.filter(blocks -> blocks.getKey().location().getNamespace().equals(UMUArcanum.MOD_ID))
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
	}
}
