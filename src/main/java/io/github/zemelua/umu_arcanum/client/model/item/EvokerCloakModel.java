package io.github.zemelua.umu_arcanum.client.model.item;

import io.github.zemelua.umu_arcanum.util.ModelUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class EvokerCloakModel extends HumanoidModel<LivingEntity> {
	public EvokerCloakModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F);
		PartDefinition part = ModelUtils.createHumanoidAlias(mesh);

		part.getChild("body")
				.addOrReplaceChild("cloak", CubeListBuilder.create()
								.texOffs(0, 0)
								.addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.3F)),
						PartPose.offset(0.0F, 0.0F, 0.0F)
				);
		part.getChild("right_arm")
				.addOrReplaceChild("right_sleeve", CubeListBuilder.create()
								.texOffs(28, 0)
								.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)),
						PartPose.offset(0.0F, 0.0F, 0.0F)
				);
		part.getChild("left_arm")
				.addOrReplaceChild("left_sleeve", CubeListBuilder.create()
								.texOffs(28, 0).mirror()
								.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)),
						PartPose.offset(0.0F, 0.0F, 0.0F)
				);

		return LayerDefinition.create(mesh, 64, 32);
	}
}
