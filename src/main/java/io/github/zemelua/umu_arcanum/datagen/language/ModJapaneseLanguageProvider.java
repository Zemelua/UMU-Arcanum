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
		this.addBlock(ModBlocks.MANDRAKE, "マンドレイク");
		this.addBlock(ModBlocks.MANA, "マナ");
		this.addBlock(ModBlocks.MANA_CAULDRON, "マナ入りの大釜");
		this.addBlock(ModBlocks.POTION_CAULDRON, "ポーション入りの大釜");
		this.addItem(ModItems.MANDRAKE, "マンドレイク");
		this.addItem(ModItems.BAT_WING, "コウモリの翼");
		this.addItem(ModItems.ARCANE_GOLD_INGOT, "神秘の金インゴット");
		this.addItem(ModItems.MANA_BOTTLE, "マナ入り瓶");
		this.addItem(ModItems.MANA_BUCKET, "マナ入りバケツ");
		this.addItem(ModItems.WITCH_HAT, "ウィッチの帽子");
		this.addItem(ModItems.EVOKER_CLOAK, "エヴォーカーの外套");
		this.addEffect(ModEffects.BLESSING, "加護");
		this.addEffect(ModEffects.VULNERABLE, "脆弱");
		this.add(PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.BLESSING.get()), "加護のポーション");
		this.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.BLESSING.get()), "加護のスプラッシュポーション");
		this.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), ModPotions.BLESSING.get()), "加護の残留ポーション");
		this.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), ModPotions.BLESSING.get()), "加護の矢");
		this.add(PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.MANDRAKE_SCREAM.get()), "マンドレイクの叫びのポーション");
		this.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.MANDRAKE_SCREAM.get()), "マンドレイクの叫びのスプラッシュポーション");
		this.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), ModPotions.MANDRAKE_SCREAM.get()), "マンドレイクの叫びの残留ポーション");
		this.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), ModPotions.MANDRAKE_SCREAM.get()), "マンドレイクの叫びの矢");
	}
}
