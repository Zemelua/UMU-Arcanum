package io.github.zemelua.umu_arcanum.datagen.lootmodifier;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class EvokerLootModifier extends LootModifier {
	protected EvokerLootModifier() {
		super(new LootItemCondition[]{
				LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.EVOKER)).build(),
				LootItemKilledByPlayerCondition.killedByPlayer().build(),
				LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.11F, 0.02F).build()
		});
	}

	@Nonnull
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		List<ItemStack> loots = new java.util.ArrayList<>(List.copyOf(generatedLoot));
		loots.add(new ItemStack(ModItems.EVOKER_CLOAK.get()));

		return loots;
	}

	protected static class Serializer extends GlobalLootModifierSerializer<EvokerLootModifier> {
		@Override
		public EvokerLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
			return new EvokerLootModifier();
		}

		@Override
		public JsonObject write(EvokerLootModifier instance) {
			return this.makeConditions(instance.conditions);
		}
	}
}
