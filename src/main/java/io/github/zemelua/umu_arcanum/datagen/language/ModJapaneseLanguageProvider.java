package io.github.zemelua.umu_arcanum.datagen.language;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.effect.ModEffects;
import io.github.zemelua.umu_arcanum.effect.ModPotions;
import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.data.LanguageProvider;

public class ModJapaneseLanguageProvider extends LanguageProvider {
	public ModJapaneseLanguageProvider(DataGenerator generator) {
		super(generator, UMUArcanum.MOD_ID, "ja_jp");
	}

	@Override
	protected void addTranslations() {
		this.addBlock(ModBlocks.MANA, "マナ");
		this.addBlock(ModBlocks.MANA_CAULDRON, "マナ入りの大釜");
		this.addBlock(ModBlocks.POTION_CAULDRON, "ポーション入りの大釜");
		this.addItem(ModItems.ARCANE_GOLD_INGOT, "神秘の金インゴット");
		this.addItem(ModItems.MANA_BOTTLE, "マナ入り瓶");
		this.addItem(ModItems.MANA_BUCKET, "マナ入りバケツ");
		this.addItem(ModItems.WITCH_HAT, "魔女の帽子");
		this.addEffect(ModEffects.BLESSING, "加護");
		this.add(PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.BLESSING.get()), "加護のポーション");
		this.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.BLESSING.get()), "加護のスプラッシュポーション");
		this.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), ModPotions.BLESSING.get()), "加護の残留ポーション");
		this.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), ModPotions.BLESSING.get()), "加護の矢");
	}
}
