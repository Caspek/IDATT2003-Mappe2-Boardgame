package edu.ntnu.iir.bidata.game;

import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.player.PlayerLoader;
import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.dice.Dice;
import edu.ntnu.iir.bidata.dice.DiceLogic;

import java.util.List;

public class BoardGameApp {
    public static void main(String[] args) {
        BoardGame game = new BoardGame();
        game.loadBoard("src/main/resources/board.json");
        Dice dice = new DiceLogic(1);
        game.setDice(dice);

        String filePath = "src/main/resources/players.json";
        List<Player> players = PlayerLoader.loadPlayersFromFile(filePath, game);

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