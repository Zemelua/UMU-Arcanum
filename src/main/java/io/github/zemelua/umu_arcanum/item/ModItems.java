package io.github.zemelua.umu_arcanum.item;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.item.tab.ModTabs;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
	private static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, UMUArcanum.MOD_ID);

	public static final RegistryObject<Item> WAND;
	public static final RegistryObject<Item> ARCANE_GOLD_INGOT;
	public static final RegistryObject<Item> MANDRAKE;
	public static final RegistryObject<Item> BAT_WING;
	public static final RegistryObject<Item> MANA_BOTTLE;
	public static final RegistryObject<Item> MANA_BUCKET;
	public static final RegistryObject<Item> WITCH_HAT;
	public static final RegistryObject<Item> EVOKER_CLOAK;

	public static void initialize(IEventBus forgeEvents, IEventBus modEvents) {
		ModItems.REGISTRY.register(modEvents);
		forgeEvents.addListener(ManaBucketItem::onPotionAdded);
		forgeEvents.addListener(WitchHatItem::onLivingHurt);
		forgeEvents.addListener(EvokerCloakItem::onLivingHurt);
	}

	static {
		WAND = ModItems.REGISTRY.register("wand", ()
				-> new Item(new Item.Properties()
				.stacksTo(1)
				.tab(ModTabs.UMU_ARCANUM))
		);
		ARCANE_GOLD_INGOT = ModItems.REGISTRY.register("arcane_gold_ingot", ()
				-> new Item(new Item.Properties()
				.tab(ModTabs.UMU_ARCANUM))
		);
		BAT_WING = ModItems.REGISTRY.register("bat_wing", ()
				-> new Item(new Item.Properties()
				.tab(ModTabs.UMU_ARCANUM))
		);
		MANDRAKE = ModItems.REGISTRY.register("mandrake", ()
				-> new MandrakeItem(ModBlocks.MANDRAKE.get(), new Item.Properties()
				.tab(ModTabs.UMU_ARCANUM)
				.food(Foods.CARROT))
		);
		MANA_BOTTLE = ModItems.REGISTRY.register("mana_bottle", ()
				-> new ManaBottleItem(new Item.Properties()
				.craftRemainder(Items.GLASS_BOTTLE)
				.stacksTo(1)
				.tab(ModTabs.UMU_ARCANUM))
		);
		MANA_BUCKET = ModItems.REGISTRY.register("mana_bucket", ()
				-> new ManaBucketItem(new Item.Properties()
				.craftRemainder(Items.BUCKET)
				.stacksTo(1)
				.tab(ModTabs.UMU_ARCANUM))
		);
		WITCH_HAT = ModItems.REGISTRY.register("witch_hat", ()
				-> new WitchHatItem(new Item.Properties()
				.tab(ModTabs.UMU_ARCANUM))
		);
		EVOKER_CLOAK = ModItems.REGISTRY.register("evoker_cloak", ()
				-> new EvokerCloakItem(new Item.Properties()
				.tab(ModTabs.UMU_ARCANUM))
		);
	}
}
