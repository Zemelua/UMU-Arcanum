package io.github.zemelua.umu_arcanum.datagen;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockModelProvider {
	protected static final String MANDRAKE_STAGE1 = "mandrake_stage1";
	protected static final String MANDRAKE_STAGE2 = "mandrake_stage2";
	protected static final String MANDRAKE_STAGE3 = "mandrake_stage3";
	protected static final String MANDRAKE_STAGE4 = "mandrake_stage4";
	protected static final String MANA = "mana";
	protected static final String MANA_CAULDRON_LEVEL1 = "mana_cauldron_level1";
	protected static final String MANA_CAULDRON_LEVEL2 = "mana_cauldron_level2";
	protected static final String MANA_CAULDRON_LEVEL3 = "mana_cauldron_level3";

	public ModBlockModelProvider(DataGenerator generator, ExistingFileHelper files) {
		super(generator, UMUArcanum.MOD_ID, files);
	}

	@Override
	protected void registerModels() {
		this.getBuilder(ModBlockModelProvider.MANDRAKE_STAGE1)
				.parent(new ModelFile.ExistingModelFile(new ResourceLocation("block/crop"), this.existingFileHelper))
				.texture("crop", "block/mandrake_stage1");
		this.getBuilder(ModBlockModelProvider.MANDRAKE_STAGE2)
				.parent(new ModelFile.ExistingModelFile(new ResourceLocation("block/crop"), this.existingFileHelper))
				.texture("crop", "block/mandrake_stage2");
		this.getBuilder(ModBlockModelProvider.MANDRAKE_STAGE3)
				.parent(new ModelFile.ExistingModelFile(new ResourceLocation("block/crop"), this.existingFileHelper))
				.texture("crop", "block/mandrake_stage3");
		this.getBuilder(ModBlockModelProvider.MANDRAKE_STAGE4)
				.parent(new ModelFile.ExistingModelFile(new ResourceLocation("block/crop"), this.existingFileHelper))
				.texture("crop", "block/mandrake_stage4");
		this.getBuilder(ModBlockModelProvider.MANA)
				.texture("particle", "block/mana_still");
		this.getBuilder(ModBlockModelProvider.MANA_CAULDRON_LEVEL1)
				.parent(new ModelFile.ExistingModelFile(new ResourceLocation("block/template_cauldron_level1"), this.existingFileHelper))
				.texture("content", "block/mana_still");
		this.getBuilder(ModBlockModelProvider.MANA_CAULDRON_LEVEL2)
				.parent(new ModelFile.ExistingModelFile(new ResourceLocation("block/template_cauldron_level2"), this.existingFileHelper))
				.texture("content", "block/mana_still");
		this.getBuilder(ModBlockModelProvider.MANA_CAULDRON_LEVEL3)
				.parent(new ModelFile.ExistingModelFile(new ResourceLocation("block/template_cauldron_full"), this.existingFileHelper))
				.texture("content", "block/mana_still");
	}

	public static ModelFile fromName(String name, ExistingFileHelper files) {
		return new ModelFile.ExistingModelFile(UMUArcanum.resource(BlockModelProvider.BLOCK_FOLDER + "/" + name), files);
	}

}
