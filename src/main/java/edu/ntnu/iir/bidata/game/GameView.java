package edu.ntnu.iir.bidata.game;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.game.TurnResult;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GameView {
    private final VBox root = new VBox(10);
    private final TextArea outputArea = new TextArea();
    private final Button nextTurnButton = new Button("Neste tur");
    private final Label currentPlayerLabel = new Label();

    public GameView(BoardGame game) {
        root.setPadding(new Insets(15));

        outputArea.setEditable(false);
        outputArea.setPrefRowCount(15);

        updateCurrentPlayer(game);

        nextTurnButton.setOnAction(e -> {
            if (!game.isGameOver()) {
                TurnResult result = game.playTurn();

                outputArea.appendText(result.getPlayer().getName() + " kastet: " + result.getRoll() + "\n");
                outputArea.appendText("Flyttet fra " + result.getFromTile().getId() + " til " + result.getToTile().getId() + "\n\n");

                if (result.hasWon()) {
                    outputArea.appendText(result.getPlayer().getName() + " har vunnet spillet!\n");
                    nextTurnButton.setDisable(true);
                } else {
                    updateCurrentPlayer(game);
                }
            }
        });

        root.getChildren().addAll(
                new Label("Board Game i JavaFX"),
                currentPlayerLabel,
                nextTurnButton,
                outputArea
        );
    }

    private void updateCurrentPlayer(BoardGame game) {
        currentPlayerLabel.setText("Tur: " + game.playTurn().getPlayer().getName());
    }

    public VBox getRoot() {
        return root;
    }
}

