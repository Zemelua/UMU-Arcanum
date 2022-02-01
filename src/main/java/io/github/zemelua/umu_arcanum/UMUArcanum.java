package io.github.zemelua.umu_arcanum;

import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.block.entity.ModBlockEntities;
import io.github.zemelua.umu_arcanum.capability.ModCapabilities;
import io.github.zemelua.umu_arcanum.client.ClientHandler;
import io.github.zemelua.umu_arcanum.datagen.ModBlockModelProvider;
import io.github.zemelua.umu_arcanum.datagen.ModBlockStateProvider;
import io.github.zemelua.umu_arcanum.datagen.ModItemModelProvider;
import io.github.zemelua.umu_arcanum.datagen.language.ModEnglishLanguageProvider;
import io.github.zemelua.umu_arcanum.datagen.language.ModJapaneseLanguageProvider;
import io.github.zemelua.umu_arcanum.datagen.lootmodifier.ModLootModifierProvider;
import io.github.zemelua.umu_arcanum.datagen.lootmodifier.ModLootModifiers;
import io.github.zemelua.umu_arcanum.datagen.loottable.ModLootTableProvider;
import io.github.zemelua.umu_arcanum.datagen.recipe.ModRecipeProvider;
import io.github.zemelua.umu_arcanum.effect.ModEffects;
import io.github.zemelua.umu_arcanum.effect.ModPotions;
import io.github.zemelua.umu_arcanum.fluid.ModFluids;
import io.github.zemelua.umu_arcanum.item.ModItems;
import io.github.zemelua.umu_arcanum.network.NetworkHandler;
import io.github.zemelua.umu_arcanum.recipe.ModRecipeSerializers;
import io.github.zemelua.umu_arcanum.replace.ReplaceBlockTagsProvider;
import io.github.zemelua.umu_arcanum.replace.ReplaceEnglishLanguageProvider;
import io.github.zemelua.umu_arcanum.replace.ReplaceJapaneseLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(UMUArcanum.MOD_ID)
public class UMUArcanum {
	public static final String MOD_ID = "umu_arcanum";
	@SuppressWarnings("unused")
	public static final Logger LOGGER = LogManager.getLogger();

	public UMUArcanum() {
		IEventBus forgeEvents = MinecraftForge.EVENT_BUS;
		IEventBus modEvents = FMLJavaModLoadingContext.get().getModEventBus();

		ModBlocks.initialize(forgeEvents, modEvents);
		ModItems.initialize(forgeEvents, modEvents);
		ModBlockEntities.initialize(forgeEvents, modEvents);
		ModFluids.initialize(forgeEvents, modEvents);
		ModEffects.initialize(forgeEvents, modEvents);
		ModPotions.initialize(forgeEvents, modEvents);
		ModCapabilities.initialize(forgeEvents, modEvents);
		ModRecipeSerializers.initialize(forgeEvents, modEvents);
		ModLootModifiers.initialize(forgeEvents, modEvents);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new ClientHandler(forgeEvents, modEvents)::initialize);

		NetworkHandler.initialize();

		modEvents.addListener(UMUArcanum::onGatherData);
	}

	public static ResourceLocation resource(String path) {
		return new ResourceLocation(UMUArcanum.MOD_ID, path);
	}

	public static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> registry(IForgeRegistry<T> base) {
		return DeferredRegister.create(base, UMUArcanum.MOD_ID);
	}

	private static void onGatherData(final GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper files = event.getExistingFileHelper();

		generator.addProvider(new ModBlockModelProvider(generator, files));
		generator.addProvider(new ModBlockStateProvider(generator, files));
		generator.addProvider(new ModItemModelProvider(generator, files));
		generator.addProvider(new ModEnglishLanguageProvider(generator));
		generator.addProvider(new ModJapaneseLanguageProvider(generator));
		generator.addProvider(new ModLootTableProvider(generator));
		generator.addProvider(new ModLootModifierProvider(generator));
		generator.addProvider(new ModRecipeProvider(generator));

		generator.addProvider(new ReplaceEnglishLanguageProvider(generator));
		generator.addProvider(new ReplaceJapaneseLanguageProvider(generator));
		generator.addProvider(new ReplaceBlockTagsProvider(generator, files));
	}
}
