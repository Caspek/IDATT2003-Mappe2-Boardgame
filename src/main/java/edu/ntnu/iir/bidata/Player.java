package edu.ntnu.iir.bidata;

public class Player {
    private String name;
    private BoardGame game;
    private Tile currentTile;
    private Dice dice;

    public Player (String name, BoardGame game) {
        this.name = name;
        this.game = game;
        this.currentTile = game.getBoard().getTile(1);
        this.dice = new Dice(1);
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile tile) {
        this.currentTile = tile;
    }

    public BoardGame getGame() {
        return game;
    }

    public void move(int steps) {
        if (steps > 0) {
            for (int i = 0; i < steps; i++) {
                if (currentTile.getNextTile() != null) {
                    currentTile = currentTile.getNextTile();
                } else {
                    System.out.println(name + " has reached the end of the game!");
                    break;
                }
            }
        } else if (steps < 0) {
            for (int i = 0; i < Math.abs(steps); i++) {
                if (currentTile.getPreviousTile() != null) {
                    currentTile = currentTile.getPreviousTile();
                } else {
                    System.out.println(name + " has reached the start of the game!");
                    break;
                }
            }
        }
    }

    public String getName () {
        return name;
    }

}
