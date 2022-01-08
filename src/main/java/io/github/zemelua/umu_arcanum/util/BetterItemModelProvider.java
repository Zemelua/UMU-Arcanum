package io.github.zemelua.umu_arcanum.util;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class BetterItemModelProvider extends ItemModelProvider {
	public BetterItemModelProvider(DataGenerator generator, String modId, ExistingFileHelper files) {
		super(generator, modId, files);
	}

	protected ItemModelBuilder generated(String name) {
		return this.withExistingParent(name, "generated");
	}
}
