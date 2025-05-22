package edu.ntnu.iir.bidata.tile;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    public void testTileLinking() {
        Tile tile1 = new Tile(1);
        Tile tile2 = new Tile(2);
        tile1.setNextTile(tile2);
        assertEquals(tile2, tile1.getNextTile());
    }

}
