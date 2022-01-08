package io.github.zemelua.umu_arcanum.datagen.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.recipe.ModRecipeSerializers;
import io.github.zemelua.umu_arcanum.util.Soup;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AlchemyRecipeBuilder implements RecipeBuilder {
	private final Item result;
	private final List<Ingredient> ingredients = new ArrayList<>();
	private final List<Soup> soups = new ArrayList<>();
	private int count = 1;
	private String group = "";
	private final Advancement.Builder advancement = Advancement.Builder.advancement();

	public AlchemyRecipeBuilder(Item result) {
		this.result = result;
	}

	public AlchemyRecipeBuilder ingredient(ItemLike... item) {
		this.ingredients.add(Ingredient.of(item));

		return this;
	}

	public AlchemyRecipeBuilder soup(MobEffect... effect) {
		this.soups.add(Soup.of(effect));

		return this;
	}

	public AlchemyRecipeBuilder count(int count) {
		this.count = count;

		return this;
	}

	@Override
	public AlchemyRecipeBuilder group(@Nullable String group) {
		if (group != null) {
			this.group = group;
		}

		return this;
	}

	@Override
	public AlchemyRecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) {
		this.advancement.addCriterion(name, trigger);

		return this;
	}

	@Override
	public void save(Consumer<FinishedRecipe> saver, ResourceLocation id) {
		this.advancement.parent(UMUArcanum.resource("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
				.rewards(AdvancementRewards.Builder.recipe(id))
				.requirements(RequirementsStrategy.OR);


		CreativeModeTab category = this.result.getItemCategory();
		saver.accept(new AlchemyRecipeBuilder.Result(
				id, this.result, this.count, this.ingredients, this.soups, this.group, this.advancement,
				UMUArcanum.resource("recipes/" + (category != null ? category.getRecipeFolderName() + "/" : "") + id.getPath())));
	}

	@Override
	public Item getResult() {
		return this.result;
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final Item result;
		private final int count;
		private final List<Ingredient> ingredients;
		private final List<Soup> soups;
		private final String group;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;

		public Result(ResourceLocation id, Item result, int count, List<Ingredient> ingredients, List<Soup> soups, String group, Advancement.Builder advancement, ResourceLocation advancementId) {
			this.id = id;
			this.result = result;
			this.count = count;
			this.ingredients = ingredients;
			this.soups = soups;
			this.group = group;
			this.advancement = advancement;
			this.advancementId = advancementId;
		}

		@Override
		public void serializeRecipeData(JsonObject jsonObject) {
			if (!this.group.isEmpty()) {
				jsonObject.addProperty("group", this.group);
			}

			JsonArray jsonIngredients = new JsonArray();
			for(Ingredient ingredient : this.ingredients) {
				jsonIngredients.add(ingredient.toJson());
			}
			jsonObject.add("ingredients", jsonIngredients);

			JsonArray jsonSoups = new JsonArray();
			for(Soup soup : this.soups) {
				jsonSoups.add(soup.toJson());
			}
			jsonObject.add("soups", jsonSoups);

			JsonObject resultJson = new JsonObject();
			ResourceLocation key = ForgeRegistries.ITEMS.getKey(this.result);
			resultJson.addProperty("item", key != null ? key.toString() : Items.AIR.toString());
			if (this.count > 1) {
				resultJson.addProperty("count", this.count);
			}

			jsonObject.add("result", resultJson);
		}

		@Override
		public ResourceLocation getId() {
			return this.id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return ModRecipeSerializers.ALCHEMY.get();
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return this.advancement.serializeToJson();
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return this.advancementId;
		}
	}
}
