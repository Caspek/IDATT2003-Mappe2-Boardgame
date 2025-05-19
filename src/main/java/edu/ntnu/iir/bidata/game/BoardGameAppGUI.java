package edu.ntnu.iir.bidata.gui;

import edu.ntnu.iir.bidata.board.BoardGame;
import edu.ntnu.iir.bidata.board.BoardGameFactory;
import edu.ntnu.iir.bidata.game.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BoardGameAppGUI extends Application {
    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(20));

        Label titleLabel = new Label("Velg brett:");
        ComboBox<String> boardSelector = new ComboBox<>();
        boardSelector.getItems().addAll("1. Simple", "2. Painful", "3. Standard", "4. Teleportation");
        boardSelector.getSelectionModel().selectFirst();

        Button startButton = new Button("Start spill");
        root.getChildren().addAll(titleLabel, boardSelector, startButton);

        Scene scene = new Scene(root, 400, 150);
        stage.setScene(scene);
        stage.setTitle("Board Game GUI");
        stage.show();

        startButton.setOnAction(e -> {
            int choice = boardSelector.getSelectionModel().getSelectedIndex() + 1;
            BoardGame game = BoardGameFactory.loadBoardFromFile(choice);
            game.setDice(2);
            game.loadPlayers("src/main/resources/players.json");

            GameView view = new GameView(game);
            Scene gameScene = new Scene(view.getRoot(), 600, 500);
            stage.setScene(gameScene);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

