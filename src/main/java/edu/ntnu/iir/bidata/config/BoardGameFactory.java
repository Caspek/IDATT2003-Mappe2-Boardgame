package edu.ntnu.iir.bidata.config;

import edu.ntnu.iir.bidata.board.Board;
import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.tile.MoveExtraStepsAction;
import edu.ntnu.iir.bidata.tile.QueueTileAction;
import edu.ntnu.iir.bidata.tile.Tile;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating and loading different types of board game configurations.
 * Provides methods to create predefined boards,
 *    load boards from files, and validate board configurations.
 */
public class BoardGameFactory {

  private static final String BOARD_DIRECTORY = "src/main/resources/";

  /**
   * Creates a simple board configuration with predefined tile actions.
   *
   * @return A {@link BoardGame} instance with a simple board setup.
   */
  public static BoardGame createSimpleBoard() {
    final BoardGame game = new BoardGame();
    Map<Integer, Tile> tiles = createAndLinkTiles(90);

    tiles.get(37).setLandAction(new MoveExtraStepsAction(9));
    tiles.get(15).setLandAction(new MoveExtraStepsAction(5));
    tiles.get(63).setLandAction(new MoveExtraStepsAction(-7));
    tiles.get(45).setLandAction(new MoveExtraStepsAction(10));

    game.setTiles(tiles);
    game.setDice(2, false);
    return game;
  }

  /**
   * Creates a "painful" board configuration with negative tile actions.
   *
   * @return A {@link BoardGame} instance with a painful board setup.
   */
  public static BoardGame createPainfulBoard() {
    final BoardGame game = new BoardGame();
    Map<Integer, Tile> tiles = createAndLinkTiles(90);

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
    game.setDice(2, false);
    return game;
  }

  /**
   * Returns a {@link BoardGame} instance with a queue-based board setup.
   *
   * @return a {@link BoardGame} instance with a queue-based board setup.
   */
  public static BoardGame createQueueBoard() {
    final BoardGame game = new BoardGame();
    Map<Integer, Tile> tiles = createAndLinkTiles(30);

    tiles.get(10).setLandAction(new QueueTileAction());
    tiles.get(20).setLandAction(new QueueTileAction());

    tiles.get(5).setLandAction(new MoveExtraStepsAction(5));
    tiles.get(25).setLandAction(new MoveExtraStepsAction(-10));

    game.setTiles(tiles);
    game.setDice(1, true);
    return game;
  }

  /**
   * Loads a board configuration based on the given choice.
   *
   * @param choice The board choice (1-6).
   * @return A {@link BoardGame} instance corresponding to the selected board configuration.
   * @throws IllegalArgumentException If the choice is invalid.
   */
  public static BoardGame loadBoardFromFile(int choice) {
    try {
      return switch (choice) {
        case 1 -> createSimpleBoard();
        case 2 -> createPainfulBoard();
        case 3 -> loadBoardFromFile(BOARD_DIRECTORY + "Board.json");
        case 4 -> loadBoardFromFile(BOARD_DIRECTORY + "RandomBoard.json");
        case 5 -> loadBoardFromFile(BOARD_DIRECTORY + "ShortBoard.json");
        case 6 -> createQueueBoard();
        default ->
          throw new IllegalArgumentException("Invalid board choice: "
              + choice + ". Please select a valid option (1-6).");
      };
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      return createSimpleBoard(); // Fallback to a default board
    }
  }

  /**
   * Loads a board configuration from a JSON file.
   *
   * @param filePath The path to the JSON file.
   * @return A {@link BoardGame} instance with the loaded board configuration.
   * @throws IllegalStateException If an error occurs while loading the board.
   */
  private static BoardGame loadBoardFromFile(String filePath) {
    BoardGame game = new BoardGame();
    try {
      game.loadBoard(filePath);
      validateBoard(game.getBoardInternal());
    } catch (Exception e) {
      throw new IllegalStateException("Error loading the board configuration: " + filePath, e);
    }
    return game;
  }

  /**
   * Creates and links a specified number of tiles sequentially.
   *
   * @param numberOfTiles The number of tiles to create.
   * @return A map of tile IDs to {@link Tile} objects with sequential links.
   */
  private static Map<Integer, Tile> createAndLinkTiles(int numberOfTiles) {
    Map<Integer, Tile> tiles = new HashMap<>();
    for (int i = 1; i <= numberOfTiles; i++) {
      tiles.put(i, new Tile(i));
    }
    for (int i = 1; i < numberOfTiles; i++) {
      tiles.get(i).setNextTile(tiles.get(i + 1));
    }
    return tiles;
  }

  /**
   * Validates the board configuration to ensure all tiles are properly linked.
   *
   * @param board The {@link Board} instance to validate.
   * @throws IllegalStateException If a tile is missing a next tile and is not a winning tile.
   */
  private static void validateBoard(Board board) {
    for (Tile tile : board.getAllTiles()) {
      if (tile.getNextTile() == null && !tile.isWinningTile()) {
        throw new IllegalStateException("Invalid board configuration: Tile "
            + tile.getId() + " is missing a next tile.");
      }
    }
  }
}
