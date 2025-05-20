
package edu.ntnu.iir.bidata.javafx;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.board.BoardGameFactory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardGameAppGUI extends Application {
    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        // Board selection
        Label boardLabel = new Label("Choose board:");
        ComboBox<String> boardSelector = new ComboBox<>();
        boardSelector.getItems().addAll("1. Simple"
                , "2. Painful"
                , "3. Standard"
                , "4. Teleportation"
                , "5. Short"
                , "6. Queue");
        boardSelector.getSelectionModel().selectFirst();

        // Player count
        Label playerCountLabel = new Label("Number of players (2-5):");
        Spinner<Integer> playerCountSpinner = new Spinner<>(2, 5, 2);

        // Piece selectors
        VBox pieceSelectorsBox = new VBox(10);
        updatePieceSelectors(pieceSelectorsBox, 2);
        playerCountSpinner.valueProperty().addListener((obs, oldVal, newVal) -> updatePieceSelectors(pieceSelectorsBox, newVal));

        // Start button
        Button startButton = new Button("Start Game");

        root.getChildren().addAll(
                boardLabel, boardSelector,
                playerCountLabel, playerCountSpinner,
                new Label("Choose pieces:"), pieceSelectorsBox,
                startButton
        );

        Scene scene = new Scene(root,200,400);
        stage.setScene(scene);
        stage.setTitle("Board Game Setup");
        stage.sizeToScene();
        stage.show();

        startButton.setOnAction(e -> {
            int boardChoice = boardSelector.getSelectionModel().getSelectedIndex() + 1;
            BoardGame game = BoardGameFactory.loadBoardFromFile(boardChoice);

            // Automatically set 2 dice for JSON boards without DiceLogic
            if (game.getDice() == null) {
                game.setDice(2, false);
            }

            List<String> selected = new ArrayList<>();
            for (Node node : pieceSelectorsBox.getChildren()) {
                if (node instanceof ComboBox) {
                    String piece = ((ComboBox<String>) node).getValue();
                    if (piece == null || selected.contains(piece)) {
                        showAlert("Each player must select a unique piece.");
                        return;
                    }
                    selected.add(piece);
                }
            }

            for (String name : selected) game.addPlayer(name);

            GameView view = new GameView(game, boardChoice);
            Scene gameScene = new Scene(view.getRoot(), 800, 600);
            stage.setScene(gameScene);
            stage.sizeToScene();
            stage.centerOnScreen();
        });
    }

    private void updatePieceSelectors(VBox container, int count) {
        container.getChildren().clear();
        List<String> pieces = Arrays.asList("TopHat", "Racecar", "Thimble", "Wheelbarrow", "Iron");
        for (int i = 0; i < count; i++) {
            ComboBox<String> cb = new ComboBox<>();
            cb.getItems().addAll(pieces);
            cb.setPromptText("Select piece for Player " + (i + 1));
            container.getChildren().add(cb);
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

