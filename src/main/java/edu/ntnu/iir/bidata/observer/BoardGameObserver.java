package edu.ntnu.iir.bidata.observer;

import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.tile.Tile;

/**
 * The BoardGameObserver interface defines methods for observing events in a board game.
 * Implementations of this interface can be used to handle game events such as player movement,
 * special tile interactions, and game completion.
 */
public interface BoardGameObserver {

  /**
   * Called when a player moves to a new tile.
   *
   * @param player   The player who moved.
   * @param fromTile The tile the player moved from.
   * @param toTile   The tile the player moved to.
   * @param roll     The dice roll that determined the move.
   */
  void onPlayerMoved(Player player, Tile fromTile, Tile toTile, int roll);

  /**
   * Called when a player lands on a special tile.
   *
   * @param player      The player who landed on the special tile.
   * @param tile        The special tile the player landed on.
   * @param description A description of the special tile's effect or message.
   */
  void onSpecialTile(Player player, Tile tile, String description);

  /**
   * Called when a player wins the game.
   *
   * @param winner The player who won the game.
   */
  void onGameWon(Player winner);
}
