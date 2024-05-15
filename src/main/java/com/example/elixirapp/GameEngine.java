package com.example.elixirapp;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameEngine implements Runnable{
    private static final Logger logger = Logger.getLogger(GameEngine.class.getName());
    private Thread gameThread;
    private GameStatus gameStatus;
    private LevelLoader levelLoader;
    private LevelController levelController;
    private Stage stage;
    private String filePath;



    public GameEngine(Stage stage){
        this.stage = stage;
        gameStatus = GameStatus.START_SCREEN;
        levelLoader = new LevelLoader();
    }

    public void start(String filePath){
        this.filePath = filePath;
        levelLoader.loadLevelData(filePath);
        levelController = new LevelController(this, levelLoader.getMap());
        levelController.createLevel(stage);
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
            levelController.checkGameStatus();
            if (gameStatus == GameStatus.RUNNING) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        levelController.gameUpdate();
                    }
                });
            }
            else if (gameStatus == GameStatus.FAIL){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                       reset();
                    }
                });
            }
            else if (gameStatus == GameStatus.WIN){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        end();
                    }
                });
            }
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

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void reset(){
        levelLoader.loadLevelData(filePath);
        levelController.reset(levelLoader.getMap());
    }

    public void end(){
        levelController.endLevel();
    }
}
