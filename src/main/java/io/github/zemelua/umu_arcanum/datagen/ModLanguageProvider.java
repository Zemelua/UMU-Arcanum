package io.github.zemelua.umu_arcanum.datagen;

import io.github.zemelua.umu_arcanum.item.ModItems;
import io.github.zemelua.umu_arcanum.util.LanguageProviderWrapper;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Collection;
import java.util.List;

public class ModLanguageProvider extends LanguageProviderWrapper {
	public ModLanguageProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected Collection<LanguageProvider> generateTranslations() {
		return List.of(
				new LanguageProviderWrapper.LanguageBuilder("en_us")
						.add(ModItems.MANA_BOTTLE.get(), "Mana Bottle")
						.add(ModItems.MANA_BUCKET.get(), "Mana Bucket")
						.add(ModItems.WITCH_HAT.get(), "Witch Hat")
						.build()
		);
	}
}
