package io.github.zemelua.umu_arcanum.effect;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public final class ModPotions {
	private static final DeferredRegister<Potion> REGISTRY = UMUArcanum.registry(ForgeRegistries.POTIONS);

	public static final RegistryObject<Potion> BLESSING;
	public static final RegistryObject<Potion> LONG_BLESSING;

	public static final Marker MARKER = MarkerManager.getMarker("POTION");

	private static boolean initialized = false;

	private ModPotions() {}

	public static void initialize(@SuppressWarnings("unused") IEventBus forgeEvents, IEventBus modEvents) {
		if (ModPotions.initialized) UMUArcanum.LOGGER.error(ModPotions.MARKER, "Already initialized!");

		ModPotions.REGISTRY.register(modEvents);
		modEvents.addListener(ModPotions::onFMLCommonSetup);

		ModPotions.initialized = true;
	}

	private static void onFMLCommonSetup(final FMLCommonSetupEvent event) {
		BrewingRecipeRegistry.addRecipe(
				Ingredient.of(ModItems.MANA_BOTTLE.get()),
				Ingredient.of(ModItems.ARCANE_GOLD_INGOT.get()),
				PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.BLESSING.get())
		);
	}

	static {
		BLESSING = ModPotions.REGISTRY.register("blessing", () -> new Potion(new MobEffectInstance(ModEffects.BLESSING.get(), 3600)));
		LONG_BLESSING = ModPotions.REGISTRY.register("long_blessing", () -> new Potion("blessing", new MobEffectInstance(ModEffects.BLESSING.get(), 9600)));
	}
}
