package com.example.elixirapp;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.File;
import java.util.List;

public class LevelManager {

    private static final Logger logger = Logger.getLogger(LevelManager.class.getName());
    private LevelLoader levelLoader;
    private List<BlockData> blocks;
    private List<CoinData> coins;
    private List<ThiefData> thieves;
    public LevelManager(String fileName) {
        levelLoader = new LevelLoader();
        LevelData lvlData = levelLoader.loadLevelData(fileName);
        blocks = lvlData.getBlocks();
        coins = lvlData.getCoins();
        thieves = lvlData.getThieves();
        ConsoleHandler handler
                = new ConsoleHandler();
        logger.addHandler(handler);
    }

    private void setImg(ImageView imgView, String pathname, double x, double y){
        File file = new File(pathname);
        Image image = new Image(file.toURI().toString());
        imgView.setImage(image);
        imgView.setLayoutX(x);
        imgView.setLayoutY(y);
    }

    public void addToLevel(Pane root){
        try {
            createBlocks(root);
        }catch (NullPointerException e){
            logger.log(Level.INFO, "No blocks in JSON file.");
        }
        try {
            createCoins(root);
        }catch (NullPointerException e){
            logger.log(Level.INFO, "No coins in JSON file.");
        }
        try {
            createThieves(root);
        }catch (NullPointerException e){
            logger.log(Level.INFO, "No thieves in JSON file.");
        }
    }

    private void createBlocks(Pane root){
        for (BlockData blockData : blocks){
            ImageView blockImg = new ImageView();
            setImg(blockImg, "src/main/resources/block.png", blockData.getX(), blockData.getY());
            blockImg.setFitWidth(50);
            blockImg.setFitHeight(50);
            root.getChildren().add(blockImg);
            logger.log(Level.INFO, "Block was added. Position: x: "+ blockImg.getLayoutX() + ", y: " + blockImg.getLayoutY());
        }
    }

    private void createCoins(Pane root){
        for (CoinData coinData : coins){
            ImageView coinImg = new ImageView();
            setImg(coinImg, "/Users/leielf/Downloads/ElixirApp/src/main/resources/coin.png", coinData.getX(), coinData.getY());
            root.getChildren().add(coinImg);
            coinImg.setFitHeight(40);
            coinImg.setFitWidth(40);
            logger.log(Level.INFO, "Coin was added. Position: x: "+ coinImg.getLayoutX() + ", y: " + coinImg.getLayoutY());
        }
    }

    private void createThieves(Pane root){
        for (ThiefData thiefData : thieves){
            ImageView thiefImg = new ImageView();
            setImg(thiefImg, "", thiefData.getX(), thiefData.getY());
            root.getChildren().add(thiefImg);
            logger.log(Level.INFO, "Thief was added. Position: x: "+ thiefImg.getLayoutX() + ", y: " + thiefImg.getLayoutY());
        }
    }

}
