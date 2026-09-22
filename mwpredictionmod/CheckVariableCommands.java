package com.WeirdMushroom.mwpredictionmod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CheckVariableCommands extends CommandBase {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public String getCommandName() { return "check"; }

    @Override
    public String getCommandUsage(ICommandSender sender) { return "/check <variable>"; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 1) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("redwither")) { if (!PlayerMessageHandler.redWitherDead) {mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Alive"));} else {mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "Dead"));} }

            if (subCommand.equalsIgnoreCase("bluewither")) { if (!PlayerMessageHandler.blueWitherDead) {mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Alive"));} else {mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "Dead"));} }

            if (subCommand.equalsIgnoreCase("greenwither")) { if (!PlayerMessageHandler.greenWitherDead) {mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Alive"));} else {mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "Dead"));} }

            if (subCommand.equalsIgnoreCase("yellowwither")) { if (!PlayerMessageHandler.yellowWitherDead) {mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Alive"));} else {mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "Dead"));} }

            if (subCommand.equalsIgnoreCase("midgamesave")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(PlayerMessageHandler.midGamePlayersSaved))); }

            if (subCommand.equalsIgnoreCase("ingame")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(PlayerMessageHandler.inGame))); }

            if (subCommand.equalsIgnoreCase("fkmap")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(PlayerMessageHandler.finalKillsByPlayers))); }

            if (subCommand.equalsIgnoreCase("deathmap")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(PlayerMessageHandler.deathsByPlayers))); }

            if (subCommand.equalsIgnoreCase("redplayers")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(ScoreboardReader.redPlayers))); }

            if (subCommand.equalsIgnoreCase("blueplayers")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(ScoreboardReader.bluePlayers))); }

            if (subCommand.equalsIgnoreCase("greenplayers")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(ScoreboardReader.greenPlayers))); }

            if (subCommand.equalsIgnoreCase("yellowplayers")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(ScoreboardReader.yellowPlayers))); }

            if (subCommand.equalsIgnoreCase("uitickcounter")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(PredictionUI.tickCounter))); }

            if (subCommand.equalsIgnoreCase("playerjointick")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(PartyTracker.playerJoinsByTick))); }

            //if (subCommand.equalsIgnoreCase("")) { mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf())); }

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
