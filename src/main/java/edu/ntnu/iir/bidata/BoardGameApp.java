package edu.ntnu.iir.bidata;

import java.util.Scanner;

public class BoardGameApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BoardGame game = new BoardGame();
        game.createBoard();
        game.createDice();

        System.out.println("Enter the name of player 1:");
        String player1Name = scanner.nextLine();
        Player player1 = new Player(player1Name, game);
        game.addPlayer(player1);

        System.out.println("Enter the name of player 2:");
        String player2Name = scanner.nextLine();
        Player player2 = new Player(player2Name, game);
        game.addPlayer(player2);

        System.out.println("Starting the game...");

        while (true) {
            for (Player player : game.getPlayers()) {
                System.out.println(player.getName() + "'s turn. Press Enter to roll the dice.");
                scanner.nextLine();
                int rollSum = game.getDice().roll();
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