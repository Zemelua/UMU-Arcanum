package io.github.zemelua.umu_arcanum;

import io.github.zemelua.umu_arcanum.block.ModBlocks;
import io.github.zemelua.umu_arcanum.block.entity.ModBlockEntities;
import io.github.zemelua.umu_arcanum.client.ClientHandler;
import io.github.zemelua.umu_arcanum.datagen.ModBlockModelProvider;
import io.github.zemelua.umu_arcanum.datagen.ModBlockStateProvider;
import io.github.zemelua.umu_arcanum.datagen.ModLanguageProvider;
import io.github.zemelua.umu_arcanum.datagen.lootmodifier.ModLootModifierProvider;
import io.github.zemelua.umu_arcanum.datagen.lootmodifier.ModLootModifiers;
import io.github.zemelua.umu_arcanum.fluid.ModFluids;
import io.github.zemelua.umu_arcanum.item.ModItems;
import io.github.zemelua.umu_arcanum.recipe.ModRecipeSerializers;
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
		ModRecipeSerializers.initialize(forgeEvents, modEvents);
		ModLootModifiers.initialize(forgeEvents, modEvents);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new ClientHandler(forgeEvents, modEvents)::initialize);

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
		generator.addProvider(new ModLootModifierProvider(generator));
		generator.addProvider(new ModLanguageProvider(generator));
	}
}
