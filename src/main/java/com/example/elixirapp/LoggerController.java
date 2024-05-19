package com.example.elixirapp;

import com.example.elixirapp.GameEntity.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerController {

    private Class<?>[] classes = {Block.class, Coin.class, DataSaver.class, ElixirApplication.class,
    GameEngine.class, GameObject.class, Herb.class, LevelController.class, LevelLoader.class,
    Map.class, Mushroom.class, Player.class, SceneController.class, SceneCreator.class, Stall.class, Thief.class};

    public LoggerController(){}
    public void disableLoggingForClasses(boolean isDisabled) {
        if(isDisabled){
            for (Class<?> cls : classes) {
                Logger logger = Logger.getLogger(cls.getName());
                logger.setLevel(Level.OFF);
            }
        }
    }
}
