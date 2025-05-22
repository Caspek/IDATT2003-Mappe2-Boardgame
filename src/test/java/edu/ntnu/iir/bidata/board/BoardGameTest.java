package edu.ntnu.iir.bidata.board;


import edu.ntnu.iir.bidata.config.BoardGameFactory;
import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.player.TurnResult;
import edu.ntnu.iir.bidata.tile.Tile;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameTest {

    private BoardGame game;

    @BeforeEach
    void setUp() {
        game = BoardGameFactory.createSimpleBoard();
        game.addPlayer("Alice");
        game.addPlayer("Bob");
    }

    @Test
    void testAddPlayerIncreasesPlayerList() {
        assertEquals(2, game.getPlayers().size());
        game.addPlayer("Charlie");
        assertEquals(3, game.getPlayers().size());
    }

    @Test
    void testPlayTurnReturnsValidTurnResult() {
        TurnResult result = game.playTurn();
        assertNotNull(result);
        assertNotNull(result.getPlayer());
        assertTrue(result.getRoll() >= 1 && result.getRoll() <= 12);
        assertNotNull(result.toString());
    }

    @Test
    void testTurnCyclesBetweenPlayers() {
        Player first = game.getCurrentPlayer();
        game.playTurn();
        Player second = game.getCurrentPlayer();
        assertNotEquals(first, second);
    }

    @Test
    void testGameEndsWhenPlayerWins() {
        // Fast-forward to winning tile (simulate winning)
        Player player = game.getCurrentPlayer();
        Tile lastTile = game.getBoardInternal().getTile(90);
        player.setCurrentTile(lastTile);

        TurnResult result = game.playTurn();
        assertTrue(result.toString().contains("hasWon=true"));
    }

    @Test
    void testSetDiceRejectsInvalidQueueSetup() {
        assertThrows(IllegalArgumentException.class, () -> game.setDice(2, true));
    }

    @Test
    void testAddPlayerRejectsNullOrEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> game.addPlayer(""));
        assertThrows(IllegalArgumentException.class, () -> game.addPlayer(null));
    }
}
