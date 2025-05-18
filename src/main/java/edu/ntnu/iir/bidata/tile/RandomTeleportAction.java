package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.board.Board;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents an action that teleports a player to a random tile on the board.
 */
public class RandomTeleportAction implements TileAction {
    private static final Logger LOGGER = Logger.getLogger(RandomTeleportAction.class.getName());
    private final Board board;

    /**
     * Constructs a RandomTeleportAction with the specified board.
     *
     * @param board The board to use for teleportation.
     * @throws IllegalArgumentException if the board is null.
     */
    public RandomTeleportAction(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }
        this.board = board;
    }

    /**
     * Executes the teleportation action for the given player.
     *
     * @param player The player to teleport.
     */
    @Override
    public void execute(Player player) {
        if (player == null) {
            LOGGER.log(Level.SEVERE, "Player cannot be null.");
            return;
        }

        if (board.getAllTiles().isEmpty()) {
            LOGGER.log(Level.WARNING, "The board has no tiles. Teleportation cannot be performed.");
            return;
        }

        Random random = new Random();
        int randomTileId;

        do {
            randomTileId = random.nextInt(board.getAllTiles().size()) + 1; // Assuming tile IDs start at 1
        } while (randomTileId == board.getAllTiles().size()); // Exclude the final tile

        Tile targetTile = board.getTile(randomTileId);
        if (targetTile == null) {
            LOGGER.log(Level.WARNING, "Target tile with ID {0} does not exist.", randomTileId);
            return;
        }

        player.setCurrentTile(targetTile);
        LOGGER.log(Level.INFO, "{0} has been teleported to tile {1}.", new Object[]{player.getName(), randomTileId});
    }
}