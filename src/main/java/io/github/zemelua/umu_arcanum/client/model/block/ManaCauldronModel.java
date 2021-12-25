package io.github.zemelua.umu_arcanum.client.model.block;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ManaCauldronModel implements IDynamicBakedModel {
	private final BakedModel base;

	public ManaCauldronModel(BakedModel base) {
		this.base = base;
	}

	@NotNull
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
		List<BakedQuad> quads = this.base.getQuads(state, side, rand, extraData);

		for (int i = 0; i < quads.size(); i++) {
			BakedQuad quad = quads.get(i);
			int[] vertices = quad.getVertices();

			for (int j = 0; j < 4; j++) {
				vertices[8 * j + 6] = 15728880;
			}

			quads.set(i, new BakedQuad(vertices, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade()));
		}

		return quads;
	}

	@Override
	public boolean useAmbientOcclusion() {
		return this.base.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return this.base.isGui3d();
	}

	@Override
	public boolean usesBlockLight() {
		return this.base.usesBlockLight();
	}

	@Override
	public boolean isCustomRenderer() {
		return this.base.isCustomRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return this.base.getParticleIcon(EmptyModelData.INSTANCE);
	}

	@Override
	public ItemOverrides getOverrides() {
		return this.base.getOverrides();
	}
}
