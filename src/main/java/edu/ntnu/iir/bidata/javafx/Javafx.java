package edu.ntnu.iir.bidata.javafx;
// package org.example;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
// import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Javafx extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Group root = new Group();
        Scene scene = new Scene(root, Color.CORNFLOWERBLUE);

        stage.setTitle("L.A.D.D.E.R");

        stage.setWidth(800);
        stage.setHeight(800);

        stage.setResizable(false);
        // stage.setFullScreen(true); fjerner det mens jeg redigerer mye

        Text section1 = new Text();
        section1.setText("Section 1");
        section1.setX(50);
        section1.setY(50);
        section1.setFont(Font.font("Verdana", 20));
        section1.setFill(Color.GREEN);



        root.getChildren().add(section1);
        stage.setScene(scene);
        stage.show();






    }


}


