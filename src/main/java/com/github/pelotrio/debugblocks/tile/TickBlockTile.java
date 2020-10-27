package com.github.pelotrio.debugblocks.tile;

import com.github.pelotrio.debugblocks.DebugBlocks;
import com.github.pelotrio.debugblocks.network.TicksPerSecondMessage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TickBlockTile extends TileEntity implements ITickable {

    private int ticksPerSecond;
    private long timeStampSecond;

    private long totalTicks;
    private long counter;

    public TickBlockTile() {
        this.ticksPerSecond = 0;
        this.timeStampSecond = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (world.isRemote)
            return;

        ticksPerSecond++;

        if ((System.currentTimeMillis() - timeStampSecond) >= 1000) {
            DebugBlocks.network.sendToAllAround(
                    new TicksPerSecondMessage(
                            this.getPos(),
                            this.ticksPerSecond,
                            this.counter == 0 ? 0 : (int) Math.round(this.totalTicks / (double) this.counter)
                    ),
                    new NetworkRegistry.TargetPoint(
                            this.world.provider.getDimension(),
                            this.getPos().getX(),
                            this.getPos().getY(),
                            this.getPos().getZ(),
                            25
                    )
            );

            totalTicks += ticksPerSecond;
            counter++;

            ticksPerSecond = 0;
            timeStampSecond = System.currentTimeMillis();
        }
    }

    public void resetAverage() {
        this.counter = 0;
        this.totalTicks = 0;
    }
}
