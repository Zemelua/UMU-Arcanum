package io.github.zemelua.umu_arcanum.datagen.language;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnglishLanguageProvider extends LanguageProvider {
	public ModEnglishLanguageProvider(DataGenerator generator) {
		super(generator, UMUArcanum.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		this.addBlock(ModBlocks.MANA, "Mana");
		this.addBlock(ModBlocks.MANA_CAULDRON, "Mana Cauldron");
		this.addBlock(ModBlocks.POTION_CAULDRON, "Potion Cauldron");
		this.addItem(ModItems.ARCANE_GOLD_INGOT, "Arcane Gold Ingot");
		this.addItem(ModItems.MANA_BOTTLE, "Mana Bottle");
		this.addItem(ModItems.MANA_BUCKET, "Mana Bucket");
		this.addItem(ModItems.WITCH_HAT, "Witch Hat");
	}
}
