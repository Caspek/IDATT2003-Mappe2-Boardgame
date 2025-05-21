package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.board.BoardGameFactory;
import edu.ntnu.iir.bidata.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialTileActionTests {

    @Test
    public void testMoveExtraStepsActionMovesForward() {
        BoardGame game = BoardGameFactory.createSimpleBoard();
        Player player = new Player("Test", game);
        Tile startTile = game.getBoardInternal().getTile(1);
        player.setCurrentTile(startTile);

        MoveExtraStepsAction action = new MoveExtraStepsAction(5);
        action.execute(player);

        assertEquals(6, player.getCurrentTile().getId());
    }

}
