package com.WeirdMushroom.mwpredictionmod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FKDHeadCommands extends CommandBase {
    Minecraft mc = Minecraft.getMinecraft();
    @Override
    public String getCommandName() { return "fkdhead"; }

    @Override
    public String getCommandUsage(ICommandSender sender) { return "/fkdhead"; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("toggle")) {
                FKDHead.fkdHeadToggle = !FKDHead.fkdHeadToggle;
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "FKD Head: " + EnumChatFormatting.LIGHT_PURPLE + FKDHead.fkdHeadToggle));
            } else if (subCommand.equalsIgnoreCase("playerfkds") || subCommand.equalsIgnoreCase("fkds")) {
                mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(FKDHead.playerFKDs)));
            }
        } else if (args.length == 2) {
            String subCommand1 = args[0]; String subCommand2 = args[1];
            if (subCommand1.equalsIgnoreCase("height")) {
                try {
                    FKDHead.textHeight = Float.parseFloat(subCommand2);
                } catch (NumberFormatException e) {
                    mc.thePlayer.addChatMessage(new ChatComponentText("Must be number"));
                }

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
