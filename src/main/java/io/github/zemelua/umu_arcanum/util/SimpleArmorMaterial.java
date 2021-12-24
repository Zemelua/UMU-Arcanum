package io.github.zemelua.umu_arcanum.util;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class SimpleArmorMaterial implements ArmorMaterial {
	private final int[] durability;
	private final int[] defences;
	private final int enchantmentValue;
	private final SoundEvent equipSound;
	private final Ingredient repairItem;
	private final String name;
	private final float toughness;
	private final float knockbackResistance;

	public SimpleArmorMaterial(int[] durability, int[] defences, int enchantmentValue, SoundEvent equipSound,
							   Ingredient repairItem, String name, float toughness, float knockbackResistance) {
		this.durability = durability;
		this.defences = defences;
		this.enchantmentValue = enchantmentValue;
		this.equipSound = equipSound;
		this.repairItem = repairItem;
		this.name = name;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot slot) {
		return this.durability[slot.getIndex()];
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slot) {
		return this.defences[slot.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairItem;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}

	public static class Builder {
		private int[] durability = new int[4];
		private int[] defences = new int[4];
		private int enchantmentValue = 0;
		private SoundEvent equipSound = SoundEvents.ARMOR_EQUIP_GENERIC;
		private Ingredient repairItem = Ingredient.EMPTY;
		private float toughness = 0.0F;
		private float knockbackResistance = 0.0F;

		@SuppressWarnings("unused")
		public Builder() {
		}

		public Builder(ArmorMaterial base) {
			this.durability = new int[]{
					base.getDurabilityForSlot(EquipmentSlot.FEET),
					base.getDurabilityForSlot(EquipmentSlot.LEGS),
					base.getDurabilityForSlot(EquipmentSlot.CHEST),
					base.getDurabilityForSlot(EquipmentSlot.HEAD)
			};
			this.defences = new int[]{
					base.getDefenseForSlot(EquipmentSlot.FEET),
					base.getDefenseForSlot(EquipmentSlot.LEGS),
					base.getDefenseForSlot(EquipmentSlot.CHEST),
					base.getDefenseForSlot(EquipmentSlot.HEAD)
			};
			this.enchantmentValue = base.getEnchantmentValue();
			this.equipSound = base.getEquipSound();
			this.repairItem = base.getRepairIngredient();
			this.toughness = base.getToughness();
			this.knockbackResistance = base.getKnockbackResistance();
		}

		public Builder durability(int feet, int legs, int chest, int head) {
			this.durability = new int[]{feet, legs, chest, head};

			return this;
		}

		public Builder defences(int feet, int legs, int chest, int head) {
			this.defences = new int[]{feet, legs, chest, head};

			return this;
		}

		public Builder enchantmentValue(int enchantmentValue) {
			this.enchantmentValue = enchantmentValue;

			return this;
		}

		public Builder equipSound(SoundEvent equipSound) {
			this.equipSound = equipSound;

			return this;
		}

		public Builder repairItem(ItemLike repairItem) {
			this.repairItem = Ingredient.of(repairItem);

			return this;
		}

		public Builder toughness(float toughness) {
			this.toughness = toughness;

			return this;
		}

		public Builder knockbackResistance(float knockbackResistance) {
			this.knockbackResistance = knockbackResistance;

			return this;
		}

		public ArmorMaterial build(String name) {
			return new SimpleArmorMaterial(this.durability, this.defences, this.enchantmentValue, this.equipSound,
					this.repairItem, UMUArcanum.resource(name).toString(), this.toughness, this.knockbackResistance);
		}
	}
}
