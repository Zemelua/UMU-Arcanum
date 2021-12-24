package io.github.zemelua.umu_arcanum.client.model;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.client.model.item.WitchHatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public final class ModModelLayers {
	public static final ModelLayerLocation WITCH_HAT;

	public static void onRegisterLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.WITCH_HAT, WitchHatModel::createLayer);
	}

	static {
		WITCH_HAT = new ModelLayerLocation(UMUArcanum.resource("witch_hat"), "main");
	}
}
