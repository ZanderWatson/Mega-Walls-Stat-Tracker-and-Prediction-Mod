package com.WeirdMushroom.mwpredictionmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.*;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class FKDHead {
    public static boolean fkdHeadToggle = true;
    public static float  textHeight = 1f;
    public final static float  textHeightLowerSneak = 0.5f;
    public final static float  textHeightLower = 0.75f;
    public final static float  textHeightHigher = 1f;
    private static final Minecraft mc = Minecraft.getMinecraft();
    int updateFKDStatsTickCounter = 400;
    public static boolean haveSentUnformattablePlayersList = false;
    public static HashMap<String, Double> playerFKDs = new HashMap<String, Double>();
    @SubscribeEvent
    public void OnClientTick(TickEvent.ClientTickEvent event) throws IOException {
        if (event.phase == TickEvent.Phase.END && mc.thePlayer != null && mc.theWorld != null && fkdHeadToggle) {
            if (PlayerMessageHandler.inGame) {
                updateFKDStatsTickCounter--;
                if (updateFKDStatsTickCounter <= 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // §9gogo_dj §7[§7A§7R§7C§7]§r
                            String input = "";
                            ArrayList<String> unformattedPlayers = new ArrayList<String>();
                            for (String player : PlayerMessageHandler.getOnlinePlayersAsString()) {
                                String playerName = StringUtil.removeFormattingCodes(player).replace("?", "").replace("*", "").replace("[S]", "").trim();
                                try {
                                    input += playerName.substring(0, playerName.indexOf(" ")) + ",";
                                } catch (IndexOutOfBoundsException e) {
                                    unformattedPlayers.add(playerName);
                                }
                            }
                            if (!haveSentUnformattablePlayersList && unformattedPlayers.size() > 0) {
                                mc.thePlayer.addChatMessage(new ChatComponentText(unformattedPlayers.toString()));
                                haveSentUnformattablePlayersList = true;
                            }
                            String result = DataAnalyzer.runPythonScript(6, input);
                            for (String text : result.split(",")) {
                                try {
                                    playerFKDs.put(text.substring(0, text.indexOf(" ")), Double.parseDouble(text.substring(text.indexOf(" ") + 1)));
                                } catch (NumberFormatException e) {
                                    mc.thePlayer.addChatMessage(new ChatComponentText("FKDHead error: parsing player FKDs from python input"));
                                }
                            }

                        }
                    }).start();
                    updateFKDStatsTickCounter = 400;
                }
            }

        }
    }
    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Specials.Post<EntityLivingBase> event) {
        if (mc.thePlayer != null && mc.theWorld != null && fkdHeadToggle) {
            if (!(event.entity instanceof EntityPlayer)) return;

            EntityPlayer player = (EntityPlayer) event.entity;
            String plainPlayerName = player.getGameProfile().getName();

            // Don't render for yourself
            if (player == mc.thePlayer) return;

            // Push to the position above the player's head based on distance and crouching
            if (player.isSneaking()) {
                 textHeight = textHeightLowerSneak;
            } else if (mc.thePlayer.getDistanceToEntity(player) > 10) {
                textHeight = textHeightLower;
            } else {
                textHeight = textHeightHigher;
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(
                    (float) event.x,
                    (float) event.y + player.height + textHeight, // above head
                    (float) event.z
            );

            // Rotate to face the camera
            RenderManager renderManager = mc.getRenderManager();
            GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-0.025F, -0.025F, 0.025F); // text size

            // Render the text
            String text;
            EnumChatFormatting color = null;
            if (playerFKDs.containsKey(plainPlayerName)) {
                if (playerFKDs.get(plainPlayerName) < 1) {
                    color = EnumChatFormatting.RED;
                } else if (playerFKDs.get(plainPlayerName) > 1 && playerFKDs.get(plainPlayerName) < 2) {
                    color = EnumChatFormatting.LIGHT_PURPLE;
                } else if (playerFKDs.get(plainPlayerName) > 2) {
                    color = EnumChatFormatting.DARK_GREEN;
                }
                if (color != null) {
                    text = EnumChatFormatting.GOLD + "FKD: " + color + ((double) (Math.round(playerFKDs.get(plainPlayerName) * 100))) / 100d;
                } else {
                    text = "";
                }
                if (playerFKDs.get(plainPlayerName) == -1) { text = ""; }
            } else {
                text = "";
            }

            FontRenderer fontRenderer = mc.fontRendererObj;
            int textWidth = fontRenderer.getStringWidth(text) / 2;

            // Draw background for visibility
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            fontRenderer.drawString(text, -textWidth, 0, 0xFFFFFF);
            GlStateManager.enableLighting();

            GlStateManager.popMatrix();
        }
    }
}
