import numpy as np
import sys
import os

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

import Other.PlayerStats as PlayerStats
import Predictions.APITesting as APITesting
import Other.PlayerFileFormatting as originalFormatting
import With_Final_Kills.Other_sDataFormatting as otherFormatting


gamesListOriginal = originalFormatting.getGamesList(timeStamps=False)

williamGamesList = otherFormatting.getOthersGamesList(
    withFKS=False,
    fileName="C:/Users/zande/Downloads/Projects/"
             "Mega Walls Player Tracker Data Analysis/WilliamPlayerData.txt",
    withWinners=True
)

herbariaGamesList = otherFormatting.getOthersGamesList(
    withFKS=False,
    fileName="C:/Users/zande/Downloads/Projects/"
             "Mega Walls Player Tracker Data Analysis/HerbariaPlayerData.txt",
    withWinners=True
)

gamesList = herbariaGamesList + williamGamesList + gamesListOriginal


def averageStatsInList(statsList):
    averagedStats = []

    for statIndex in range(4):
        values = [stats[statIndex] for stats in statsList]

        if values:
            averagedStats.append(round(sum(values) / len(values), 2))
        else:
            averagedStats.append(0)

    return averagedStats


teamCodes = ["R", "B", "G", "Y"]

winnerNumbers = {
    "RedTeam": 1,
    "BlueTeam": 2,
    "GreenTeam": 3,
    "YellowTeam": 4
}

classNumberMap = {
    "[ANG]": 0,
    "[ARC]": 1,
    "[ASN]": 2,
    "[ATN]": 3,
    "[BLA]": 4,
    "[COW]": 5,
    "[CRE]": 6,
    "[DRE]": 7,
    "[DRG]": 8,
    "[END]": 9,
    "[GOL]": 10,
    "[HBR]": 11,
    "[HUN]": 12,
    "[MOL]": 13,
    "[PHX]": 14,
    "[PIG]": 15,
    "[PIR]": 16,
    "[REN]": 17,
    "[SHA]": 18,
    "[SHP]": 19,
    "[SRK]": 20,
    "[SKE]": 21,
    "[SNO]": 22,
    "[SPI]": 23,
    "[SQU]": 24,
    "[WER]": 25,
    "[ZOM]": 26
}


gameNumber = 0
finalStatsListToArray = []
testStatsListToArray = []
finalWinsListToArray = []
testWinsListToArray = []

playerStatsDictionary = PlayerStats.getPlayerStatsDict()

for gameList in gamesList:
    gameStatsList = []

    teamData = {
        teamCode: {
            "players": 0,
            "nicks": 0,
            "cheaters": 0,
            "hardCheaters": 0,
            "goodPlayers": 0,
            "classList": [0] * len(classNumberMap),
            "stats": []
        }
        for teamCode in teamCodes
    }

    winner = None

    for player in gameList:

        # Process each team's player data
        for teamCode in teamCodes:
            teamMarker = f"|{teamCode}|"

            if teamMarker not in player:
                continue

            team = teamData[teamCode]
            team["players"] += 1

            if "Winner-" not in player and "|NICK|" not in player:
                nameStart = player.index(teamMarker) + len(teamMarker)
                nameEnd = player.index("[")
                name = player[nameStart:nameEnd]

                if name not in playerStatsDictionary:
                    playerStatsDictionary[name] = (
                        APITesting.getStatsFromName(name, False)
                    )

                playerStats = playerStatsDictionary[name]

                if playerStats != []:
                    team["stats"].append(playerStats)

                    if playerStats[1] > 1.25:
                        team["goodPlayers"] += 1

            if "|NICK|" in player:
                team["nicks"] += 1
                team["stats"].append([3500, 2.5, 1500, 0.35])

            if "|CHEAT|" in player:
                team["cheaters"] += 1

            if "|CHEAT!|" in player:
                team["hardCheaters"] += 1

            for classTag, classNumber in classNumberMap.items():
                if classTag in player:
                    team["classList"][classNumber] += 1

        # Determine the winner
        for winnerText, winnerNumber in winnerNumbers.items():
            if winnerText in player:
                winner = winnerNumber

    # Append features for every team in the same order as before
    for teamCode in teamCodes:
        team = teamData[teamCode]

        gameStatsList.extend([
            team["players"],
            team["nicks"],
            team["cheaters"],
            team["hardCheaters"],
            team["goodPlayers"]
        ])

        gameStatsList.extend(team["classList"])
        gameStatsList.extend(averageStatsInList(team["stats"]))

    gamesToTest = 1

    if gameNumber < len(gamesList) - gamesToTest:
        finalStatsListToArray.append(gameStatsList)
        finalWinsListToArray.append(winner)
    else:
        testStatsListToArray.append(gameStatsList)
        testWinsListToArray.append(winner)

    gameNumber += 1


X_train = np.array(finalStatsListToArray)
y_train = np.array(finalWinsListToArray)

X_test = np.array(testStatsListToArray)
testWins = np.array(testWinsListToArray)


from sklearn.preprocessing import StandardScaler

scaler = StandardScaler()

X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)


from sklearn.ensemble import RandomForestClassifier

model = RandomForestClassifier(
    max_depth=10,
    max_features=None,
    min_samples_leaf=12,
    min_samples_split=10,
    n_estimators=100
)

model.fit(X_train, y_train)

finalPrediction = model.predict(X_test)
probabilityPrediction = model.predict_proba(X_test)

sortedPrediction = np.argsort(
    probabilityPrediction,
    axis=1
)[:, ::-1]

prediction = ""

for i, ranks in enumerate(sortedPrediction):
    for rank, cls in enumerate(ranks):
        prediction += (
            f"{cls}: "
            f"{probabilityPrediction[i][cls] * 100:.1f}% "
        )

prediction = (
    prediction
    .replace("0:", "Red:")
    .replace("1:", "Blue:")
    .replace("2:", "Green:")
    .replace("3:", "Yellow:")
)

print(prediction)