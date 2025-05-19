package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.board.Board;
import edu.ntnu.iir.bidata.player.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MoveExtraStepsAction implements TileAction {
    private static final Logger LOGGER = Logger.getLogger(MoveExtraStepsAction.class.getName());
    private final int steps;

    public MoveExtraStepsAction(int steps) {
        if (steps == 0) {
            throw new IllegalArgumentException("Steps cannot be zero.");
        }
        this.steps = steps;
    }

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

    private void moveToSpecificTile(Player player, int targetId) {
        if (player.getCurrentTile() == null) {
            LOGGER.log(Level.SEVERE, "Player's current tile is null.");
            return;
        }

        targetId = Math.max(1, targetId);
        Board board = player.getGame().getBoardInternal();
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


