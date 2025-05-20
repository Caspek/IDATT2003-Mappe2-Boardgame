package edu.ntnu.iir.bidata.board;

import edu.ntnu.iir.bidata.tile.Tile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Integer, Tile> tiles = new HashMap<>(); // Stores all tiles by their ID.

    /**
     * Sets the tiles for the board.
     * @param tiles A map of tile IDs to Tile objects.
     * @throws IllegalArgumentException if the tiles map is null or contains invalid data.
     */
    public void setTiles(Map<Integer, Tile> tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Tiles cannot be null.");
        }
        for (Map.Entry<Integer, Tile> entry : tiles.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new IllegalArgumentException("Tile IDs and Tile objects cannot be null.");
            }
            if (entry.getKey() < 1) {
                throw new IllegalArgumentException("Tile IDs must be positive integers.");
            }
        }
        this.tiles.clear();
        this.tiles.putAll(tiles);
    }

    /**
     * Retrieves all tiles on the board.
     * @return A collection of all Tile objects.
     */
    public Collection<Tile> getAllTiles() {
        return tiles.values();
    }

    /**
     * Retrieves a specific tile by its ID.
     * @param id The ID of the tile.
     * @return The Tile object with the specified ID.
     * @throws IllegalArgumentException if the tile ID is invalid or does not exist.
     */
    public Tile getTile(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Tile ID must be a positive integer.");
        }
        Tile tile = tiles.get(id);
        if (tile == null) {
            throw new IllegalArgumentException("No tile found with ID: " + id);
        }
        return tile;
    }

    public Tile getNextTile(Tile currentTile, int roll) {
        if (currentTile == null || !tiles.containsValue(currentTile)) {
            throw new IllegalArgumentException("Current tile is invalid or does not belong to this board.");
        }
        Tile nextTile = currentTile;
        for (int i = 0; i < roll; i++) {
            if (nextTile.getNextTile() == null) {
                return nextTile; // Stop at the last tile if no further tiles exist.
            }
            nextTile = nextTile.getNextTile();
        }
        return nextTile;
    }
}