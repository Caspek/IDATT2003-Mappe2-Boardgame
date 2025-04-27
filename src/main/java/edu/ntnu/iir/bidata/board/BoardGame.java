package edu.ntnu.iir.bidata.board;

import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.dice.Dice;

import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private final Board board = new Board();
    private final List<Player> players = new ArrayList<>();
    public Dice dice;

    /**
     * Loads the board configuration from a file.
     * @param filePath The path to the board configuration file.
     */
    public void loadBoard(String filePath) {
        board.loadBoardFromFile(filePath);
    }

    /**
     * Sets the dice to be used in the game.
     * @param dice The Dice object.
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    /**
     * Rolls the dice and returns the result.
     * @return The sum of the dice roll.
     */
    public int rollDice() {
        return dice.roll();
    }

    /**
     * Adds a player to the game.
     * @param player The Player object to add.
     */
    public void addPlayer(Player player) {
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

    public Dice getDice() {
        return dice;
    }
}
