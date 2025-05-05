package edu.ntnu.iir.bidata.javafx;
// package org.example;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
// import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Javafx extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Group root = new Group();
        Scene scene = new Scene(root, Color.CORNFLOWERBLUE);

        stage.setScene(scene);
        stage.show();






    }


}


