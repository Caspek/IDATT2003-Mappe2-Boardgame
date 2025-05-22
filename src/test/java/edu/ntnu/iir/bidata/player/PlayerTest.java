package edu.ntnu.iir.bidata.player;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.config.BoardGameFactory;
import edu.ntnu.iir.bidata.tile.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private BoardGame game;
    private Player player;
    private Tile startTile;

    @BeforeEach
    void setup() {
        game = BoardGameFactory.createSimpleBoard();
        player = new Player("TestPlayer", game);
        startTile = game.getStartingTile();
        player.setCurrentTile(startTile);
    }

    @Test
    void testSetAndGetCurrentTile() {
        Tile newTile = game.getBoardInternal().getTile(5);
        player.setCurrentTile(newTile);
        assertEquals(newTile, player.getCurrentTile());
    }

    @Test
    void testMoveUpdatesTile() {
        Tile before = player.getCurrentTile();
        Tile after = player.move(3);
        assertEquals(after, player.getCurrentTile());
        assertNotEquals(before, after);
    }

    @Test
    void testSetAndGetLastSpecialMoveSteps() {
        player.setLastSpecialMoveSteps(4);
        assertEquals(4, player.getLastSpecialMoveSteps());
    }

    @Test
    void testSetAndGetLastActionMessage() {
        String msg = "Landed on a special tile.";
        player.setLastActionMessage(msg);
        assertEquals(msg, player.getLastActionMessage());
    }

    @Test
    void testMoveBeyondBoardEndsAtLastTile() {
        Tile moved = player.move(200); // intentionally too far
        assertTrue(moved.isWinningTile());
    }

    @Test
    void testSetCurrentTileToNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> player.setCurrentTile(null));
    }


}

