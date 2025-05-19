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
        boardSelector.getItems().addAll("1. Simple", "2. Painful", "3. Standard", "4. Teleportation");
        boardSelector.getSelectionModel().selectFirst();

        // Player count
        Label playerCountLabel = new Label("Number of players (2-5):");
        Spinner<Integer> playerCountSpinner = new Spinner<>(2, 5, 2);

        // Player piece selectors
        VBox pieceSelectorsBox = new VBox(10);
        updatePieceSelectors(pieceSelectorsBox, 2); // initial count

        playerCountSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            updatePieceSelectors(pieceSelectorsBox, newVal);
        });

        // Start button
        Button startButton = new Button("Start Game");

        root.getChildren().addAll(
                boardLabel, boardSelector,
                playerCountLabel, playerCountSpinner,
                new Label("Choose pieces:"), pieceSelectorsBox,
                startButton
        );

        Scene scene = new Scene(root, 400, 450);
        stage.setScene(scene);
        stage.setTitle("Board Game Setup");
        stage.show();

        startButton.setOnAction(e -> {
            int boardChoice = boardSelector.getSelectionModel().getSelectedIndex() + 1;
            BoardGame game = BoardGameFactory.loadBoardFromFile(boardChoice);
            game.setDice(2);

            List<String> selectedPieces = new ArrayList<>();
            for (Node node : pieceSelectorsBox.getChildren()) {
                if (node instanceof ComboBox) {
                    ComboBox<String> cb = (ComboBox<String>) node;
                    String piece = cb.getValue();
                    if (piece == null || selectedPieces.contains(piece)) {
                        showAlert("Each player must select a unique piece.");
                        return;
                    }
                    selectedPieces.add(piece);
                }
            }

            for (String name : selectedPieces) {
                game.addPlayer(name);
            }

            // Start game view
            GameView view = new GameView(game);
            Scene gameScene = new Scene(view.getRoot(), 600, 500);
            stage.setScene(gameScene);
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

