package edu.ntnu.iir.bidata.config;

import edu.ntnu.iir.bidata.board.Board;
import edu.ntnu.iir.bidata.tile.Tile;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardFileReaderTest {

    @Test
    void testReadValidBoardFile() {
        String path = Paths.get("src/main/resources/RandomBoard.json").toAbsolutePath().toString();
        System.out.println("Debug: File path is " + path);

        Board board = new Board();
        Map<Integer, Tile> tiles = new BoardFileReader().readBoardFromFile(path, board);

        assertNotNull(tiles);
        assertEquals(90, tiles.size());
        assertTrue(tiles.containsKey(3));
        assertNotNull(tiles.get(3).getNextTile());
    }
}
