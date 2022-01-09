package io.github.zemelua.umu_arcanum.replace;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.data.LanguageProvider;

public class ReplaceEnglishLanguageProvider extends LanguageProvider {
	public ReplaceEnglishLanguageProvider(DataGenerator generator) {
		super(generator, "minecraft", "en_us");
	}

	@Override
	protected void addTranslations() {
		this.add(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.EMPTY), "Unknown Potion");
		this.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), Potions.EMPTY), "Arrow of Unknown");
	}
}
