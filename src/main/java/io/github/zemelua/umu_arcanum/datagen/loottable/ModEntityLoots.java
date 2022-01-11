package io.github.zemelua.umu_arcanum.datagen.loottable;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.stream.Collectors;

public class ModEntityLoots extends EntityLoot {
	@Override
	protected void addTables() {
	}

	@Override
	protected Iterable<EntityType<?>> getKnownEntities() {
		return ForgeRegistries.ENTITIES.getEntries().stream()
				.filter(blocks -> blocks.getKey().location().getNamespace().equals(UMUArcanum.MOD_ID))
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
	}
}
