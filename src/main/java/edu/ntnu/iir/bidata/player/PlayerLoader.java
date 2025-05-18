package edu.ntnu.iir.bidata.player;

import edu.ntnu.iir.bidata.board.BoardGame;
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
     * Loads players from a JSON file and associates them with the given game.
     * @param filePath The path to the JSON file containing player data.
     * @param game The game to associate the players with.
     * @return A list of players loaded from the file.
     */
    public static List<Player> loadPlayersFromFile(String filePath, BoardGame game) {
        List<Player> players = new ArrayList<>();
        try {
            // Read the file content
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parse the JSON array
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Validate required fields
                if (!jsonObject.has("name") || !jsonObject.has("playingPiece")) {
                    LOGGER.log(Level.WARNING, "Skipping player entry at index {0}: Missing required fields.", i);
                    continue;
                }

                String name = jsonObject.getString("name").trim();
                String playingPiece = jsonObject.getString("playingPiece").trim();

                // Ensure fields are not empty
                if (name.isEmpty() || playingPiece.isEmpty()) {
                    LOGGER.log(Level.WARNING, "Skipping player entry at index {0}: Empty name or playing piece.", i);
                    continue;
                }

                // Create and add the player
                Player player = new Player(name, playingPiece, game);
                players.add(player);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to read the file: " + filePath, e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An unexpected error occurred while loading players.", e);
        }
        return players;
    }
}
