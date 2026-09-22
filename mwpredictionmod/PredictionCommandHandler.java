package com.WeirdMushroom.mwpredictionmod;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PredictionCommandHandler extends CommandBase{
    private final Minecraft mc = Minecraft.getMinecraft();
    private final File tempPlayerListFile = new File(mc.mcDataDir, "config/TempPlayerData.txt");
    private final File midGamePlayerListFile = new File(mc.mcDataDir, "config/MidGamePlayerData.txt");

    @Override
    public String getCommandName() {
        return "predict";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/predict <mid|say> <say>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = DataAnalyzer.runPythonScript(1);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                }
            }).start();
            return;
        }
        if (args.length == 1) {
            String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("mid")) {
                PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = DataAnalyzer.runPythonScript(2);
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                        PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                    }
                }).start();
            }
            if (subCommand.equalsIgnoreCase("fks")) {
                PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(predictFKS()));
                        PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                    }
                }).start();
            }
            if (subCommand.equalsIgnoreCase("say")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = DataAnalyzer.runPythonScript(1);
                        DelayedMessageHandler.requestDelayedMessage("Jarvis: \"Prediction - " + result + "\"", 2);
                    }
                }).start();
            }
            if (subCommand.equalsIgnoreCase("midgamesave")) {
                mc.thePlayer.addChatMessage(new ChatComponentText("Mid-game save players completed."));
                PlayerMessageHandler.sendPlayerListMessage();
                PlayerMessageHandler.savePlayerListToFile(midGamePlayerListFile);
                PlayerMessageHandler.midGamePlayersSaved = true;
            }
            if (subCommand.equalsIgnoreCase("explain")) {
                DelayedMessageHandler.requestDelayedMessage("The prediction uses the average stats of the players on each team", 1);
                DelayedMessageHandler.requestDelayedMessage("and the # of each class, nicks, and cheaters. Win rate is the best determinant for who wins", 75);
            }
            return;
        }
        if (args.length == 2) {
            String subCommand1 = args[0];
            String subCommand2 = args[1];
            if (subCommand1.equalsIgnoreCase("mid")) {
                if (subCommand2.equalsIgnoreCase("say")) {
                    PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DataAnalyzer.runPythonScript(2);
                            DelayedMessageHandler.requestDelayedMessage("Jarvis: \"Prediction - " + result + "\"", 2);
                            PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                        }
                    }).start();
                }
            }
            if (subCommand1.equalsIgnoreCase("fks")) {
                if (subCommand2.equalsIgnoreCase("say")) {
                    PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DelayedMessageHandler.requestDelayedMessage("Jarvis: \"Prediction - " + predictFKS() + "\"", 2);
                            PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                        }
                    }).start();
                }
            }
            if (subCommand1.equalsIgnoreCase("positionx")) {
                PredictionUI.positionFromLeft = Integer.parseInt(subCommand2);
            }
            if (subCommand1.equalsIgnoreCase("midgamesave")) {
                if (subCommand2.equalsIgnoreCase("toggle")) {
                    PlayerMessageHandler.midGamePlayersSaved = !PlayerMessageHandler.midGamePlayersSaved;
                    mc.thePlayer.addChatMessage(new ChatComponentText("mid game players saved = " + String.valueOf(PlayerMessageHandler.midGamePlayersSaved)));
                }
            }
            if (subCommand1.equalsIgnoreCase("auto")) {
                if (subCommand2.equalsIgnoreCase("toggle")) {
                    PlayerMessageHandler.autoPredict = !PlayerMessageHandler.autoPredict;
                    mc.thePlayer.addChatMessage(new ChatComponentText("autoPredict = " + String.valueOf(PlayerMessageHandler.autoPredict)));
                }
            }
            return;
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

    public static String predictFKS() {
        String result = "";
        Gson gson = new Gson();
        String fkMap = gson.toJson(PlayerMessageHandler.finalKillsByPlayers);
        String deathMap = gson.toJson(PlayerMessageHandler.deathsByPlayers);
        try {
            File tempFileFKS = File.createTempFile("finalKills", ".json");
            File tempFileDeaths = File.createTempFile("deaths", ".json");
            FileWriter writer1 = new FileWriter(tempFileFKS);
            writer1.write(fkMap);
            writer1.close();
            FileWriter writer2 = new FileWriter(tempFileDeaths);
            writer2.write(deathMap);
            writer2.close();

            result = DataAnalyzer.runPythonScript(5, tempFileFKS.toString(), tempFileDeaths.toString());
            tempFileFKS.deleteOnExit();
            tempFileDeaths.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Error in /predict fks: " + e.getMessage()));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(e.toString()));
        }
        return result;
    }
}
