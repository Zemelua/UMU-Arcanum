package io.github.zemelua.umu_arcanum.fluid;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ManaFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(
			ModFluids.MANA,
			ModFluids.FLOWING_MANA,
			FluidAttributes.builder(UMUArcanum.location("block/mana_still"), UMUArcanum.location("block/mana_flow"))
					.sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
					.luminosity(15))
			.block(() -> (LiquidBlock) ModBlocks.MANA.get())
			.bucket(ModItems.MANA_BUCKET);

	private ManaFluid() {}

	public static void onFMLClientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(ModFluids.MANA.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MANA.get(), RenderType.translucent());
	}
}
