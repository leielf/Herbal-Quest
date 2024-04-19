package com.example.elixirapp;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    public void addToLevel(Group root){
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
//        createBlocks(root);
//        createCoins(root);
//        createThieves(root);
    }

    private void createBlocks(Group root){
        for (BlockData blockData : blocks){
            ImageView blockImg = new ImageView();
            setImg(blockImg, "", blockData.getX(), blockData.getY());
            root.getChildren().add(blockImg);
            logger.log(Level.INFO, "Block was added. Position: x: "+ blockImg.getLayoutX() + ", y: " + blockImg.getLayoutY());
        }
    }

    private void createCoins(Group root){
        for (CoinData coinData : coins){
            ImageView coinImg = new ImageView();
            setImg(coinImg, "/Users/leielf/Downloads/ElixirApp/src/main/resources/coin.png", coinData.getX(), coinData.getY());
            root.getChildren().add(coinImg);
            coinImg.setFitHeight(40);
            coinImg.setFitWidth(40);
            logger.log(Level.INFO, "Coin was added. Position: x: "+ coinImg.getLayoutX() + ", y: " + coinImg.getLayoutY());
//            System.out.printf("Coin was added at position x: %f, y: %f\n", coinImg.getLayoutX(), coinImg.getLayoutY());
        }
    }

    private void createThieves(Group root){
        for (ThiefData thiefData : thieves){
            ImageView thiefImg = new ImageView();
            setImg(thiefImg, "", thiefData.getX(), thiefData.getY());
            root.getChildren().add(thiefImg);
            logger.log(Level.INFO, "Thief was added. Position: x: "+ thiefImg.getLayoutX() + ", y: " + thiefImg.getLayoutY());
        }
    }

}
