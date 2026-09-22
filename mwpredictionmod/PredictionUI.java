package com.WeirdMushroom.mwpredictionmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;

public class PredictionUI {
    Minecraft mc = Minecraft.getMinecraft();
    private final File tempPlayerListFile = new File(mc.mcDataDir, "config/TempPlayerData.txt");
    public static int tickCounter = 1200;
    private String predictionTextRed = "";
    private String predictionTextBlue = "";
    private String predictionTextGreen = "";
    private String predictionTextYellow = "";
    public static int positionFromLeft = 565;
    public static int positionFromTop = 10;
    ScaledResolution sr = new ScaledResolution(mc);

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        FontRenderer font = mc.fontRendererObj;

        font.drawString(predictionTextRed, positionFromLeft, positionFromTop, 0xFFFFFF, true);
        font.drawString(predictionTextBlue, positionFromLeft, positionFromTop * 2, 0xFFFFFF, true);
        font.drawString(predictionTextGreen, positionFromLeft, positionFromTop * 3, 0xFFFFFF, true);
        font.drawString(predictionTextYellow, positionFromLeft, positionFromTop * 4, 0xFFFFFF, true);
    }
    @SubscribeEvent
    public void OnClientTick(TickEvent.ClientTickEvent event) {
        try {
            if (PlayerMessageHandler.autoPredict && PlayerMessageHandler.statsUpdated) {
                if (event.phase == TickEvent.Phase.END) {
                    tickCounter--;
                    if (tickCounter <= 0) {
                        PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String entirePrediction = "";
                                if (ScoreboardReader.inDeathmatch) {
                                    entirePrediction = PredictionCommandHandler.predictFKS();
                                } else {
                                    entirePrediction = DataAnalyzer.runPythonScript(2);
                                }
                                predictionTextRed = EnumChatFormatting.RED + entirePrediction.substring(entirePrediction.indexOf("R"), entirePrediction.indexOf("R") + 5) + "    " + EnumChatFormatting.GOLD + entirePrediction.substring(entirePrediction.indexOf("R") + 5, entirePrediction.indexOf("R") + 10);
                                predictionTextBlue = EnumChatFormatting.BLUE + entirePrediction.substring(entirePrediction.indexOf("B"), entirePrediction.indexOf("B") + 6) + "   " + EnumChatFormatting.GOLD + entirePrediction.substring(entirePrediction.indexOf("B") + 6, entirePrediction.indexOf("B") + 11);
                                predictionTextGreen = EnumChatFormatting.GREEN + entirePrediction.substring(entirePrediction.indexOf("G"), entirePrediction.indexOf("G") + 7) + " " + EnumChatFormatting.GOLD + entirePrediction.substring(entirePrediction.indexOf("G") + 7, entirePrediction.indexOf("G") + 12);
                                predictionTextYellow = EnumChatFormatting.YELLOW + entirePrediction.substring(entirePrediction.indexOf("Y"), entirePrediction.indexOf("Y") + 8) + " " + EnumChatFormatting.GOLD + entirePrediction.substring(entirePrediction.indexOf("Y") + 8, entirePrediction.indexOf("Y") + 13);
                                PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                            }
                        }).start();
                        tickCounter = 600;
                    }
                }
            } else {
                predictionTextRed = EnumChatFormatting.DARK_RED + "Not predicting";
                predictionTextBlue = "";
                predictionTextGreen = "";
                predictionTextYellow = "";
            }
        } catch (Exception e) {
            if (mc.thePlayer != null) {
                mc.thePlayer.addChatMessage(new ChatComponentText("Error in PredictionUI: OnClientTick - \"" + e.getMessage() + "\""));
            }
        }


    }

}
