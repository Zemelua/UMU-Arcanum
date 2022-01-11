package io.github.zemelua.umu_arcanum.datagen.lootmodifier;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_arcanum.fluid.ModFluids;
import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CarrotsLootModifier extends LootModifier {
	protected CarrotsLootModifier() {
		super(new LootItemCondition[]{
				LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.CARROTS)
						.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CarrotBlock.AGE, 7)).build(),
				LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1F, 0.02F).build(),
				LocationCheck.checkLocation(LocationPredicate.Builder.location()
								.setFluid(FluidPredicate.Builder.fluid().of(ModFluids.MANA.get()).build()),
						new BlockPos(0, -2, 0)
				).build()
		});
	}

	@NotNull
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		List<ItemStack> loots = new java.util.ArrayList<>(List.copyOf(generatedLoot));
		loots.add(new ItemStack(ModItems.MANDRAKE.get()));

		return loots;
	}

	protected static class Serializer extends GlobalLootModifierSerializer<CarrotsLootModifier> {
		@Override
		public CarrotsLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
			return new CarrotsLootModifier();
		}

		@Override
		public JsonObject write(CarrotsLootModifier instance) {
			return this.makeConditions(instance.conditions);
		}
	}
}
