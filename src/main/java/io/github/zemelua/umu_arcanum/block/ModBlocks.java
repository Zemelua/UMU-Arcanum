package io.github.zemelua.umu_arcanum.block;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.fluid.ModFluids;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
	private static final DeferredRegister<Block> REGISTRY = UMUArcanum.registry(ForgeRegistries.BLOCKS);

	public static final RegistryObject<Block> MANA;

	public static void initialize(IEventBus forgeEvents, IEventBus modEvents) {
		ModBlocks.REGISTRY.register(modEvents);
	}

	static {
		MANA = REGISTRY.register("mana", ()
						-> new LiquidBlock(ModFluids.MANA, BlockBehaviour.Properties.of(Material.WATER)
						.noCollission()
						.randomTicks()
						.strength(100.0F)
						.noDrops()
						.lightLevel((state) -> 15)
				)
		);
	}
}
