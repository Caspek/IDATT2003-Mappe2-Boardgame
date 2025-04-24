package edu.ntnu.iir.bidata;

import java.util.List;
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

      while (true) {
         for (Player player : game.getPlayers()) {
            System.out.println(player.getName() + "'s turn. Press Enter to roll the dice.");
            scanner.nextLine();
            int rollSum = game.rollDice();
            System.out.println(player.getName() + " rolled a: " + rollSum);
            player.move(rollSum);
            System.out.println(player.getName() + " is now on tile: " + player.getCurrentTile().getId());

            if (player.getCurrentTile().getNextTile() == null) {
                System.out.println(player.getName() + " has reached the end of the game!");
                System.out.println(player.getName() + " wins!");
                scanner.close();
                return;
         }
         }
      }
   }
}
