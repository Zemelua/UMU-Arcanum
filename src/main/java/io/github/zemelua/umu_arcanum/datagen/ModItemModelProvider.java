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
		this.handheld("wand")
				.texture("layer0", "item/wand");
		this.generated("arcane_gold_ingot")
				.texture("layer0", "item/arcane_gold_ingot");
		this.generated("mandrake")
				.texture("layer0", "item/mandrake");
		this.generated("bat_wing")
				.texture("layer0", "item/bat_wing");
		this.generated("mana_bottle")
				.texture("layer0", "item/mana_bottle");
		this.generated("mana_bucket")
				.texture("layer0", "item/mana_bucket");
		this.generated("witch_hat")
				.texture("layer0", "item/witch_hat");
		this.generated("evoker_cloak")
				.texture("layer0", "item/evoker_cloak");
		this.generated("soul_ring")
				.texture("layer0", "item/soul_ring");
	}
}
