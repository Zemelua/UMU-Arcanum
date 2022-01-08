package io.github.zemelua.umu_arcanum.recipe.alchemy;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_arcanum.inventory.AlchemyContainer;
import io.github.zemelua.umu_arcanum.recipe.ModRecipeSerializers;
import io.github.zemelua.umu_arcanum.util.RecipeUtils;
import io.github.zemelua.umu_arcanum.util.Soup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class AlchemyRecipe implements IAlchemyRecipe {
	private final ResourceLocation id;
	private final List<Ingredient> ingredients;
	private final List<Soup> soups;
	private final ItemStack result;

	public AlchemyRecipe(ResourceLocation id, Collection<Ingredient> ingredients, Collection<Soup> soups, ItemStack result) {
		this.id = id;
		this.ingredients = List.copyOf(ingredients);
		this.soups = List.copyOf(soups);
		this.result = result;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> nonNullList = NonNullList.create();

		for (int i = 0; i < this.ingredients.size(); i++) {
			nonNullList.add(i, this.ingredients.get(i));
		}

		return nonNullList;
	}

	public List<Soup> getSoups() {
		return this.soups;
	}

	@Override
	public boolean matches(AlchemyContainer container, Level level) {
		return RecipeMatcher.findMatches(container.getItemStacks(), this.ingredients) != null
				&& RecipeMatcher.findMatches(container.getEffectInstances(), this.soups) != null;
	}

	@Override
	public ItemStack assemble(AlchemyContainer container) {
		return this.result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return this.result;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.ALCHEMY.get();
	}

	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AlchemyRecipe> {
		@Override
		public AlchemyRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
			List<Ingredient> ingredients = RecipeUtils.ingredientsFromJson(jsonObject.getAsJsonArray("ingredients"));
			List<Soup> soups = RecipeUtils.soupsFromJson(jsonObject.getAsJsonArray("soups"));
			ItemStack result = ShapedRecipe.itemStackFromJson(jsonObject.getAsJsonObject("result"));

			return new AlchemyRecipe(id, ingredients, soups, result);
		}

		@Nullable
		@Override
		public AlchemyRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
			List<Ingredient> ingredients = RecipeUtils.ingredientsFromBuffer(buffer.readVarInt(), buffer);
			List<Soup> soups = RecipeUtils.soupsFromBuffer(buffer.readVarInt(), buffer);
			ItemStack result = buffer.readItem();

			return new AlchemyRecipe(id, ingredients, soups, result);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, AlchemyRecipe recipe) {
			buffer.writeVarInt(recipe.getIngredients().size());
			RecipeUtils.ingredientsToBuffer(buffer, recipe.getIngredients());
			buffer.writeVarInt(recipe.getSoups().size());
			RecipeUtils.soupsToBuffer(buffer, recipe.getSoups());
			buffer.writeItem(recipe.getResultItem());
		}
	}
}
