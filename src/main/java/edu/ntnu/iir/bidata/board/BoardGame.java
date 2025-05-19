package edu.ntnu.iir.bidata.board;

import edu.ntnu.iir.bidata.dice.Dice;
import edu.ntnu.iir.bidata.dice.DiceLogic;
import edu.ntnu.iir.bidata.observer.BoardGameObserver;
import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.player.PlayerLoader;
import edu.ntnu.iir.bidata.tile.Tile;
import edu.ntnu.iir.bidata.game.TurnResult;

import java.util.*;

public class BoardGame {
    private final Board board = new Board();
    private final List<Player> players = new ArrayList<>();
    private Dice dice;
    private int currentPlayerIndex = 0;
    private boolean gameOver = false;
    private final Set<BoardGameObserver> observers = new HashSet<>();

    public void loadBoard(String filePath) {
        BoardFileReader boardFileReader = new BoardFileReader(board);
        board.setTiles(boardFileReader.readBoardFromFile(filePath));
    }

    public void setTiles(Map<Integer, Tile> tiles) {
        board.setTiles(tiles);
    }

    public Board getBoardInternal() {
        return this.board;
    }

    public Tile getStartingTile() {
        return board.getTile(1);
    }

    public void setDice(int numberOfDice) {
        this.dice = new DiceLogic(numberOfDice);
    }

    public void addPlayer(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty.");
        }
        Player player = new Player(name, this);
        player.setCurrentTile(getStartingTile());
        players.add(player);
    }

    public void loadPlayers(String filePath) {
        List<Player> loaded = PlayerLoader.loadPlayersFromFile(filePath, this);
        for (Player player : loaded) {
            player.setCurrentTile(getStartingTile());
        }
        players.addAll(loaded);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public TurnResult playTurn() {
        if (gameOver || dice == null || players.isEmpty()) {
            throw new IllegalStateException("Game cannot proceed. Check dice, players, and game status.");
        }

        Player player = getCurrentPlayer();

        player.setLastActionMessage(null);
        player.setLastSpecialMoveSteps(0);

        Tile fromTile = player.getCurrentTile();
        int roll = dice.roll();
        Tile toTile = board.getNextTile(fromTile, roll);

        notifyPlayerMoved(player, fromTile, toTile, roll);

        player.setCurrentTile(toTile);
        toTile.landPlayer(player);

        boolean hasWon = toTile.isWinningTile();
        if (hasWon) {
            gameOver = true;
            notifyGameWon(player);
        } else {
            nextPlayer();
        }

        return new TurnResult(player, roll, fromTile, player.getCurrentTile(), hasWon);
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void addObserver(BoardGameObserver observer) {
        if (observer != null) observers.add(observer);
    }

    public void removeObserver(BoardGameObserver observer) {
        observers.remove(observer);
    }

    private void notifyPlayerMoved(Player player, Tile from, Tile to, int roll) {
        for (BoardGameObserver o : observers) {
            o.onPlayerMoved(player, from, to, roll);
        }
    }

    private void notifyGameWon(Player winner) {
        for (BoardGameObserver o : observers) {
            o.onGameWon(winner);
        }
    }
}

