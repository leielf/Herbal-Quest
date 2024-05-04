package com.example.elixirapp;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ElixirApplication extends Application implements Runnable{

    private static final Logger logger = Logger.getLogger(ElixirApplication.class.getName());
    private Thread gameThread;
    LevelController levelController = new LevelController();
    @Override
    public void start(Stage stage) throws IOException {
        levelController.createLevel(stage, "/Users/leielf/Downloads/second_json.json");
        startGameThread();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double updateInterval = 1000000000/60;
        double nextUpdateTime = System.nanoTime() + updateInterval;
        while (gameThread != null){
            if(levelController.isPlayerDead()){
                gameThread = null;
            }
            levelController.gameUpdate();
            try {
                double remainingTime = nextUpdateTime - System.nanoTime();
                remainingTime /= 1000000;
                if (remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                nextUpdateTime += updateInterval;
            }catch (InterruptedException e){
                logger.log(Level.INFO, "error in thread");
            }
        }
    }


    public static void main(String[] args) {
        launch();
    }

}