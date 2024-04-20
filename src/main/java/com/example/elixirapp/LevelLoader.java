package com.example.elixirapp;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.sun.javafx.application.PlatformImpl.exit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LevelLoader {
    private static final Logger logger = Logger.getLogger(LevelLoader.class.getName());
    public LevelLoader() {
    }

    public LevelData loadLevelData(String filePath){
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath);
            JSONObject jsonLevel = (JSONObject) parser.parse(reader);
            return loadData(jsonLevel);
        }
        catch (ParseException | IOException e){
            logger.log(Level.SEVERE, "Error occured while parsing file");
            exit();
        }
        return null;
    }

    public LevelData loadData(JSONObject jsonLevel){
        LevelData levelData = new LevelData();
        JSONArray coinsArray = (JSONArray) jsonLevel.get("coins");
        JSONArray blocksArray = (JSONArray) jsonLevel.get("blocks");
        JSONArray thievesArray = (JSONArray) jsonLevel.get("thieves");
        try {
            levelData.setCoins(loadCoins(coinsArray));
        }catch (NullPointerException e){
            logger.log(Level.INFO, "No coins in JSON file.");
            levelData.setCoins(null);
        }
        try {
            levelData.setBlocks(loadBlocks(blocksArray));
        }catch (NullPointerException e){
            logger.log(Level.INFO, "No blocks in JSON file.");
            levelData.setBlocks(null);
        }
        try {
            levelData.setThieves(loadThieves(thievesArray));
        }catch (NullPointerException e){
            logger.log(Level.INFO, "No thieves in JSON file.");
            levelData.setThieves(null);
        }
//        levelData.setCoins(loadCoins(coinsArray));
//        levelData.setBlocks(loadBlocks(blocksArray));
//        levelData.setThieves(loadThieves(thievesArray));
        return levelData;
    }

    private List<CoinData> loadCoins(JSONArray arr){
        List<CoinData> coins = new ArrayList<>();
        for (Object coinObj : arr) {
            JSONObject coinJson = (JSONObject) coinObj;
            final double x = ((Number) coinJson.get("x")).doubleValue();
            final double y = ((Number) coinJson.get("y")).doubleValue();
            final int value = ((Number) coinJson.get("value")).intValue();
            CoinData coin = new CoinData(x, y, value);
            coins.add(coin);
        }
        return coins;
    }

    private List<BlockData> loadBlocks(JSONArray arr){
        List<BlockData> blocks = new ArrayList<>();
        for (Object blockObj : arr) {
            JSONObject blockJson = (JSONObject) blockObj;
            final double x = ((Number) blockJson.get("x")).doubleValue();
            final double y = ((Number) blockJson.get("y")).doubleValue();
            BlockData block = new BlockData(x, y);
            blocks.add(block);
        }
        return blocks;
    }

    private List<ThiefData> loadThieves(JSONArray arr){
        List<ThiefData> thieves = new ArrayList<>();
        for (Object thiefObj : arr) {
            JSONObject coinJson = (JSONObject) thiefObj;
            final double x = ((Number) coinJson.get("x")).doubleValue();
            final double y = ((Number) coinJson.get("y")).doubleValue();
            ThiefData thief = new ThiefData(x, y);
            thieves.add(thief);
        }
        return thieves;
    }
}
class LevelData {
    private List<BlockData> blocks;
    private List<CoinData> coins;
    private List<ThiefData> thieves;

    public void setBlocks(List<BlockData> blocks) {
        this.blocks = blocks;
    }

    public void setCoins(List<CoinData> coins) {
        this.coins = coins;
    }

    public void setThieves(List<ThiefData> thieves) {
        this.thieves = thieves;
    }

    public List<BlockData> getBlocks() {
        return blocks;
    }

    public List<CoinData> getCoins() {
        return coins;
    }

    public List<ThiefData> getThieves() {
        return thieves;
    }
// Getters and setters
}

class BlockData {
    private double x;
    private double y;

    public BlockData(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

class CoinData {
    private double x;
    private double y;
    private int value;

    public CoinData(double x, double y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
}

class ThiefData {
    private double x;
    private double y;

    public ThiefData(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
