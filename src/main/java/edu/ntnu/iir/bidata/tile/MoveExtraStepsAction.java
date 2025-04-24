package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.board.Board;
import edu.ntnu.iir.bidata.player.Player;

public class MoveExtraStepsAction implements TileAction {
    private final int steps;

    public MoveExtraStepsAction(int steps) {
        this.steps = steps;
    }

    @Override
    public void execute(Player player) {
        String direction = steps > 0 ? "forward" : "back";
        System.out.println(player.getName() + " encounters a special tile! Moving " +
                Math.abs(steps) + " steps " + direction + ".");

        if (steps < 0) {
            moveToSpecificTile(player, player.getCurrentTile().getId() + steps);
        } else {
            player.move(steps);
        }
    }

    private void moveToSpecificTile(Player player, int targetId) {
        // Ensure we don't go below tile 1
        targetId = Math.max(1, targetId);

        Board board = player.getGame().getBoard();
        Tile targetTile = board.getTile(targetId);

        if (targetTile != null) {
            player.getCurrentTile().leavePlayer(player);
            player.setCurrentTile(targetTile);
            targetTile.landPlayer(player);
        }
    }
}
