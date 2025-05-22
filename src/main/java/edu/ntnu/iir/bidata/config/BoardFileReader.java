package edu.ntnu.iir.bidata.config;

import edu.ntnu.iir.bidata.util.JsonParsingException;
import edu.ntnu.iir.bidata.board.Board;
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
 * Responsible for reading and parsing board configurations from a JSON file.
 * This class creates a map of tiles and links them based on the configuration
 * provided in the JSON file. It also sets up special actions for tiles, such as
 * moving extra steps or random teleportation.
 */
public class BoardFileReader {

  /**
   * Reads the board configuration from a JSON file and returns a map of tiles.
   *
   * @param filePath The path to the JSON file containing the board configuration.
   * @return A map of tile IDs to Tile objects, with all tiles linked and actions set.
   * @throws JsonParsingException If the file cannot be read or the JSON is invalid.
   */
  public Map<Integer, Tile> readBoardFromFile(String filePath, Board board) {
    Map<Integer, Tile> tiles = new HashMap<>();
    try {
      String content = new String(Files.readAllBytes(Paths.get(filePath)));
      JSONObject jsonObject = new JSONObject(content);
      JSONArray tileArray = jsonObject.getJSONArray("tiles");

      for (int i = 0; i < tileArray.length(); i++) {
        JSONObject tileObject = tileArray.getJSONObject(i);
        int id = tileObject.getInt("id");
        tiles.put(id, new Tile(id));
      }

      for (int i = 0; i < tileArray.length(); i++) {
        JSONObject tileObject = tileArray.getJSONObject(i);
        int id = tileObject.getInt("id");
        Integer nextTileId = tileObject.isNull("nextTile") ? null : tileObject.getInt("nextTile");

        Tile currentTile = tiles.get(id);
        if (nextTileId != null) {
          currentTile.setNextTile(tiles.get(nextTileId));
        }

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
