package edu.ntnu.iir.bidata;

public class TileAction {


    public void tileActionClimb4(Player player) {
        Tile currentTile = player.getCurrentTile();
        if (currentTile.getId() == 4) {
            Tile newTile = player.getGame().getBoard().getTile(37);
            player.setCurrentTile(newTile);
            System.out.println(player.getName() + " has climbed the ladder from tile 4 to tile 37!");
        }
    }

    public void tileActionClimb12(Player player) {
        Tile currentTile = player.getCurrentTile();
        if (currentTile.getId() == 12) {
            Tile newTile = player.getGame().getBoard().getTile(49);
            player.setCurrentTile(newTile);
            System.out.println(player.getName() + " has climbed the ladder from tile 12 to tile 49!");
        }
    }

    public void tileActionClimb27(Player player) {
        Tile currentTile = player.getCurrentTile();
        if (currentTile.getId() == 27) {
            Tile newTile = player.getGame().getBoard().getTile(55);
            player.setCurrentTile(newTile);
            System.out.println(player.getName() + " has climbed the ladder from tile 27 to tile 55!");
        }
    }


}
