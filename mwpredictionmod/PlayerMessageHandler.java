package com.WeirdMushroom.mwpredictionmod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.*;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


// Trigger message, "the walls have come down! fight!"
// All withers are dead! 1 second until deathmatch!
// The Green Wither has died!
// "<WeirdMushroom> test"
// Your wither has died. You can no longer respawn!
public class PlayerMessageHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private final File playerListFile;
    private final File tempPlayerListFile;
    private final File midGamePlayerListFile;

    public static boolean midGamePlayersSaved = false;

    public static boolean inGame = false;
    public static boolean autoPredict = false;
    public static boolean statsUpdated = false;

    public static boolean redWitherDead = false;
    public static boolean greenWitherDead = false;
    public static boolean blueWitherDead = false;
    public static boolean yellowWitherDead = false;
    public static boolean deathMatchDamageCounts = false;

    public static HashMap<String, Integer> finalKillsByPlayers = new HashMap<String, Integer>();
    public static HashMap<String, Boolean> deathsByPlayers = new HashMap<String, Boolean>();

    private static final String GAME_START_MESSAGE = "prepare your defenses!";
    private static final String DM_START_MESSAGE = "All withers are dead! 1 second until deathmatch!";
    private static final String TRIGGER_MESSAGE = "Withers no longer enraged! (Special attacks now disabled)";
    private static final String[] FINAL_KILL_MESSAGES = {
            " was shot and killed by ", " was snowballed to death by ", " was killed by ", " was killed with a potion by ", " was killed with an explosion by ", " was killed with magic by ", " was blocked by ", " was put into cold storage by ", " was deleted by ", " was purged by an antivirus owned by ", " accidentally closed the game while fighting ",
            " had their computer switched off by ", " had their computer fried by ", " was struck down by ", " was turned to dust by ", " was turned to ash by ", " was melted by ", " was incinerated by ", " was vaporized by ", " was struck with Cupid's arrow by ", " was given the cold shoulder by ", " was hugged too hard by ", " drank a love potion from ", " was hit by a love bomb from ",
            " was no match for ", " was smote from afar by ", " was justly ended by ", " was purified by ", " was killed with holy water by ", " was dealt vengeful justice by ", " was returned to dust by ", " was glazed in BBQ sauce by ", " was sprinkled with chili powder by ", " was sliced up by ", " was overcooked by ", " was deep fried by ", " was boiled by", " be shot and killed by ", " be snowballed to death ",
            " be sent to Davy Jones' locker by ", " be killed with rum by ", " be shot with cannon by ", " be killed with magic by ", " was filled full of lead by ", " was iced by ", " met their end by ", " lost a drinking contest with ", " was killed with dynamite by ", " lost the draw to ", " got banana pistol'd by ", " was peeled by ", " was mushed by ", " was hit by a banana split from ", " was killed by an explosive banana from ", " was killed by a magic banana from ",
            " was turned into mush by ", " was squeaked from a distance by ", " was hit by frozen cheese from ", " was chewed up by ", " was chemically cheesed by ", " was turned into cheese whiz by ", " was magically squeaked by ", " was hit by a flying bunny by ", " was hit by a bunny thrown by ", " was turned into a carrot by ", " was hit by a carrot from ", " was bitten by a bunny from ", " was magically turned into a bunny by ", " was fed to a bunny by "
    };
    private static final String[] NATURAL_DEATH_MESSAGES = {
            " starved to death.", " hit the ground too hard.", " blew up.", " exploded.", " tried to swim in lava.", " went up in flames.", " burned to death.", " suffocated in a wall.", " suffocated.", " fell out of the world.", " had a block fall on them.", " drowned.", " died from a cactus."
    };
    private static final String[] ABILITY_NAMES = {
            "Eagle's Eye", "From the Depths", "Arcane Beam", "Arcane Explosion", "Shadow Burst", "Iron Punch", "Wrath", "Burning Soul", "Immolating Burst", "Tornado", "Wolf Pack", "Squid Splash", "Detonate", "Fission Heart", "Cannon Fire", "Angry Birds", "Dyed Disguise", "Explosive Arrow", "Leap", "Venom Strike", "Lycanthropy", "Shadow Cloak", "EMP", "Dig", "Sun Ray", "Scorching Breath", "Hellfire", "Frenzied Flames", "Rend", "Ice Bolt"
    };
    private static final String[] WINNER_MESSAGES = {
            "Winner - Yellow Team",
            "Winner - Red Team",
            "Winner - Blue Team",
            "Winner - Green Team"
    };

    private String winner = "No winner determined";

    public PlayerMessageHandler() {
        // Create the file object pointing to the desired save location
        playerListFile = new File(mc.mcDataDir, "config/PlayerData.txt");
        tempPlayerListFile = new File(mc.mcDataDir, "config/TempPlayerData.txt");
        midGamePlayerListFile = new File(mc.mcDataDir, "config/MidGamePlayerData.txt");
    }

    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) throws IOException {
        String message = stripFormatting(event.message.getUnformattedText());
        String formattedMessage = event.message.getFormattedText();
        // replace all remaining styles using regex
        message = message.replaceAll("(?i)[&§][0-9A-Z]", "");

        if (message.equalsIgnoreCase(GAME_START_MESSAGE)) {
            // Update player stats list when game starts and start auto-prediction
            savePlayerListToFile(tempPlayerListFile);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DataAnalyzer.runPythonScript(3);
                    removeTextInFile(tempPlayerListFile);
                    mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "Stats updated"));
                    statsUpdated = true;
                }
            }).start();
            PredictionUI.tickCounter = 1200;
            inGame = true; autoPredict = true; midGamePlayersSaved = false; deathMatchDamageCounts = false; FKDHead.haveSentUnformattablePlayersList = false;
            finalKillsByPlayers.clear(); deathsByPlayers.clear();
        }
        if (message.equalsIgnoreCase(DM_START_MESSAGE)) {
            deathMatchDamageCounts = true;
        }

        // Check for the enrage trigger message
        if (message.equalsIgnoreCase(TRIGGER_MESSAGE)) {
            savePlayerListToFile(playerListFile);
        }
        // Check for winner messages
        for (String winnerMessage : WINNER_MESSAGES) {
            if (message.toLowerCase().contains(winnerMessage.toLowerCase())) {
                if (!message.contains(":")) {
                    winner = winnerMessage;
                    writeWinner(playerListFile);
                    writeWinner(midGamePlayerListFile);
                    inGame = false;
                    autoPredict = false;
                    statsUpdated = false;
                    break;
                }
                deathMatchDamageCounts = false;
            }
        }
        // Check for prediction message
        if (message.contains("jarvis, predict the winner.")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = DataAnalyzer.runPythonScript(1);
                    DelayedMessageHandler.requestDelayedMessage("Jarvis: \"Prediction - " + result + "\"", 10);
                }
            }).start();
        }
        String killingPlayerName = "none";
        String deadPlayerName = "none";
        boolean killedByAbility = false;
        for (String killMessage : FINAL_KILL_MESSAGES) {
            if (message.contains(killMessage) && !message.contains(":")) {
                if (formattedMessage.charAt(3) == 'c' && redWitherDead) {
                    deadPlayerName = stripFormatting(message).substring(0, stripFormatting(message).indexOf(" "));
                    for (String abilityName : ABILITY_NAMES) {
                        if (stripFormatting(message).contains(abilityName)) {
                            killedByAbility = true;
                            String messageWithoutAbilityName = stripFormatting(message).substring(0, message.indexOf(abilityName) - 3);
                            killingPlayerName = messageWithoutAbilityName.substring(messageWithoutAbilityName.lastIndexOf(" ") + 1);
                            break;
                        }
                    }
                    if (!killedByAbility) {
                        killingPlayerName = stripFormatting(message).substring(stripFormatting(message).lastIndexOf(" ") + 1);
                    }
                    break;
                }
                else if (formattedMessage.charAt(3) == 'a' && greenWitherDead) {
                    deadPlayerName = stripFormatting(message).substring(0, stripFormatting(message).indexOf(" "));
                    for (String abilityName : ABILITY_NAMES) {
                        if (stripFormatting(message).contains(abilityName)) {
                            killedByAbility = true;
                            String messageWithoutAbilityName = stripFormatting(message).substring(0, message.indexOf(abilityName) - 3);
                            killingPlayerName = messageWithoutAbilityName.substring(messageWithoutAbilityName.lastIndexOf(" ") + 1);
                            break;
                        }
                    }
                    if (!killedByAbility) {
                        killingPlayerName = stripFormatting(message).substring(stripFormatting(message).lastIndexOf(" ") + 1);
                    }
                    break;
                }
                else if (formattedMessage.charAt(3) == '9' && blueWitherDead) {
                    deadPlayerName = stripFormatting(message).substring(0, stripFormatting(message).indexOf(" "));
                    for (String abilityName : ABILITY_NAMES) {
                        if (stripFormatting(message).contains(abilityName)) {
                            killedByAbility = true;
                            String messageWithoutAbilityName = stripFormatting(message).substring(0, message.indexOf(abilityName) - 3);
                            killingPlayerName = messageWithoutAbilityName.substring(messageWithoutAbilityName.lastIndexOf(" ") + 1);
                            break;
                        }
                    }
                    if (!killedByAbility) {
                        killingPlayerName = stripFormatting(message).substring(stripFormatting(message).lastIndexOf(" ") + 1);
                    }
                    break;
                }
                else if (formattedMessage.charAt(3) == 'e' && yellowWitherDead) {
                    deadPlayerName = stripFormatting(message).substring(0, stripFormatting(message).indexOf(" "));
                    for (String abilityName : ABILITY_NAMES) {
                        if (stripFormatting(message).contains(abilityName)) {
                            killedByAbility = true;
                            String messageWithoutAbilityName = stripFormatting(message).substring(0, message.indexOf(abilityName) - 3);
                            killingPlayerName = messageWithoutAbilityName.substring(messageWithoutAbilityName.lastIndexOf(" ") + 1);
                            break;
                        }
                    }
                    if (!killedByAbility) {
                        killingPlayerName = stripFormatting(message).substring(stripFormatting(message).lastIndexOf(" ") + 1);
                    }
                    break;
                }
            }
        }
        for (String deathMessage : NATURAL_DEATH_MESSAGES) {
            if (message.contains(deathMessage) && !message.contains(":")) {
                deadPlayerName = message.substring(0, stripFormatting(message).indexOf(" "));
                try {
                    updateDeath(deadPlayerName);
                    // Update dynamic hash map of deaths
                    deathsByPlayers.put(deadPlayerName, false);
                } catch (IOException e) {
                    mc.thePlayer.addChatMessage(new ChatComponentText("Failed to update player death"));
                }
            }
        }
        if (!killingPlayerName.equals("none")) {
            try {
                updateFinalKill(killingPlayerName);
                // Update dynamic hash map of final kills and dm final kills
                if (finalKillsByPlayers.containsKey(killingPlayerName)) {
                    finalKillsByPlayers.put(killingPlayerName, finalKillsByPlayers.get(killingPlayerName) + 1);
                } else {
                    finalKillsByPlayers.put(killingPlayerName, 1);
                }

            } catch (IOException e) {
                mc.thePlayer.addChatMessage(new ChatComponentText("Failed to update final kill"));
            }
            try {
                updateDeath(deadPlayerName);
                // Update dynamic hash map of deaths
                deathsByPlayers.put(deadPlayerName, false);

            } catch (IOException e) {
                mc.thePlayer.addChatMessage(new ChatComponentText("Failed to update player death"));
            }
        }
    }

    public static void sendPlayerListMessage() {
        List<IChatComponent> onlinePlayers = getOnlinePlayersWithDisplayName();
        if (onlinePlayers.isEmpty()) {
            mc.thePlayer.addChatMessage(new ChatComponentText("No players online."));
        } else {
            ChatComponentText message = new ChatComponentText("Players: ");
            for (IChatComponent playerName : onlinePlayers) {
                message.appendSibling(playerName).appendText(", ");
            }
            // Remove the trailing comma and space
            String messageText = message.getUnformattedText();
            if (messageText.endsWith(", ")) {
                message = new ChatComponentText(messageText.substring(0, messageText.length() - 2));
            }
            mc.thePlayer.addChatMessage(message);
        }
    }

    private void writeWinner(File fileToWriteTo) {
        BufferedWriter writer = null;
        try {
            // Open the file in append mode
            writer = new BufferedWriter(new FileWriter(fileToWriteTo, true));

            // Write the winner
            writer.write(winner);
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateFinalKill(String targetName) throws IOException {
        List<String> lines = new ArrayList<String>();
        int lastOccurrenceIndex = -1;
        // Read the file and find the last occurrence of the target name
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(playerListFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("FK")) {
                    lines.add(line.substring(0, line.length() - 1)); // Add the line to the list
                } else {
                    lines.add(line);
                }
                // Check if this line contains the target name
                String[] parts = line.split(":");
                String name = parts[0];
                if (name.contains(targetName)) {
                    lastOccurrenceIndex = lines.size() - 1; // Update last occurrence index
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        // If the target name was found, increment the count in its last occurrence
        if (lastOccurrenceIndex != -1) {
            String lastOccurrenceLine = lines.get(lastOccurrenceIndex);

            String[] parts = lastOccurrenceLine.split(":");
            String name = parts[0];
            int count = Integer.parseInt(parts[1]) + 1; // Increment the count

            lines.set(lastOccurrenceIndex, name + ":" + count); // Update the line in the list
        }

        // Write the updated content back to the file
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(playerListFile));
            for (String updatedLine : lines) {
                if (updatedLine.contains("FK")) {
                    writer.write(updatedLine + "|");
                    writer.newLine();
                } else {
                    writer.write(updatedLine);
                    writer.newLine();
                }

            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private void updateDeath(String targetName) throws IOException {
        List<String> lines = new ArrayList<String>();
        int lastOccurrenceIndex = -1;
        // Read the file and find the last occurrence of the target name
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(playerListFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("FK")) {
                    lines.add(line.substring(0, line.length() - 1)); // Add the line to the list
                } else {
                    lines.add(line);
                }
                // Example name: Name436|ALIVE||FK:0|
                // Check if this line contains the target name
                if (line.contains(targetName)) {
                    lastOccurrenceIndex = lines.size() - 1; // Update last occurrence index
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        // If the target name was found, replace alive with dead
        if (lastOccurrenceIndex != -1) {
            lines.set(lastOccurrenceIndex, lines.get(lastOccurrenceIndex).replace("|ALIVE|", "|DEAD|")); // Update the line in the list
            // Remove player from final kill list
            String lastOccurrenceLine = lines.get(lastOccurrenceIndex);
        }

        // Write the updated content back to the file
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(playerListFile));
            for (String updatedLine : lines) {
                if (updatedLine.contains("FK")) {
                    writer.write(updatedLine + "|");
                    writer.newLine();
                } else {
                    writer.write(updatedLine);
                    writer.newLine();
                }

            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    static void savePlayerListToFile(File fileToSaveTo) {
        BufferedWriter writer = null;
        try {
            // Open the file in append mode
            writer = new BufferedWriter(new FileWriter(fileToSaveTo, true));

            // Add a blank line to separate the lists
            writer.newLine();

            // Write a timestamp or any other indicator for the new list
            writer.write("Player list updated on: " + new java.util.Date());
            writer.newLine();

            // Write the list of player names
            List<String> playerNames = getOnlinePlayersAsString();
            for (String playerName : playerNames) {
                if (playerName.contains("[")) {
                    String playerName1 = playerName.substring(0, playerName.lastIndexOf("[") - 2);
                    String playerName2 = playerName.substring(playerName.lastIndexOf("[") - 2);
                    playerName2 = playerName2.replaceAll("[&§][9aec]", "");
                    playerName = playerName1 + playerName2;
                    playerName = playerName.replace("§e§l?", "|CHEAT|").replace("§4§l?", "|CHEAT|").replace("§4§l *", "|NICK|").replaceAll("[&§][9]", "|B|").replaceAll("[&§][a]", "|G|").replaceAll("[&§][e]", "|Y|").replaceAll("[&§][c]", "|R|").replaceAll("[&§][7]", "").replaceAll("[&§][r]", "").replaceAll("(?i)[&§][0-9A-Z]", "").replace("?", "|CHEAT|").replaceAll(".\\*", "|NICK|").replace("[S]", "");
                    writer.write(playerName + "|ALIVE|" + "|FK:0|");
                    writer.newLine();
                }
            }

            // Ensure that the last line has a new line character
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            mc.thePlayer.addChatMessage(new ChatComponentText("Error is savePlayerListToFile: " + e.getMessage()));
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static void removeTextInFile(File fileToRemoveText) {
        BufferedWriter writer = null;
        try {
            // Open the file in append mode
            writer = new BufferedWriter(new FileWriter(fileToRemoveText, false));
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String stripFormatting(String text) {
        return text.replaceAll("§.", ""); // Removes Minecraft color codes
    }

    public static List<IChatComponent> getOnlinePlayersWithDisplayName() {
        ArrayList<IChatComponent> players = new ArrayList<IChatComponent>();
        Collection<NetworkPlayerInfo> playerCollection = mc.getNetHandler().getPlayerInfoMap();

        for (NetworkPlayerInfo networkPlayerInfo : playerCollection) {
            IChatComponent displayName = networkPlayerInfo.getDisplayName();
            if (displayName != null) {
                players.add(displayName);
            } else {
                String plainName = networkPlayerInfo.getGameProfile().getName();
                ScorePlayerTeam team = mc.theWorld.getScoreboard().getPlayersTeam(plainName);
                if (team != null) {
                    String formattedName = team.formatString(plainName);
                    players.add(new ChatComponentText(formattedName));
                } else {
                    players.add(new ChatComponentText(plainName));
                }
            }
        }

        return players;
    }

    public static List<String> getOnlinePlayersAsString() {
        List<IChatComponent> playerComponents = getOnlinePlayersWithDisplayName();
        List<String> playerStrings = new ArrayList<String>();
        for (IChatComponent component : playerComponents) {
            playerStrings.add(component.getUnformattedText());
        }
        return playerStrings;
    }
}
