package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.player.Player;

public class Tile {
    private final int id;
    private Tile nextTile;
    private TileAction landAction;

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

    public void setLandAction(TileAction landAction) {
        this.landAction = landAction;
    }

    public void landPlayer(Player player) {
        if (landAction != null) {
            landAction.execute(player);
        }
    }

    public void leavePlayer(Player player) {
        // Placeholder for any logic when a player leaves the tile
    }
}
