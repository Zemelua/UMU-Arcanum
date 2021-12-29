package io.github.zemelua.umu_arcanum.recipe.alchemy;

import io.github.zemelua.umu_arcanum.block.entity.PotionCauldronBlockEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;

import java.util.Collection;

public class TippedArrowRecipe implements IAlchemyRecipe {
	@Override
	public boolean matches(Potion root, Collection<MobEffectInstance> solution, Collection<ItemStack> ingredients) {
		return ingredients.size() < 2 && ingredients.stream().allMatch(itemStack -> itemStack.is(Items.ARROW));
	}

	@Override
	public ItemStack getResult(Potion root, Collection<MobEffectInstance> solution, Collection<ItemStack> ingredients, PotionCauldronBlockEntity blockEntity) {
		int count = Math.min(ingredients.stream().mapToInt(ItemStack::getCount).sum(), 64);
		ItemStack result = new ItemStack(Items.TIPPED_ARROW, count);
		PotionUtils.setPotion(result, root);
		PotionUtils.setCustomEffects(result, blockEntity.scoop());
		Level level = blockEntity.getLevel();
		if (level != null) {
			LayeredCauldronBlock.lowerFillLevel(blockEntity.getBlockState(), level, blockEntity.getBlockPos());
		}

		return result;
	}
}
