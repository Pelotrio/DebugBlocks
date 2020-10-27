package com.github.pelotrio.debugblocks.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TicksPerSecondMessage implements IMessage, IMessageHandler<TicksPerSecondMessage, IMessage> {

    private int tps;

    public TicksPerSecondMessage() {
    }

    public TicksPerSecondMessage(int tps) {
        this.tps = tps;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.tps = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.tps);
    }

    @Override
    public IMessage onMessage(TicksPerSecondMessage message, MessageContext ctx) {
        Minecraft.getMinecraft().player.sendChatMessage("Tps for Tick Block: " + message.tps);

        return null;
    }
}
