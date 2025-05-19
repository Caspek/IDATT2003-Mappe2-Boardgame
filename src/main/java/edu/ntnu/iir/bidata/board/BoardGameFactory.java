package edu.ntnu.iir.bidata.board;

import edu.ntnu.iir.bidata.dice.DiceLogic;
import edu.ntnu.iir.bidata.tile.MoveExtraStepsAction;
import edu.ntnu.iir.bidata.tile.Tile;

import java.util.HashMap;
import java.util.Map;

public class BoardGameFactory {

    private static final String BOARD_DIRECTORY = "src/main/resources/";

    public static BoardGame createSimpleBoard() {
        BoardGame game = new BoardGame();
        Map<Integer, Tile> tiles = new HashMap<>();

        for (int i = 1; i <= 90; i++) {
            tiles.put(i, new Tile(i));
        }
        for (int i = 1; i < 90; i++) {
            tiles.get(i).setNextTile(tiles.get(i + 1));
        }

        tiles.get(37).setLandAction(new MoveExtraStepsAction(9));
        tiles.get(15).setLandAction(new MoveExtraStepsAction(5));
        tiles.get(63).setLandAction(new MoveExtraStepsAction(-7));
        tiles.get(45).setLandAction(new MoveExtraStepsAction(10));

        game.setTiles(tiles);
        game.setDice(2);
        return game;
    }

    public static BoardGame createPainfulBoard() {
        BoardGame game = new BoardGame();
        Map<Integer, Tile> tiles = new HashMap<>();

        for (int i = 1; i <= 90; i++) {
            tiles.put(i, new Tile(i));
        }
        for (int i = 1; i < 90; i++) {
            tiles.get(i).setNextTile(tiles.get(i + 1));
        }

        tiles.get(9).setLandAction(new MoveExtraStepsAction(-5));
        tiles.get(16).setLandAction(new MoveExtraStepsAction(-10));
        tiles.get(20).setLandAction(new MoveExtraStepsAction(-7));
        tiles.get(32).setLandAction(new MoveExtraStepsAction(-8));
        tiles.get(44).setLandAction(new MoveExtraStepsAction(-6));
        tiles.get(57).setLandAction(new MoveExtraStepsAction(-9));
        tiles.get(65).setLandAction(new MoveExtraStepsAction(-10));
        tiles.get(73).setLandAction(new MoveExtraStepsAction(-12));
        tiles.get(80).setLandAction(new MoveExtraStepsAction(-15));

        game.setTiles(tiles);
        game.setDice(1);
        return game;
    }

    public static BoardGame loadBoardFromFile(int choice) {
        switch (choice) {
            case 1:
                return createSimpleBoard();
            case 2:
                return createPainfulBoard();
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
        game.loadBoard(filePath);
        return game;
    }
}
