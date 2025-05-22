package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.player.Player;

/**
 * The TileAction interface defines a contract for actions that can be performed
 * when a player lands on a tile. Implementations of this interface should define
 * the specific action to be taken.
 */
public interface TileAction {

    /**
     * Executes the action associated with the tile for the given player.
     *
     * @param player The player who landed on the tile.
     */
  void execute(Player player);
}