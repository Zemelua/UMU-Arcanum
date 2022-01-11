package io.github.zemelua.umu_arcanum.client;

import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.block.entity.ModBlockEntities;
import io.github.zemelua.umu_arcanum.client.model.ModModelLayers;
import io.github.zemelua.umu_arcanum.client.model.block.ManaCauldronModel;
import io.github.zemelua.umu_arcanum.client.renderer.blockentity.PotionCauldronRenderer;
import io.github.zemelua.umu_arcanum.fluid.ModFluids;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ColorHandlerEvent;
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
		this.modEvents.addListener(ClientHandler::onItemColorHandler);
		this.modEvents.addListener(ClientHandler::onModelBake);
		this.modEvents.addListener(ModModelLayers::onRegisterLayerDefinitions);
	}

	private static void onFMLClientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.MANDRAKE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.MANA_CAULDRON.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModFluids.MANA.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MANA.get(), RenderType.translucent());

		event.enqueueWork(() -> {
			BlockEntityRenderers.register(ModBlockEntities.POTION_CAULDRON.get(), context -> new PotionCauldronRenderer());
		});
	}

	private static void onItemColorHandler(final ColorHandlerEvent.Item event) {
		event.getItemColors().register((itemStack, tint) -> {
			if (tint > 0) return -1;

			Potion potion = PotionUtils.getPotion(itemStack);
			if (potion == Potions.EMPTY) return PotionUtils.getColor(PotionUtils.getCustomEffects(itemStack));

			return PotionUtils.getColor(itemStack);
		}, Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION, Items.TIPPED_ARROW);
	}

	private static void onModelBake(final ModelBakeEvent event) {
		for (BlockState state : ModBlocks.MANA_CAULDRON.get().getStateDefinition().getPossibleStates()) {
			ModelResourceLocation location = BlockModelShaper.stateToModelLocation(state);
			BakedModel parent = event.getModelManager().getModel(location);

			event.getModelRegistry().put(location, new ManaCauldronModel(parent));
		}
	}
}
