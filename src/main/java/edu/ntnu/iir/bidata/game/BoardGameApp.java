package edu.ntnu.iir.bidata.game;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.board.BoardGameFactory;

import java.util.Scanner;

public class BoardGameApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a board to load:");
        System.out.println("1. Simple Board");
        System.out.println("2. Painful Board");
        System.out.println("3. Standard Board");
        System.out.println("4. Teleportation Board");
        System.out.print("Enter your choice (1-4): ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        BoardGame game;
        try {
            game = BoardGameFactory.loadBoardFromFile(choice);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid choice: " + e.getMessage());
            return;
        }

        game.setDice(2, false);

        String playersFilePath = "src/main/resources/players.json";
        game.loadPlayers(playersFilePath);

        System.out.println("Players loaded. Starting game...");

        GameController controller = new GameController(game);
        controller.startGame();
    }
}
