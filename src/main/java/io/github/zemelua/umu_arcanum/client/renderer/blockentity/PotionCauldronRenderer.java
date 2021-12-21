package io.github.zemelua.umu_arcanum.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.block.entity.PotionCauldronBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.LayeredCauldronBlock;

import java.util.function.Function;

public class PotionCauldronRenderer implements BlockEntityRenderer<PotionCauldronBlockEntity> {
	private static final ResourceLocation WATER_LOCATION = new ResourceLocation("minecraft", "block/water_still");

	@Override
	@SuppressWarnings("deprecation")
	public void render(PotionCauldronBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource source, int light, int overlay) {
		double height = ((LayeredCauldronBlock) ModBlocks.POTION_CAULDRON.get()).getContentHeight(blockEntity.getBlockState());
		Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS);
		TextureAtlasSprite texture = atlas.apply(PotionCauldronRenderer.WATER_LOCATION);
		int color = blockEntity.getColor();

		int red = FastColor.ARGB32.red(color);
		int green = FastColor.ARGB32.green(color);
		int blue = FastColor.ARGB32.blue(color);

		Matrix4f matrix = poseStack.last().pose();
		VertexConsumer consumer = source.getBuffer(RenderType.translucentNoCrumbling());
		consumer.vertex(matrix, 2F / 16F, (float) height, 2F / 16F)
				.color(red, green, blue, 255)
				.uv(texture.getU(2F), texture.getV(2F))
				.uv2(overlay)
				.normal(0.0F, 1.0F, 0.0F)
				.endVertex();
		consumer.vertex(matrix, 2F / 16F, (float) height, 14F / 16F)
				.color(red, green, blue, 255)
				.uv(texture.getU(14F), texture.getV(2F))
				.uv2(overlay)
				.normal(0.0F, 1.0F, 0.0F)
				.endVertex();
		consumer.vertex(matrix, 14F / 16F, (float) height, 14F / 16F)
				.color(red, green, blue, 255)
				.uv(texture.getU(14F), texture.getV(14F))
				.uv2(light)
				.normal(0.0F, 1.0F, 0.0F)
				.endVertex();
		consumer.vertex(matrix, 14F / 16F, (float) height, 2F / 16F)
				.color(red, green, blue, 255)
				.uv(texture.getU(2F), texture.getV(14F))
				.uv2(light)
				.normal(0.0F, 1.0F, 0.0F)
				.endVertex();
	}
}
