package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.player.Player;


/**
 * Represents a tile on the board that a player can land on or leave.
 */
public class Tile {
  private final int id;
  private Tile nextTile;
  private TileAction landAction;

  /**
   * Constructs a Tile with the specified ID.
   *
   * @param id The unique identifier for the tile.
   * @throws IllegalArgumentException if the ID is less than 1.
   */
  public Tile(int id) {
    if (id < 1) {
      throw new IllegalArgumentException("Tile ID must be greater than or equal to 1.");
    }
    this.id = id;
  }

  /**
   * Gets the ID of the tile.
   *
   * @return The ID of the tile.
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the next tile linked to this tile.
   *
   * @return The next tile, or null if none is set.
   */
  public Tile getNextTile() {
    return nextTile;
  }

  /**
   * Sets the next tile linked to this tile.
   *
   * @param nextTile The next tile to link.
   * @throws IllegalArgumentException if nextTile is null.
   */
  public void setNextTile(Tile nextTile) {
    if (nextTile == null) {
      throw new IllegalArgumentException("Next tile cannot be null.");
    }
    this.nextTile = nextTile;
  }

  /**
   * Sets the action to be executed when a player lands on this tile.
   *
   * @param landAction The action to set.
   * @throws IllegalArgumentException if landAction is null.
   */
  public void setLandAction(TileAction landAction) {
    if (landAction == null) {
      throw new IllegalArgumentException("Land action cannot be null.");
    }
    this.landAction = landAction;
  }

  /**
   * Executes the land action for the given player.
   *
   * @param player The player landing on the tile.
   * @throws IllegalArgumentException if player is null.
   */
  public void landPlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (landAction != null) {
      landAction.execute(player);
    }
  }

  /**
   * Handles logic when a player leaves the tile.
   *
   * @param player The player leaving the tile.
   * @throws IllegalArgumentException if player is null.
   */
  public void leavePlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
  }

  /**
   * Checks if this tile is the winning tile.
   *
   * @return true if this tile is the winning tile, false otherwise.
   */
  public boolean isWinningTile() {
    return nextTile == null; // A tile is the winning tile if it has no next tile.
  }
}
