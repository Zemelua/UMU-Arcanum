package io.github.zemelua.umu_arcanum.datagen.loottable;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
	private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> loots = ImmutableList.of(
			Pair.of(ModBlockLoots::new, LootContextParamSets.BLOCK),
			Pair.of(ModEntityLoots::new, LootContextParamSets.ENTITY),
			Pair.of(ModFishingLoots::new, LootContextParamSets.FISHING),
			Pair.of(ModChestLoot::new, LootContextParamSets.CHEST),
			Pair.of(ModPiglinBarterLoots::new, LootContextParamSets.PIGLIN_BARTER),
			Pair.of(ModGiftLoots::new, LootContextParamSets.GIFT)
	);

	public ModLootTableProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
		return this.loots;
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext tracker) {
	}
}
