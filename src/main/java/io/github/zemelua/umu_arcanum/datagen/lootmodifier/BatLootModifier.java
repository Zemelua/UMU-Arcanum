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
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BatLootModifier extends LootModifier {
	protected BatLootModifier() {
		super(new LootItemCondition[]{
				LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.BAT)).build(),
				LootItemKilledByPlayerCondition.killedByPlayer().build()
		});
	}

	@NotNull
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		List<ItemStack> loots = new java.util.ArrayList<>(List.copyOf(generatedLoot));
		loots.add(new ItemStack(ModItems.BAT_WING.get()));

		return loots;
	}

	protected static class Serializer extends GlobalLootModifierSerializer<BatLootModifier> {
		@Override
		public BatLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
			return new BatLootModifier();
		}

		@Override
		public JsonObject write(BatLootModifier instance) {
			return this.makeConditions(instance.conditions);
		}
	}
}
