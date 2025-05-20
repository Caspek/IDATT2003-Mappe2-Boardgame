package edu.ntnu.iir.bidata.javafx;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.observer.BoardGameObserver;
import edu.ntnu.iir.bidata.game.TurnResult;
import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.tile.Tile;
import javafx.geometry.Insets;
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
    private static final int COLS = 0;
    private static final int ROWS = 0;

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

    public GameView(BoardGame game) {
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
            if (!game.isGameOver()) {
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

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(e -> {
            javafx.application.Platform.runLater(() -> {
                try {
                    new BoardGameAppGUI().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            ((Stage) root.getScene().getWindow()).close();
        });
        ((VBox) nextTurnButton.getParent()).getChildren().add(playAgainButton);
    }


    public VBox getRoot() { return root; }
}