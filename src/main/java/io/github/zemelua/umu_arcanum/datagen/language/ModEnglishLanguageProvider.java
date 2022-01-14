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

public class ModEnglishLanguageProvider extends LanguageProvider {
	public ModEnglishLanguageProvider(DataGenerator generator) {
		super(generator, UMUArcanum.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		this.addBlock(ModBlocks.MANDRAKE, "Mandrake");
		this.addBlock(ModBlocks.MANA, "Mana");
		this.addBlock(ModBlocks.MANA_CAULDRON, "Mana Cauldron");
		this.addBlock(ModBlocks.POTION_CAULDRON, "Potion Cauldron");
		this.addItem(ModItems.ARCANE_GOLD_INGOT, "Arcane Gold Ingot");
		this.addItem(ModItems.MANDRAKE, "Mandrake");
		this.addItem(ModItems.BAT_WING, "Bat Wing");
		this.addItem(ModItems.MANA_BOTTLE, "Mana Bottle");
		this.addItem(ModItems.MANA_BUCKET, "Mana Bucket");
		this.addItem(ModItems.WITCH_HAT, "Witch Hat");
		this.addItem(ModItems.EVOKER_CLOAK, "Evoker cloak");
		this.addEffect(ModEffects.BLESSING, "Blessing");
		this.addEffect(ModEffects.VULNERABLE, "Vulnerable");
		this.add(PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.BLESSING.get()), "Blessing Potion");
		this.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.BLESSING.get()), "Blessing Splash Potion");
		this.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), ModPotions.BLESSING.get()), "Blessing Lingering Potion");
		this.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), ModPotions.BLESSING.get()), "Blessing Arrow");
		this.add(PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.MANDRAKE_SCREAM.get()), "Mandrake Scream Potion");
		this.add(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.MANDRAKE_SCREAM.get()), "Mandrake Scream Splash Potion");
		this.add(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), ModPotions.MANDRAKE_SCREAM.get()), "Mandrake Scream Lingering Potion");
		this.add(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), ModPotions.MANDRAKE_SCREAM.get()), "Mandrake Scream Arrow");
	}
}
