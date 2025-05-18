package edu.ntnu.iir.bidata.player;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.tile.Tile;

public class Player {
    private final String name;
    private final String playingPiece;
    private BoardGame game;
    private Tile currentTile;

    /**
     * Constructs a Player object.
     * @param name The name of the player.
     * @param playingPiece The playing piece of the player.
     * @param game The game the player is part of.
     */
    public Player(String name, String playingPiece, BoardGame game) {
        this.name = name;
        this.playingPiece = playingPiece;
        this.setGame(game);
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    /**
     * Sets the current tile of the player.
     * @param currentTile The tile to set as the current tile.
     */
    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public String getPlayingPiece() {
        return playingPiece;
    }

    /**
     * Moves the player by the specified number of steps.
     * @param steps The number of steps to move.
     * @return The tile the player lands on.
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

    public String getName() {
        return name;
    }

    /**
     * Sets the game for the player and initializes the starting tile.
     * @param game The game to set.
     */
    public void setGame(BoardGame game) {
        this.game = game;
        this.currentTile = game.getBoard().getTile(1);
    }

    public BoardGame getGame() {
        return game;
    }
}