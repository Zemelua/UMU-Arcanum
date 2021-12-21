package io.github.zemelua.umu_arcanum.block.entity;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ConstantConditions")
public final class ModBlockEntities {
	private static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, UMUArcanum.MOD_ID);

	public static final RegistryObject<BlockEntityType<PotionCauldronBlockEntity>> POTION_CAULDRON;

	public static void initialize(IEventBus forgeEvents, IEventBus modEvents) {
		ModBlockEntities.REGISTRY.register(modEvents);
	}

	static {
		POTION_CAULDRON = ModBlockEntities.REGISTRY.register("potion_cauldron", ()
				-> BlockEntityType.Builder.of(PotionCauldronBlockEntity::new, ModBlocks.POTION_CAULDRON.get()).build(null)
		);
	}
}
