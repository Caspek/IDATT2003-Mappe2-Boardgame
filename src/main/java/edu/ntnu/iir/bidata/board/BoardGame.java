package edu.ntnu.iir.bidata.board;

import edu.ntnu.iir.bidata.dice.Dice;
import edu.ntnu.iir.bidata.dice.DiceLogic;
import edu.ntnu.iir.bidata.observer.BoardGameObserver;
import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.player.PlayerLoader;
import edu.ntnu.iir.bidata.tile.Tile;
import edu.ntnu.iir.bidata.game.TurnResult;

import java.util.*;

/**
 * Represents a board game that manages the game state, players, dice, and board.
 * It provides methods to load the board, add players, play turns, and notify observers of game events.
 */
public class BoardGame {
    private final Board board = new Board();
    private final List<Player> players = new ArrayList<>();
    private Dice dice;
    private int currentPlayerIndex = 0;
    private boolean gameOver = false;
    private final Set<BoardGameObserver> observers = new HashSet<>();

    /**
     * Loads the board configuration from a file.
     * @param filePath The path to the file containing the board configuration.
     */
    public void loadBoard(String filePath) {
        BoardFileReader boardFileReader = new BoardFileReader();
        board.setTiles(boardFileReader.readBoardFromFile(filePath, this.board));
    }

    /**
     * Sets the tiles for the board.
     * @param tiles A map of tile IDs to Tile objects.
     */
    public void setTiles(Map<Integer, Tile> tiles) {
        board.setTiles(tiles);
    }

    /**
     * Retrieves the internal board object.
     * @return The board object.
     */
    public Board getBoardInternal() {
        return this.board;
    }

    /**
     * Retrieves the starting tile of the board.
     * @return The starting Tile object.
     */
    public Tile getStartingTile() {
        return board.getTile(1);
    }

    /**
     * Sets the dice configuration for the game.
     * @param numberOfDice The number of dice to use.
     * @param isQueueBoard Whether the board is a queue board (only allows 1 die).
     * @throws IllegalArgumentException If the board is a queue board and more than 1 die is specified.
     */
    public void setDice(int numberOfDice, boolean isQueueBoard) {
        if (isQueueBoard && numberOfDice != 1) {
            throw new IllegalArgumentException("QueueBoard only allows 1 die.");
        }
        this.dice = new DiceLogic(numberOfDice);
    }

    /**
     * Adds a player to the game.
     * @param name The name of the player.
     * @throws IllegalArgumentException If the player name is null or empty.
     */
    public void addPlayer(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty.");
        }
        Player player = new Player(name, this);
        player.setCurrentTile(getStartingTile());
        players.add(player);
    }

    /**
     * Loads players from a file and adds them to the game.
     * @param filePath The path to the file containing player data.
     */
    public void loadPlayers(String filePath) {
        List<Player> loaded = PlayerLoader.loadPlayersFromFile(filePath, this);
        for (Player player : loaded) {
            player.setCurrentTile(getStartingTile());
        }
        players.addAll(loaded);
    }

    /**
     * Checks if the game is over.
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return !gameOver;
    }

    /**
     * Plays a turn for the current player.
     * @return A TurnResult object containing the details of the turn.
     * @throws IllegalStateException If the game is over, dice are not initialized, or no players are available.
     */
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

        player.setCurrentTile(toTile);
        toTile.landPlayer(player);

        notifyPlayerMoved(player, fromTile, toTile, roll);

        if (player.getLastActionMessage() != null) {
            Tile specialTo = player.getCurrentTile();

            if (!specialTo.equals(toTile)) {
                notifyPlayerMoved(
                        player,
                        toTile,
                        specialTo,
                        player.getLastSpecialMoveSteps()
                );
            }
            notifySpecialTile(player, specialTo, player.getLastActionMessage());
        }

        boolean hasWon = player.getCurrentTile().isWinningTile();
        if (hasWon) {
            gameOver = true;
            notifyGameWon(player);
        } else {
            nextPlayer();
        }

        return new TurnResult(player, roll, fromTile, player.getCurrentTile(), hasWon);
    }

    /**
     * Advances to the next player's turn.
     */
    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Retrieves the current player.
     * @return The current Player object.
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Adds an observer to the game.
     * @param observer The observer to add.
     */
    public void addObserver(BoardGameObserver observer) {
        if (observer != null) observers.add(observer);
    }

    /**
     * Notifies observers when a player moves.
     * @param player The player who moved.
     * @param from The tile the player moved from.
     * @param to The tile the player moved to.
     * @param roll The dice roll that caused the move.
     */
    private void notifyPlayerMoved(Player player, Tile from, Tile to, int roll) {
        for (BoardGameObserver o : observers) {
            o.onPlayerMoved(player, from, to, roll);
        }
    }

    /**
     * Notifies observers when a player wins the game.
     * @param winner The player who won the game.
     */
    private void notifyGameWon(Player winner) {
        for (BoardGameObserver o : observers) {
            o.onGameWon(winner);
        }
    }

    /**
     * Notifies observers when a player lands on a special tile.
     * @param player The player who landed on the special tile.
     * @param tile The special tile.
     * @param description A description of the special action.
     */
    private void notifySpecialTile(Player player, Tile tile, String description) {
        for (BoardGameObserver o : observers) {
            o.onSpecialTile(player, tile, description);
        }
    }

    /**
     * Retrieves the list of players in the game.
     * @return An unmodifiable list of players.
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * Retrieves the dice used in the game.
     * @return The Dice object.
     */
    public Dice getDice() {
        return dice;
    }
}

