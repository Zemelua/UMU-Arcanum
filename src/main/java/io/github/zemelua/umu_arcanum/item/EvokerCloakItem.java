package io.github.zemelua.umu_arcanum.item;

import io.github.zemelua.umu_arcanum.client.model.ModModelLayers;
import io.github.zemelua.umu_arcanum.client.model.item.EvokerCloakModel;
import io.github.zemelua.umu_arcanum.item.material.ModArmorMaterials;
import io.github.zemelua.umu_arcanum.util.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.function.Consumer;

public class EvokerCloakItem extends ArmorItem {
	public EvokerCloakItem(Properties properties) {
		super(ModArmorMaterials.EVOKER, EquipmentSlot.CHEST, properties);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			@SuppressWarnings("unchecked")
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
				return (A) new EvokerCloakModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.EVOKER_CLOAK));
			}
		});
	}

	protected static void onLivingHurt(final LivingDamageEvent event) {
		DamageSource damageSource = event.getSource();
		Entity sourceEntity = damageSource.getEntity();
		LivingEntity living = event.getEntityLiving();
		ItemStack chestStack = living.getItemBySlot(EquipmentSlot.CHEST);

		if (!living.getLevel().isClientSide() && chestStack.is(ModItems.EVOKER_CLOAK.get()) && sourceEntity != null) {
			double yStartPos = Math.min(sourceEntity.getY(), living.getY());
			double yEndPos = Math.max(sourceEntity.getY(), living.getY()) + 1.0D;

			float radian = (float) Mth.atan2(sourceEntity.getZ() - living.getZ(), sourceEntity.getX() - living.getX());

			if (damageSource.isProjectile()) {
				for (int i = 0; i < 16; ++i) {
					double distance = 1.25D * (double) (i + 1);
					WorldUtils.summonEvokerFang(living, living.getX() + (double) Mth.cos(radian) * distance, yStartPos, yEndPos, living.getZ() + (double) Mth.sin(radian) * distance, radian, i);
				}
			} else {
				for (int i = 0; i < 5; ++i) {
					float distance = radian + (float) i * (float) Math.PI * 0.4F;
					WorldUtils.summonEvokerFang(living, living.getX() + (double) Mth.cos(distance) * 1.5D, yStartPos, yEndPos, living.getZ() + (double) Mth.sin(distance) * 1.5D, distance, 0);
				}

				for (int i = 0; i < 8; ++i) {
					float distance = radian + (float) i * (float) Math.PI * 2.0F / 8.0F + 1.2566371F;
					WorldUtils.summonEvokerFang(living, living.getX() + (double) Mth.cos(distance) * 2.5D, yStartPos, yEndPos, living.getZ() + (double) Mth.sin(distance) * 2.5D, distance, 3);
				}
			}
		}
	}
}
