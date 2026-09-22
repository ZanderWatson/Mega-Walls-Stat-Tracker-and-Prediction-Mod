package com.WeirdMushroom.mwpredictionmod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class SetVariableCommands extends CommandBase {
    private final Minecraft mc = Minecraft.getMinecraft();
    @Override
    public String getCommandName() {
        return "setvar";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/setvar (variable)";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 2) {
            String subCommand1 = args[0];
            String subCommand2 = args[1];
            if (subCommand1.contains("mid")) {
                if (subCommand2.equalsIgnoreCase("false")) {
                    PlayerMessageHandler.midGamePlayersSaved = false;
                } else if (subCommand2.equalsIgnoreCase("true")) {
                    PlayerMessageHandler.midGamePlayersSaved = true;
                }
                mc.thePlayer.addChatMessage(new ChatComponentText("midGamePlayersSaved = " + PlayerMessageHandler.midGamePlayersSaved));
            }
            if (subCommand1.equalsIgnoreCase("statsupdated") || subCommand1.equalsIgnoreCase("stats")) {
                if (subCommand2.equalsIgnoreCase("false")) {
                    PlayerMessageHandler.statsUpdated = false;
                } else if (subCommand2.equalsIgnoreCase("true")) {
                    PlayerMessageHandler.statsUpdated = true;
                }
                mc.thePlayer.addChatMessage(new ChatComponentText("statsUpdated = " + PlayerMessageHandler.statsUpdated));
            }
            if (subCommand1.equalsIgnoreCase("uitickcounter")) {
                try {
                    PredictionUI.tickCounter = Integer.parseInt(subCommand2);
                } catch (NumberFormatException e) {
                    mc.thePlayer.addChatMessage(new ChatComponentText("Must be number"));
                }
                mc.thePlayer.addChatMessage(new ChatComponentText("UI tickCounter = " + PredictionUI.tickCounter));
            }
            if (subCommand1.equalsIgnoreCase("ingame")) {
                try {
                    PlayerMessageHandler.inGame = Boolean.parseBoolean(subCommand2);
                } catch (NumberFormatException e) {
                    mc.thePlayer.addChatMessage(new ChatComponentText("Must be true/false"));
                }
                mc.thePlayer.addChatMessage(new ChatComponentText("inGame = " + PlayerMessageHandler.inGame));
            }

        }
    }
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
