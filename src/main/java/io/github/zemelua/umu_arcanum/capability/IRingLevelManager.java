package io.github.zemelua.umu_arcanum.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IRingLevelManager extends INBTSerializable<CompoundTag> {
	public void addExperience(int value);

	public int getLevel();

	public int getBarWidth();

	public static ICapabilityProvider createProvider() {
		return new RingLevelManagerProvider();
	}

	public static IRingLevelManager get(ItemStack itemStack) {
		return itemStack.getCapability(ModCapabilities.RING_LEVEL_MANAGER).orElseThrow(RuntimeException::new);
	}

	public static class RingLevelManager implements IRingLevelManager {
		private int experience = 0;

		@Override
		public void addExperience(int value) {
			this.experience = Math.min(500, this.experience + value);
		}

		@Override
		public int getLevel() {
			if (this.experience < 10) return 0;
			else if (this.experience < 30) return 1;
			else if (this.experience < 100) return 2;
			else if (this.experience < 500) return 3;
			else if (this.experience == 500) return 4;

			return 0;
		}

		@Override
		public int getBarWidth() {
			int maxExperience = switch (this.getLevel()) {
				case 0 -> 9;
				case 1 -> 29;
				case 2 -> 99;
				case 3 -> 499;
				case 4 -> 500;
				default -> throw new IllegalStateException("Unexpected value: " + this.getLevel());
			};

			if (this.experience == maxExperience) return -1;

			return Math.round(this.experience * 13.0F / maxExperience);
		}

		@Override
		public CompoundTag serializeNBT() {
			CompoundTag nbt = new CompoundTag();
			nbt.putInt("Experience", this.experience);

			return nbt;
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			this.experience = nbt.getInt("Experience");
		}
	}

	public static class RingLevelManagerProvider implements ICapabilitySerializable<CompoundTag> {
		private final IRingLevelManager instance = new RingLevelManager();
		private final LazyOptional<IRingLevelManager> supplier = LazyOptional.of(() -> this.instance);

		@NotNull
		@Override
		public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
			return ModCapabilities.RING_LEVEL_MANAGER.orEmpty(capability, this.supplier);
		}

		@Override
		public CompoundTag serializeNBT() {
			return this.instance.serializeNBT();
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			this.instance.deserializeNBT(nbt);
		}
	}
}
