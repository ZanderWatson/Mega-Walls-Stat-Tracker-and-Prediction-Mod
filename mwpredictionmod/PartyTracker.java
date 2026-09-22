package com.WeirdMushroom.mwpredictionmod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;

public class PartyTracker {
    public static HashMap<String, Integer> playerJoinsByTick = new HashMap<String, Integer>();
    Minecraft mc = Minecraft.getMinecraft();
    int tickCounter = 0;
    private static final String[] WINNER_MESSAGES = {
            "Winner - Yellow Team",
            "Winner - Red Team",
            "Winner - Blue Team",
            "Winner - Green Team"
    };
    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText().replaceAll("(?i)[&§][0-9A-Z]", "");
        if (message.contains("has joined")) {
            String name = GetName(message);
            if (!playerJoinsByTick.containsKey(name)) {
                playerJoinsByTick.put(name, tickCounter);
                AlexdoruPartyDetector.onPlayerJoin(name, System.currentTimeMillis());
            }

        }
        for (String winnermessage : WINNER_MESSAGES) {
            if (message.contains(winnermessage)) {
                tickCounter = 0;
                playerJoinsByTick.clear();
            }
        }
    }
    @SubscribeEvent
    public void OnClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && mc.thePlayer != null && mc.theWorld != null) {
            tickCounter += 1;
        }
    }

    private String GetName(String message) {
        return message.substring(0, message.indexOf(" "));
    }
}
