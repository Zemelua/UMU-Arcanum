package io.github.zemelua.umu_arcanum.block.entity;

import io.github.zemelua.umu_arcanum.inventory.AlchemyContainer;
import io.github.zemelua.umu_arcanum.recipe.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class PotionCauldronBlockEntity extends BlockEntity implements Clearable {
	private final AlchemyContainer container;

	public PotionCauldronBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.POTION_CAULDRON.get(), pos, state);

		this.container = new AlchemyContainer();
	}

	public void pourEffect(Collection<MobEffectInstance> effectInstances) {
		effectInstances.forEach(this.container::addEffectInstance);
		this.container.countVolume();

		this.onDataChanged();
	}

	public void pourMana() {
		this.container.duplicateEffectInstances();
		this.container.countVolume();

		this.onDataChanged();
	}

	public Collection<MobEffectInstance> scoopEffect() {
		Collection<MobEffectInstance> scooping = this.container.separateEffectInstances();
		this.container.consumeVolume();

		this.onDataChanged();

		return scooping;
	}

	public Optional<ItemStack> stir(ItemStack... itemStacks) {
		Arrays.stream(itemStacks).forEach(this.container::addItemStack);

		if (this.level != null) {
			return this.level.getRecipeManager().getRecipeFor(ModRecipeTypes.ALCHEMY, container, this.level)
					.map(alchemyRecipe -> alchemyRecipe.assemble(container));
		}

		return Optional.empty();
	}

	public int getColor() {
		return PotionUtils.getColor(this.container.getEffectInstances());
	}

	@Override
	public void clearContent() {
		this.container.clearContent();
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.put("Container", this.container.serializeNBT());
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);

		this.clearContent();
		this.container.deserializeNBT(nbt.getCompound("Container"));
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	private void onDataChanged() {
		this.setChanged();
		if (this.getLevel() != null) {
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}
}
