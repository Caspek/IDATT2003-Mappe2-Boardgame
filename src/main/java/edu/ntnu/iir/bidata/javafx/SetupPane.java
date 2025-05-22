package edu.ntnu.iir.bidata.javafx;

import edu.ntnu.iir.bidata.config.PlayerLoader;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;


/**
 * The SetupPane class represents the setup screen for the board game.
 * It allows users to select the board configuration, the number of players,
 * and the pieces for each player.
 */
public class SetupPane extends VBox {
  private final ComboBox<String> boardSelector = new ComboBox<>();
  private final VBox pieceSelectorsBox = new VBox(10);

  /**
   * Constructs a new SetupPane instance.
   * Initializes the UI components for board selection, player count, and piece selection.
   */
  public SetupPane() {
    super(10);
    setPadding(new Insets(20));

    // Board selection
    final Label boardLabel = new Label("Choose board:");
    boardSelector.getItems().addAll("1. Simple",
        "2. Painful", "3. Standard", "4. Teleportation", "5. Short", "6. Queue");
    boardSelector.getSelectionModel().selectFirst();

    // Player count
    Label playerCountLabel = new Label("Number of players (2-5):");
    Spinner<Integer> playerCountSpinner = new Spinner<>(2, 5, 2);
    playerCountSpinner.valueProperty()
        .addListener((obs, oldVal, newVal) -> updatePieceSelectors(newVal));

    // Piece selectors
    updatePieceSelectors(2);

    getChildren().addAll(
        boardLabel, boardSelector,
        playerCountLabel, playerCountSpinner,
        new Label("Choose pieces:"), pieceSelectorsBox
    );
  }

  /**
   * Updates the piece selectors based on the number of players.
   *
   * @param count The number of players.
   */
  private void updatePieceSelectors(int count) {
    pieceSelectorsBox.getChildren().clear();
    List<String> pieces = PlayerLoader.loadPlayerNamesFromFile("src/main/resources/Players.json");
    for (int i = 0; i < count; i++) {
      ComboBox<String> cb = new ComboBox<>();
      cb.getItems().addAll(pieces);
      cb.setPromptText("Select piece for Player " + (i + 1));
      pieceSelectorsBox.getChildren().add(cb);
    }
  }

  /**
   * Gets the selected board configuration.
   *
   * @return The index of the selected board configuration (1-based).
   */
  public int getBoardChoice() {
    return boardSelector.getSelectionModel().getSelectedIndex() + 1;
  }

  /**
   * Gets the selected pieces for all players.
   *
   * @return A list of selected pieces.
   */
  public List<String> getSelectedPieces() {
    List<String> selectedPieces = new ArrayList<>();
    for (var node : pieceSelectorsBox.getChildren()) {
      if (node instanceof ComboBox<?> cb && cb.getValue() instanceof String) {
        selectedPieces.add((String) cb.getValue());
      }
    }
    return selectedPieces;
  }
}