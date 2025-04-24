package edu.ntnu.iir.bidata;

public class Tile {
    private final int id;
    private Tile nextTile;

    public Tile(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public void getPreviousTile() {
    }

    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }

    public void landPlayer(Player player) {

    }

    public void leavePlayer(Player player) {
    }

    public void setLandAction(TileAction landAction) {
    }
}
