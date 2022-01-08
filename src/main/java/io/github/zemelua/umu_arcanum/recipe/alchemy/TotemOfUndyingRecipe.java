package io.github.zemelua.umu_arcanum.recipe.alchemy;

import io.github.zemelua.umu_arcanum.inventory.AlchemyContainer;
import io.github.zemelua.umu_arcanum.recipe.ModRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class TotemOfUndyingRecipe implements IAlchemyRecipe {
	private final ResourceLocation id;
	private final List<Ingredient> ingredients = new ArrayList<>();
	private final ItemStack banner = Raid.getLeaderBannerInstance();
	private final ItemStack result = new ItemStack(Items.TOTEM_OF_UNDYING);

	public TotemOfUndyingRecipe(ResourceLocation id) {
		this.id = id;
		this.ingredients.add(Ingredient.of(Items.CLAY_BALL));
		this.ingredients.add(Ingredient.of(Items.GOLD_INGOT));
	}

	@Override
	public boolean matches(AlchemyContainer container, Level world) {
		List<ItemStack> itemStacks = new ArrayList<>(List.copyOf(container.getItemStacks()));

		boolean matchesBanner = false;
		for (int i = 0; i < container.getItemStacks().size(); i++) {
			ItemStack itemStack = itemStacks.get(i);

			if (ItemStack.matches(itemStack, this.banner)) {
				itemStacks.remove(itemStack);
				matchesBanner = true;
				break;
			}
		}
		if (!matchesBanner) return false;

		return RecipeMatcher.findMatches(itemStacks, this.ingredients) != null;
	}

	@Override
	public ItemStack assemble(AlchemyContainer p_44001_) {
		return this.result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
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
		return ModRecipeSerializers.TOTEM_OF_UNDYING.get();
	}
}
