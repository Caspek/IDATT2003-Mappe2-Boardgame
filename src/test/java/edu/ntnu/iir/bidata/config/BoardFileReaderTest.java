package edu.ntnu.iir.bidata.config;

import edu.ntnu.iir.bidata.board.Board;
import edu.ntnu.iir.bidata.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BoardFileReaderTest {

    @Test
    void testReadValidBoardFile() {
        Board board = new Board();
        Map<Integer, Tile> tiles = new BoardFileReader().readBoardFromFile("boards/Board.json", board);
        assertNotNull(tiles);
        assertFalse(tiles.isEmpty());
    }
}
