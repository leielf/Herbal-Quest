package com.example.elixirapp;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.example.elixirapp.GameEntity.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.application.Platform.exit;

/**
 * The LevelLoader class is responsible for loading level data from a JSON file and creating a Map for the level.
 * It parses the JSON file and creates game entities such as coins, blocks, thieves, mushrooms, herbs, and the player.
 *
 * Author: Leila Babayeva
 */
public class LevelLoader {

    Map map;
    private boolean isLoaded;
    private static final Logger logger = Logger.getLogger(LevelLoader.class.getName());

    public LevelLoader() {
        map = new Map();
    }

    /**
     * Loads level data from the specified JSON file path.
     *
     * @param filePath the file path of the JSON file containing the level data
     */
    public void loadLevelData(String filePath) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(filePath);
            JSONObject jsonLevel = (JSONObject) parser.parse(reader);
            loadData(jsonLevel);
            isLoaded = true;
            logger.log(Level.INFO, "Level was loaded.");
        } catch (ParseException | IOException e) {
            logger.log(Level.SEVERE, "Error occured while parsing file");
            isLoaded = false;
        }
    }

    /**
     * Loads data from the given JSON object and populates the map with game entities.
     *
     * @param jsonLevel the JSON object containing the level data
     */
    public void loadData(JSONObject jsonLevel) {
        JSONArray coinsArray = (JSONArray) jsonLevel.get("coins");
        JSONArray blocksArray = (JSONArray) jsonLevel.get("blocks");
        JSONArray thievesArray = (JSONArray) jsonLevel.get("thieves");
        JSONArray mushroomsArray = (JSONArray) jsonLevel.get("mushrooms");
        JSONArray herbsArray = (JSONArray) jsonLevel.get("herbs");
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
        try {
            loadHerbs(herbsArray);
        } catch (NullPointerException e) {
            map.setHerbs(null);
        }
        loadPlayer();
    }


    private void loadMushrooms(JSONArray arr) {
        ArrayList<Mushroom> mushrooms = new ArrayList<>();
        for (Object mushroomObj : arr) {
            JSONObject mushroomJson = (JSONObject) mushroomObj;
            final double x = ((Number) mushroomJson.get("x")).doubleValue();
            final double y = ((Number) mushroomJson.get("y")).doubleValue();
            Mushroom mushroom = new Mushroom(x, y);
            checkCoordinates(mushroom, x, y);
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
            checkCoordinates(coin, x, y);
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
            blocks.add(block);
        }
        map.setBlocks(blocks);
    }

    private void loadThieves(JSONArray arr) {
        ArrayList<Thief> thieves = new ArrayList<>();
        for (Object thiefObj : arr) {
            JSONObject thiefJson = (JSONObject) thiefObj;
            final double x = ((Number) thiefJson.get("x")).doubleValue();
            final double y = ((Number) thiefJson.get("y")).doubleValue();
            Thief thief = new Thief(x, y);
            checkCoordinates(thief, x, y);
            thieves.add(thief);
        }
        map.setThieves(thieves);
    }

    private void loadHerbs(JSONArray arr) {
        ArrayList<Herb> herbs = new ArrayList<>();
        for (Object herbObj : arr) {
            JSONObject herbJson = (JSONObject) herbObj;
            final String name = (String) herbJson.get("name");
            final double x = ((Number) herbJson.get("x")).doubleValue();
            final double y = ((Number) herbJson.get("y")).doubleValue();
            final int cost = ((Number) herbJson.get("cost")).intValue();
            Herb herb = new Herb(name, x, y, cost);
            herbs.add(herb);
        }
        map.setHerbs(herbs);
    }

    public void loadPlayer() {
        Player player = new Player((double) 100, 500, 6);
        player.setX(player.getX() - player.getWidth());
        map.setPlayer(player);
    }

    /**
     * Checks and adjusts the coordinates of game objects to ensure they are within valid bounds.
     *
     * @param obj the game object to check
     * @param x the x-coordinate to validate
     * @param y the y-coordinate to validate
     */
    public void checkCoordinates(GameObject obj, double x, double y){
        if(x < obj.getWidth()) obj.setX(obj.getWidth() + 1);
        if(x > SceneCreator.SCENE_WIDTH*5) obj.setX(SceneCreator.SCENE_WIDTH*5 - 5- obj.getWidth());
        if(y < obj.getHeight()) obj.setY(obj.getHeight() + 1);
        if(y+ obj.getHeight() > SceneCreator.BORDER) obj.setY(SceneCreator.BORDER- obj.getHeight());
    }
    public Map getMap() {
        return map;
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}