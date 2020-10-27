package com.github.pelotrio.debugblocks.tile;

import com.github.pelotrio.debugblocks.DebugBlocks;
import com.github.pelotrio.debugblocks.network.TicksPerSecondMessage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TickBlockTile extends TileEntity implements ITickable {

    private int ticks;
    private long timeStamp;

    public TickBlockTile() {
        this.ticks = 0;
        this.timeStamp = System.currentTimeMillis();
    }

    @Override
    public void update() {
        ticks++;

        if ((System.currentTimeMillis() - timeStamp) >= 1000) {
            DebugBlocks.network.sendToAll(new TicksPerSecondMessage(ticks));
            System.out.println(ticks);
            ticks = 0;
            timeStamp = System.currentTimeMillis();
        }
    }

    public int getTicks() {
        return ticks;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
