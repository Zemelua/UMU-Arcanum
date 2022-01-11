package io.github.zemelua.umu_arcanum.datagen.loottable;

import net.minecraft.data.loot.FishingLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

public class ModFishingLoots extends FishingLoot {
	@Override
	public void accept(BiConsumer<ResourceLocation, LootTable.Builder> builders) {
	}
}
