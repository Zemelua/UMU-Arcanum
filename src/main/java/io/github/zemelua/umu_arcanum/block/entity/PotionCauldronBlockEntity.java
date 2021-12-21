package io.github.zemelua.umu_arcanum.block.entity;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PotionCauldronBlockEntity extends BlockEntity {
	private final List<EffectMixture> mixtures = new ArrayList<>();

	private Potion root = Potions.EMPTY;

	public PotionCauldronBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.POTION_CAULDRON.get(), pos, state);
	}

	public void pour(ItemStack itemStack) {
		Collection<MobEffectInstance> effectInstances;

		if (this.root == Potions.EMPTY) {
			this.root = PotionUtils.getPotion(itemStack);
			effectInstances = PotionUtils.getCustomEffects(itemStack);
		} else {
			effectInstances = PotionUtils.getMobEffects(itemStack);
		}

		effectInstances.forEach(effectInstance -> {
			if (!this.contains(effectInstance)) {
				this.mixtures.add(new EffectMixture(effectInstance));
			}
		});

		this.onDataChanged();
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private boolean contains(MobEffectInstance effectInstance) {
		return this.mixtures.stream().anyMatch(mixtures -> mixtures.tryBlend(effectInstance))
				|| this.root.getEffects().stream().anyMatch(rootInstance -> rootInstance.getEffect() == effectInstance.getEffect());
	}

	public void pourMana() {
		this.mixtures.forEach(EffectMixture::blendMana);

		this.onDataChanged();
	}

	public List<MobEffectInstance> scoop() {
		List<MobEffectInstance> effectInstances = this.mixtures.stream()
				.map(EffectMixture::scoop)
				.collect(Collectors.toList());

		this.onDataChanged();

		return effectInstances;
	}

	public void stir(ItemStack... ingredients) {
		for (ItemStack ingredient : ingredients) {
			UMUArcanum.LOGGER.info(ingredient);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public CompoundTag save(CompoundTag tag) {
		super.save(tag);

		ListTag mixtureTags = new ListTag();
		for (EffectMixture mixture : this.mixtures) {
			mixtureTags.add(mixture.save());
		}
		tag.put("Mixtures", mixtureTags);
		tag.putString("Root", Registry.POTION.getKey(this.root).toString());

		return tag;
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);

		this.mixtures.clear();
		ListTag mixtures = tag.getList("Mixtures", 10);
		for (int i = 0; i < mixtures.size(); i++) {
			this.mixtures.add(EffectMixture.load(this, mixtures.getCompound(i)));
		}
		this.root = Potion.byName(tag.getString("Root"));
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.save(new CompoundTag());
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		this.load(tag);
	}

	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection network, ClientboundBlockEntityDataPacket packet) {
		CompoundTag tag = packet.getTag();

		if (tag != null) {
			this.handleUpdateTag(tag);
		}
	}

	private void onDataChanged() {
		this.setChanged();
		if (this.getLevel() == null) return;

		this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	public Potion getRoot() {
		return this.root;
	}

	public int getColor() {
		return PotionUtils.getColor(PotionUtils.getAllEffects(this.root, this.mixtures.stream()
				.map(EffectMixture::toInstance)
				.collect(Collectors.toList())
		));
	}

	private class EffectMixture {
		private final MobEffect effect;
		private double duration;
		private double amplifier;

		private EffectMixture(MobEffect effect, double duration, double amplifier) {
			this.effect = effect;
			this.duration = duration;
			this.amplifier = amplifier;
		}

		private EffectMixture(MobEffectInstance instance) {
			this.effect = instance.getEffect();
			this.duration = instance.getDuration();
			this.amplifier = instance.getAmplifier();
		}

		private boolean tryBlend(MobEffectInstance instance) {
			if (this.effect != instance.getEffect()) return false;

			double duration = instance.getDuration();
			double amplifier = instance.getAmplifier();

			if (this.amplifier != amplifier) {
				this.amplifier += amplifier * duration / (this.duration + duration);
			}

			this.duration += duration;

			return true;
		}

		private void blendMana() {
			this.duration += this.duration / PotionCauldronBlockEntity.this.getBlockState().getValue(LayeredCauldronBlock.LEVEL);
		}

		private MobEffectInstance scoop() {
			double scoopDuration = this.duration / PotionCauldronBlockEntity.this.getBlockState().getValue(LayeredCauldronBlock.LEVEL);
			MobEffectInstance instance
					= new MobEffectInstance(this.effect, (int) Math.round(scoopDuration), (int) Math.floor(this.amplifier));

			this.duration -= scoopDuration;

			return instance;
		}

		public MobEffectInstance toInstance() {
			return new MobEffectInstance(this.effect, (int) Math.round(this.duration), (int) Math.floor(this.amplifier));
		}

		private CompoundTag save() {
			CompoundTag tag = new CompoundTag();

			tag.putInt("Id", MobEffect.getId(this.effect));
			tag.putDouble("Duration", this.duration);
			tag.putDouble("Amplifier", this.amplifier);

			return tag;
		}

		private static EffectMixture load(PotionCauldronBlockEntity blockEntity, CompoundTag tag) {
			MobEffect effect = MobEffect.byId(tag.getInt("Id"));

			return blockEntity.new EffectMixture(
					effect != null ? effect : MobEffects.MOVEMENT_SPEED,
					tag.getDouble("Duration"),
					tag.getDouble("Amplifier")
			);
		}
	}
}
