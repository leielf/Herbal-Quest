package com.example.elixirapp;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LevelLoader {

    Map map;
    private static final Logger logger = Logger.getLogger(LevelLoader.class.getName());

    public LevelLoader() {
        map = new Map();
    }

    public void loadLevelData(String filePath) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath);
            JSONObject jsonLevel = (JSONObject) parser.parse(reader);
            loadData(jsonLevel);
        } catch (ParseException | IOException e) {
            logger.log(Level.SEVERE, "Error occured while parsing file");
//            exit();
        }
    }

    public void loadData(JSONObject jsonLevel) {
        JSONArray coinsArray = (JSONArray) jsonLevel.get("coins");
        JSONArray blocksArray = (JSONArray) jsonLevel.get("blocks");
        JSONArray thievesArray = (JSONArray) jsonLevel.get("thieves");
        JSONArray mushroomsArray = (JSONArray) jsonLevel.get("mushrooms");
        try {
            loadCoins(coinsArray);
        } catch (NullPointerException e) {
            map.setCoins(null);
        }
        try {
            loadBlocks(blocksArray);
        } catch (NullPointerException e) {
            map.setBlocks(null);
        }
        try {
            loadThieves(thievesArray);
        } catch (NullPointerException e) {
            map.setThieves(null);
        }
        try {
            loadMushrooms(mushroomsArray);
        } catch (NullPointerException e) {
            map.setMushrooms(null);
        }
        loadPlayer();
    }

    private void loadMushrooms(JSONArray arr) {
        ArrayList<Mushroom> mushrooms = new ArrayList<>();
        for (Object coinObj : arr) {
            JSONObject coinJson = (JSONObject) coinObj;
            final double x = ((Number) coinJson.get("x")).doubleValue();
            final double y = ((Number) coinJson.get("y")).doubleValue();
            Mushroom mushroom = new Mushroom(x, y);
            mushroom.setHeight(40);
            mushroom.setWidth(40);
            mushrooms.add(mushroom);
        }
        map.setMushrooms(mushrooms);
    }

    private void loadCoins(JSONArray arr) {
        ArrayList<Coin> coins = new ArrayList<>();
        for (Object coinObj : arr) {
            JSONObject coinJson = (JSONObject) coinObj;
            final double x = ((Number) coinJson.get("x")).doubleValue();
            final double y = ((Number) coinJson.get("y")).doubleValue();
            final int value = ((Number) coinJson.get("value")).intValue();
            Coin coin = new Coin(value, x, y);
            coin.setHeight(40);
            coin.setWidth(40);
            coins.add(coin);
        }
        map.setCoins(coins);
    }

    private void loadBlocks(JSONArray arr) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (Object blockObj : arr) {
            JSONObject blockJson = (JSONObject) blockObj;
            final double x = ((Number) blockJson.get("x")).doubleValue();
            final double y = ((Number) blockJson.get("y")).doubleValue();
            Block block = new Block(x, y);
            block.setHeight(40);
            block.setWidth(50);
            blocks.add(block);
        }
        map.setBlocks(blocks);
    }

    private void loadThieves(JSONArray arr) {
        ArrayList<Thief> thieves = new ArrayList<>();
        for (Object thiefObj : arr) {
            JSONObject coinJson = (JSONObject) thiefObj;
            final double x = ((Number) coinJson.get("x")).doubleValue();
            final double y = ((Number) coinJson.get("y")).doubleValue();
            Thief thief = new Thief(x, y);
            thief.setWidth(60);
            thief.setHeight(60);
            thieves.add(thief);
        }
        map.setThieves(thieves);
    }

    public void loadPlayer() {
        Player player = new Player(UIController.SCENE_WIDTH / 2, 500, 7);
        player.setWidth(80);
        player.setHeight(100);
        player.setX(player.getX() - player.getWidth());
        map.setPlayer(player);
    }

    public Map getMap() {
        return map;
    }

}