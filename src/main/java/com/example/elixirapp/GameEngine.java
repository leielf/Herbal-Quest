package com.example.elixirapp;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The GameEngine class manages the main game loop, updating the game state, and
 * handling transitions between different game states.
 * It implements the Runnable interface to run the game loop on a separate thread.
 * The game states include START_SCREEN, RUNNING, FAIL, and WIN.
 */
public class GameEngine implements Runnable{
    private final AtomicBoolean active = new AtomicBoolean(true);
    private static final Logger logger = Logger.getLogger(GameEngine.class.getName());
    private Thread gameThread;
    private GameStatus gameStatus;
    private final LevelLoader levelLoader;
    private final DataSaver dataSaver;
    private LevelController levelController;
    private final Stage stage;
    private String filePath;

    /**
     * Constructs a GameEngine with the specified stage.
     * Initializes the game status to START_SCREEN, and sets up the LevelLoader and DataSaver.
     *
     * @param stage the primary stage for the game window
     */
    public GameEngine(Stage stage){
        this.stage = stage;
        gameStatus = GameStatus.START_SCREEN;
        levelLoader = new LevelLoader();
        dataSaver = new DataSaver();
    }

    /**
     * Starts the game by loading level data from the specified file path and initializing the LevelController.
     * Also starts the game loop thread.
     *
     * @param filePath the file path of the level data to load
     */
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

    /**
     * The main game loop, which runs on a separate thread.
     * It updates the game state at a fixed interval and handles transitions between different game states.
     */
    @Override
    public void run() {
        double updateInterval = 1000000000/60;
        double nextUpdateTime = System.nanoTime() + updateInterval;
        while (active.get()){
            logger.log(Level.INFO, "GAME IS RUNNING!");
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

    /**
     * Resets the game by reloading the level data and resetting the LevelController.
     */
    public void reset(){
        levelLoader.loadLevelData(filePath);
        levelController.reset(levelLoader.getMap());
    }

    /**
     * Ends the game by stopping the game loop and setting the active flag to false.
     */
    public void end(){
        levelController.endLevel();
        active.set(false);
    }
}
