package io.github.zemelua.umu_arcanum.replace;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ReplaceJapaneseLanguageProvider extends LanguageProvider {
	public ReplaceJapaneseLanguageProvider(DataGenerator generator) {
		super(generator, "minecraft", "ja_jp");
	}

	@Override
	protected void addTranslations() {
		this.add("item.minecraft.potion.effect.empty", "未知のポーション");
		this.add("item.minecraft.tipped_arrow.effect.empty", "未知の矢");
	}
}
