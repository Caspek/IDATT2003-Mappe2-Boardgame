package edu.ntnu.iir.bidata;

public class Player {
    private final String name;
    private final String playingPiece;
    private BoardGame game;
    private Tile currentTile;
    private final Dice dice;

    public Player (String name, String playingPiece, BoardGame game) {
        this.name = name;
        this.playingPiece = playingPiece;
        this.game = game;
        this.currentTile = game.getBoard().getTile(1);
        this.dice = new Dice(1);
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public String getPlayingPiece() {
        return playingPiece;
    }


    public Tile move(int steps) {
        if (steps > 0) {
            for (int i = 0; i < steps; i++) {
                if (currentTile.getNextTile() != null) {
                    currentTile = currentTile.getNextTile();
                } else {
                    System.out.println(name + " has reached the end of the game!");
                    break;
                }
            }
        }
        return currentTile;
    }

    public String getName () {
        return name;
    }

    public void setGame(BoardGame game) {
        this.game = game;
        this.currentTile = game.getBoard().getTile(1); // Reset to the starting tile
    }

}
