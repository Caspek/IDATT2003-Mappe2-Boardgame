package edu.ntnu.iir.bidata.game;

import edu.ntnu.iir.bidata.board.BoardGameFactory;
import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.player.PlayerLoader;
import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.dice.DiceLogic;

import java.util.List;
import java.util.Scanner;

public class BoardGameApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the player to choose a board
        System.out.println("Choose a board to load:");
        System.out.println("1. Simple Board");
        System.out.println("2. Painful Board");
        System.out.println("3. Standard Board");
        System.out.println("4. Teleportation Board");
        System.out.print("Enter your choice (1-4): ");

        int choice = scanner.nextInt();

        // Create the board using BoardGameFactory
        BoardGame game;
        try {
            game = BoardGameFactory.loadBoardFromFile(choice);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Set the dice for the game
        game.setDice(new DiceLogic(2));

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