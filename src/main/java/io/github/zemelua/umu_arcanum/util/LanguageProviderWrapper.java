package io.github.zemelua.umu_arcanum.util;

import io.github.zemelua.umu_arcanum.UMUArcanum;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

import java.io.IOException;
import java.util.*;

public abstract class LanguageProviderWrapper implements DataProvider {
	private final DataGenerator generator;

	protected LanguageProviderWrapper(DataGenerator generator) {
		this.generator = generator;
	}

	protected final Set<LanguageProvider> languages = new HashSet<>();

	protected abstract Collection<LanguageProvider> generateTranslations();

	@Override
	public void run(HashCache cache) throws IOException {
		this.languages.addAll(this.generateTranslations());

		for (LanguageProvider language : this.languages) {
			language.run(cache);
		}
	}

	@Override
	public String getName() {
		return Arrays.toString(this.languages.stream().map(LanguageProvider::getName).toArray());
	}

	public class LanguageBuilder {
		private final String languageName;
		private final Map<String, String> translations = new HashMap<>();

		public LanguageBuilder(String languageName) {
			this.languageName = languageName;
		}

		public LanguageBuilder add(Block key, String name) {
			return this.add(key.getDescriptionId(), name);
		}

		public LanguageBuilder add(Item key, String name) {
			return this.add(key.getDescriptionId(), name);
		}

		public LanguageBuilder add(ItemStack key, String name) {
			return this.add(key.getDescriptionId(), name);
		}

		public LanguageBuilder add(Enchantment key, String name) {
			return this.add(key.getDescriptionId(), name);
		}

		public LanguageBuilder add(MobEffect key, String name) {
			return this.add(key.getDescriptionId(), name);
		}

		public LanguageBuilder add(EntityType<?> key, String name) {
			return this.add(key.getDescriptionId(), name);
		}

		public LanguageBuilder add(String key, String value) {
			if (translations.put(key, value) != null) {
				throw new IllegalStateException("Duplicate translation key " + key);
			}

			return this;
		}

		public LanguageProvider build() {
			return new LanguageProvider(LanguageProviderWrapper.this.generator, UMUArcanum.MOD_ID, this.languageName) {
				@Override
				protected void addTranslations() {
					for (Map.Entry<String, String> entry : LanguageBuilder.this.translations.entrySet()) {
						this.add(entry.getKey(), entry.getValue());
					}
				}
			};
		}
	}
}
