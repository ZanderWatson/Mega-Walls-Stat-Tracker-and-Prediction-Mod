package com.WeirdMushroom.mwpredictionmod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlexdoruPartyDetector {

    private static String lastPlayerJoining = "";
    private static long timeLastJoin = 0;
    public static final HashMap<String, List<String>> partysMap = new HashMap<String, List<String>>();

    public static void onPlayerJoin(String playername, long jointime) {

        if (timeLastJoin != 0 && jointime - timeLastJoin > 25L * 60L * 1000L) {
            partysMap.clear();
        }

        if (jointime - timeLastJoin < 101L && !lastPlayerJoining.isEmpty()) {
            final List<String> partyLastPlayer = partysMap.get(lastPlayerJoining);
            final List<String> partyPlayer = partysMap.get(playername);
            if (partyLastPlayer != null && partyPlayer != null) {
                addAllWithoutDuplicates(partyLastPlayer, partyPlayer);
                partysMap.put(playername, partyLastPlayer);
            } else if (partyLastPlayer != null) {
                partyLastPlayer.add(playername);
                partysMap.put(playername, partyLastPlayer);
            } else if (partyPlayer != null) {
                partyPlayer.add(lastPlayerJoining);
                partysMap.put(lastPlayerJoining, partyPlayer);
            } else {
                final List<String> newParty = new ArrayList<String>();
                newParty.add(lastPlayerJoining);
                newParty.add(playername);
                partysMap.put(lastPlayerJoining, newParty);
                partysMap.put(playername, newParty);
            }
        }

        lastPlayerJoining = playername;
        timeLastJoin = jointime;

    }

    private static void addAllWithoutDuplicates(List<String> listToKeep, List<String> listToAdd) {
        for (final String s : listToAdd) {
            if (!listToKeep.contains(s)) {
                listToKeep.add(s);
            }
        }
    }
}