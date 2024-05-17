package com.example.elixirapp;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameEngine implements Runnable{
    private AtomicBoolean active = new AtomicBoolean(true);
    private static final Logger logger = Logger.getLogger(GameEngine.class.getName());
    private Thread gameThread;
    private GameStatus gameStatus;
    private LevelLoader levelLoader;

    private DataSaver dataSaver;
    private LevelController levelController;
    private Stage stage;
    private String filePath;



    public GameEngine(Stage stage){
        this.stage = stage;
        gameStatus = GameStatus.START_SCREEN;
        levelLoader = new LevelLoader();
        dataSaver = new DataSaver();
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
        while (active.get()){
            levelController.checkGameStatus();
            if(gameStatus == GameStatus.START_SCREEN){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        levelController.checkStartScreen();
                    }
                });
            }
            else if (gameStatus == GameStatus.RUNNING) {
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
                        dataSaver.saveData(levelController.map.getPlayer());
                        end();
                    }
                });
            }
            if(!gameThread.isInterrupted()){
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
            }}
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
        active.set(false);
    }
}
