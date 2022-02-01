package io.github.zemelua.umu_arcanum.replace.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import io.github.zemelua.umu_arcanum.capability.ModCapabilities;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.awt.*;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin implements ResourceManagerReloadListener {
	@Shadow protected abstract void fillRect(BufferBuilder p_115153_, int p_115154_, int p_115155_, int p_115156_, int p_115157_, int p_115158_, int p_115159_, int p_115160_, int p_115161_);

	@Inject(method = "renderGuiItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
			at = @At(value = "INVOKE",
					shift = At.Shift.BEFORE,
					target = "Lnet/minecraft/world/item/ItemStack;isBarVisible()Z",
					ordinal = 0))
	@SuppressWarnings("SpellCheckingInspection")
	private void renderGuiItemDecorations(Font font, ItemStack stack, int drawX, int drawY, @Nullable String count, CallbackInfo callback) {
		stack.getCapability(ModCapabilities.RING_LEVEL_MANAGER).ifPresent(ringLevelManager -> {
			RenderSystem.disableDepthTest();
			RenderSystem.disableTexture();
			RenderSystem.disableBlend();
			Tesselator tesselator = Tesselator.getInstance();
			BufferBuilder bufferbuilder = tesselator.getBuilder();
			Color color = new Color(9878527);
			int width = ringLevelManager.getBarWidth();
			this.fillRect(bufferbuilder, drawX + 2, drawY + 13, 13, 2, 0, 0, 0, 255);
			if (width >= 0) {
				this.fillRect(bufferbuilder, drawX + 2, drawY + 13, width, 1, color.getRed(), color.getGreen(), color.getBlue(), 255);
			}
			RenderSystem.enableBlend();
			RenderSystem.enableTexture();
			RenderSystem.enableDepthTest();
		});
	}
}
