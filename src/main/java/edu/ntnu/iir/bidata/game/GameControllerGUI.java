package edu.ntnu.iir.bidata.game;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.tile.Tile;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameControllerGUI {
    private static final Logger LOGGER = Logger.getLogger(GameControllerGUI.class.getName());
    private final BoardGame game;

    public GameControllerGUI(BoardGame game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null.");
        }
        this.game = game;
    }

    public TurnResult playTurn() {
        try {
            TurnResult result = game.playTurn();

            LOGGER.log(Level.INFO, "Turn played: {0} rolled {1} and moved from {2} to {3}.",
                    new Object[]{
                            result.getPlayer().getName(),
                            result.getRoll(),
                            result.getFromTile().getId(),
                            result.getToTile().getId()
                    });

            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during playTurn: " + e.getMessage(), e);
            throw new RuntimeException("Failed to execute playTurn.", e);
        }
    }
}
