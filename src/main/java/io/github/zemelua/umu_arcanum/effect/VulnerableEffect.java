package io.github.zemelua.umu_arcanum.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Objects;

public class VulnerableEffect extends MobEffect {
	public VulnerableEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	protected static void onLivingHurt(final LivingHurtEvent event) {
		LivingEntity living = event.getEntityLiving();

		if (living.hasEffect(ModEffects.VULNERABLE.get())) {
			int level = Objects.requireNonNull(living.getEffect(ModEffects.VULNERABLE.get())).getAmplifier() + 1;

			event.setAmount(event.getAmount() + event.getAmount() * level / 5);
		}
	}
}
