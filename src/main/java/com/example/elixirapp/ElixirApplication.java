package com.example.elixirapp;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class ElixirApplication extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        GameEngine gameEngine = new GameEngine(stage);
        gameEngine.start("/Users/leielf/Downloads/firstlevel.json");
    }

    public static void main(String[] args) {
        launch();
    }

}