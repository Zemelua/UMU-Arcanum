package io.github.zemelua.umu_arcanum.replace.mixin;

import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.block.entity.PotionCauldronBlockEntity;
import io.github.zemelua.umu_arcanum.util.ColorEffectMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Collections;

@Mixin(CatSitOnBlockGoal.class)
public abstract class CatSitOnBlockGoalMixin extends MoveToBlockGoal {
	@Shadow
	@Final
	private Cat cat;

	@Deprecated
	public CatSitOnBlockGoalMixin(PathfinderMob mob, double speed, int range) {
		super(mob, speed, range);
	}

	@Inject(method = "tick",
			at = @At(value = "RETURN",
					ordinal = 0))
	private void tick(CallbackInfo callback) {
		if (this.cat.getCatType() == Cat.TYPE_ALL_BLACK && this.isReachedTarget()) {
			Level world = this.cat.getLevel();
			BlockPos pos = this.cat.blockPosition().below();
			BlockState blockState = world.getBlockState(pos);

			if (blockState.is(ModBlocks.MANA_CAULDRON.get()) && this.cat.getRandom().nextInt(60) > 58) {
				int level = blockState.getValue(LayeredCauldronBlock.LEVEL);
				world.setBlock(pos, ModBlocks.POTION_CAULDRON.get().withPropertiesOf(blockState), 3);
				@Nullable PotionCauldronBlockEntity blockEntity = (PotionCauldronBlockEntity) world.getBlockEntity(pos);

				if (blockEntity != null) {
					for (int i = 0; i < level; i++) {
						blockEntity.pourEffect(Collections.singleton(new MobEffectInstance(MobEffects.LUCK, 3600)));
					}
				}

				int color = PotionUtils.getColor(Collections.singleton(new MobEffectInstance(MobEffects.LUCK)));
				for (int i = 0; i < 6; i++) {
					ColorEffectMessage.send(this.cat.getRandomX(0.5D), this.cat.getRandomY(), this.cat.getRandomZ(0.5D), color);
				}
			}
		}
	}

	@Inject(method = "isValidTarget",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/world/level/LevelReader;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
					ordinal = 0),
			cancellable = true)
	@SuppressWarnings("SpellCheckingInspection")
	private void isValidTarget(LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
		BlockState blockState = world.getBlockState(pos);
		if (this.cat.getCatType() == Cat.TYPE_ALL_BLACK && blockState.is(ModBlocks.MANA_CAULDRON.get())) {
			callback.setReturnValue(true);
		}
	}
}
