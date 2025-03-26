import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import edu.ntnu.iir.bidata.Player;
import edu.ntnu.iir.bidata.BoardGame;
import edu.ntnu.iir.bidata.Tile;


public class PlayerTest {
    private BoardGame game;
    private Player player;

    @BeforeEach
    public void setUp() {
        game = new BoardGame();
        game.createBoard();
        player = new Player("TestPlayer", game);
    }

    @Test
    public void testMoveSeveralTiles() {
        player.move(5);
        assertEquals(6, player.getCurrentTile().getId());
    }

    @Test
    public void testMoveZeroTiles() {
        Tile initialTile = player.getCurrentTile();
        player.move(0);
        assertEquals(initialTile, player.getCurrentTile());
    }

    @Test
    public void testMoveNegativeTiles() {
        player.move(6);
        System.out.println(player.getCurrentTile().getId()); // should be 7
        player.move(-2);
        assertEquals(5, player.getCurrentTile().getId()); // should be 5
        System.out.println(player.getCurrentTile().getId());
    }
}
