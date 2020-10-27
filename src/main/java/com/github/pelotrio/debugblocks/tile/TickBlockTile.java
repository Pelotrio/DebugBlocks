package com.github.pelotrio.debugblocks.tile;

import com.github.pelotrio.debugblocks.DebugBlocks;
import com.github.pelotrio.debugblocks.network.TicksPerSecondMessage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TickBlockTile extends TileEntity implements ITickable {

    private int ticks;
    private long timeStamp;

    public TickBlockTile() {
        this.ticks = 0;
        this.timeStamp = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (world.isRemote)
            return;

        ticks++;

        if ((System.currentTimeMillis() - timeStamp) >= 1000) {
            DebugBlocks.network.sendToAllAround(new TicksPerSecondMessage(this.getPos(), ticks),
                    new NetworkRegistry.TargetPoint(this.world.provider.getDimension(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 25));
            ticks = 0;
            timeStamp = System.currentTimeMillis();
        }
    }
}
