package edu.ntnu.iir.bidata;

import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private final Board board = new Board();
    private final List<Player> players = new ArrayList<>();
    public Dice dice;

    public void loadBoard(String filePath) {
        board.loadBoardFromFile(filePath);
    }

    public void createDice() {
        this.dice = new Dice(1);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public Dice getDice() {
        return dice;
    }
}
