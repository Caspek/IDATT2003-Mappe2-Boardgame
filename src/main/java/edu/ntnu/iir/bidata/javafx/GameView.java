// GameView.java
package edu.ntnu.iir.bidata.javafx;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.game.TurnResult;
import edu.ntnu.iir.bidata.player.Player;
import edu.ntnu.iir.bidata.tile.TileAction;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GameView {
    private final VBox root = new VBox(10);
    private final TextArea outputArea = new TextArea();
    private final Button nextTurnButton = new Button("Next turn");
    private final Label currentPlayerLabel = new Label();

    public GameView(BoardGame game) {
        root.setPadding(new Insets(15));

        outputArea.setEditable(false);
        outputArea.setPrefRowCount(15);

        updateCurrentPlayer(game);

        nextTurnButton.setOnAction(e -> {
            if (!game.isGameOver()) {
                TurnResult result = game.playTurn();
                Player player = result.getPlayer();

                // 1) roll & basic move
                outputArea.appendText(player.getName() + " rolled: " + result.getRoll() + "\n");
                outputArea.appendText("Moved from tile "
                        + result.getFromTile().getId()
                        + " to tile "
                        + result.getToTile().getId()
                        + "\n");

                // 2) special action message, if any
                String actionMsg = player.getLastActionMessage();
                if (actionMsg != null) {
                    outputArea.appendText(actionMsg + "\n");
                }

                outputArea.appendText("\n");

                // 3) win check
                if (result.hasWon()) {
                    outputArea.appendText(player.getName() + " has won the game!\n");
                    nextTurnButton.setDisable(true);
                } else {
                    updateCurrentPlayer(game);
                }
            }
        });

        root.getChildren().addAll(
                new Label("Board Game in JavaFX"),
                currentPlayerLabel,
                nextTurnButton,
                outputArea
        );
    }

    private void updateCurrentPlayer(BoardGame game) {
        currentPlayerLabel.setText("Turn: " + game.getCurrentPlayer().getName());
    }

    public VBox getRoot() {
        return root;
    }
}

