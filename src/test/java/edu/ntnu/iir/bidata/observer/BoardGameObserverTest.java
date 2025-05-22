package edu.ntnu.iir.bidata.observer;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.config.BoardGameFactory;
import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameObserverTest {

    static class MockObserver implements BoardGameObserver {
        boolean moved = false;
        boolean special = false;
        boolean won = false;

        @Override
        public void onPlayerMoved(Player player, Tile fromTile, Tile toTile, int roll) {
            moved = true;
        }

        @Override
        public void onSpecialTile(Player player, Tile tile, String description) {
            special = true;
        }

        @Override
        public void onGameWon(Player winner) {
            won = true;
        }
    }

    @Test
    void testObserverReceivesEvents() {
        BoardGame game = BoardGameFactory.createSimpleBoard();
        MockObserver observer = new MockObserver();
        game.addPlayer("Test");
        game.addObserver(observer);

        game.playTurn();

        assertTrue(observer.moved || observer.special || observer.won);
    }
}
