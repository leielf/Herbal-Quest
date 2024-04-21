package com.example.elixirapp;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.sun.javafx.application.PlatformImpl.exit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LevelLoader {
    private static final Logger logger = Logger.getLogger(LevelLoader.class.getName());

    private ArrayList<Block> blocks;
    private ArrayList<Coin> coins;
    private ArrayList<Thief> thieves;
    public LevelLoader() {
        blocks = new ArrayList<>();
        coins = new ArrayList<>();
        thieves = new ArrayList<>();
    }

    public void addToLevel(Pane root, String filePath){
        loadLevelData(filePath);
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
        for (Block block : blocks){
            block.getImg().setFitWidth(50);
            block.getImg().setFitHeight(50);
            root.getChildren().add(block.getImg());
            logger.log(Level.INFO, "Block was added. Position: x: "+ block.getX() + ", y: " + block.getY());
        }
    }

    private void createCoins(Pane root){
        for (Coin coin : coins){
            coin.getImg().setFitHeight(40);
            coin.getImg().setFitWidth(40);
            root.getChildren().add(coin.getImg());
            logger.log(Level.INFO, "Coin was added. Position: x: "+ coin.getX() + ", y: " + coin.getY());
        }
    }

    private void createThieves(Pane root){
        for (Thief thief : thieves){
            root.getChildren().add(thief.getImg());
            logger.log(Level.INFO, "Thief was added. Position: x: "+ thief.getX() + ", y: " + thief.getY());
        }
    }

    public void loadLevelData(String filePath){
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath);
            JSONObject jsonLevel = (JSONObject) parser.parse(reader);
            loadData(jsonLevel);
        }
        catch (ParseException | IOException e){
            logger.log(Level.SEVERE, "Error occured while parsing file");
            exit();
        }
    }

    public void loadData(JSONObject jsonLevel){
        JSONArray coinsArray = (JSONArray) jsonLevel.get("coins");
        JSONArray blocksArray = (JSONArray) jsonLevel.get("blocks");
        JSONArray thievesArray = (JSONArray) jsonLevel.get("thieves");
        try {
            loadCoins(coinsArray);
        }catch (NullPointerException e){
            coins = null;
        }
        try {
            loadBlocks(blocksArray);
        }catch (NullPointerException e){
            blocks = null;
        }
        try {
            loadThieves(thievesArray);
        }catch (NullPointerException e){
            thieves = null;
        }
    }

    private void loadCoins(JSONArray arr){
        Image coinImg = new Image("/coin.png");
        for (Object coinObj : arr) {
            JSONObject coinJson = (JSONObject) coinObj;
            final double x = ((Number) coinJson.get("x")).doubleValue();
            final double y = ((Number) coinJson.get("y")).doubleValue();
            final int value = ((Number) coinJson.get("value")).intValue();
            Coin coin = new Coin(coinImg, value, x, y);
            coins.add(coin);
        }
    }

    private void loadBlocks(JSONArray arr){
        Image blockImg = new Image("/block.png");
        for (Object blockObj : arr) {
            JSONObject blockJson = (JSONObject) blockObj;
            final double x = ((Number) blockJson.get("x")).doubleValue();
            final double y = ((Number) blockJson.get("y")).doubleValue();
            Block block = new Block(blockImg, x ,y);
            blocks.add(block);
        }
    }

    private void loadThieves(JSONArray arr){
        Image thiefImg = new Image("/red_circle.png");
        for (Object thiefObj : arr) {
            JSONObject coinJson = (JSONObject) thiefObj;
            final double x = ((Number) coinJson.get("x")).doubleValue();
            final double y = ((Number) coinJson.get("y")).doubleValue();
            Thief thief = new Thief(thiefImg, x, y);
            thieves.add(thief);
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public ArrayList<Thief> getThieves() {
        return thieves;
    }
}
