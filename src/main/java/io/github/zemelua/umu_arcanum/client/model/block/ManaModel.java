package io.github.zemelua.umu_arcanum.client.model.block;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class ManaModel implements IDynamicBakedModel {
	private final BakedModel parent; // must be super FluidModel

	public ManaModel(BakedModel parent) {
		this.parent = parent;
	}

	@Nonnull
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random random, IModelData extraData) {
		List<BakedQuad> quads = this.parent.getQuads(state, side, random, extraData);

		UMUArcanum.LOGGER.info("loggg");

		for (int i = 0; i < quads.size(); i++) {
			BakedQuad quad = quads.get(i);
			BakedQuadBuilder consumer = new BakedQuadBuilder(quad.getSprite());

			quad.pipe(consumer); // copy to builder

			this.putLightVertex(consumer);

			quads.set(i, consumer.build());
		}

		return quads;
	}

	private void putLightVertex(IVertexConsumer consumer) {
		// Max light value?
		consumer.put(3, (15<<4)/32768.0f, (15<<4)/32768.0f);
	}

	@Override
	public boolean useAmbientOcclusion() {
		return this.parent.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return this.parent.isGui3d();
	}

	@Override
	public boolean usesBlockLight() {
		return this.parent.usesBlockLight();
	}

	@Override
	public boolean isCustomRenderer() {
		return this.parent.isCustomRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return this.parent.getParticleIcon(EmptyModelData.INSTANCE);
	}

	@Override
	public ItemOverrides getOverrides() {
		return this.parent.getOverrides();
	}
}
