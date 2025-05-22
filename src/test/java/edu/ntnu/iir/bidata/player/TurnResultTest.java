package edu.ntnu.iir.bidata.player;

import edu.ntnu.iir.bidata.config.BoardGameFactory;
import edu.ntnu.iir.bidata.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnResultTest {

    @Test
    void testConstructorAndGetters() {
        var game = BoardGameFactory.createSimpleBoard();
        Player player = new Player("Tester", game);
        Tile from = game.getStartingTile();
        Tile to = from.getNextTile();

        TurnResult result = new TurnResult(player, 5, from, to, false);

        assertEquals(player, result.getPlayer());
        assertEquals(5, result.getRoll());
        assertEquals(from, result.getFromTile());
        assertEquals(to, result.getToTile());
        assertFalse(result.hasWon());
    }

    @Test
    void testInvalidRollThrowsException() {
        var game = BoardGameFactory.createSimpleBoard();
        Player player = new Player("Tester", game);
        Tile from = game.getStartingTile();
        Tile to = from.getNextTile();

        assertThrows(IllegalArgumentException.class, () -> new TurnResult(player, 0, from, to, false));
        assertThrows(IllegalArgumentException.class, () -> new TurnResult(player, 13, from, to, false));
    }

    @Test
    void testNullArgumentsThrowException() {
        var game = BoardGameFactory.createSimpleBoard();
        Player player = new Player("Tester", game);
        Tile tile = game.getStartingTile();

        assertThrows(IllegalArgumentException.class, () -> new TurnResult(null, 5, tile, tile, false));
        assertThrows(IllegalArgumentException.class, () -> new TurnResult(player, 5, null, tile, false));
        assertThrows(IllegalArgumentException.class, () -> new TurnResult(player, 5, tile, null, false));
    }

    @Test
    void testToStringFormat() {
        var game = BoardGameFactory.createSimpleBoard();
        Player player = new Player("Tester", game);
        Tile from = game.getStartingTile();
        Tile to = from.getNextTile();
        TurnResult result = new TurnResult(player, 4, from, to, true);

        String str = result.toString();
        assertTrue(str.contains("Tester"));
        assertTrue(str.contains("roll=4"));
        assertTrue(str.contains("hasWon=true"));
    }
}


