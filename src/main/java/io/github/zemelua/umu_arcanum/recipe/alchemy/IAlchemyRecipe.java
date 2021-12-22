package io.github.zemelua.umu_arcanum.recipe.alchemy;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;

import java.util.Collection;

public interface IAlchemyRecipe {
	boolean matches(Potion root, Collection<MobEffectInstance> solution, Collection<ItemStack> ingredients);

	ItemStack getResult();
}
