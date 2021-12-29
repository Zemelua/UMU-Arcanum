package io.github.zemelua.umu_arcanum.util;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public interface IDrinkableItem {
	int DRINK_DURATION = 32;

	Item getSelf();

	default void onDrunk(ItemStack itemStack, Level level, LivingEntity living) {
	}

	default InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	default ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity living, ItemStack container) {
		if (!level.isClientSide()) this.onDrunk(itemStack, level, living);

		if (living instanceof Player player) {
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}

			if (player instanceof ServerPlayer playerServer) {
				CriteriaTriggers.CONSUME_ITEM.trigger(playerServer, itemStack);
				playerServer.awardStat(Stats.ITEM_USED.get(this.getSelf()));
			}
		}

		return itemStack.isEmpty() ? container : itemStack;
	}

	default int getUseDuration(ItemStack itemStack) {
		return IDrinkableItem.DRINK_DURATION;
	}

	default UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.DRINK;
	}
}
