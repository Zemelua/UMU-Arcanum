package io.github.zemelua.umu_arcanum.item;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.item.tab.ModTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
	private static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, UMUArcanum.MOD_ID);

	public static final RegistryObject<Item> ARCANE_GOLD_INGOT;
	public static final RegistryObject<Item> MANA_BOTTLE;
	public static final RegistryObject<Item> MANA_BUCKET;
	public static final RegistryObject<Item> WITCH_HAT;

	public static void initialize(IEventBus forgeEvents, IEventBus modEvents) {
		ModItems.REGISTRY.register(modEvents);
		forgeEvents.addListener(WitchHatItem::onLivingHurt);
		forgeEvents.addListener(ManaBucketItem::onPotionAdded);
	}

	static {
		ARCANE_GOLD_INGOT = REGISTRY.register("arcane_gold_ingot", ()
				-> new Item(new Item.Properties()
				.tab(ModTabs.UMU_ARCANUM))
		);
		MANA_BOTTLE = REGISTRY.register("mana_bottle", ()
				-> new ManaBottleItem(new Item.Properties()
				.craftRemainder(Items.GLASS_BOTTLE)
				.stacksTo(1)
				.tab(ModTabs.UMU_ARCANUM))
		);
		MANA_BUCKET = REGISTRY.register("mana_bucket", ()
				-> new ManaBucketItem(new Item.Properties()
				.craftRemainder(Items.BUCKET)
				.stacksTo(1)
				.tab(ModTabs.UMU_ARCANUM))
		);
		WITCH_HAT = REGISTRY.register("witch_hat", ()
				-> new WitchHatItem(new Item.Properties()
				.tab(ModTabs.UMU_ARCANUM))
		);
	}
}
