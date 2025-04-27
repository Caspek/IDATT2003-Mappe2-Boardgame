package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.board.Board;

import java.util.Random;

public class RandomTeleportAction implements TileAction {
    private final Board board;

    public RandomTeleportAction(Board board) {
        this.board = board;
    }

    @Override
    public void execute(Player player) {
        Random random = new Random();
        int randomTileId;

        do {
            randomTileId = random.nextInt(board.getAllTiles().size()) + 1; // Assuming tile IDs start at 1
        } while (randomTileId == board.getAllTiles().size()); // Exclude the final tile

        player.setCurrentTile(board.getTile(randomTileId));
        System.out.println(player.getName() + " has been teleported to tile " + randomTileId + "!");
    }
}
