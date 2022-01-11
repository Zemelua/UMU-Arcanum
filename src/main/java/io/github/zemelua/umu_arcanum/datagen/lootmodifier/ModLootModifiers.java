package io.github.zemelua.umu_arcanum.datagen.lootmodifier;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModLootModifiers {
	private static final DeferredRegister<GlobalLootModifierSerializer<?>> REGISTRY = UMUArcanum.registry(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS);

	public static final RegistryObject<GlobalLootModifierSerializer<CarrotsLootModifier>> CARROTS;
	public static final RegistryObject<GlobalLootModifierSerializer<WitchLootModifier>> WITCH;


	public static void initialize(@SuppressWarnings("unused") IEventBus forgeEvents, IEventBus modEvents) {
		ModLootModifiers.REGISTRY.register(modEvents);
	}

	static {
		CARROTS = ModLootModifiers.REGISTRY.register("carrots_replace", CarrotsLootModifier.Serializer::new);
		WITCH = ModLootModifiers.REGISTRY.register("witch_replace", WitchLootModifier.Serializer::new);
	}
}
