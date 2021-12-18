package io.github.zemelua.umu_arcanum.item.tab;

import io.github.zemelua.umu_arcanum.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModTabs {
	public static final CreativeModeTab UMU_ARCANUM = new CreativeModeTab("umu_arcanum") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModItems.MANA_BUCKET.get());
		}
	};
}
