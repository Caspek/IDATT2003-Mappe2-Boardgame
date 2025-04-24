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

      while (true) {
         for (Player player : game.getPlayers()) {
            System.out.println(player.getName() + "'s turn. Current position: " +
                    player.getCurrentTile().getId() + ". Press Enter to roll the dice.");
            scanner.nextLine();
            int rollSum = game.rollDice();
            System.out.println(player.getName() + " rolled a: " + rollSum);

            Tile startingTile = player.getCurrentTile();
            player.move(rollSum);

            System.out.println(player.getName() + " moved from " + startingTile.getId() +
                    " to " + player.getCurrentTile().getId());

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
