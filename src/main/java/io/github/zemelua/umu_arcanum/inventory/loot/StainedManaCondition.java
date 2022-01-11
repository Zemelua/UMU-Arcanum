package io.github.zemelua.umu_arcanum.inventory.loot;

import io.github.zemelua.umu_arcanum.fluid.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

public class StainedManaCondition implements LootItemCondition {
	@Override
	public LootItemConditionType getType() {
		return LootItemConditions.BLOCK_STATE_PROPERTY;
	}

	@Override
	public boolean test(LootContext lootContext) {
		BlockPos pos = new BlockPos(lootContext.getParam(LootContextParams.ORIGIN));
		Level world = lootContext.getLevel();

		for(BlockPos aroundPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
			if (world.getFluidState(aroundPos).is(ModFluids.MANA.get())) {
				return true;
			}
		}

		return false;
	}
}
