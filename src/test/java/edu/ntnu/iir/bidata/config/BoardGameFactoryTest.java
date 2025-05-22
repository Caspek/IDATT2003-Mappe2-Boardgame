package edu.ntnu.iir.bidata.config;

import edu.ntnu.iir.bidata.board.BoardGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardGameFactoryTest {

    @Test
    void testCreateSimpleBoard() {
        BoardGame game = BoardGameFactory.createSimpleBoard();
        assertNotNull(game.getStartingTile());
        assertNotNull(game.getDice());
    }

    @Test
    void testCreatePainfulBoard() {
        BoardGame game = BoardGameFactory.createPainfulBoard();
        assertNotNull(game.getStartingTile());
        assertNotNull(game.getDice());
    }

    @Test
    void testCreateQueueBoard() {
        BoardGame game = BoardGameFactory.createQueueBoard();
        assertNotNull(game.getStartingTile());
        assertNotNull(game.getDice());
    }

    @Test
    void testInvalidBoardChoiceReturnsFallback() {
        BoardGame game = BoardGameFactory.loadBoardFromFile(9);
        assertNotNull(game.getStartingTile());
    }
}
