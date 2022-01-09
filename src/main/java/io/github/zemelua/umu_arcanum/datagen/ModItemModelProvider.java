package io.github.zemelua.umu_arcanum.datagen;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.util.BetterItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends BetterItemModelProvider {
	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper files) {
		super(generator, UMUArcanum.MOD_ID, files);
	}

	@Override
	protected void registerModels() {
		this.generated("arcane_gold_ingot")
				.texture("layer0", "item/arcane_gold_ingot");
		this.generated("mana_bottle")
				.texture("layer0", "item/mana_bottle");
		this.generated("mana_bucket")
				.texture("layer0", "item/mana_bucket");
		this.generated("witch_hat")
				.texture("layer0", "item/witch_hat");
	}
}