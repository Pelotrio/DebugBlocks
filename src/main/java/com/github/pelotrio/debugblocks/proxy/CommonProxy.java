package com.github.pelotrio.debugblocks.proxy;

import com.github.pelotrio.debugblocks.DebugBlocks;
import com.github.pelotrio.debugblocks.network.TicksPerSecondMessage;
import com.github.pelotrio.debugblocks.tile.TickBlockTile;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        int id = 0;
        DebugBlocks.network.registerMessage(TicksPerSecondMessage.class, TicksPerSecondMessage.class, id++, Side.CLIENT);

        GameRegistry.registerTileEntity(TickBlockTile.class, new ResourceLocation(DebugBlocks.MOD_ID, "tick_block_tile"));
    }

    public void init(FMLInitializationEvent e) {

    }
}
