package edu.ntnu.iir.bidata;

import java.util.List;
import java.util.Scanner;

public class BoardGameApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BoardGame game = new BoardGame();
        game.createBoard();
        game.createDice();

        String filePath = "src/main/resources/players.json";
        List<Player> players = PlayerLoader.loadPlayersFromFile(filePath, game);

        if (players == null || players.isEmpty()) {
            System.out.println("No players found in the file.");
            return;
        }
        for (Player player : players) {
            game.addPlayer(player);
            System.out.println("Player added: " + player.getName());
        }

        System.out.println("Game setup complete. Players are ready to play.");


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