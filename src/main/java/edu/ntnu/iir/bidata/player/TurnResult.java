package edu.ntnu.iir.bidata.player;

import edu.ntnu.iir.bidata.tile.Tile;
import java.util.Objects;

/**
 * Represents the result of a single turn in the game.
 * This class is immutable and contains information about the player, dice roll,
 * the tiles involved in the move, and whether the player has won.
 */
public class TurnResult {
  private static final int MIN_ROLL = 1;
  private static final int MAX_ROLL = 12;

  private final Player player;
  private final int roll;
  private final Tile fromTile;
  private final Tile toTile;
  private final boolean hasWon;

  /**
   * Constructs a new TurnResult instance.
   *
   * @param player   The player who took the turn.
   * @param roll     The dice roll for the turn.
   * @param fromTile The tile the player moved from.
   * @param toTile   The tile the player moved to.
   * @param hasWon   Whether the player has won the game.
   * @throws IllegalArgumentException if any parameter is null, or if the roll is out of range.
   */
  public TurnResult(Player player, int roll, Tile fromTile, Tile toTile, boolean hasWon) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (fromTile == null) {
      throw new IllegalArgumentException("FromTile cannot be null.");
    }
    if (toTile == null) {
      throw new IllegalArgumentException("ToTile cannot be null.");
    }
    if (roll < MIN_ROLL || roll > MAX_ROLL) {
      throw new IllegalArgumentException("Roll must be between "
          + MIN_ROLL + " and " + MAX_ROLL + ".");
    }

    this.player = player;
    this.roll = roll;
    this.fromTile = fromTile;
    this.toTile = toTile;
    this.hasWon = hasWon;
  }

  /**
   * Gets the player who took the turn.
   *
   * @return The player.
   */
  public Player getPlayer() {
    return player;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    TurnResult that = (TurnResult) object;
    return roll == that.roll
        && hasWon == that.hasWon
        && Objects.equals(player, that.player)
        && Objects.equals(fromTile, that.fromTile)
        && Objects.equals(toTile, that.toTile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(player, roll, fromTile, toTile, hasWon);
  }

  @Override
  public String toString() {
    return "TurnResult{"
        + "player=" + player.getName()
        + ", roll=" + roll
        + ", fromTile=" + fromTile.getId()
        + ", toTile=" + toTile.getId()
        + ", hasWon=" + hasWon
        + '}';
  }

  /**
   * Gets the dice roll for the turn.
   *
   * @return The roll.
   */
  public int getRoll() {
    return roll;
  }

  /**
   * Gets the tile the player moved from.
   *
   * @return The fromTile.
   */
  public Tile getFromTile() {
    return fromTile;
  }

  /**
   * Gets the tile the player moved to.
   *
   * @return The toTile.
   */
  public Tile getToTile() {
    return toTile;
  }

  /**
   * Checks if the player has won the game.
   *
   * @return true if the player has won, false otherwise.
   */
  public boolean hasWon() {
    return hasWon;
  }

}

