package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.board.Board;
import edu.ntnu.iir.bidata.player.Player;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomTeleportAction implements TileAction {
    private static final Logger LOGGER = Logger.getLogger(RandomTeleportAction.class.getName());
    private final Board board;
    private final Random rand = new Random();

    public RandomTeleportAction(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }
        this.board = board;
    }

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

        int specialTileId = player.getCurrentTile().getId();
        Random random = new Random();
        int randomTileId;
        do {
            randomTileId = random.nextInt(board.getAllTiles().size()) + 1;
        } while (randomTileId == board.getAllTiles().size());

        Tile targetTile = board.getTile(randomTileId);
        if (targetTile == null) {
            LOGGER.log(Level.WARNING, "Target tile with ID {0} does not exist.", randomTileId);
            return;
        }

        player.setCurrentTile(targetTile);
        String message = player.getName()
                + " landed on special tile "
                + specialTileId
                + " and was teleported to tile "
                + randomTileId
                + "!";
        player.setLastActionMessage(message);
        LOGGER.log(Level.INFO, message);
    }
}
