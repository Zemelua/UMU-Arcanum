package io.github.zemelua.umu_arcanum.recipe.alchemy;

import io.github.zemelua.umu_arcanum.inventory.AlchemyContainer;
import io.github.zemelua.umu_arcanum.recipe.ModRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class TippedArrowRecipe implements IAlchemyRecipe {
	private final ResourceLocation id;

	public TippedArrowRecipe(ResourceLocation id) {
		this.id = id;
	}

	@Override
	public boolean matches(AlchemyContainer container, Level world) {
		return container.getItemStacks().stream().allMatch(itemStack -> itemStack.is(Items.ARROW))
				&& container.getItemStacks().stream().mapToInt(ItemStack::getCount).sum() <= 64;
	}

	@Override
	public ItemStack assemble(AlchemyContainer container) {
		int count = container.getItemStacks().stream().mapToInt(ItemStack::getCount).sum();
		ItemStack tippedArrow = new ItemStack(Items.TIPPED_ARROW, count);
		PotionUtils.setCustomEffects(tippedArrow, container.getEffectInstances());

		return tippedArrow;
	}

	@Override
	public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
		return false;
	}

	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.TIPPED_ALLOW.get();
	}
}
