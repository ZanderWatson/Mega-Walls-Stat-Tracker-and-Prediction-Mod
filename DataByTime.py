import sys
import os
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))
import With_Final_Kills.Other_sDataFormatting as otherFormatting
import Other.PlayerFileFormatting as originalFormatting

gamesList = originalFormatting.getGamesList(timeStamps=True)
williamGamesList = otherFormatting.getOthersGamesList(withFKS=False, fileName="C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/WilliamPlayerData.txt", withWinners=True)
herbariaGamesList = otherFormatting.getOthersGamesList(withFKS=False, fileName="C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/HerbariaPlayerData.txt", withWinners=True)
for list in herbariaGamesList:
    gamesList.append(list)
for list1 in williamGamesList:
    gamesList.append(list1)
print(gamesList[-2])
# Class lists by day
classListMonday = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classListTuesday = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classListWednesday = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classListThursday = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classListFriday = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classListSaturday = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classListSunday = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

gameTotalsListMonday = [];gameTotalsListTuesday = [];gameTotalsListWednesday = [];gameTotalsListThursday = [];gameTotalsListFriday = [];gameTotalsListSaturday = [];gameTotalsListSunday = []
timeTotals1 = []; timeTotals2 = []; timeTotals3 = []; timeTotals4 = []; timeTotals5 = []; timeTotals6 = []; timeTotals7 = []; timeTotals8 = []; timeTotals9 = []; timeTotals10 = []; timeTotals11 = []; timeTotals12 = []; timeTotals13 = []; timeTotals14 = []; timeTotals15 = []; timeTotals16 = []; timeTotals17 = []; timeTotals18 = []; timeTotals19 = []; timeTotals20 = []; timeTotals21 = []; timeTotals22 = []; timeTotals23 = []; timeTotals0 = []; 
# Class lists by time
classList0 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList1 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList2 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList3 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList4 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList5 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList6 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList7 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList8 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList9 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList10 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList11 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList12 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList13 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList14 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList15 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList16 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList17 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList18 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList19 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList20 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList21 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList22 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
classList23 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

# Class list for Mythic Hour, Friday-Sunday at 1:00, 5:00, 9:00, 13:00, 17:00, 21:00
classListMythicHour = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

dayTotalsMap = {"|Mon|": gameTotalsListMonday, "|Tue|" : gameTotalsListTuesday, "|Wed|" : gameTotalsListWednesday, "|Thu|" : gameTotalsListThursday, "|Fri|" : gameTotalsListFriday, "|Sat|" : gameTotalsListSaturday, "|Sun|" : gameTotalsListSunday}
timeTotalsMap = {"|01|" : timeTotals1, "|02|" : timeTotals2, "|03|" : timeTotals3, "|04|" : timeTotals4, "|05|" : timeTotals5, "|06|" : timeTotals6, "|07|" : timeTotals7, "|08|" : timeTotals8, "|09|" : timeTotals9, "|10|" : timeTotals10, "|11|" : timeTotals11, "|12|" : timeTotals12, "|13|" : timeTotals13, "|14|" : timeTotals14, "|15|" : timeTotals15, "|16|" : timeTotals16, "|17|" : timeTotals17, "|18|" : timeTotals18, "|19|" : timeTotals19, "|20|" : timeTotals20, "|21|" : timeTotals21, "|22|" : timeTotals22, "|23|" : timeTotals23, "|00|" : timeTotals0}
timeCountsMap = {"|01|" : classList1, "|02|" : classList2, "|03|" : classList3, "|04|" : classList4, "|05|" : classList5, "|06|" : classList6, "|07|" : classList7, "|08|" : classList8, "|09|" : classList9, "|10|" : classList10, "|11|" : classList11, "|12|" : classList12, "|13|" : classList13, "|14|" : classList14, "|15|" : classList15, "|16|" : classList16, "|17|" : classList17, "|18|" : classList18, "|19|" : classList19, "|20|" : classList20, "|21|" : classList21, "|22|" : classList22, "|23|" : classList23, "|00|" : classList0}
dayCountsMap = {"|Mon|": classListMonday, "|Tue|" : classListTuesday, "|Wed|" : classListWednesday, "|Thu|" : classListThursday, "|Fri|" : classListFriday, "|Sat|" : classListSaturday, "|Sun|" : classListSunday}
classNumberMap = {"[ANG]": 0,"[ARC]": 1,"[ASN]": 2,"[ATN]": 3,"[BLA]": 4,"[COW]": 5,"[CRE]": 6,"[DRE]": 7,"[DRG]": 8,"[END]": 9,"[GOL]": 10,"[HBR]": 11,"[HUN]": 12,"[MOL]": 13,"[PHX]": 14,"[PIG]": 15,"[PIR]": 16,"[REN]": 17,"[SHA]": 18,"[SHP]": 19,"[SRK]": 20,"[SKE]": 21,"[SNO]": 22,"[SPI]": 23,"[SQU]": 24,"[WER]": 25,"[ZOM]": 26, "|NICK|": 27, "|CHEAT|": 28}
for gamelist in gamesList:
    for day, totalList in dayTotalsMap.items():
            if day[1:4] in gamelist[0]:
                totalList.append(len(gamelist) - 2)
    for time, totalList in timeTotalsMap.items():
            if gamelist[0][28:30] == time[1:3]:
                totalList.append(len(gamelist) - 2)
    for item in gamelist:
        for day, dayList in dayCountsMap.items():
            if day in item:
                for tag, index in classNumberMap.items():
                    if tag in item:
                        dayList[index] += 1
        for time, timeList in timeCountsMap.items():
            if time in item:
                for tag, index in classNumberMap.items():
                    if tag in item:
                        timeList[index] += 1

        if "|Fri|" in item or "|Sat|" in item or "|Sun|" in item:
            if "|01|" in item or "|05|" in item or "|09|" in item or "|13|" in item or "|17|" in item or "|21|" in item:
                for tag, index in classNumberMap.items():
                    if tag in item:
                        classListMythicHour[index] += 1

#print(timeTotalsMap)
for time, totalList in timeTotalsMap.items():
    print(time)
    print(f"Games: {len(totalList)}")
    try:
        print(f"Average Players: {round(sum(totalList)/len(totalList),2)}")
    except ZeroDivisionError:
        print(f"Average Players: {round(sum(totalList)/1,2)}")

#print(dayTotalsMap)
for day, totalList in dayTotalsMap.items():
    print(day)
    print(f"Games: {len(totalList)}")
    print(f"Average Players: {round(sum(totalList)/len(totalList),2)}")

#print(dayCountsMap)
#print(timeCountsMap)
#print(gamesList)
print("Mythic Hours: " + str(classListMythicHour))
try:
    print(classListMythicHour[-1]/sum(classListMythicHour[:27]))
except ZeroDivisionError:
    print(0)
for time, timeList in timeCountsMap.items():
    try:
        print(time[1:3] + ":00" + " Cheater Percent: " + str(round(timeList[-1]/sum(timeList[:27]) * 100, 2)) + f"%  ({timeList[-1]} / {sum(timeList[:27])})")
    except ZeroDivisionError:
        print(time[1:3] + ":00" + " Cheater Percent: " + str(timeList[-1]/1) + f"%  ({timeList[-1]} / {sum(timeList[:27])})")
print(" ")
for day, dayList in dayCountsMap.items():
    try:
        print(day[1:4] + " Cheater Percent: " + str(round(dayList[-1]/sum(dayList[:27]) * 100, 2)) + f"%  ({dayList[-1]} / {sum(dayList[:27])})")
    except ZeroDivisionError:
        print(day[1:4] + " Cheater Percent: " + str(dayList[-1]/1) + f"%  ({dayList[-1]} / {sum(dayList[:27])})")

