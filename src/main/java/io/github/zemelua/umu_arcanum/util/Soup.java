package io.github.zemelua.umu_arcanum.util;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

public class Soup implements Predicate<MobEffectInstance> {
	private final Soup.Value[] values;
	private final MobEffectInstance[] effectInstances;

	public Soup(Soup.Value... values) {
		this.values = values;
		this.effectInstances = Arrays.stream(values)
				.flatMap(value -> value.getEffects().stream())
				.toArray(MobEffectInstance[]::new);
	}

	@Override
	public boolean test(MobEffectInstance input) {
		for (MobEffectInstance effectInstance : this.effectInstances) {
			if (effectInstance.getEffect() == input.getEffect()) {
				return true;
			}
		}

		return false;
	}

	public JsonElement toJson() {
		if (this.values.length == 1) {
			return this.values[0].serialize();
		} else {
			JsonArray jsonArray = new JsonArray();

			for (Soup.Value value : this.values) {
				jsonArray.add(value.serialize());
			}

			return jsonArray;
		}
	}

	public void toBuffer(FriendlyByteBuf buffer) {
		buffer.writeCollection(Arrays.asList(this.effectInstances), (bufferArg, effectInstance) -> {
			ResourceLocation key = ForgeRegistries.MOB_EFFECTS.getKey(effectInstance.getEffect());

			if (key != null) {
				bufferArg.writeResourceLocation(key);
			}
		});
	}

	public static Soup of(MobEffect... effect) {
		return new Soup(Arrays.stream(effect)
				.map(arg -> new Soup.EffectValue(new MobEffectInstance(arg)))
				.toArray(Soup.Value[]::new)
		);
	}

	private static interface Value {
		Collection<MobEffectInstance> getEffects();

		JsonObject serialize();
	}

	public static class EffectValue implements Soup.Value {
		private final MobEffectInstance effect;

		public EffectValue(MobEffectInstance effect) {
			this.effect = effect;
		}

		public Collection<MobEffectInstance> getEffects() {
			return Collections.singleton(this.effect);
		}

		public JsonObject serialize() {
			JsonObject jsonObject = new JsonObject();
			ResourceLocation key = ForgeRegistries.MOB_EFFECTS.getKey(this.effect.getEffect());
			if (key != null) {
				jsonObject.addProperty("effect", key.toString());
			}

			return jsonObject;
		}
	}

	public static Soup fromJson(JsonElement jsonElement) {
		if (jsonElement instanceof JsonObject jsonObject) {
			String key = jsonObject.get("effect").getAsString();
			@Nullable MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(key));

			if (effect != null) {
				return new Soup(new Soup.EffectValue(new MobEffectInstance(effect)));
			}
		}

		return new Soup();
	}

	public static Soup fromBuffer(FriendlyByteBuf buffer) {
		Collection<Soup.Value> values = buffer.readCollection(Lists::newArrayListWithCapacity, bufferArg -> {
					ResourceLocation key = bufferArg.readResourceLocation();
					@Nullable MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(key);

					if (effect != null) {
						return new Soup.EffectValue(new MobEffectInstance(effect));
					}

					return null;
				}
		);

		return new Soup(values.toArray(Soup.Value[]::new));
	}
}
