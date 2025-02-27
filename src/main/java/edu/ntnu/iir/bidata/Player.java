package edu.ntnu.iir.bidata;

public class Player extends BoardGame {
    private String name;
    private Tile currentTile;

    public Player (String name, BoardGame game) {
    this.name = name;
    }

    public void placeOnTile(Tile tile) {

    }

    public void move(int steps) {

    }

    public String getName () {
        return name;
    }
}
