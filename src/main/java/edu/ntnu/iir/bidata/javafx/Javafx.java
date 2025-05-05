package edu.ntnu.iir.bidata.javafx;
// package org.example;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
// import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
        Scene scene = new Scene(root, Color.WHITE);

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
        section1.setFill(Color.BLACK);

        Line lineTop = new Line();
        lineTop.setStartX(50);
        lineTop.setStartY(75);
        lineTop.setEndX(575);
        lineTop.setEndY(75);
        lineTop.setStrokeWidth(2);

        Line lineBottom = new Line();
        lineBottom.setStartX(50);
        lineBottom.setStartY(650);
        lineBottom.setEndX(575);
        lineBottom.setEndY(650);
        lineBottom.setStrokeWidth(2);

        Line lineRight = new Line();
        lineRight.setStartX(575);
        lineRight.setStartY(650);
        lineRight.setEndX(575);
        lineRight.setEndY(75);
        lineRight.setStrokeWidth(2);

        Line lineLeft = new Line();
        lineLeft.setStartX(50);
        lineLeft.setStartY(650);
        lineLeft.setEndX(50);
        lineLeft.setEndY(75);
        lineLeft.setStrokeWidth(2);


        root.getChildren().add(section1);
        root.getChildren().add(lineTop);
        root.getChildren().add(lineBottom);
        root.getChildren().add(lineRight);
        root.getChildren().add(lineLeft);
        stage.setScene(scene);
        stage.show();






    }


}


