package io.github.zemelua.umu_arcanum.datagen;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import io.github.zemelua.umu_arcanum.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
	private final ExistingFileHelper files;

	public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper files) {
		super(generator, UMUArcanum.MOD_ID, files);

		this.files = files;
	}

	@Override
	protected void registerStatesAndModels() {
		this.simpleBlock(ModBlocks.MANA.get(), ModBlockModelProvider.fromName(ModBlockModelProvider.MANA, this.files));
		this.getVariantBuilder(ModBlocks.MANA_CAULDRON.get()).partialState()
				.with(BlockStateProperties.LEVEL_CAULDRON, 1)
				.setModels(new ConfiguredModel(ModBlockModelProvider.fromName(ModBlockModelProvider.MANA_CAULDRON_LEVEL1, this.files)));
		this.getVariantBuilder(ModBlocks.MANA_CAULDRON.get()).partialState()
				.with(BlockStateProperties.LEVEL_CAULDRON, 2)
				.setModels(new ConfiguredModel(ModBlockModelProvider.fromName(ModBlockModelProvider.MANA_CAULDRON_LEVEL2, this.files)));
		this.getVariantBuilder(ModBlocks.MANA_CAULDRON.get()).partialState()
				.with(BlockStateProperties.LEVEL_CAULDRON, 3)
				.setModels(new ConfiguredModel(ModBlockModelProvider.fromName(ModBlockModelProvider.MANA_CAULDRON_LEVEL3, this.files)));
		this.simpleBlock(ModBlocks.POTION_CAULDRON.get(), new ModelFile.ExistingModelFile(new ResourceLocation("block/cauldron"), this.files));
	}
}
