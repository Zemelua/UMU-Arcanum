package io.github.zemelua.umu_arcanum.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;

public class BlessingEffect extends MobEffect {
	public BlessingEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	protected static void onPotionApplicable(final PotionEvent.PotionApplicableEvent event) {
		if (event.getEntityLiving().hasEffect(ModEffects.BLESSING.get())
				&& event.getPotionEffect().getEffect().getCategory() == MobEffectCategory.HARMFUL) {
			event.setResult(Event.Result.DENY);
		}
	}
}
