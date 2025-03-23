package edu.ntnu.iir.bidata;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Integer, Tile> tiles;

    public Board(int numberOfTiles) {
        tiles = new HashMap<>();
        for (int i = 1; i <= numberOfTiles; i++) {
            tiles.put(i, new Tile(i));
        }
        for (int i = 1; i < numberOfTiles; i++) {
            tiles.get(i).setNextTile(tiles.get(i + 1));
        }
    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }

}
