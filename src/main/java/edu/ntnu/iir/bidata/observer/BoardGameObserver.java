package edu.ntnu.iir.bidata.observer;

import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.tile.Tile;

public interface BoardGameObserver {
    void onPlayerMoved(Player player, Tile fromTile, Tile toTile, int roll);
    void onGameWon(Player winner);
}
