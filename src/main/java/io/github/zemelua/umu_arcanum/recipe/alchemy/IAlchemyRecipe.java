package io.github.zemelua.umu_arcanum.recipe.alchemy;

import io.github.zemelua.umu_arcanum.inventory.AlchemyContainer;
import io.github.zemelua.umu_arcanum.recipe.ModRecipeTypes;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface IAlchemyRecipe extends Recipe<AlchemyContainer> {
	@Override
	default RecipeType<?> getType() {
		return ModRecipeTypes.ALCHEMY;
	};
}
