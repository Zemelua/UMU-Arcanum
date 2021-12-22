package io.github.zemelua.umu_arcanum.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Stream;

public class StrictlyIngredient extends Ingredient {
	protected StrictlyIngredient(Stream<? extends Value> values) {
		super(values);
	}

	@Override
	public boolean test(@Nullable ItemStack input) {
		if (input == null) {
			return false;
		} else {
			ItemStack[] itemStacks = this.getItems();
			if (itemStacks.length == 0) {
				return input.isEmpty();
			} else {
				for(ItemStack itemstack : itemStacks) {
					if (itemstack.equals(input, true)) {
						return true;
					}
				}

				return false;
			}
		}
	}

	public static Ingredient of(ItemStack... itemStacks) {
		return StrictlyIngredient.of(Arrays.stream(itemStacks));
	}

	public static Ingredient of(Stream<ItemStack> itemStacks) {
		return StrictlyIngredient.fromValues(itemStacks
				.filter((itemStack) -> !itemStack.isEmpty())
				.map(Ingredient.ItemValue::new)
		);
	}

	public static Ingredient fromValues(Stream<? extends Ingredient.Value> values) {
		return new StrictlyIngredient(values);
	}
}
