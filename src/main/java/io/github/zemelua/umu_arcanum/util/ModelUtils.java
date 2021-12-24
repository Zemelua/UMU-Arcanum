package io.github.zemelua.umu_arcanum.util;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class ModelUtils {
	private ModelUtils() {
	}

	public static PartDefinition createHumanoidAlias(MeshDefinition mesh) {
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
		root.addOrReplaceChild("hat", new CubeListBuilder(), PartPose.ZERO);
		root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
		root.addOrReplaceChild("right_arm", new CubeListBuilder(), PartPose.ZERO);
		root.addOrReplaceChild("left_arm", new CubeListBuilder(), PartPose.ZERO);
		root.addOrReplaceChild("right_leg", new CubeListBuilder(), PartPose.ZERO);
		root.addOrReplaceChild("left_leg", new CubeListBuilder(), PartPose.ZERO);

		return root;
	}
}
