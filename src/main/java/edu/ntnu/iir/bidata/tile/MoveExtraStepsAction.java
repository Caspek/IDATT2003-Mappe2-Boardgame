package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.board.Board;
import edu.ntnu.iir.bidata.player.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents an action that moves a player a specified number of steps
 * when they land on a special tile.
 */
public class MoveExtraStepsAction implements TileAction {
    private static final Logger LOGGER = Logger.getLogger(MoveExtraStepsAction.class.getName());
    private final int steps;

    /**
     * Constructs a MoveExtraStepsAction with the specified number of steps.
     *
     * @param steps The number of steps to move the player. Positive values move forward,
     *              and negative values move backward. Cannot be zero.
     * @throws IllegalArgumentException if steps is zero.
     */
    public MoveExtraStepsAction(int steps) {
        if (steps == 0) {
            throw new IllegalArgumentException("Steps cannot be zero.");
        }
        this.steps = steps;
    }

    /**
     * Executes the action for the given player, moving them the specified number of steps.
     *
     * @param player The player to move. If null, the action is not executed.
     */
    @Override
    public void execute(Player player) {
        if (player == null) {
            LOGGER.log(Level.SEVERE, "Player cannot be null.");
            return;
        }

        String direction = steps > 0 ? "forward" : "back";
        LOGGER.log(Level.INFO, "{0} encounters a special tile! Moving {1} steps {2}.",
                new Object[]{player.getName(), Math.abs(steps), direction});

        if (steps < 0) {
            moveToSpecificTile(player, player.getCurrentTile() != null ? player.getCurrentTile().getId() + steps : 1);
        } else {
            player.move(steps);
        }
    }

    /**
     * Moves the player to a specific tile based on the target tile ID.
     *
     * @param player   The player to move.
     * @param targetId The ID of the target tile to move to. If the target tile does not exist,
     *                 the action is not executed.
     */
    private void moveToSpecificTile(Player player, int targetId) {
        if (player.getCurrentTile() == null) {
            LOGGER.log(Level.SEVERE, "Player's current tile is null.");
            return;
        }

        // Ensure we don't go below tile 1
        targetId = Math.max(1, targetId);

        Board board = player.getGame().getBoard();
        Tile targetTile = board.getTile(targetId);

        if (targetTile == null) {
            LOGGER.log(Level.WARNING, "Target tile with ID {0} does not exist.", targetId);
            return;
        }

        player.getCurrentTile().leavePlayer(player);
        player.setCurrentTile(targetTile);
        targetTile.landPlayer(player);
    }
}
