package io.github.zemelua.umu_arcanum.item;

import io.github.zemelua.umu_arcanum.capability.IRingLevelManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import javax.annotation.Nullable;
import java.util.List;

public class SoulRingItem extends Item {
	public SoulRingItem(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return IRingLevelManager.createProvider();
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
		IRingLevelManager ringLevelManager = IRingLevelManager.get(stack);
		components.add(new TextComponent("Level: " + ringLevelManager.getLevel()));
	}

	protected static void onLivingHurt(final LivingHurtEvent event) {
		LivingEntity target = event.getEntityLiving();
		DamageSource source = event.getSource();
		Entity sourceEntity = source.getEntity();
		float amount = event.getAmount();

		if (sourceEntity == null) return;
		if (sourceEntity.getLevel().isClientSide()) return;

		if (sourceEntity instanceof LivingEntity sourceLiving) {
			ItemStack stack = ItemStack.EMPTY;

			for (InteractionHand hand : InteractionHand.values()) {
				ItemStack heldStack = sourceLiving.getItemInHand(hand);

				if (heldStack.is(ModItems.SOUL_RING.get())) {
					stack = heldStack;

					break;
				}
			}

			if (!stack.isEmpty()) {
				IRingLevelManager ringLevelManager = IRingLevelManager.get(stack);

				switch (ringLevelManager.getLevel()) {
					case 0 -> event.setAmount(amount + 1);
					case 1 -> event.setAmount(amount + 2);
					case 2 -> {
						event.setAmount(amount + 2);
						target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60));
					}
					case 3 -> {
						event.setAmount(amount + 3);
						target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3));
					}
					case 4 -> {
						event.setAmount(amount + 5);
						target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 3));
						target.addEffect(new MobEffectInstance(MobEffects.WITHER, 60));
					}
				}
			}
		}
	}

	protected static void onLivingDeath(final LivingDeathEvent event) {
		DamageSource source = event.getSource();
		Entity sourceEntity = source.getEntity();

		if (sourceEntity instanceof LivingEntity sourceLiving) {
			for (InteractionHand hand : InteractionHand.values()) {
				ItemStack stack = sourceLiving.getItemInHand(hand);

				if (stack.is(ModItems.SOUL_RING.get())) {
					IRingLevelManager ringLevelManager = IRingLevelManager.get(stack);
					ringLevelManager.addExperience(1);
				}
			}
		}
	}
}
