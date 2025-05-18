package edu.ntnu.iir.bidata.game;

import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.tile.Tile;
import edu.ntnu.iir.bidata.board.BoardGame;

import java.util.Scanner;

public class GameController {
   private final BoardGame game;
   private final Scanner scanner;

   public GameController(BoardGame game) {
      this.game = game;
      this.scanner = new Scanner(System.in);
   }

   public void startGame() {
      System.out.println("Game setup complete. Players are ready to play.");

      while (!game.isGameOver()) {
         System.out.println("Press Enter to play the next turn.");
         scanner.nextLine();

         TurnResult turnResult = playTurn();

         Player player = turnResult.getPlayer();
         System.out.println(player.getName() + " rolled a: " + turnResult.getRoll());
         System.out.println(player.getName() + " moved from " + turnResult.getFromTile().getId() +
                 " to " + turnResult.getToTile().getId());

         if (turnResult.hasWon()) {
            System.out.println(player.getName() + " has reached the end of the game!");
            System.out.println(player.getName() + " wins!");
         }
      }

      scanner.close();
   }

   private TurnResult playTurn() {
      Player player = game.getCurrentPlayer();
      Tile fromTile = player.getCurrentTile();
      int roll = game.rollDice();
      Tile toTile = player.move(roll);
      boolean hasWon = toTile.getNextTile() == null;

      if (hasWon) {
         game.setGameOver(true);
      }

      game.nextPlayer();

      return new TurnResult(player, roll, fromTile, toTile, hasWon);
   }
}
