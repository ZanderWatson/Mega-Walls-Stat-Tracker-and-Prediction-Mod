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

public class PartyTrackerCommands extends CommandBase {
    Minecraft mc = Minecraft.getMinecraft();
    @Override
    public String getCommandName() { return "parties"; }

    @Override
    public String getCommandUsage(ICommandSender sender) { return "/parties"; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            // Grouping the names by the same tick number
            HashMap<Integer, ArrayList<String>> valueToKeys = new HashMap<Integer, ArrayList<String>>();
            for (Map.Entry<String, Integer> entry : PartyTracker.playerJoinsByTick.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                ArrayList<String> keyList = valueToKeys.get(value);
                if (keyList == null) {
                    keyList = new ArrayList<String>();
                    valueToKeys.put(value, keyList);
                }
                keyList.add(key);
            }
            // adding to "parties" array list
            ArrayList<ArrayList<String>> parties = new ArrayList<ArrayList<String>>();
            for (ArrayList<String> group : valueToKeys.values()) {
                if (group.size() >= 2) {
                    parties.add(group);
                }
            }
            List<String> playerNames = PlayerMessageHandler.getOnlinePlayersAsString();
            for (ArrayList<String> party : parties) {
                int partyMembersInGame = 0;
                EnumChatFormatting color = null;
                for (String name : playerNames) {
                    for (String partyMember : party) {
                        if (name.contains(partyMember)) {
                            partyMembersInGame ++;
                        }
                    }
                }
                if (partyMembersInGame >= party.size() - 1) {
                    if (!PlayerMessageHandler.inGame) {
                        mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + String.valueOf(party)));
                    } else {
                        for (String name : playerNames) {
                            if (name.contains(party.get(0))) {
                                String team = AlexdoruStringUtil.getLastColorCodeBefore(name, party.get(0));
                                if (team.equals("c")) {
                                    color = EnumChatFormatting.RED;
                                } else if (team.equals("9")){
                                    color = EnumChatFormatting.BLUE;
                                } else if (team.equals("a")) {
                                    color = EnumChatFormatting.GREEN;
                                } else if (team.equals("e")){
                                    color = EnumChatFormatting.YELLOW;
                                }
                            }
                        }
                        if (color != null) {
                            mc.thePlayer.addChatMessage(new ChatComponentText(color + String.valueOf(party)));
                        } else {
                            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + String.valueOf(party)));
                        }

                    }
                }
            }
        }
        if (args.length == 1) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("alexdoru") || subCommand.equalsIgnoreCase("alex")) {
                ArrayList<List<String>> parties = new ArrayList<List<String>>();
                for (Map.Entry<String, List<String>> entry : AlexdoruPartyDetector.partysMap.entrySet()) {
                    if (!parties.contains(entry.getValue())) {
                        parties.add(entry.getValue());
                    }
                }
                for (List<String> party : parties) {
                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + String.valueOf(party)));
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
