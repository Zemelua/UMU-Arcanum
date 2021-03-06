package io.github.zemelua.umu_arcanum.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class AlchemyContainer implements Container, INBTSerializable<CompoundTag> {
	private final List<ItemStack> itemStacks;
	private final List<MobEffectInstance> effectInstances;

	private int volume;

	public AlchemyContainer() {
		this.itemStacks = new ArrayList<>();
		this.effectInstances = new ArrayList<>();

		this.volume = 0;
	}

	public List<ItemStack> getItemStacks() {
		return this.itemStacks;
	}

	public List<MobEffectInstance> getEffectInstances() {
		return this.effectInstances;
	}

	public void addItemStack(ItemStack itemStack) {
		this.itemStacks.add(itemStack);
	}

	public void addEffectInstance(MobEffectInstance inputInstance) {
		for (int i = 0; i < this.effectInstances.size(); i++) {
			MobEffectInstance existingInstance = this.effectInstances.get(i);

			if (existingInstance.getEffect() == inputInstance.getEffect()) {
				this.effectInstances.set(i, new MobEffectInstance(
						existingInstance.getEffect(),
						existingInstance.getDuration() + inputInstance.getDuration(),
						Math.max(existingInstance.getAmplifier(), inputInstance.getAmplifier())
				));

				return;
			}
		}

		this.effectInstances.add(inputInstance);
	}

	public void duplicateEffectInstances() {
		for (MobEffectInstance existingInstance : this.effectInstances) {
			if (this.volume > 0) {
				this.addEffectInstance(new MobEffectInstance(
						existingInstance.getEffect(),
						existingInstance.getDuration() / this.volume,
						existingInstance.getAmplifier()
				));
			}
		}
	}

	public List<MobEffectInstance> separateEffectInstances() {
		List<MobEffectInstance> separating = new ArrayList<>();

		for (int i = 0; i < this.effectInstances.size(); i++) {
			MobEffectInstance existingInstance = this.effectInstances.get(i);

			separating.add(new MobEffectInstance(
					existingInstance.getEffect(),
					existingInstance.getDuration() / this.volume,
					existingInstance.getAmplifier()
			));

			this.effectInstances.set(i, new MobEffectInstance(
					existingInstance.getEffect(),
					existingInstance.getDuration() - existingInstance.getDuration() / this.volume,
					existingInstance.getAmplifier()
			));
		}

		return separating;
	}

	public void countVolume() {
		this.volume++;
	}

	public void consumeVolume() {
		this.volume--;
	}

	@Override
	public int getContainerSize() {
		return this.itemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : this.itemStacks) {
			if (!itemStack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getItem(int index) {
		return this.itemStacks.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		ItemStack itemStack = ContainerHelper.removeItem(this.itemStacks, index, count);
		if (!itemStack.isEmpty()) {
			this.setChanged();
		}

		return itemStack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return this.itemStacks.remove(index);
	}

	@Override
	public void setItem(int index, ItemStack itemStack) {
		this.itemStacks.set(index, itemStack);
	}

	@Override
	public void setChanged() {

	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		this.itemStacks.clear();
		this.effectInstances.clear();
		this.volume = 0;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag compoundNBT = new CompoundTag();

		ListTag itemsNBT = new ListTag();
		for (ItemStack itemStack : this.itemStacks) {
			CompoundTag itemNBT = new CompoundTag();
			itemsNBT.add(itemStack.save(itemNBT));
		}
		compoundNBT.put("Items", itemsNBT);

		ListTag effectsNBT = new ListTag();
		for (MobEffectInstance effectInstance : this.effectInstances) {
			CompoundTag effectNBT = new CompoundTag();
			effectsNBT.add(effectInstance.save(effectNBT));
		}
		compoundNBT.put("Effects", effectsNBT);

		compoundNBT.putInt("Volume", this.volume);

		return compoundNBT;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.itemStacks.clear();
		ListTag itemsNBT = nbt.getList("Items", 10);
		for (int i = 0; i < itemsNBT.size(); i++) {
			ItemStack itemStack = ItemStack.of(itemsNBT.getCompound(i));
			this.itemStacks.add(itemStack);
		}

		this.effectInstances.clear();
		ListTag effectsNBT = nbt.getList("Effects", 10);
		for (int i = 0; i < effectsNBT.size(); i++) {
			MobEffectInstance effectInstance = MobEffectInstance.load(effectsNBT.getCompound(i));
			this.effectInstances.add(effectInstance);
		}

		this.volume = nbt.getInt("Volume");
	}
}
