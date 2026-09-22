package com.WeirdMushroom.mwpredictionmod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.io.File;

public class GetStatsCommand extends CommandBase {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final File tempPlayerListFile = new File(mc.mcDataDir, "config/TempPlayerData.txt");

    @Override
    public String getCommandName() {
        return "get";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "get <team> <stat>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Updating Stats..."));
            PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DataAnalyzer.runPythonScript(3);
                    PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Updated Stats."));
                }
            }).start();
        }
        if (args.length == 1) {
            final String subCommand = args[0];
            if (subCommand.equalsIgnoreCase("red")) {
                PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = DataAnalyzer.runPythonScript(4, "get stats red");
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                        PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                    }
                }).start();
            }
            else if (subCommand.equalsIgnoreCase("blue")) {
                PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = DataAnalyzer.runPythonScript(4, "get stats blue");
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                        PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                    }
                }).start();
            }
            else if (subCommand.equalsIgnoreCase("green")) {
                PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = DataAnalyzer.runPythonScript(4, "get stats green");
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                        PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                    }
                }).start();
            }
            else if (subCommand.equalsIgnoreCase("yellow")) {
                PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = DataAnalyzer.runPythonScript(4, "get stats yellow");
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                        PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                    }
                }).start();
            }
            else {
                PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = DataAnalyzer.runPythonScript(4, "get stats " + subCommand);
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                        PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                    }
                }).start();
            }
            return;
        }
        if (args.length == 2) {
            final String subCommand1 = args[0];
            final String subCommand2 = args[1];
            if (subCommand1.equalsIgnoreCase("all")) {
                if (subCommand2.equalsIgnoreCase("fks")) {
                    PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DataAnalyzer.runPythonScript(4, "get stats all fks");
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                            PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                        }
                    }).start();
                }
                if (subCommand2.equalsIgnoreCase("fkd")) {
                    PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DataAnalyzer.runPythonScript(4, "get stats all fkd");
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                            PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                        }
                    }).start();
                }
                if (subCommand2.equalsIgnoreCase("games")) {
                    PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DataAnalyzer.runPythonScript(4, "get stats all games");
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                            PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                        }
                    }).start();
                }
                if (subCommand2.equalsIgnoreCase("winrate")) {
                    PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DataAnalyzer.runPythonScript(4, "get stats all winrate");
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                            PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                        }
                    }).start();
                }
            }
            else if (subCommand1.equalsIgnoreCase("top")) {
                if (subCommand2.equalsIgnoreCase("fks")) {
                    PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DataAnalyzer.runPythonScript(4, "get stats top fks");
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                            PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                        }
                    }).start();
                }
                if (subCommand2.equalsIgnoreCase("fkd")) {
                    PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DataAnalyzer.runPythonScript(4, "get stats top fkd");
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                            PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                        }
                    }).start();
                }
                if (subCommand2.equalsIgnoreCase("games")) {
                    PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DataAnalyzer.runPythonScript(4, "get stats top games");
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                            PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                        }
                    }).start();
                }
                if (subCommand2.equalsIgnoreCase("winrate")) {
                    PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = DataAnalyzer.runPythonScript(4, "get stats top winrate");
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(result));
                            PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                        }
                    }).start();
                }
            } else if (subCommand1.equalsIgnoreCase("gay")) {
                PlayerMessageHandler.savePlayerListToFile(tempPlayerListFile);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = DataAnalyzer.runPythonScript(4, "get stats gay " + subCommand2);
                        float gayMeter = 0;
                        try {
                            float fkd = Float.parseFloat(result.substring(0, result.indexOf(",")));
                            float winrate = Float.parseFloat(result.substring(result.indexOf(",") + 1));
                            gayMeter = (float) ((1 + fkd + winrate) / (((winrate + 0.25)/0.25) * ((fkd + 1.5)/1.5)));
                            gayMeter = Math.round(gayMeter * 100);
                        } catch (Exception e) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Error in creating gay meter"));
                        }
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Jarvis gay meter for " + subCommand2 + ": " + gayMeter + "%"));
                        PlayerMessageHandler.removeTextInFile(tempPlayerListFile);
                    }
                }).start();
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
