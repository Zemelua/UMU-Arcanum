package io.github.zemelua.umu_arcanum.client.model;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.client.model.item.EvokerCloakModel;
import io.github.zemelua.umu_arcanum.client.model.item.WitchHatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public final class ModModelLayers {
	public static final ModelLayerLocation WITCH_HAT;
	public static final ModelLayerLocation EVOKER_CLOAK;

	public static void onRegisterLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.WITCH_HAT, WitchHatModel::createLayer);
		event.registerLayerDefinition(ModModelLayers.EVOKER_CLOAK, EvokerCloakModel::createLayer);
	}

	static {
		WITCH_HAT = new ModelLayerLocation(UMUArcanum.resource("witch_hat"), "main");
		EVOKER_CLOAK = new ModelLayerLocation(UMUArcanum.resource("evoker_cloak"), "main");
	}
}
