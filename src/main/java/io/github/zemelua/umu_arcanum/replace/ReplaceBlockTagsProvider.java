package io.github.zemelua.umu_arcanum.replace;

import io.github.zemelua.umu_arcanum.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ReplaceBlockTagsProvider extends BlockTagsProvider {
	public ReplaceBlockTagsProvider(DataGenerator generator, ExistingFileHelper files) {
		super(generator, "minecraft", files);
	}

	@Override
	protected void addTags() {
		this.tag(BlockTags.CAULDRONS).add(ModBlocks.MANA_CAULDRON.get()).add(ModBlocks.POTION_CAULDRON.get());
	}
}
