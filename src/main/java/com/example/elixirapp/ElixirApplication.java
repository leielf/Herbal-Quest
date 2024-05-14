package com.example.elixirapp;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;


public class ElixirApplication extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        GameEngine gameEngine = new GameEngine(stage);
        gameEngine.start("/Users/leielf/Downloads/second_json.json");
    }

    public static void main(String[] args) {
        launch();
    }

}