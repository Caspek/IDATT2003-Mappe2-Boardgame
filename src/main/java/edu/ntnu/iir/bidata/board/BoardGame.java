package edu.ntnu.iir.bidata.board;

import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.dice.Dice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardGame {
    private final Board board = new Board();
    private final List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private Dice dice;
    private int currentPlayerIndex = 0;
    private boolean gameOver = false;
    private boolean hasWon = false;

    /**
     * Loads the board configuration from a file.
     * @param filePath The path to the board configuration file.
     * @throws IllegalArgumentException if the filePath is null or empty.
     */
    public void loadBoard(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty.");
        }
        try {
            BoardFileReader boardFileReader = new BoardFileReader(board);
            board.setTiles(boardFileReader.readBoardFromFile(filePath));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load the board: " + e.getMessage(), e);
        }
    }

    /**
     * Sets the dice to be used in the game.
     * @param dice The Dice object.
     * @throws IllegalArgumentException if the dice is null.
     */
    public void setDice(Dice dice) {
        if (dice == null) {
            throw new IllegalArgumentException("Dice cannot be null.");
        }
        this.dice = dice;
    }

    /**
     * Rolls the dice and returns the result.
     * @return The sum of the dice roll.
     * @throws IllegalStateException if the dice is not set.
     */
    public int rollDice() {
        if (dice == null) {
            throw new IllegalStateException("Dice has not been set.");
        }
        return dice.roll();
    }

    /**
     * Adds a player to the game.
     * @param player The Player object to add.
     * @throws IllegalArgumentException if the player is null.
     */
    public void addPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
        players.add(player);
    }

    /**
     * Retrieves the list of players in the game.
     * @return A list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Retrieves the game board.
     * @return The Board object.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Retrieves the current player.
     * @return The current Player object.
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Retrieves the index of the next player.
     * @return The index of the current player.
     */
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Checks if the game is over.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Checks if a player has won the game.
     * @return true if a player has won, false otherwise.
     */
    public boolean hasWon() {
        return hasWon;
    }

    /**
     * Sets the game over status.
     * @param status The game over status.
     */
    public void setGameOver(boolean status) {
        this.gameOver = status;
    }
}