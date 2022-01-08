package io.github.zemelua.umu_arcanum.replace.mixin;

import io.github.zemelua.umu_arcanum.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin extends Entity {
	@Deprecated
	public LightningBoltMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Inject(method = "tick()V",
			at = @At(value = "INVOKE",
					shift = At.Shift.AFTER,
					target = "Lnet/minecraft/world/entity/LightningBolt;gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;)V"))
	@SuppressWarnings("SpellCheckingInspection")
	private void tick(CallbackInfo callback) {
		LightningBoltMixin.createManaOnLightningStrike(this.getLevel(), this.blockPosition());
	}

	private static void createManaOnLightningStrike(Level level, BlockPos pos) {
		BlockState blockState = level.getBlockState(pos);
		BlockPos targetPos;
		BlockState targetState;
		if (blockState.is(Blocks.LIGHTNING_ROD)) {
			targetPos = pos.relative(blockState.getValue(LightningRodBlock.FACING).getOpposite());
			targetState = level.getBlockState(targetPos);
		} else {
			targetPos = pos;
			targetState = blockState;
		}

		if (targetState.is(Blocks.WATER_CAULDRON)) {
			level.setBlockAndUpdate(targetPos, ModBlocks.MANA_CAULDRON.get().withPropertiesOf(targetState));
			BlockPos.MutableBlockPos posMutable = pos.mutable();

			for(int i = 0; i < level.getRandom().nextInt(3) + 3; i++) {
				int random = level.getRandom().nextInt(8) + 1;
				randomWalkCreatingMana(level, targetPos, posMutable, random);
			}
		}
	}

	private static void randomWalkCreatingMana(Level level, BlockPos targetPos, MutableBlockPos centerPos, int random) {
		centerPos.set(targetPos);

		for(int i = 0; i < random; i++) {
			Optional<BlockPos> stepPos = randomStepCreatingMana(level, centerPos);
			if (stepPos.isEmpty()) {
				break;
			}

			centerPos.set(stepPos.get());
		}
	}

	private static Optional<BlockPos> randomStepCreatingMana(Level level, BlockPos centerPos) {
		for(BlockPos targetPos : BlockPos.randomInCube(level.random, 10, centerPos, 1)) {
			BlockState targetState = level.getBlockState(targetPos);
			if (targetState.is(Blocks.WATER_CAULDRON)) {
				level.setBlockAndUpdate(targetPos, ModBlocks.MANA_CAULDRON.get().withPropertiesOf(targetState));

				return Optional.of(targetPos);
			}
		}

		return Optional.empty();
	}
}
