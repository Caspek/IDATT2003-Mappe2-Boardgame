package edu.ntnu.iir.bidata;


import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private final Board board;
    private final List<Player> players;
    private Player currentplayer;
    public Dice dice;

public BoardGame() {
    players = new ArrayList<>();
    board = new Board(100);
}
    public void addPlayer(Player player) {
        if (players.size() < 2) {
            players.add(player);
            if (players.size() == 1) {
                currentplayer = player;
            }
        }
        else {
            throw new IllegalArgumentException("Too many players");
        }

    }

    public void createDice() {
        this.dice = new Dice(1);
        }


    public void createBoard() {

    }

    public Board getBoard() {
        return board;
    }

    public Dice getDice() {
        return dice;
    }


    public List<Player> getPlayers() {
    return players;
    }

}
