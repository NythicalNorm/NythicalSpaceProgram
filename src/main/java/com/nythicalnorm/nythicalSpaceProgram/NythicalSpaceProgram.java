package com.nythicalnorm.nythicalSpaceProgram;

import com.mojang.logging.LogUtils;
import com.nythicalnorm.nythicalSpaceProgram.Item.NSPItems;
import com.nythicalnorm.nythicalSpaceProgram.sound.NSPSounds;
import com.nythicalnorm.nythicalSpaceProgram.util.NSPCreativeModeTab;
import com.nythicalnorm.nythicalSpaceProgram.block.NSPBlocks;
import com.nythicalnorm.nythicalSpaceProgram.block.manufacturing.entity.NSPBlockEntities;
import com.nythicalnorm.nythicalSpaceProgram.fluid.NSPFluids;
import com.nythicalnorm.nythicalSpaceProgram.recipe.NSPRecipes;
import com.nythicalnorm.nythicalSpaceProgram.gui.NSPMenuTypes;
import com.nythicalnorm.nythicalSpaceProgram.commands.NSPArguments;
import com.nythicalnorm.nythicalSpaceProgram.network.PacketHandler;
import com.nythicalnorm.nythicalSpaceProgram.solarsystem.PlanetsProvider;
import com.nythicalnorm.nythicalSpaceProgram.spacecraft.EntitySpacecraftBody;
import com.nythicalnorm.nythicalSpaceProgram.Item.NSPItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Optional;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NythicalSpaceProgram.MODID)
public class NythicalSpaceProgram
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "nythicalspaceprogram";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    //only use this in the Logical Server side
    private static SolarSystem solarSystem;
    private static CelestialStateSupplier celestialStateSupplier;

    public NythicalSpaceProgram(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        NSPItems.register(modEventBus);
        NSPBlocks.register(modEventBus);
        NSPSounds.register(modEventBus);
        NSPBlockEntities.register(modEventBus);
        NSPMenuTypes.register(modEventBus);
        NSPRecipes.register(modEventBus);
        NSPFluids.FLUID_TYPES.register(modEventBus);
        NSPFluids.FLUIDS.register(modEventBus);
        NSPArguments.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::commonSetup);
        NSPCreativeModeTab.register(modEventBus);
        //modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        //context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        event.enqueueWork(PacketHandler::register);
    }

    public static ResourceLocation rl(String location) {
        return ResourceLocation.fromNamespaceAndPath(MODID, location);
    }

    public static void log(String msg){
        LOGGER.debug(msg);
    }

    public static void logError(String msg){
        LOGGER.error(msg);
    }
    public static void logWarn(String msg){
        LOGGER.warn(msg);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event)
    {
        PlanetsProvider planets = new PlanetsProvider(false);
        solarSystem = new SolarSystem(event.getServer(), planets);
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event)
    {
        if (NythicalSpaceProgram.getSolarSystem().isPresent()) {
            NythicalSpaceProgram.getSolarSystem().get().serverStarted();
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            NSPItemProperties.addCustomItemProperties();
            NSPMenuTypes.registerMenus();
        }
    }

    public static void startClient(EntitySpacecraftBody playerData) {
        PlanetsProvider planets = new PlanetsProvider(true);
        celestialStateSupplier = new CelestialStateSupplier(playerData, planets);
    }

    public static Optional<SolarSystem> getSolarSystem() {
        if (solarSystem != null) {
            return Optional.of(solarSystem);
        }
        return Optional.empty();
    }

    public static Optional<CelestialStateSupplier> getCelestialStateSupplier() {
        if (celestialStateSupplier != null) {
            return Optional.of(celestialStateSupplier);
        }
        else {
            return Optional.empty();
        }
    }
}