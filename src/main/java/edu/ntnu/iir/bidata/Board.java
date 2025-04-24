package edu.ntnu.iir.bidata;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Integer, Tile> tiles = new HashMap<>();

    public void loadBoardFromFile(String filePath) {
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
            }
        } catch (IOException e) {
            System.err.println("Failed to load board from file: " + e.getMessage());
        }
    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }
}
