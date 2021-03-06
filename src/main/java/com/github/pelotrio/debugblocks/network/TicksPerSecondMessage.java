package com.github.pelotrio.debugblocks.network;

import com.github.pelotrio.debugblocks.tile.TickBlockTile;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TicksPerSecondMessage implements IMessage, IMessageHandler<TicksPerSecondMessage, IMessage> {

    private int tps;
    private int average;
    private BlockPos pos;

    public TicksPerSecondMessage() {
    }

    public TicksPerSecondMessage(BlockPos pos, int tps, int average) {
        this.pos = pos;
        this.tps = tps;
        this.average = average;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.tps = buf.readInt();
        this.average = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
        buf.writeInt(this.tps);
        buf.writeInt(this.average);
    }

    @Override
    public IMessage onMessage(TicksPerSecondMessage message, MessageContext ctx) {
        World world = Minecraft.getMinecraft().world;

        if (!world.isBlockLoaded(message.pos))
            return null;

        TileEntity te = world.getTileEntity(message.pos);
        if (!(te instanceof TickBlockTile))
            return null;

        ((TickBlockTile) te).updateData(message.average, message.tps);

        return null;
    }
}
