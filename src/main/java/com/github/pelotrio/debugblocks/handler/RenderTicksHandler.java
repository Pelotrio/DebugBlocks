package com.github.pelotrio.debugblocks.handler;

import com.github.pelotrio.debugblocks.network.TicksPerSecondMessage;
import com.github.pelotrio.debugblocks.render.HudRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Iterator;
import java.util.Map;

public class RenderTicksHandler {

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        World world = Minecraft.getMinecraft().player.world;
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

        double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * e.getPartialTicks();
        double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * e.getPartialTicks();
        double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * e.getPartialTicks();

        float f = renderManager.playerViewY;
        float f1 = renderManager.playerViewX;
        if (renderManager.options == null)
            return;

        boolean flag1 = renderManager.options.thirdPersonView == 2;

        for (Iterator<Map.Entry<BlockPos, Pair<Integer, Integer>>> iterator = TicksPerSecondMessage.TICK_MAP.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<BlockPos, Pair<Integer, Integer>> entry = iterator.next();

            BlockPos pos = entry.getKey();
            if (!world.isBlockLoaded(pos)) {
                iterator.remove();
                continue;
            }

            if (world.getTileEntity(pos) != null) {
                HudRenderHelper.drawNameplate(Minecraft.getMinecraft().fontRenderer,
                        entry.getValue().getLeft() + "/" + entry.getValue().getRight(),
                        (float) (pos.getX() - doubleX) + 0.5f,
                        (float) (pos.getY() - doubleY) + 1.5f,
                        (float) (pos.getZ() - doubleZ) + 0.5f,
                        0,
                        f,
                        f1,
                        flag1,
                        false);
            } else {
                iterator.remove();
            }
        }
    }

    @SubscribeEvent
    public void onWorldChange(PlayerEvent.PlayerChangedDimensionEvent e) {
        TicksPerSecondMessage.TICK_MAP.clear();
    }
}
