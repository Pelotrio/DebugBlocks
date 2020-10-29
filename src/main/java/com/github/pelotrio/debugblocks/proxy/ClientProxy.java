package com.github.pelotrio.debugblocks.proxy;

import com.github.pelotrio.debugblocks.render.TickBlockTileRenderer;
import com.github.pelotrio.debugblocks.tile.TickBlockTile;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ClientRegistry.bindTileEntitySpecialRenderer(TickBlockTile.class, new TickBlockTileRenderer());
    }
}
