import requests
import json
import time

#api_key = ""

# API key for "Mega Walls Stats" app:
api_key = "e9ea7449-35bf-4fe3-b0a8-6c8a65ad8c33"

#api_key = "95986840-1369-40e0-afe8-2c502fe01e12"
def getStatsFromName(name, debug):
    url = f'https://api.mojang.com/users/profiles/minecraft/{name}?'
    #url = f'https://api.minecraftservices.com/minecraft/profile/lookup/name/{name}'
    shouldContinue = True
    waitingTime = 1
    while shouldContinue:
        response = requests.get(url)
        if response.status_code == 429:
            if debug:
                print("ERROR CODE 429!!!!!!!! Waiting " + str(waitingTime) + " seconds for " + str(name))
            time.sleep(waitingTime)
            waitingTime *= 2
        else:
            shouldContinue = False
            waitingTime = 1
        
    if 'application/json' not in response.headers.get('Content-Type', ''):
        print(f"Response is not JSON. Received content: {response.text[:100]}")

    try: 
        if response.json()['id'].strip():
            uuid = response.json()['id']
        else:
            uuid = "null"
    except KeyError:
        uuid = "null"
    except requests.exceptions.JSONDecodeError as error:
        print(f"requests JSON decode error: {error}")
        uuid = "null"
    except json.JSONDecodeError as e:
        print(f"JSON decode error: {e}")

            
    if uuid != "null":
        data = requests.get(
            # https://api.hypixel.net/player?key=e9ea7449-35bf-4fe3-b0a8-6c8a65ad8c33&uuid=
            # c54ecea8-3427-444b-afbe-72d33ca9a6af
            url = "https://api.hypixel.net/player",
            params = {
                "key": api_key,
                "uuid": uuid
            }
        ).json()
        if debug:
            print(name + " " + str(data["success"]))
            if data["success"] == False:
                print(data["cause"])
        try: 
            final_kills = data["player"]["stats"]["Walls3"]["final_kills"]
        except KeyError: 
            final_kills = 0
        except TypeError:
            return []
        try:
            final_deaths = data["player"]["stats"]["Walls3"]["final_deaths"]
        except KeyError:
            final_deaths = 0
        except TypeError:
            return []
        try:
            finalDeaths = data["player"]["stats"]["Walls3"]["finalDeaths"]
        except KeyError:
            finalDeaths = 0
        except TypeError:
            return []
        final_deaths_total = final_deaths + finalDeaths
        try:
            losses = data["player"]["stats"]["Walls3"]["losses"]
        except KeyError:
            losses = 1
        except TypeError:
            return []
        try:
            wins = data["player"]["stats"]["Walls3"]["wins"]
        except KeyError:
            wins = 0
        except TypeError:
            return []
        if final_deaths_total == 0:
            final_deaths_total = 1
        fkdr = final_kills/final_deaths_total
        games_played = wins + losses
        win_rate = wins / games_played
        return [final_kills, fkdr, games_played, win_rate]
    else:
        if debug:
            print(name + " uuid does not exist")
        return []

#print(getStatsFromName("icyspeed"))


def getClassStatsFromName(nameWithClass):
    name = nameWithClass[:-4]
    classTag = nameWithClass[-4:]
    url = f'https://api.mojang.com/users/profiles/minecraft/{name}?'
    shouldContinue = True
    waitingTime = 3
    while shouldContinue:
        response = requests.get(url)
        if response.status_code == 429:
            print("ERROR CODE 429!!!!!!!! Waiting " + str(waitingTime) + " seconds")
            time.sleep(waitingTime)
            waitingTime *= 2
        else:
            shouldContinue = False
            waitingTime = 3
        
    if 'application/json' not in response.headers.get('Content-Type', ''):
        print(f"Response is not JSON. Received content: {response.text[:200]}")

    try: 
        if response.json()['id'].strip():
            uuid = response.json()['id']
        else:
            uuid = "null"
    except KeyError:
        uuid = "null"
    except requests.exceptions.JSONDecodeError as error:
        print(f"requests JSON decode error: {error}")
        uuid = "null"
    except json.JSONDecodeError as e:
        print(f"JSON decode error: {e}")

    if uuid != "null":
        data = requests.get(
            url = "https://api.hypixel.net/player",
            params = {
                "key": api_key,
                "uuid": uuid
            }
        ).json()
        classToGetStats = ""

        tag_map = {"[ANG": "angel","[ARC": "arcanist","[ASN": "assassin","[ATN": "automaton","[BLA": "blaze","[COW": "cow","[CRE": "creeper","[DRE": "dreadlord","[DRG": "dragon","[END": "enderman","[GOL": "golem","[HBR": "herobrine","[HUN": "hunter","[MOL": "moleman","[PHX": "phoenix","[PIG": "pigman","[PIR": "pirate","[REN": "renegade","[SHA": "shaman","[SHP": "sheep","[SRK": "shark","[SKE": "skeleton","[SNO": "snowman","[SPI": "spider","[SQU": "squid","[WER": "werewolf","[ZOM": "zombie"}
        for tag, className in tag_map.items():
            if tag == classTag:
                classToGetStats = className
        
        try: 
            final_kills = data["player"]["stats"]["Walls3"][f"{classToGetStats}_final_kills"]
        except KeyError: 
            final_kills = 0
        except TypeError:
            return []
        try: 
            final_deaths = data["player"]["stats"]["Walls3"][f"{classToGetStats}_final_deaths"]
        except KeyError:
            final_deaths = 1
        except TypeError:
            return []
        try:
            games_played = data["player"]["stats"]["Walls3"][f"{classToGetStats}_games_played"]
        except KeyError:
            games_played = 1
        except TypeError:
            return []
        try:
            wins = data["player"]["stats"]["Walls3"][f"{classToGetStats}_wins"]
        except KeyError:
            wins = 0
        except TypeError:
            return []
        if final_deaths == 0:
            final_deaths = 1
        fkdr = final_kills/final_deaths
        win_rate = wins / games_played

        return [final_kills, fkdr, games_played, win_rate]
    else:
        return []

