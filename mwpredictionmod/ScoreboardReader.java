package com.WeirdMushroom.mwpredictionmod;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ScoreboardReader {
    public static boolean inDeathmatch = false;
    public static int redPlayers = 0;
    public static int bluePlayers = 0;
    public static int greenPlayers = 0;
    public static int yellowPlayers = 0;
    Minecraft mc = Minecraft.getMinecraft();
    private final File midGamePlayerListFile = new File(mc.mcDataDir, "config/MidGamePlayerData.txt");
    String line = "";
    @SubscribeEvent
    public void OnClientTick(TickEvent.ClientTickEvent event) throws IOException {
        if (event.phase == TickEvent.Phase.END && mc.thePlayer != null && mc.theWorld != null) {
            Scoreboard scoreboard = mc.theWorld.getScoreboard();
            ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
            if (objective != null) {
                Collection<Score> scores = scoreboard.getSortedScores(objective);
                List<Score> filteredScores = new ArrayList<Score>();
                for (Object obj : scores) {
                    if (obj instanceof Score) {
                        Score score = (Score) obj;
                        String entry = score.getPlayerName();

                        if (entry != null && !entry.startsWith("#")) {
                            filteredScores.add(score);
                        }
                    }
                }
                boolean scoreboardContainsInGameString = false;
                for (Score score : filteredScores) {
                    String entry = score.getPlayerName();
                    ScorePlayerTeam team = scoreboard.getPlayersTeam(entry);
                    line = "";
                    if (team != null) {
                        line += team.getColorPrefix();
                    }
                    line += entry;
                    if (team != null) {
                        line += team.getColorSuffix();
                    }
                    line = line.replaceAll("(?i)[&§][0-9A-Z]", "");
                    if (PlayerMessageHandler.inGame && !PlayerMessageHandler.midGamePlayersSaved) {
                        if (line.contains("Game End")) {
                            if (line.contains("14") && line.contains("00")) {
                                PlayerMessageHandler.savePlayerListToFile(midGamePlayerListFile);
                                PlayerMessageHandler.midGamePlayersSaved = true;
                                mc.thePlayer.addChatMessage(new ChatComponentText( EnumChatFormatting.DARK_GREEN + "Mid-game save players completed."));
                                try {
                                    UpdateFKSWithHashMap();
                                    mc.thePlayer.addChatMessage(new ChatComponentText( EnumChatFormatting.DARK_GREEN + "Updated final kills with hashmap"));
                                } catch (Exception e) {
                                    mc.thePlayer.addChatMessage(new ChatComponentText(e.toString()));
                                }
                            }
                        }
                        // if at 16 players save mid game player list
                        if (PlayerMessageHandler.getOnlinePlayersWithDisplayName().size() <= 16 && (line.contains("Game End") || line.contains("Enrage Off"))) {
                            PlayerMessageHandler.savePlayerListToFile(midGamePlayerListFile);
                            PlayerMessageHandler.midGamePlayersSaved = true;
                            mc.thePlayer.addChatMessage(new ChatComponentText( EnumChatFormatting.DARK_GREEN + "Mid-game save players completed."));
                            try {
                                UpdateFKSWithHashMap();
                            } catch (Exception e) {
                                mc.thePlayer.addChatMessage(new ChatComponentText(e.toString()));
                            }
                            mc.thePlayer.addChatMessage(new ChatComponentText( EnumChatFormatting.DARK_GREEN + "Updated final kills with hashmap"));
                        }
                    }
                    if (line.contains("[R]")) {
                        if (line.contains("Wither")) { PlayerMessageHandler.redWitherDead = false; }
                        if (line.contains("Players")) {
                            PlayerMessageHandler.redWitherDead = true;
                            Pattern pattern = Pattern.compile("\\d+");
                            Matcher matcher = pattern.matcher(line);
                            if (matcher.find()) {
                                redPlayers = Integer.parseInt(matcher.group());
                            }
                        }
                    }
                    if (line.contains("[B]")) {
                        if (line.contains("Wither")) { PlayerMessageHandler.blueWitherDead = false; }
                        if (line.contains("Players")) {
                            PlayerMessageHandler.blueWitherDead = true;
                            Pattern pattern = Pattern.compile("\\d+");
                            Matcher matcher = pattern.matcher(line);
                            if (matcher.find()) {
                                bluePlayers = Integer.parseInt(matcher.group());
                            }
                        }
                    }
                    if (line.contains("[G]")) {
                        if (line.contains("Wither")) { PlayerMessageHandler.greenWitherDead = false; }
                        if (line.contains("Players")) {
                            PlayerMessageHandler.greenWitherDead = true;
                            Pattern pattern = Pattern.compile("\\d+");
                            Matcher matcher = pattern.matcher(line);
                            if (matcher.find()) {
                                greenPlayers = Integer.parseInt(matcher.group());
                            }
                        }
                    }
                    if (line.contains("[Y]")) {
                        if (line.contains("Wither")) { PlayerMessageHandler.yellowWitherDead = false; }
                        if (line.contains("Players")) {
                            PlayerMessageHandler.yellowWitherDead = true;
                            Pattern pattern = Pattern.compile("\\d+");
                            Matcher matcher = pattern.matcher(line);
                            if (matcher.find()) {
                                yellowPlayers = Integer.parseInt(matcher.group());
                            }
                        }
                    }
                    if (line.contains("Game End") || line.contains("Enrage Off") || line.contains("Walls Fall")) {
                        scoreboardContainsInGameString = true;
                    }
                }
                PlayerMessageHandler.inGame = scoreboardContainsInGameString;
            }
            inDeathmatch = PlayerMessageHandler.redWitherDead && PlayerMessageHandler.blueWitherDead && PlayerMessageHandler.greenWitherDead && PlayerMessageHandler.yellowWitherDead;
        }
    }

    // Get index of last game, split list into the game before and current game, then only update names for the current game
    public void UpdateFKSWithHashMap() throws IOException {
        Set<Map.Entry<String, Integer>> fkPlayersMap = PlayerMessageHandler.finalKillsByPlayers.entrySet();
        for (Map.Entry<String, Integer> entry : fkPlayersMap) {
            List<String> lines = new ArrayList<String>();
            int lastOccurrenceIndex = -1;
            int lastGameOccurrenceIndex = -1;
            // Read the file and find the last occurrence of the target name
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(midGamePlayerListFile));
                String line;
                while ((line = reader.readLine()) != null) { // go through all the lines of the file
                    if (line.contains("FK")) {
                        lines.add(line.substring(0, line.length() - 1)); // Add the line to the list without last "|"
                    } else {
                        lines.add(line);
                    }
                    if (line.contains(entry.getKey())) {
                        lastOccurrenceIndex = lines.size() - 1; // Update last occurrence index
                    }
                    if (line.contains("Player list")) {
                        lastGameOccurrenceIndex = lines.size() - 1;
                    }
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }

            // If the target name was found, increment the count in its last occurrence
            if (lastOccurrenceIndex != -1 && lastGameOccurrenceIndex < lastOccurrenceIndex) {
                String lastOccurrenceLine = lines.get(lastOccurrenceIndex);
                String[] parts = lastOccurrenceLine.split(":");
                String name = parts[0];
                int count = entry.getValue(); // Set final kills of players

                lines.set(lastOccurrenceIndex, name + ":" + count); // Update the line in the list
            }

            // Write the updated content back to the file
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(midGamePlayerListFile));
                for (String updatedLine : lines) {
                    if (updatedLine.contains("FK")) {
                        writer.write(updatedLine + "|");
                    } else {
                        writer.write(updatedLine);
                    }
                    writer.newLine();

                }
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }

    }

}
