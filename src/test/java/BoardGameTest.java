import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardGameTest {

    @Test
    public void testAddPlayer() {
        BoardGame game = new BoardGame();
        Player player1 = new Player("Alice", game);
        Player player2 = new Player("Bob", game);
        game.addPlayer(player1);
        game.addPlayer(player2);
        for (Player player : game.getPlayers()) {
            System.out.println(player.getName());
        }
        assertEquals(2, game.getPlayers().size());
    }

}
