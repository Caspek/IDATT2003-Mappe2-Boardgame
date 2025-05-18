package edu.ntnu.iir.bidata.game;

import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.tile.Tile;
import java.util.Objects;

/**
 * Represents the result of a single turn in the game.
 */
public class TurnResult {
    private final Player player;
    private final int roll;
    private final Tile fromTile;
    private final Tile toTile;
    private final boolean hasWon;

    public TurnResult(Player player, int roll, Tile fromTile, Tile toTile, boolean hasWon) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
        if (fromTile == null) {
            throw new IllegalArgumentException("FromTile cannot be null.");
        }
        if (toTile == null) {
            throw new IllegalArgumentException("ToTile cannot be null.");
        }
        if (roll < 1) {
            throw new IllegalArgumentException("Roll must be greater than or equal to 1.");
        }

        this.player = player;
        this.roll = roll;
        this.fromTile = fromTile;
        this.toTile = toTile;
        this.hasWon = hasWon;
    }

    public Player getPlayer() {
        return player;
    }

    public int getRoll() {
        return roll;
    }

    public Tile getFromTile() {
        return fromTile;
    }

    public Tile getToTile() {
        return toTile;
    }

    public boolean hasWon() {
        return hasWon;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TurnResult that = (TurnResult) object;
        return roll == that.roll &&
                hasWon == that.hasWon &&
                Objects.equals(player, that.player) &&
                Objects.equals(fromTile, that.fromTile) &&
                Objects.equals(toTile, that.toTile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, roll, fromTile, toTile, hasWon);
    }

    @Override
    public String toString() {
        return "TurnResult{" +
                "player=" + player.getName() +
                ", roll=" + roll +
                ", fromTile=" + fromTile.getId() +
                ", toTile=" + toTile.getId() +
                ", hasWon=" + hasWon +
                '}';
    }
}

