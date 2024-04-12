package com.example.elixirapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class ElixirApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ElixirApplication.class.getResource("/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1268, 708);
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.UP && !ElixirController.player.isJumping()){
                ElixirController.player.setJumping(true);
            }
            if(e.getCode() == KeyCode.LEFT){
                ElixirController.player.setLeft(true);
            }
            if(e.getCode() == KeyCode.RIGHT){
                ElixirController.player.setRight(true);
            }
        });
        scene.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.SPACE){
                ElixirController.player.setJumping(false);
            }
            if(e.getCode() == KeyCode.LEFT){
                ElixirController.player.setLeft(false);
            }
            if(e.getCode() == KeyCode.RIGHT){
                ElixirController.player.setRight(false);
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}