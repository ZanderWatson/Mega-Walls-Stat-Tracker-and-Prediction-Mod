import Predictions.APITesting as APITesting
import PlayerStatsByClass as PlayerStatsByClass
import PlayerFileFormatting

gamesList = PlayerFileFormatting.getGamesList()


statsForRed = []; statsForBlue = []; statsForGreen = []; statsForYellow = []

statsForANG = []; statsForARC = []; statsForASN = []; statsForATN = []; statsForBLA = []; statsForCOW = []; statsForCRE = []; statsForDRE = []; statsForDRG = []; statsForEND = []
statsForGOL = []; statsForHBR = []; statsForHUN = []; statsForMOL = []; statsForPHX = []; statsForPIG = []; statsForPIR = []; statsForREN = []; statsForSHA = []; statsForSHP = []
statsForSRK = []; statsForSKE = []; statsForSNO = []; statsForSPI = []; statsForSQU = []; statsForWER = []; statsForZOM = []
stats_map = {"[ANG]": statsForANG,"[ARC]": statsForARC,"[ASN]": statsForASN,"[ATN]": statsForATN,"[BLA]": statsForBLA,"[COW]": statsForCOW,"[CRE]": statsForCRE,"[DRE]": statsForDRE,"[DRG]": statsForDRG,"[END]": statsForEND,"[GOL]": statsForGOL,"[HBR]": statsForHBR,"[HUN]": statsForHUN,"[MOL]": statsForMOL,"[PHX]": statsForPHX,"[PIG]": statsForPIG,"[PIR]": statsForPIR,"[REN]": statsForREN,"[SHA]": statsForSHA,"[SHP]": statsForSHP,"[SRK]": statsForSRK,"[SKE]": statsForSKE,"[SNO]": statsForSNO,"[SPI]": statsForSPI,"[SQU]": statsForSQU,"[WER]": statsForWER,"[ZOM]": statsForZOM}

#if name not in playerStatsDictionary:
#    stats = APITesting.getStatsFromName(name)
#    playerStatsDictionary[name] = stats

playerStatsByClassDictionary = PlayerStatsByClass.getPlayerStatsByClassDict()
# Getting stat lists
gameNumber = -1
for gameList in gamesList:
    gameNumber += 1
    for player in gameList:
        if "Winner-" not in player and "|NICK|" not in player and "WeirdMushroom" not in player:
            if "|R|" in player:
                name = player[player.index("|R|") + 3: player.index("]")]
            elif "|B|" in player:
                name = player[player.index("|B|") + 3: player.index("]")]
            elif "|G|" in player:
                name = player[player.index("|G|") + 3: player.index("]")]
            elif "|Y|" in player:
                name = player[player.index("|Y|") + 3: player.index("]")]

            if name not in playerStatsByClassDictionary:
                stats = APITesting.getClassStatsFromName(name)
                playerStatsByClassDictionary[name] = stats

            if playerStatsByClassDictionary[name] != []:
                for tag, stats_list in stats_map.items():
                    if tag in player:
                        stats_list.append(playerStatsByClassDictionary[name])

#print(playerStatsByClassDictionary)

def averageStatsInList(statsList):
    averagedStats = []
    for i in range(4): 
        listToAverage = []
        for list in statsList:
            listToAverage.append(list[i])
        averagedStats.append(round(sum(listToAverage) / (len(listToAverage) + 0.00000001), 2))
    return averagedStats


print("Avg: fks, fkdr, games, win rate")
print(" ")
print("ANG: " + str(averageStatsInList(statsForANG)))
print("ARC: " + str(averageStatsInList(statsForARC)))
print("ASN: " + str(averageStatsInList(statsForASN)))
print("ATN: " + str(averageStatsInList(statsForATN)))
print("BLA: " + str(averageStatsInList(statsForBLA)))
print("COW: " + str(averageStatsInList(statsForCOW)))
print("CRE: " + str(averageStatsInList(statsForCRE)))
print("DRE: " + str(averageStatsInList(statsForDRE)))
print("DRG: " + str(averageStatsInList(statsForDRG)))
print("END: " + str(averageStatsInList(statsForEND)))
print("GOL: " + str(averageStatsInList(statsForGOL)))
print("HBR: " + str(averageStatsInList(statsForHBR)))
print("HUN: " + str(averageStatsInList(statsForHUN)))
print("MOL: " + str(averageStatsInList(statsForMOL)))
print("PHX: " + str(averageStatsInList(statsForPHX)))
print("PIG: " + str(averageStatsInList(statsForPIG)))
print("PIR: " + str(averageStatsInList(statsForPIR)))
print("REN: " + str(averageStatsInList(statsForREN)))
print("SHA: " + str(averageStatsInList(statsForSHA)))
print("SHP: " + str(averageStatsInList(statsForSHP)))
print("SRK: " + str(averageStatsInList(statsForSRK)))
print("SKE: " + str(averageStatsInList(statsForSKE)))
print("SNO: " + str(averageStatsInList(statsForSNO)))
print("SPI: " + str(averageStatsInList(statsForSPI)))
print("SQU: " + str(averageStatsInList(statsForSQU)))
print("WER: " + str(averageStatsInList(statsForWER)))
print("ZOM: " + str(averageStatsInList(statsForZOM)))