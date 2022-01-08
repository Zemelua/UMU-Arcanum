package io.github.zemelua.umu_arcanum.recipe;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.recipe.alchemy.TippedArrowRecipe;
import io.github.zemelua.umu_arcanum.recipe.alchemy.AlchemyRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public final class ModRecipeSerializers {
	private static final DeferredRegister<RecipeSerializer<?>> REGISTRY = UMUArcanum.registry(ForgeRegistries.RECIPE_SERIALIZERS);

	public static final RegistryObject<RecipeSerializer<AlchemyRecipe>> ALCHEMY;
	public static final RegistryObject<RecipeSerializer<TippedArrowRecipe>> TIPPED_ALLOW;

	protected static final Marker MARKER = MarkerManager.getMarker("RECIPE_TYPE");

	private static boolean initialized = false;

	private ModRecipeSerializers() {}

	public static void initialize(@SuppressWarnings("unused") IEventBus forgeEvents, @SuppressWarnings("unused") IEventBus modEvents) {
		if (ModRecipeSerializers.initialized) UMUArcanum.LOGGER.error(ModRecipeSerializers.MARKER, "Already initialized!");

		ModRecipeSerializers.REGISTRY.register(modEvents);

		ModRecipeSerializers.initialized = true;
	}

	static {
		ALCHEMY = ModRecipeSerializers.REGISTRY.register("alchemy", AlchemyRecipe.Serializer::new);
		TIPPED_ALLOW = ModRecipeSerializers.REGISTRY.register("tipped_allow", () -> new SimpleRecipeSerializer<>(TippedArrowRecipe::new));
	}


}
