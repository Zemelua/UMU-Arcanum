package io.github.zemelua.umu_arcanum.client.model.item;

import io.github.zemelua.umu_arcanum.util.ModelUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class WitchHatModel extends HumanoidModel<LivingEntity> {
	public WitchHatModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F);
		PartDefinition part = ModelUtils.createHumanoidAlias(mesh);

		part.getChild("head")
				.addOrReplaceChild("brim", CubeListBuilder.create()
								.texOffs(0, 0)
								.addBox(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F),
						PartPose.offset(-5.0F, -8.03125F, -5.0F)
				)
				.addOrReplaceChild("slack0", CubeListBuilder.create()
								.texOffs(0, 12)
								.addBox(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F),
						PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.05235988F, 0.0F, 0.02617994F)
				)
				.addOrReplaceChild("slack1", CubeListBuilder.create()
								.texOffs(0, 23)
								.addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F),
						PartPose.offsetAndRotation(1.75F, -4.0F, 2.0F, -0.10471976F, 0.0F, 0.05235988F)
				)
				.addOrReplaceChild("tip", CubeListBuilder.create()
								.texOffs(40, 0)
								.addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)),
						PartPose.offsetAndRotation(1.75F, -2.0F, 2.0F, -0.20943952F, 0.0F, 0.10471976F)
				);

		return LayerDefinition.create(mesh, 64, 32);
	}
}
