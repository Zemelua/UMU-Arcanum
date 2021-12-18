package io.github.zemelua.umu_arcanum.fluid;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModFluids {
	private static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, UMUArcanum.MOD_ID);

	public static final RegistryObject<FlowingFluid> MANA;
	public static final RegistryObject<FlowingFluid> FLOWING_MANA;

	public static void initialize(IEventBus forgeEvents, IEventBus modEvents) {
		ModFluids.REGISTRY.register(modEvents);
	}

	static {
		MANA = REGISTRY.register("mana", ()
				-> new ForgeFlowingFluid.Source(ManaFluid.PROPERTIES)
		);
		FLOWING_MANA = REGISTRY.register("mana_flowing", ()
				-> new ForgeFlowingFluid.Flowing(ManaFluid.PROPERTIES)
		);
	}
}
