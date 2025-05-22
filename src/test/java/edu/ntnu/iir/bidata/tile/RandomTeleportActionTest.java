package edu.ntnu.iir.bidata.tile;

import edu.ntnu.iir.bidata.board.Board;
import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.config.BoardGameFactory;
import edu.ntnu.iir.bidata.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RandomTeleportActionTest {

    private BoardGame game;
    private Board board;
    private Tile actionTile;
    private Player player;

    @BeforeEach
    void setup() {
        game = BoardGameFactory.createSimpleBoard();
        board = game.getBoardInternal();

        actionTile = board.getTile(3); // tile with RandomTeleportAction
        actionTile.setLandAction(new RandomTeleportAction(board));

        player = new Player("Teleporter", game);
        player.setCurrentTile(actionTile);
    }

    @Test
    void testPlayerIsTeleportedToDifferentTile() {
        int beforeId = player.getCurrentTile().getId();

        actionTile.landPlayer(player); // trigger teleport

        int afterId = player.getCurrentTile().getId();

        assertNotEquals(beforeId, afterId, "Player should be teleported.");
        assertTrue(afterId >= 1 && afterId <= board.getAllTiles().size());
        assertNotNull(player.getLastActionMessage());
        assertTrue(player.getLastActionMessage().contains("teleported to tile"));
    }

    @Test
    void testTeleportFailsWhenBoardHasNoTiles() {
        Board emptyBoard = new Board() {
            @Override
            public Collection<Tile> getAllTiles() {
                return Collections.emptyList();
            }

            @Override
            public Tile getTile(int id) {
                return null;
            }
        };

        RandomTeleportAction action = new RandomTeleportAction(emptyBoard);
        Player testPlayer = new Player("Test", game);
        testPlayer.setCurrentTile(actionTile);

        // ActionTile should not be null
        assertDoesNotThrow(() -> action.execute(testPlayer));

        // Check that no teleport is performed
        assertEquals(actionTile, testPlayer.getCurrentTile());
        assertNull(testPlayer.getLastActionMessage(), "Teleportation should not occur when there are no tiles.");
    }
}

