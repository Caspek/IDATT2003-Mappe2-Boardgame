package edu.ntnu.iir.bidata.player;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerLoader {

    private static final Logger LOGGER = Logger.getLogger(PlayerLoader.class.getName());

    /**
     * Loads player names (or pieces) from a JSON file for GUI purposes.
     * @param filePath The path to the JSON file containing player data.
     * @return A list of player names or pieces.
     */
    public static List<String> loadPlayerNamesFromFile(String filePath) {
        List<String> playerNames = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (!jsonObject.has("name")) {
                    LOGGER.log(Level.WARNING, "Skipping player entry at index {0}: Missing required fields.", i);
                    continue;
                }

                String name = jsonObject.getString("name").trim();
                if (!name.isEmpty()) {
                    playerNames.add(name);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to read the file: " + filePath, e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred while loading player names.", e);
        }
        return playerNames;
    }
}
