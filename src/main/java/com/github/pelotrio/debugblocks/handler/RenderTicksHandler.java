package com.github.pelotrio.debugblocks.handler;

import com.github.pelotrio.debugblocks.network.TicksPerSecondMessage;
import com.github.pelotrio.debugblocks.render.HudRenderHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Iterator;
import java.util.Map;

public class RenderTicksHandler {

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        World world = Minecraft.getMinecraft().player.world;
        EntityPlayerSP player = Minecraft.getMinecraft().player;

        double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * e.getPartialTicks();
        double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * e.getPartialTicks();
        double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * e.getPartialTicks();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-doubleX, -doubleY, -doubleZ);

        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        for (Iterator<Map.Entry<BlockPos, Integer>> iterator = TicksPerSecondMessage.TICK_MAP.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<BlockPos, Integer> entry = iterator.next();

            BlockPos pos = entry.getKey();
            if (!world.isBlockLoaded(pos)) {
                iterator.remove();
                continue;
            }

            if (world.getTileEntity(pos) != null) {
                HudRenderHelper.renderHud(
                        Lists.newArrayList(entry.getValue() + ""),
                        HudRenderHelper.HudPlacement.HUD_ABOVE,
                        HudRenderHelper.HudOrientation.HUD_TOPLAYER,
                        EnumFacing.NORTH,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        5f);
            } else {
                iterator.remove();
            }
        }

        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    @SubscribeEvent
    public void onWorldChange(PlayerEvent.PlayerChangedDimensionEvent e) {
        TicksPerSecondMessage.TICK_MAP.clear();
    }
}
