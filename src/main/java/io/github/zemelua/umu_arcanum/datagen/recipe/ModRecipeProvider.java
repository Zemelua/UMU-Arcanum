package io.github.zemelua.umu_arcanum.datagen.recipe;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.item.ModItems;
import io.github.zemelua.umu_arcanum.recipe.ModRecipeSerializers;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.tags.ItemTags;
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
		ShapedRecipeBuilder.shaped(ModItems.WAND.get())
				.pattern("#")
				.pattern("X")
				.pattern("Y")
				.define('#', Items.AMETHYST_SHARD)
				.define('X', ItemTags.LOGS)
				.define('Y', Items.STICK)
				.unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
				.save(saver);
		new AlchemyRecipeBuilder(Items.GOLD_INGOT)
				.ingredient(Items.IRON_INGOT)
				.ingredient(Items.BLAZE_POWDER)
				.ingredient(Items.GLOWSTONE_DUST)
				.soup(MobEffects.DAMAGE_BOOST)
				.soup(MobEffects.WEAKNESS)
				.soup(MobEffects.HEAL)
				.save(saver, UMUArcanum.resource("alchemy_gold_ingot"));
		new AlchemyRecipeBuilder(Items.DIAMOND)
				.ingredient(Items.CLAY_BALL)
				.ingredient(Items.BLAZE_POWDER)
				.ingredient(Items.GOLD_INGOT)
				.ingredient(Items.AMETHYST_SHARD)
				.soup(MobEffects.DAMAGE_BOOST)
				.soup(MobEffects.WEAKNESS)
				.soup(MobEffects.HEAL)
				.save(saver, UMUArcanum.resource("alchemy_diamond"));
		new AlchemyRecipeBuilder(Items.NETHERITE_INGOT)
				.ingredient(Items.GOLD_INGOT)
				.ingredient(Items.BLACKSTONE)
				.ingredient(Items.CALCITE)
				.ingredient(Items.OBSIDIAN)
				.soup(MobEffects.DAMAGE_BOOST)
				.soup(MobEffects.WEAKNESS)
				.soup(MobEffects.HEAL)
				.save(saver, UMUArcanum.resource("alchemy_netherite_ingot"));
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
