package com.example.elixirapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;

public class ElixirApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(ElixirApplication.class.getResource("/hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1268, 708);
        Group root = new Group();

//        HBox box = new HBox();
        Image img = new Image("/background_long.png");
        ImageView backgroundView = new ImageView(img);
        root.getChildren().add(backgroundView);
        LevelManager lvlManager = new LevelManager("/Users/leielf/Downloads/first_json.json");
        lvlManager.addToLevel(root);
        Scene scene2 = new Scene(root, 1268, 708);
//        LevelLoader lvlLoader = new LevelLoader();
//        lvlLoader.loadLevelData("/Users/leielf/Downloads/first_json.json");

        scene2.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.RIGHT){
//                ElixirController.player.setRight(true);
                backgroundView.setLayoutX(backgroundView.getLayoutX()-15);
            }

        });
//        scene.setOnKeyPressed(e -> {
//            if(e.getCode() == KeyCode.UP && !ElixirController.player.isJumping()){
//                ElixirController.player.setJumping(true);
//            }
//            if(e.getCode() == KeyCode.LEFT){
//                ElixirController.player.setLeft(true);
//            }
//            if(e.getCode() == KeyCode.RIGHT){
//                ElixirController.player.setRight(true);
//            }
//        });
//        scene.setOnKeyReleased(e -> {
//            if(e.getCode() == KeyCode.SPACE){
//                ElixirController.player.setJumping(false);
//            }
//            if(e.getCode() == KeyCode.LEFT){
//                ElixirController.player.setLeft(false);
//            }
//            if(e.getCode() == KeyCode.RIGHT){
//                ElixirController.player.setRight(false);
//            }
//        });
        stage.setScene(scene2);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}