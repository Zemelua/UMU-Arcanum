package io.github.zemelua.umu_arcanum.item.material;

import io.github.zemelua.umu_arcanum.util.SimpleArmorMaterial;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;

public final class ModArmorMaterials {
	public static final ArmorMaterial WITCH;
	public static final ArmorMaterial EVOKER;

	static {
		WITCH = new SimpleArmorMaterial.Builder(ArmorMaterials.LEATHER)
				.durability(156, 180, 192, 132)
				.defences(2, 5, 6, 2)
				.enchantmentValue(12)
				.equipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
				.repairItem(Items.LEATHER)
				.toughness(0.5F)
				.knockbackResistance(0.0F)
				.build("witch");
		EVOKER = new SimpleArmorMaterial.Builder(ArmorMaterials.LEATHER)
				.durability(156, 180, 192, 132)
				.defences(2, 5, 6, 2)
				.enchantmentValue(9)
				.equipSound(SoundEvents.ARMOR_EQUIP_LEATHER)
				.repairItem(Items.LEATHER)
				.toughness(0.0F)
				.knockbackResistance(0.0F)
				.build("evoker");
	}
}
