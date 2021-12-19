package io.github.zemelua.umu_arcanum.mixin;

import io.github.zemelua.umu_arcanum.block.ModBlocks;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlockRenderer.class)
public abstract class LiquidBlockRendererMixin {
	@Inject(method = "getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I",
			at = @At("RETURN"),
			cancellable = true)
	@SuppressWarnings("SpellCheckingInspection")
	private void getLightColor(BlockAndTintGetter getter, BlockPos pos, CallbackInfoReturnable<Integer> callback) {
		if (getter.getBlockState(pos).is(ModBlocks.MANA.get())) {
			callback.setReturnValue(255);
		}
	}
}
