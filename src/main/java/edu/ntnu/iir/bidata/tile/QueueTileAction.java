package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.player.Player;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The QueueTileAction class represents a special tile action where players
 * must wait in a queue. If the tile is already occupied by another player,
 * the current player is sent back to the starting tile.
 */
public class QueueTileAction implements TileAction {
  private static final Logger LOGGER = Logger.getLogger(QueueTileAction.class.getName());
  private Player currentOccupant = null;

  /**
   * Executes the queue tile action for the given player.
   * If the tile is unoccupied or occupied by the same player, the player stays on the tile.
   * If the tile is occupied by another player, the current player is sent back to the starting tile.
   *
   * @param player The player attempting to enter the queue tile.
   */
  @Override
  public void execute(Player player) {
    if (player == null) {
      LOGGER.log(Level.SEVERE, "Player cannot be null.");
      return;
    }

    if (currentOccupant == null || currentOccupant == player) {
      currentOccupant = player;
      player.setLastActionMessage(player.getName() + " has entered the queue tile.");
    } else {
      player.setLastActionMessage(player.getName() + " tried to enter the queue tile, but " +
            currentOccupant.getName() + " is already there. Its back to the start for you!");
      currentOccupant.getCurrentTile().leavePlayer(player);
      player.setCurrentTile(player.getGame().getStartingTile());
      player.getCurrentTile().landPlayer(player);
    }
  }
}
