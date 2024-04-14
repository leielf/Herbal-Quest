package com.example.elixirapp;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.List;

public class LevelManager {

    private ImageView coinImg, blockImg, thiefImg;
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
    }

    private void setImg(ImageView imgView, String pathname, double x, double y){
        File file = new File(pathname);
        Image image = new Image(file.toURI().toString());
        imgView.setImage(image);
        imgView.setLayoutX(x);
        imgView.setLayoutY(y);
    }

//    public void imgViewCreator(){
//        setImg(coinImg);
//        setImg(blockImg);
//        setImg(thiefImg);
//    }

    public void addToLevel(Group root){
        try {
            createBlocks(root);
        }catch (NullPointerException e){
            System.out.println("No blocks in JSON file.");
        }
        try {
            createCoins(root);
        }catch (NullPointerException e){
            System.out.println("No coins in JSON file.");
        }
        try {
            createThieves(root);
        }catch (NullPointerException e){
            System.out.println("No thieves in JSON file.");
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
        }
    }

    private void createCoins(Group root){
        for (CoinData coinData : coins){
            ImageView coinImg = new ImageView();
            setImg(coinImg, "coin.png", coinData.getX(), coinData.getY());
            root.getChildren().add(coinImg);
            coinImg.setY(100);
            coinImg.setX(100);
            System.out.printf("Coin was added at position x: %f, y: %f\n", coinImg.getLayoutX(), coinImg.getLayoutY());
        }
    }

    private void createThieves(Group root){
        for (ThiefData thiefData : thieves){
            ImageView thiefImg = new ImageView();
            setImg(thiefImg, "", thiefData.getX(), thiefData.getY());
            root.getChildren().add(thiefImg);
        }
    }

}
