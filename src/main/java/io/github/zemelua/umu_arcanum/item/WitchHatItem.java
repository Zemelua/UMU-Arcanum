package io.github.zemelua.umu_arcanum.item;

import io.github.zemelua.umu_arcanum.client.model.ModModelLayers;
import io.github.zemelua.umu_arcanum.client.model.item.WitchHatModel;
import io.github.zemelua.umu_arcanum.item.material.ModArmorMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.function.Consumer;

public class WitchHatItem extends ArmorItem {
	public WitchHatItem(Properties properties) {
		super(ModArmorMaterials.WITCH, EquipmentSlot.HEAD, properties);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			@SuppressWarnings("unchecked")
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
				return (A) new WitchHatModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.WITCH_HAT));
			}
		});
	}

	protected static void onLivingHurt(final LivingDamageEvent event) {
		DamageSource damageSource = event.getSource();
		LivingEntity living = event.getEntityLiving();

		if (living.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.WITCH_HAT.get()) && damageSource.isMagic()) {
			if (damageSource.getEntity() == living) {
				event.setAmount(0.0F);
			} else {
				event.setAmount(event.getAmount() * 0.15F);
			}
		}
	}
}
