package edu.ntnu.iir.bidata.board;

import edu.ntnu.iir.bidata.JsonParsingException;
import edu.ntnu.iir.bidata.tile.MoveExtraStepsAction;
import edu.ntnu.iir.bidata.tile.RandomTeleportAction;
import edu.ntnu.iir.bidata.tile.Tile;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for reading the board configuration from a JSON file.
 */
public class BoardFileReader {
    private final Board board;

    public BoardFileReader(Board board) {
        this.board = board;
    }



    /**
     * Reads the board configuration from a JSON file and returns a map of tiles.
     * @param filePath The path to the JSON file.
     * @return A map of tile IDs to Tile objects.
     */
    public Map<Integer, Tile> readBoardFromFile(String filePath) {
        Map<Integer, Tile> tiles = new HashMap<>();
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
                        currentTile.setLandAction(new RandomTeleportAction(board));
                    }
                }
            }
        } catch (IOException e) {
            throw new JsonParsingException("Failed to read the JSON file: " + filePath, e);
        } catch (Exception e) {
            throw new JsonParsingException("Failed to parse the JSON file: " + filePath, e);
        }
        return tiles;
    }
}
