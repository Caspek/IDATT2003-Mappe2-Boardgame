package edu.ntnu.iir.bidata.player;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.tile.Tile;

/**
 * The Player class represents a player in the board game.
 * It contains information about the player's name, current position on the board,
 * and the game they are participating in.
 */
public class Player {
    private final String name;
    private final BoardGame game;
    private Tile currentTile;
    private int lastSpecialMoveSteps = 0;
    private String lastActionMessage = null;

    /**
     * Constructs a new Player instance.
     *
     * @param name The name of the player.
     * @param game The board game the player is participating in.
     */
    public Player(String name, BoardGame game) {
        this.name = name;
        this.game = game;
    }

    /**
     * Gets the current tile the player is on.
     *
     * @return The current tile.
     */
    public Tile getCurrentTile() {
        return currentTile;
    }

    /**
     * Sets the current tile the player is on.
     *
     * @param currentTile The tile to set as the player's current position.
     */
    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    /**
     * Sets the number of steps the player moved during the last special move.
     *
     * @param steps The number of steps.
     */
    public void setLastSpecialMoveSteps(int steps) {
        this.lastSpecialMoveSteps = steps;
    }

    /**
     * Gets the number of steps the player moved during the last special move.
     *
     * @return The number of steps.
     */
    public int getLastSpecialMoveSteps() {
        return lastSpecialMoveSteps;
    }

    /**
     * Sets the last action message for the player.
     *
     * @param message The action message to set.
     */
    public void setLastActionMessage(String message) {
        this.lastActionMessage = message;
    }

    /**
     * Gets the last action message for the player.
     *
     * @return The last action message.
     */
    public String getLastActionMessage() {
        return lastActionMessage;
    }

    /**
     * Moves the player a specified number of steps on the board.
     * If the player reaches the end of the board, the movement stops.
     *
     * @param steps The number of steps to move.
     * @return The tile the player ends up on after the move.
     */
    public Tile move(int steps) {
        if (steps > 0) {
            for (int i = 0; i < steps; i++) {
                if (currentTile.getNextTile() != null) {
                    currentTile.leavePlayer(this);
                    currentTile = currentTile.getNextTile();
                } else {
                    System.out.println(name + " has reached the end of the game!");
                    return currentTile;
                }
            }
            currentTile.landPlayer(this);
        }
        return currentTile;
    }

    /**
     * Gets the name of the player.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the board game the player is participating in.
     *
     * @return The board game.
     */
    public BoardGame getGame() {
        return game;
    }
}
