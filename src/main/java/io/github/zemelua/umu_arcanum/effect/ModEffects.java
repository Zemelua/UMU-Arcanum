package io.github.zemelua.umu_arcanum.effect;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public final class ModEffects {
	private static final DeferredRegister<MobEffect> REGISTRY = UMUArcanum.registry(ForgeRegistries.MOB_EFFECTS);

	public static final RegistryObject<MobEffect> BLESSING;
	public static final RegistryObject<MobEffect> VULNERABLE;

	public static final Marker MARKER = MarkerManager.getMarker("EFFECT");

	private static boolean initialized = false;

	private ModEffects() {}

	public static void initialize(IEventBus forgeEvents, IEventBus modEvents) {
		if (ModEffects.initialized) UMUArcanum.LOGGER.error(ModEffects.MARKER, "Already initialized!");

		ModEffects.REGISTRY.register(modEvents);
		forgeEvents.addListener(BlessingEffect::onPotionApplicable);
		forgeEvents.addListener(VulnerableEffect::onLivingHurt);

		ModEffects.initialized = true;
	}

	static {
		BLESSING = ModEffects.REGISTRY.register("blessing", () -> new BlessingEffect(MobEffectCategory.BENEFICIAL, 14737448));
		VULNERABLE = ModEffects.REGISTRY.register("vulnerable", () -> new VulnerableEffect(MobEffectCategory.HARMFUL, 3686214));
	}
}
