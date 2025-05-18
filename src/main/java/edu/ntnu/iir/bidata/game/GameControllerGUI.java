package edu.ntnu.iir.bidata.game;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.tile.Tile;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the future GUI of the game.
 */
public class GameControllerGUI {
    private static final Logger LOGGER = Logger.getLogger(GameControllerGUI.class.getName());
    private final BoardGame game;

    /**
     * Constructs a GameControllerGUI instance.
     * @param game The BoardGame instance to control.
     * @throws IllegalArgumentException if the game is null.
     */
    public GameControllerGUI(BoardGame game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null.");
        }
        this.game = game;
    }

    /**
     * Executes a single turn of the game and returns the result.
     * @return The result of the turn as a TurnResult object.
     */
    public TurnResult playTurn() {
        try {
            Player player = game.getCurrentPlayer();
            Tile fromTile = player.getCurrentTile();
            int roll = game.rollDice();
            Tile toTile = player.move(roll);
            boolean hasWon = toTile.getNextTile() == null;

            if (hasWon) {
                game.setGameOver(true);
            }

            game.nextPlayer();

            LOGGER.log(Level.INFO, "Turn played: {0} rolled {1} and moved from {2} to {3}.",
                    new Object[]{player.getName(), roll, fromTile.getId(), toTile.getId()});

            return new TurnResult(player, roll, fromTile, toTile, hasWon);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during playTurn: " + e.getMessage(), e);
            throw new RuntimeException("Failed to execute playTurn.", e);
        }
    }
}

