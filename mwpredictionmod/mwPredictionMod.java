package com.WeirdMushroom.mwpredictionmod;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = mwPredictionMod.MODID, version = mwPredictionMod.VERSION)
public class mwPredictionMod
{
    public static final String MODID = "mwpredictionmod";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Register
        MinecraftForge.EVENT_BUS.register(new PlayerMessageHandler());
        MinecraftForge.EVENT_BUS.register(new PartyTracker());
        MinecraftForge.EVENT_BUS.register(new DelayedMessageHandler());
        MinecraftForge.EVENT_BUS.register(new PredictionUI());
        MinecraftForge.EVENT_BUS.register(new ScoreboardReader());
        MinecraftForge.EVENT_BUS.register(new FKDHead());
        ClientCommandHandler.instance.registerCommand(new PredictionCommandHandler());
        ClientCommandHandler.instance.registerCommand(new GetStatsCommand());
        ClientCommandHandler.instance.registerCommand(new CheckVariableCommands());
        ClientCommandHandler.instance.registerCommand(new PartyTrackerCommands());
        ClientCommandHandler.instance.registerCommand(new SetVariableCommands());
        ClientCommandHandler.instance.registerCommand(new FKDHeadCommands());
    }

}



