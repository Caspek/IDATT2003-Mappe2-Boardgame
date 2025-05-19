package edu.ntnu.iir.bidata.player;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.tile.Tile;

public class Player {
    private final String name;
    private final String playingPiece;
    private BoardGame game;
    private Tile currentTile;

    public Player(String name, String playingPiece, BoardGame game) {
        this.name = name;
        this.playingPiece = playingPiece;
        this.game = game;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public String getPlayingPiece() {
        return playingPiece;
    }

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

    public BoardGame getGame() {
        return game;
    }
}

