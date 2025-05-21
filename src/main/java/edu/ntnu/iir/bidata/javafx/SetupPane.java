package edu.ntnu.iir.bidata.javafx;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetupPane extends VBox {
    private final ComboBox<String> boardSelector = new ComboBox<>();
    private final Spinner<Integer> playerCountSpinner = new Spinner<>(2, 5, 2);
    private final VBox pieceSelectorsBox = new VBox(10);

    public SetupPane() {
        super(10);
        setPadding(new Insets(20));

        // Board selection
        Label boardLabel = new Label("Choose board:");
        boardSelector.getItems().addAll("1. Simple", "2. Painful", "3. Standard", "4. Teleportation", "5. Short", "6. Queue");
        boardSelector.getSelectionModel().selectFirst();

        // Player count
        Label playerCountLabel = new Label("Number of players (2-5):");
        playerCountSpinner.valueProperty().addListener((obs, oldVal, newVal) -> updatePieceSelectors(newVal));

        // Piece selectors
        updatePieceSelectors(2);

        getChildren().addAll(
                boardLabel, boardSelector,
                playerCountLabel, playerCountSpinner,
                new Label("Choose pieces:"), pieceSelectorsBox
        );
    }

    private void updatePieceSelectors(int count) {
        pieceSelectorsBox.getChildren().clear();
        List<String> pieces = Arrays.asList("TopHat", "Racecar", "Thimble", "Wheelbarrow", "Iron");
        for (int i = 0; i < count; i++) {
            ComboBox<String> cb = new ComboBox<>();
            cb.getItems().addAll(pieces);
            cb.setPromptText("Select piece for Player " + (i + 1));
            pieceSelectorsBox.getChildren().add(cb);
        }
    }

    public int getBoardChoice() {
        return boardSelector.getSelectionModel().getSelectedIndex() + 1;
    }

    public int getPlayerCount() {
        return playerCountSpinner.getValue();
    }

    public List<String> getSelectedPieces() {
        List<String> selectedPieces = new ArrayList<>();
        for (var node : pieceSelectorsBox.getChildren()) {
            if (node instanceof ComboBox) {
                ComboBox<String> cb = (ComboBox<String>) node;
                selectedPieces.add(cb.getValue());
            }
        }
        return selectedPieces;
    }
}