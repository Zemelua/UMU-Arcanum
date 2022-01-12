package io.github.zemelua.umu_arcanum.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class WorldUtils {
	private WorldUtils() {
	}

	public static void summonEvokerFang(LivingEntity owner, double xPos, double yStartPos, double yEndPos, double zPos, float rotate, int warmUpDelay) {
		Level world = owner.getLevel();
		BlockPos basePos = new BlockPos(xPos, yEndPos, zPos);
		boolean canSummon = false;
		double yOffsetPos = 0.0D;

		do {
			BlockPos belowPos = basePos.below();
			BlockState belowState = world.getBlockState(belowPos);

			if (belowState.isFaceSturdy(world, belowPos, Direction.UP)) {
				if (!world.isEmptyBlock(basePos)) {
					BlockState summonState = world.getBlockState(basePos);
					VoxelShape collision = summonState.getCollisionShape(world, basePos);
					if (!collision.isEmpty()) {
						yOffsetPos = collision.max(Direction.Axis.Y);
					}
				}

				canSummon = true;

				break;
			}

			basePos = basePos.below();
		} while (basePos.getY() >= Mth.floor(yStartPos) - 1);

		if (canSummon) {
			world.addFreshEntity(new EvokerFangs(world, xPos, basePos.getY() + yOffsetPos, zPos, rotate, warmUpDelay, owner));
		}
	}
}
