package io.github.zemelua.umu_arcanum.recipe.alchemy;

import io.github.zemelua.umu_arcanum.util.StrictlyIngredient;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("ClassCanBeRecord")
public class AlchemyRecipe implements IAlchemyRecipe {
	private final Predicate<Potion> root;
	private final Predicate<Collection<MobEffectInstance>> solution;
	private final List<Ingredient> ingredients;
	private final ItemStack result;

	public AlchemyRecipe(Predicate<Potion> root, Predicate<Collection<MobEffectInstance>> solution,
						 List<Ingredient> ingredients, ItemStack result) {
		this.root = root;
		this.solution = solution;
		this.ingredients = ingredients;
		this.result = result;
	}

	@Override
	public boolean matches(Potion root, Collection<MobEffectInstance> solution, Collection<ItemStack> ingredients) {
		return this.root.test(root) && this.solution.test(solution)
				&& RecipeMatcher.findMatches(List.copyOf(ingredients), this.ingredients) != null;
	}

	@Override
	public ItemStack getResult() {
		return this.result.copy();
	}

	public static class Builder {
		private Predicate<Potion> root = root -> true;
		private Predicate<Collection<MobEffectInstance>> solution = solution -> true;
		private final List<Ingredient> ingredients = new ArrayList<>();

		public Builder setRoot(Potion root) {
			this.root = rootArg -> rootArg == root;

			return this;
		}

		public Builder addSolution(MobEffect effect) {
			this.solution = this.solution.and(solution -> solution.stream()
					.anyMatch(effectInstance -> effectInstance.getEffect() == effect)
			);

			return this;
		}

		@SuppressWarnings("unused")
		public Builder addSolution(MobEffect effect, int minAmplifier) {
			this.solution = this.solution.and(solution -> solution.stream()
					.anyMatch(effectInstance -> effectInstance.getEffect() == effect && effectInstance.getAmplifier() >= minAmplifier)
			);

			return this;
		}

		public Builder addIngredient(ItemStack itemStack) {
			this.ingredients.add(StrictlyIngredient.of(itemStack));

			return this;
		}

		public AlchemyRecipe build(ItemStack result) {
			return new AlchemyRecipe(this.root, this.solution, this.ingredients, result);
		}
	}
}
