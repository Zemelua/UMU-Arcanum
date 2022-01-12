package io.github.zemelua.umu_arcanum.datagen.lootmodifier;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModLootModifierProvider extends GlobalLootModifierProvider {
	public ModLootModifierProvider(DataGenerator generator) {
		super(generator, UMUArcanum.MOD_ID);
	}

	@Override
	protected void start() {
		this.add("carrots_replace", ModLootModifiers.CARROTS.get(), new CarrotsLootModifier());
		this.add("witch_replace", ModLootModifiers.WITCH.get(), new WitchLootModifier());
		this.add("evoker_replace", ModLootModifiers.EVOKER.get(), new EvokerLootModifier());
	}
}
