package com.WeirdMushroom.mwpredictionmod;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;

import java.util.LinkedList;
import java.util.Queue;

public class DelayedMessageHandler {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private static class DelayedMessage {
        String message;
        int ticksRemaining;

        DelayedMessage(String message, int delay) {
            this.message = message;
            this.ticksRemaining = delay;
        }
    }

    private static final Queue<DelayedMessage> messageQueue = new LinkedList<DelayedMessage>();

    public static void requestDelayedMessage(String message, int delay) {
        messageQueue.add(new DelayedMessage(message, delay));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (messageQueue.isEmpty()) return;

        // Process only the front of the queue
        DelayedMessage current = messageQueue.peek();
        current.ticksRemaining--;

        if (current.ticksRemaining <= 0) {
            mc.thePlayer.sendChatMessage(current.message);
            messageQueue.poll(); // Remove the message from the queue
        }
    }
}