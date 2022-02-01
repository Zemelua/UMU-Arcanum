package io.github.zemelua.umu_arcanum.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public final class ModCapabilities {
	public static final Capability<IRingLevelManager> RING_LEVEL_MANAGER = CapabilityManager.get(new CapabilityToken<>() {});

	public static void initialize(IEventBus forgeEvents, IEventBus modEvents) {
		modEvents.addListener(ModCapabilities::onRegisterCapabilities);
	}

	private static void onRegisterCapabilities(final RegisterCapabilitiesEvent event) {
		event.register(IRingLevelManager.class);
	}
}
