package io.github.zemelua.umu_arcanum.replace;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ReplaceEnglishLanguageProvider extends LanguageProvider {
	public ReplaceEnglishLanguageProvider(DataGenerator generator) {
		super(generator, "minecraft", "en_us");
	}

	@Override
	protected void addTranslations() {
		this.add("item.minecraft.potion.effect.empty", "Unknown Potion");
		this.add("item.minecraft.tipped_arrow.effect.empty", "Arrow of Unknown");
	}
}
