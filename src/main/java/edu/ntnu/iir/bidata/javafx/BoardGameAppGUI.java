package edu.ntnu.iir.bidata.javafx;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.config.BoardGameFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;

/**
 * The main GUI application for the board game.
 * This class handles the setup screen, user input validation, and transitions to the game view.
 */
public class BoardGameAppGUI extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        SetupPane setupPane = new SetupPane();

        setupPane.getChildren().add(new javafx.scene.control.Button("Start Game") {{
            setOnAction(e -> {
                try {
                    int boardChoice = setupPane.getBoardChoice();
                    List<String> selectedPieces = setupPane.getSelectedPieces();

                    // Validate user input
                    if (selectedPieces.contains(null) || selectedPieces.stream().distinct().count() != selectedPieces.size()) {
                        showAlert("Each player must select a unique piece.");
                        return;
                    }

                    // Load the board and initialize the game
                    BoardGame game = BoardGameFactory.loadBoardFromFile(boardChoice);
                    if (game.getDice() == null) {
                        game.setDice(2, false);
                    }
                    selectedPieces.forEach(game::addPlayer);

                    // Transition to the game view
                    GameView gameView = new GameView(game, boardChoice);
                    Scene gameScene = new Scene(gameView.getRoot(), 800, 600);
                    stage.setScene(gameScene);
                    stage.sizeToScene();
                    stage.centerOnScreen();
                } catch (Exception ex) {
                    showAlert("Failed to start the game: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        }});

        // Set up the initial scene
        Scene scene = new Scene(setupPane, 200, 400);
        stage.setScene(scene);
        stage.setTitle("Board Game Setup");
        stage.show();
    }

    /**
     * Displays an alert dialog with the specified message.
     *
     * @param msg the message to display in the alert dialog.
     */
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * The main entry point for the application.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
