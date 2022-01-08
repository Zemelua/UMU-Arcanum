package io.github.zemelua.umu_arcanum.recipe;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.recipe.alchemy.IAlchemyRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;

public final class ModRecipeTypes {
	public static final RecipeType<IAlchemyRecipe> ALCHEMY;

	private ModRecipeTypes() {}

	static {
		ALCHEMY = Registry.register(Registry.RECIPE_TYPE, UMUArcanum.resource("alchemy"), new RecipeType<IAlchemyRecipe>() {});
	}
}
