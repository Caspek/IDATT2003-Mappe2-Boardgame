package edu.ntnu.iir.bidata;

public class Tile {
    private int id;
    private Tile nextTile;
    private Tile previousTile;

    public Tile(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }

    public Tile getPreviousTile() {
        return previousTile;
    }

    public void setPreviousTile(Tile previousTile) {
        this.previousTile = previousTile;
    }

    public void landPlayer(Player player) {

    }

    public void leavePlayer(Player player) {
    }

    public void setLandAction(TileAction landAction) {
    }
}
