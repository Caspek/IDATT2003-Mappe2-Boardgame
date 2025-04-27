package edu.ntnu.iir.bidata.board;

import edu.ntnu.iir.bidata.tile.MoveExtraStepsAction;
import edu.ntnu.iir.bidata.tile.RandomTeleportAction;
import edu.ntnu.iir.bidata.tile.Tile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Integer, Tile> tiles = new HashMap<>(); // Stores all tiles by their ID.

    /**
     * Loads the board configuration from a JSON file.
     * @param filePath The path to the JSON file containing the board configuration.
     */
    public void loadBoardFromFile(String filePath) {
        try {
            // Read the JSON file content.
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray tileArray = jsonObject.getJSONArray("tiles");

            // Create Tile objects for each tile in the JSON file.
            for (int i = 0; i < tileArray.length(); i++) {
                JSONObject tileObject = tileArray.getJSONObject(i);
                int id = tileObject.getInt("id");
                tiles.put(id, new Tile(id));
            }

            // Link tiles and set land actions (e.g., ladders or snakes).
            for (int i = 0; i < tileArray.length(); i++) {
                JSONObject tileObject = tileArray.getJSONObject(i);
                int id = tileObject.getInt("id");
                Integer nextTileId = tileObject.isNull("nextTile") ? null : tileObject.getInt("nextTile");

                Tile currentTile = tiles.get(id);
                if (nextTileId != null) {
                    currentTile.setNextTile(tiles.get(nextTileId));
                }

                // Set land actions for special tiles.
                if (tileObject.has("landAction")) {
                    if (tileObject.get("landAction") instanceof Integer) {
                        int steps = tileObject.getInt("landAction");
                        currentTile.setLandAction(new MoveExtraStepsAction(steps));
                    } else if ("randomTeleport".equals(tileObject.getString("landAction"))) {
                        currentTile.setLandAction(new RandomTeleportAction(this));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load board from file: " + e.getMessage());
        }
    }

    public Collection<Tile> getAllTiles() {
        return tiles.values();
    }

    /**
     * Retrieves a specific tile by its ID.
     * @param id The ID of the tile.
     * @return The Tile object with the specified ID.
     */
    public Tile getTile(int id) {
        return tiles.get(id);
    }
}
