package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.config.BoardGameFactory;
import edu.ntnu.iir.bidata.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueTileActionTest {
    private BoardGame game;
    private Tile tile;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setup() {
        game = BoardGameFactory.createQueueBoard();
        tile = game.getBoardInternal().getTile(10); // tile with QueueTileAction
        QueueTileAction action = new QueueTileAction();
        tile.setLandAction(action);
        player1 = new Player("Player1", game);
        player2 = new Player("Player2", game);
    }

    @Test
    void testPlayerCanOccupyQueueTile() {
        player1.setCurrentTile(tile);
        tile.landPlayer(player1);
        assertEquals("Player1 has entered the queue tile.", player1.getLastActionMessage());
    }

    @Test
    void testPlayerIsSentBackIfTileOccupied() {
        // Player1 is on the queue tile
        player1.setCurrentTile(tile);
        tile.landPlayer(player1);

        // Player2 is on the starting tile
        Tile startTile = game.getStartingTile();
        player2.setCurrentTile(startTile);
        startTile.landPlayer(player2);

        // Player2 tries to enter the queue tile
        player2.setCurrentTile(tile);
        tile.landPlayer(player2);

        // assert that Player2 is sent back to the starting tile
        assertEquals(startTile.getId(), player2.getCurrentTile().getId());
        assertTrue(player2.getLastActionMessage().contains("is already there"));
    }

}
