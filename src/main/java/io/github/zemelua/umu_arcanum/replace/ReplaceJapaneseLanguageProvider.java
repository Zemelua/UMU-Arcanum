package io.github.zemelua.umu_arcanum.replace;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.data.LanguageProvider;

public class ReplaceJapaneseLanguageProvider extends LanguageProvider {
	public ReplaceJapaneseLanguageProvider(DataGenerator generator) {
		super(generator, "minecraft", "ja_jp");
	}

	@Override
	protected void addTranslations() {
		this.add(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.EMPTY), "未知のポーション");
		this.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), Potions.EMPTY), "未知の矢");
	}
}
