package io.github.zemelua.umu_arcanum.block;

import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MandrakeBlock extends CropBlock {
	public MandrakeBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ModItems.MANDRAKE.get();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(this.getAgeProperty())) {
			case 0 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
			case 1 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
			case 2 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
			case 3 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
			case 4 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D);
			case 5 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
			case 6 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
			case 7 -> Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
			default -> Shapes.block();
		};
	}
}
