package edu.ntnu.iir.bidata;


import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private Board board;
    private List<Player> players;
    private Player currentplayer;
    private Dice dice;

public BoardGame() {
    players = new ArrayList<>();
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

    }

    public void createBoard() {

    }

    //public Player getWinner() {

    //}

    public void play() {


    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentplayer;
    }
}
