package com.github.pelotrio.debugblocks.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashMap;
import java.util.Map;

public class TicksPerSecondMessage implements IMessage, IMessageHandler<TicksPerSecondMessage, IMessage> {

    private int tps;
    private BlockPos pos;

    //pos -> ticks per second
    public static final Map<BlockPos, Integer> TICK_MAP = new HashMap<>();

    public TicksPerSecondMessage() {
    }

    public TicksPerSecondMessage(BlockPos pos, int tps) {
        this.pos = pos;
        this.tps = tps;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.tps = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
        buf.writeInt(this.tps);
    }

    @Override
    public IMessage onMessage(TicksPerSecondMessage message, MessageContext ctx) {
        TICK_MAP.put(message.pos, message.tps);

        return null;
    }
}
