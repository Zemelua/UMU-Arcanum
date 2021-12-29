package io.github.zemelua.umu_arcanum.recipe.alchemy;

import io.github.zemelua.umu_arcanum.block.entity.PotionCauldronBlockEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

import java.util.Collection;
import java.util.List;

public final class AlchemyRecipes {
	private static final List<IAlchemyRecipe> RECIPES;

	private AlchemyRecipes() {
	}

	public static ItemStack tryMatch(Potion root, Collection<MobEffectInstance> solution, Collection<ItemStack> ingredients, PotionCauldronBlockEntity blockEntity) {
		for (IAlchemyRecipe recipe : AlchemyRecipes.RECIPES) {
			if (recipe.matches(root, solution, ingredients)) {
				return recipe.getResult(root, solution, ingredients, blockEntity);
			}
		}

		return ItemStack.EMPTY;
	}

	static {
		RECIPES = List.of(
				new AlchemyRecipe.Builder()
						.setRoot(Potions.HEALING)
						.addSolution(MobEffects.REGENERATION)
						.addIngredient(new ItemStack(Items.APPLE))
						.addIngredient(new ItemStack(Items.GOLD_INGOT, 4))
						.build(new ItemStack(Items.GOLDEN_APPLE)),
				new TippedArrowRecipe()
		);
	}
}
