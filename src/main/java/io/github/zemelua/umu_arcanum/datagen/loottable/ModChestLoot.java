package io.github.zemelua.umu_arcanum.datagen.loottable;

import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

public class ModChestLoot extends ChestLoot {
	@Override
	public void accept(BiConsumer<ResourceLocation, LootTable.Builder> builders) {
	}
}
