package io.github.zemelua.umu_arcanum.datagen.recipe;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.recipe.ModRecipeSerializers;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
	public ModRecipeProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> saver) {
		new AlchemyRecipeBuilder(Items.GOLDEN_APPLE)
				.ingredient(Items.APPLE)
				.ingredient(Items.GOLD_INGOT)
				.soup(MobEffects.REGENERATION)
				.save(saver, UMUArcanum.resource("alchemy_golden_apple"));
		SpecialRecipeBuilder.special((SimpleRecipeSerializer<?>) ModRecipeSerializers.TIPPED_ALLOW.get())
				.save(saver, UMUArcanum.resource("alchemy_tipped_arrow").toString());
		SpecialRecipeBuilder.special((SimpleRecipeSerializer<?>) ModRecipeSerializers.TOTEM_OF_UNDYING.get())
				.save(saver, UMUArcanum.resource("alchemy_totem_of_undying").toString());
	}
}
