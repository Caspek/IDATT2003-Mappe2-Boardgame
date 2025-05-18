package edu.ntnu.iir.bidata.game;

import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.player.PlayerLoader;
import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.dice.Dice;
import edu.ntnu.iir.bidata.dice.DiceLogic;

import java.util.List;
import java.util.Scanner;

public class BoardGameApp {
    public static void main(String[] args) {
        // Create a new game instance
        BoardGame game = new BoardGame();
        Scanner scanner = new Scanner(System.in);

        // Prompt the player to choose a board
        System.out.println("Choose a board to load:");
        System.out.println("1. Board");
        System.out.println("2. RandomBoard");
        System.out.print("Enter your choice (1 or 2): ");

        int choice = scanner.nextInt();
        String filePath;

        // Use a switch statement to determine which board to load
        switch (choice) {
            case 1:
                filePath = "src/main/resources/Board.json";
                break;
            case 2:
                filePath = "src/main/resources/RandomBoard.json";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to RandomBoard.");
                filePath = "src/main/resources/RandomBoard.json";
        }

        // Load the selected board
        game.loadBoard(filePath);
        Dice dice = new DiceLogic(1);
        game.setDice(dice);

        // Load players from the JSON file
        String playersFilePath = "src/main/resources/players.json";
        List<Player> players = PlayerLoader.loadPlayersFromFile(playersFilePath, game);

        if (players.isEmpty()) {
            System.out.println("No players found in the file.");
            return;
        }

        for (Player player : players) {
            game.addPlayer(player);
            System.out.println("Player added: " + player.getName() + " (" + player.getPlayingPiece() + ")");
        }

        GameController controller = new GameController(game);
        controller.startGame();
    }
}