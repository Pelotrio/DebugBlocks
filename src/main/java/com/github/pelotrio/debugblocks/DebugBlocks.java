package com.github.pelotrio.debugblocks;

import com.github.pelotrio.debugblocks.block.TickBlock;
import com.github.pelotrio.debugblocks.handler.RenderTicksHandler;
import com.github.pelotrio.debugblocks.network.TicksPerSecondMessage;
import com.github.pelotrio.debugblocks.tile.TickBlockTile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = DebugBlocks.MOD_ID,
        name = DebugBlocks.MOD_NAME,
        version = DebugBlocks.VERSION
)
public class DebugBlocks {

    public static final String MOD_ID = "debugblocks";
    public static final String MOD_NAME = "DebugBlocks";
    public static final String VERSION = "1.0-SNAPSHOT";

    @Mod.Instance(MOD_ID)
    public static DebugBlocks INSTANCE;

    public static Logger LOGGER;

    public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);


    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();

        MinecraftForge.EVENT_BUS.register(new RenderTicksHandler());

        int id = 0;
        network.registerMessage(TicksPerSecondMessage.class, TicksPerSecondMessage.class, id++, Side.CLIENT);

        GameRegistry.registerTileEntity(TickBlockTile.class, new ResourceLocation(MOD_ID, "tick_block_tile"));
    }

    /**
     * custom recipes
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    /**
     * Blocks
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {
        public static final TickBlock TICK_BLOCK = null;
    }

    /**
     * Items
     */
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Items {
        public static final ItemBlock TICK_BLOCK_ITEM = null;
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        /**
         * Listen for the register event for creating custom items
         */
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemBlock(Blocks.TICK_BLOCK).setRegistryName(Blocks.TICK_BLOCK.getRegistryName()));
        }

        /**
         * Listen for the register event for creating custom blocks
         */
        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new TickBlock().setRegistryName(new ResourceLocation(MOD_ID, "tick_block")).setTranslationKey("tick_block"));
        }

        @SubscribeEvent
        public static void registerRenders(ModelRegistryEvent event) {
            registerRender(Item.getItemFromBlock(Blocks.TICK_BLOCK));
        }

        public static void registerRender(Item item) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}
