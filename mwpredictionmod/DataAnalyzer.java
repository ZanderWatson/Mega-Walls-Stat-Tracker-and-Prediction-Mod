package com.WeirdMushroom.mwpredictionmod;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DataAnalyzer {
    public static String runPythonScript(int scriptNumber) {
        try {
            ProcessBuilder pb = null;
            if (scriptNumber == 1) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/Predictions/PredictionForInGame.py");
            } else if (scriptNumber == 2) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/Predictions/MidGamePrediction.py");
            } else if (scriptNumber == 3) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/Predictions/StartGameUpdatePlayerStats.py");
            } else if (scriptNumber == 4) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/Predictions/StatGetterInGame.py");
            }
            
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine(); // Read the result from Python
            process.waitFor();
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
    public static String runPythonScript(int scriptNumber, String input) {
        try {
            ProcessBuilder pb = null;
            if (scriptNumber == 1) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/Predictions/PredictionForInGame.py");
            } else if (scriptNumber == 2) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/Predictions/MidGamePrediction.py");
            } else if (scriptNumber == 3) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/Predictions/StartGameUpdatePlayerStats.py");
            } else if (scriptNumber == 4) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/Predictions/StatGetterInGame.py", input);
            } else if (scriptNumber == 5) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/With_Final_Kills/PredictionWithFinals.py", input);
            } else if (scriptNumber == 6) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/With_Final_Kills/FKDHeadStats.py", input);
            }

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine(); // Read the result from Python
            process.waitFor();
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
    public static String runPythonScript(int scriptNumber, String input, String input2) {
        try {
            ProcessBuilder pb = null;
            if (scriptNumber == 5) {
                pb = new ProcessBuilder("python", "C:/Users/zande/Downloads/Projects/Mega Walls Player Tracker Data Analysis/With_Final_Kills/PredictionWithFinals.py", input, input2);
            }
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine(); // Read the result from Python
            process.waitFor();
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
