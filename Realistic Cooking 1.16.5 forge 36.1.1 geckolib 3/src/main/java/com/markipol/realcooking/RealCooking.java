package com.markipol.realcooking;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.markipol.realcooking.client.ter.BlenderTileRenderer;
import com.markipol.realcooking.common.screens.FirstBlockScreen;
import com.markipol.realcooking.core.init.BlockInit;
import com.markipol.realcooking.core.init.ContainerTypesInit;
import com.markipol.realcooking.core.init.ItemInit;
import com.markipol.realcooking.core.init.RecipeInit;
import com.markipol.realcooking.core.init.TileEntityTypesInit;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RealCooking.MOD_ID)
public class RealCooking
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "realcooking";

    public RealCooking() {
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addGenericListener(IRecipeSerializer.class, RecipeInit::registerRecipes);
       
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        TileEntityTypesInit.TILE_ENTITY_TYPE.register(bus);
        ContainerTypesInit.CONTAINER_TYPES.register(bus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        GeckoLib.initialize();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        
        ScreenManager.register(ContainerTypesInit.FIRST_BLOCK_CONTAINER.get(), FirstBlockScreen::new);
        
        ClientRegistry.bindTileEntityRenderer(TileEntityTypesInit.BLENDER_TILE_ENTITY.get(), BlenderTileRenderer::new);
        RenderTypeLookup.setRenderLayer(BlockInit.BLENDER.get(), RenderType.translucent());
        
    }

  
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
