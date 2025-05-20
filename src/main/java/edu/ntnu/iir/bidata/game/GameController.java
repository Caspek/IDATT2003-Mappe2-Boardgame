package edu.ntnu.iir.bidata.game;

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

      while (game.isGameOver()) {
         System.out.println("Press Enter to play the next turn.");
         scanner.nextLine();

         TurnResult turnResult = game.playTurn();

         System.out.println(turnResult.getPlayer().getName() + " rolled a: " + turnResult.getRoll());
         System.out.println(turnResult.getPlayer().getName() + " moved from " +
                 turnResult.getFromTile().getId() + " to " + turnResult.getToTile().getId());

         if (turnResult.hasWon()) {
            System.out.println(turnResult.getPlayer().getName() + " has reached the end of the game!");
            System.out.println(turnResult.getPlayer().getName() + " wins!");
         }
      }

      scanner.close();
   }
}

