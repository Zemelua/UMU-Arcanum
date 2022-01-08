package io.github.zemelua.umu_arcanum.replace.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Arrow.class)
public abstract class ArrowMixin extends AbstractArrow {
	@Shadow @Final private Set<MobEffectInstance> effects;

	@Deprecated
	private ArrowMixin(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	@Inject(method = "doPostHurtEffects(Lnet/minecraft/world/entity/LivingEntity;)V",
			at = @At(value = "INVOKE",
					shift = At.Shift.BEFORE,
					target = "Ljava/util/Set;isEmpty()Z",
					ordinal = 0),
			cancellable = true)
	@SuppressWarnings("SpellCheckingInspection")
	private void doPostHurtEffects(LivingEntity living, CallbackInfo callback) {
		if (!this.effects.isEmpty()) {
			for (MobEffectInstance effectInstance : this.effects) {
				living.addEffect(new MobEffectInstance(
						effectInstance.getEffect(),
						Math.max(effectInstance.getDuration() / 8, 1),
						effectInstance.getAmplifier(),
						effectInstance.isAmbient(),
						effectInstance.isVisible()
				), this.getEffectSource());

				callback.cancel();
			}
		}
	}
}
