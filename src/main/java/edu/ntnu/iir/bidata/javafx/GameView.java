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
 * JavaFX view that also displays special-tile messages and moves extra steps immediately.
 */
public class GameView implements BoardGameObserver {
    private static final int TILE_SIZE = 50;

    private final VBox root = new VBox(10);
    private final GridPane boardGrid = new GridPane();
    private final TextArea outputArea = new TextArea();
    private final Button nextTurnButton = new Button("Next turn");
    private final Label currentPlayerLabel = new Label();
    private final Map<Integer, StackPane> tilePanes = new HashMap<>();
    private final Map<Player, Circle> pieceNodes = new HashMap<>();
    private final List<Color> pieceColors = Arrays.asList(
            Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PURPLE
    );
    private int boardChoice;
    private final BoardGame game;

    public GameView(BoardGame game, int boardChoice) {
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
            if (game.isGameOver()) {
                TurnResult result = game.playTurn();
                // roll and any messages are emitted via observer callbacks
                updateCurrentPlayer(game);
            }
        });

        game.addObserver(this);

        // Layout side-by-side
        VBox controlsBox = new VBox(10, currentPlayerLabel, nextTurnButton, outputArea);
        controlsBox.setPrefWidth(250);
        HBox mainBox = new HBox(10, boardGrid, controlsBox);
        root.getChildren().addAll(title, mainBox);
    }

    private void updateCurrentPlayer(BoardGame game) {
        currentPlayerLabel.setText("Turn: " + game.getCurrentPlayer().getName());
    }

    /** Fired for each movement, including extra steps. */
    @Override
    public void onPlayerMoved(Player player, Tile fromTile, Tile toTile, int roll) {
        Circle c = pieceNodes.get(player);
        tilePanes.get(fromTile.getId()).getChildren().remove(c);
        tilePanes.get(toTile.getId()).getChildren().add(c);
        outputArea.appendText(
                player.getName() + " rolled a " + roll + ",\n" +
                        "moved from " + fromTile.getId() + " to " + toTile.getId() + "\n\n"
        );
    }

    @Override
    public void onSpecialTile(Player player, Tile tile, String message) {
        outputArea.appendText(message + "\n\n");
    }



    @Override
    public void onGameWon(Player winner) {
        nextTurnButton.setDisable(true);
        outputArea.appendText(winner.getName() + " has won the game!\n");
        currentPlayerLabel.setText(winner.getName() + " wins!");

        Button replayButton = new Button("Play again");
        replayButton.setOnAction(e -> {
            javafx.application.Platform.runLater(() -> {
                try {
                    BoardGame sameGame = BoardGameFactory.loadBoardFromFile(GameView.this.boardChoice);
                    for (Player oldPlayer : game.getPlayers()) {
                        sameGame.addPlayer(oldPlayer.getName());
                    }
                    sameGame.setDice(2, false);

                    GameView newView = new GameView(sameGame, GameView.this.boardChoice);
                    Scene newScene = new Scene(newView.getRoot(), 800, 600);
                    Stage stage = new Stage();
                    stage.setScene(newScene);
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
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



    public VBox getRoot() { return root; }
}