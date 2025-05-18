package edu.ntnu.iir.bidata.board;

import edu.ntnu.iir.bidata.dice.DiceLogic;
import edu.ntnu.iir.bidata.tile.MoveExtraStepsAction;
import edu.ntnu.iir.bidata.tile.Tile;

import java.util.HashMap;
import java.util.Map;

public class BoardGameFactory {

    private static final String BOARD_DIRECTORY = "src/main/resources/";

    /**
     * A simple board with a few traps and extra steps.
     */
    public static BoardGame createSimpleBoard() {
        BoardGame game = new BoardGame();
        Map<Integer, Tile> tiles = new HashMap<>();

        // Create 90 Tiles
        for (int i = 1; i <= 90; i++) {
            tiles.put(i, new Tile(i));
        }
        // Connect the Tiles
        for (int i = 1; i < 90; i++) {
            tiles.get(i).setNextTile(tiles.get(i + 1));
        }

        tiles.get(37).setLandAction(new MoveExtraStepsAction(9));  // 37 -> 46
        tiles.get(15).setLandAction(new MoveExtraStepsAction(5)); // 10 -> 20
        tiles.get(63).setLandAction(new MoveExtraStepsAction(-7)); // 63 -> 56
        tiles.get(45).setLandAction(new MoveExtraStepsAction(10)); // 45 -> 55

        game.getBoard().setTiles(tiles);
        game.setDice(new DiceLogic(2)); // 2 Dice
        return game;
    }

    /**
     * A simple board with only traps.
     */
    public static BoardGame createPainfulBoard() {
        BoardGame game = new BoardGame();
        Map<Integer, Tile> tiles = new HashMap<>();

        for (int i = 1; i <= 90; i++) {
            tiles.put(i, new Tile(i));
        }

        for (int i = 1; i < 90; i++) {
            tiles.get(i).setNextTile(tiles.get(i + 1));
        }

        // Negative land actions
        tiles.get(9).setLandAction(new MoveExtraStepsAction(-5)); // 9 -> 4
        tiles.get(16).setLandAction(new MoveExtraStepsAction(-10)); // 16 -> 6
        tiles.get(20).setLandAction(new MoveExtraStepsAction(-7)); // 20 -> 13
        tiles.get(32).setLandAction(new MoveExtraStepsAction(-8)); // 32 -> 24
        tiles.get(44).setLandAction(new MoveExtraStepsAction(-6)); // 44 -> 38
        tiles.get(57).setLandAction(new MoveExtraStepsAction(-9)); // 57 -> 48
        tiles.get(65).setLandAction(new MoveExtraStepsAction(-10)); // 65 -> 55
        tiles.get(73).setLandAction(new MoveExtraStepsAction(-12)); // 73 -> 61
        tiles.get(80).setLandAction(new MoveExtraStepsAction(-15)); // 80 -> 65


        game.getBoard().setTiles(tiles);
        game.setDice(new DiceLogic(1));
        return game;
    }

    /**
     * Creates a board based on the user's choice.
     *
     * @param choice The user's choice (1 for standard board, 2 for random board).
     * @return A configured BoardGame instance.
     */
    public static BoardGame loadBoardFromFile(int choice) {
        switch (choice) {
            case 1:
                return createSimpleBoard(); // Simple board
            case 2:
                return createPainfulBoard(); // Painful board
            case 3: {
                String filePath = BOARD_DIRECTORY + "Board.json";
                return loadBoardFromFile(filePath);
            }
            case 4: {
                String filePath = BOARD_DIRECTORY + "RandomBoard.json";
                return loadBoardFromFile(filePath);
            }
            default:
                throw new IllegalArgumentException("Invalid board choice: " + choice);
        }
    }

    private static BoardGame loadBoardFromFile(String filePath) {
        BoardGame game = new BoardGame();
        BoardFileReader reader = new BoardFileReader(game.getBoard());
        game.getBoard().setTiles(reader.readBoardFromFile(filePath));
        return game;
    }
}