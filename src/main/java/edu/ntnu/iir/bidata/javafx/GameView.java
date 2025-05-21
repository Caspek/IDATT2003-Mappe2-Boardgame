package edu.ntnu.iir.bidata.javafx;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.board.BoardGameFactory;
import edu.ntnu.iir.bidata.observer.BoardGameObserver;
import edu.ntnu.iir.bidata.game.TurnResult;
import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.tile.Tile;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

/**
 * Represents the main game view in the JavaFX application.
 * This class displays the game board, player pieces, and controls for gameplay.
 * It also observes the game state and updates the UI accordingly.
 */
public class GameView implements BoardGameObserver {
    private final BoardGame game;
    private final int boardChoice;
    private final VBox root = new VBox(10);
    private final GridPane boardGrid = new GridPane();
    private final Map<Integer, StackPane> tilePanes = new HashMap<>();
    private final Map<Player, Circle> pieceNodes = new HashMap<>();
    private final Label currentPlayerLabel = new Label();
    private final Button nextTurnButton = new Button("Next Turn");
    private final TextArea outputArea = new TextArea();
    private static final int TILE_SIZE = 50;
    private static final List<Color> pieceColors = List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PURPLE);

    /**
     * Constructs a new GameView instance.
     *
     * @param game        The {@link BoardGame} instance representing the current game state.
     * @param boardChoice The board configuration choice selected by the user.
     * @throws IllegalArgumentException if the game is null.
     * @throws IllegalStateException    if the game board or starting tile is not initialized.
     */
    public GameView(BoardGame game, int boardChoice) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null.");
        }
        if (game.getBoardInternal() == null || game.getStartingTile() == null) {
            throw new IllegalStateException("Game board or starting tile is not initialized.");
        }

        this.game = game;
        this.boardChoice = boardChoice;
        root.setPadding(new Insets(15));
        Label title = new Label("Board Game (Grid View)");

        // Build grid
        boardGrid.setHgap(2);
        boardGrid.setVgap(2);
        List<Tile> tiles = new ArrayList<>(game.getBoardInternal().getAllTiles());
        int totalTiles = tiles.size();
        int COLS = 10;
        int ROWS = (int) Math.ceil((double) totalTiles / COLS);

        tiles.sort(Comparator.comparingInt(Tile::getId));
        for (Tile t : tiles) {
            int id = t.getId();
            int idx = id - 1;
            int row = idx / COLS;
            int col = idx % COLS;
            StackPane cell = new StackPane();
            cell.setPrefSize(TILE_SIZE, TILE_SIZE);
            Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
            rect.setStroke(Color.BLACK);
            rect.setFill(Color.WHITESMOKE);
            Label lbl = new Label(String.valueOf(id));
            cell.getChildren().addAll(rect, lbl);
            tilePanes.put(id, cell);
            boardGrid.add(cell, col, row);
        }

        // Place pieces
        int colorIdx = 0;
        for (Player p : game.getPlayers()) {
            Circle c = new Circle(TILE_SIZE / 4.0);
            c.setFill(pieceColors.get(colorIdx++ % pieceColors.size()));
            pieceNodes.put(p, c);
            int startId = game.getStartingTile().getId();
            tilePanes.get(startId).getChildren().add(c);
        }

        // Log
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(ROWS + 2);

        // Controls
        updateCurrentPlayer(game);
        nextTurnButton.setOnAction(e -> {
            if (game == null || game.isGameOver() == false) {
                outputArea.appendText("Game is not in a valid state to proceed.\n");
                return;
            }
            try {
                TurnResult result = game.playTurn();
                updateCurrentPlayer(game);
            } catch (Exception ex) {
                outputArea.appendText("Error during turn: " + ex.getMessage() + "\n");
                ex.printStackTrace();
            }
        });

        game.addObserver(this);

        // Layout side-by-side
        VBox controlsBox = new VBox(10, currentPlayerLabel, nextTurnButton, outputArea);
        controlsBox.setPrefWidth(250);
        HBox mainBox = new HBox(10, boardGrid, controlsBox);
        root.getChildren().addAll(title, mainBox);
    }

    /**
     * Updates the label to display the current player's turn.
     *
     * @param game The {@link BoardGame} instance representing the current game state.
     */
    private void updateCurrentPlayer(BoardGame game) {
        currentPlayerLabel.setText("Turn: " + game.getCurrentPlayer().getName());
    }

    /**
     * Handles the event when a player moves to a new tile.
     *
     * @param player   The player who moved.
     * @param fromTile The tile the player moved from.
     * @param toTile   The tile the player moved to.
     * @param roll     The dice roll that determined the move.
     */
    @Override
    public void onPlayerMoved(Player player, Tile fromTile, Tile toTile, int roll) {
        try {
            Circle c = pieceNodes.get(player);
            if (c == null || fromTile == null || toTile == null) {
                throw new IllegalStateException("Invalid player or tile data.");
            }
            tilePanes.get(fromTile.getId()).getChildren().remove(c);
            tilePanes.get(toTile.getId()).getChildren().add(c);
            outputArea.appendText(
                    player.getName() + " rolled a " + roll + ",\n" +
                            "moved from " + fromTile.getId() + " to " + toTile.getId() + "\n\n"
            );
        } catch (Exception ex) {
            outputArea.appendText("Error updating player movement: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    /**
     * Handles the event when a player lands on a special tile.
     *
     * @param player  The player who landed on the special tile.
     * @param tile    The special tile.
     * @param message The message associated with the special tile.
     */
    @Override
    public void onSpecialTile(Player player, Tile tile, String message) {
        try {
            outputArea.appendText(message + "\n\n");
        } catch (Exception ex) {
            outputArea.appendText("Error displaying special tile message: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    /**
     * Handles the event when a player wins the game.
     * Gives the option to either play again or return to the main menu.
     *
     * @param winner The player who won the game.
     */
    @Override
    public void onGameWon(Player winner) {
        nextTurnButton.setDisable(true);
        outputArea.appendText(winner.getName() + " has won the game!\n");
        currentPlayerLabel.setText(winner.getName() + " wins!");

        Button replayButton = new Button("Play again");
        replayButton.setOnAction(e -> {
            new Thread(() -> {
                try {
                    BoardGame sameGame = BoardGameFactory.loadBoardFromFile(GameView.this.boardChoice);
                    for (Player oldPlayer : game.getPlayers()) {
                        sameGame.addPlayer(oldPlayer.getName());
                    }
                    sameGame.setDice(2, false);

                    javafx.application.Platform.runLater(() -> {
                        GameView newView = new GameView(sameGame, GameView.this.boardChoice);
                        Scene newScene = new Scene(newView.getRoot(), 800, 600);
                        Stage stage = new Stage();
                        stage.setScene(newScene);
                        stage.show();
                    });
                } catch (Exception ex) {
                    javafx.application.Platform.runLater(() -> outputArea.appendText("Error replaying game: " + ex.getMessage() + "\n"));
                    ex.printStackTrace();
                }
            }).start();
            ((Stage) root.getScene().getWindow()).close();
        });

        Button homeButton = new Button("Main menu");
        homeButton.setOnAction(e -> {
            javafx.application.Platform.runLater(() -> {
                try {
                    new BoardGameAppGUI().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            ((Stage) root.getScene().getWindow()).close();
        });

        VBox buttonBox = new VBox(10, replayButton, homeButton);
        ((VBox) nextTurnButton.getParent()).getChildren().add(buttonBox);
    }

    /**
     * Returns the root layout of the game view.
     *
     * @return The root {@link VBox} layout.
     */
    public VBox getRoot() {
        return root;
    }
}