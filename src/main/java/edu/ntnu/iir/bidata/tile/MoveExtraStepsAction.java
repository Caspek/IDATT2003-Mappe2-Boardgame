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

        int specialTileId = player.getCurrentTile().getId();
        String direction = steps > 0 ? "forward" : "backward";
        int targetTileId = Math.max(1, specialTileId + steps);
        String message = player.getName()
                + " landed on a special tile! Moving "
                + Math.abs(steps)
                + " steps "
                + direction
                + " to tile "
                + targetTileId
                + ".";
        player.setLastSpecialMoveSteps(steps);
        player.setLastActionMessage(message);
        LOGGER.log(Level.INFO, message);

        if (steps < 0) {
            moveToSpecificTile(player, targetTileId);
        } else {
            Tile newTile = player.move(steps);
            newTile.landPlayer(player);
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

    public int getSteps() {
        return steps;
    }
}



