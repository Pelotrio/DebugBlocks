package com.github.pelotrio.debugblocks.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class TicksPerSecondMessage implements IMessage, IMessageHandler<TicksPerSecondMessage, IMessage> {

    private int tps;
    private int average;
    private BlockPos pos;

    //pos -> ticks per second | average
    public static final Map<BlockPos, Pair<Integer, Integer>> TICK_MAP = new HashMap<>();

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
        TICK_MAP.put(message.pos, Pair.of(message.tps, message.average));

        return null;
    }
}
