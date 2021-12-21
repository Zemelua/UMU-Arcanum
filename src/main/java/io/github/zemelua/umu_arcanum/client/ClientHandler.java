package io.github.zemelua.umu_arcanum.client;

import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.block.entity.ModBlockEntities;
import io.github.zemelua.umu_arcanum.client.model.block.ManaModel;
import io.github.zemelua.umu_arcanum.client.renderer.blockentity.PotionCauldronRenderer;
import io.github.zemelua.umu_arcanum.fluid.ModFluids;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	private final IEventBus forgeEvents;
	private final IEventBus modEvents;

	public ClientHandler(IEventBus forgeEvents, IEventBus modEvents) {
		this.forgeEvents = forgeEvents;
		this.modEvents = modEvents;
	}

	public void initialize() {
		this.modEvents.addListener(ClientHandler::onFMLClientSetup);
		this.modEvents.addListener(ClientHandler::onModelBake);
	}

	private static void onFMLClientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.MANA_CAULDRON.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModFluids.MANA.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MANA.get(), RenderType.translucent());

		event.enqueueWork(() -> {
			BlockEntityRenderers.register(ModBlockEntities.POTION_CAULDRON.get(), context -> new PotionCauldronRenderer());
		});
	}

	private static void onModelBake(final ModelBakeEvent event) {
		for (BlockState state : ModBlocks.MANA.get().getStateDefinition().getPossibleStates()) {
			ModelResourceLocation location = BlockModelShaper.stateToModelLocation(state);
			BakedModel parent = event.getModelManager().getModel(location);

			event.getModelRegistry().put(location, new ManaModel(parent));
		}
	}
}
