package com.example.elixirapp;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Map{
    private UIController uiController;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private ArrayList<Thief> thieves = new ArrayList<>();
    private ArrayList<Mushroom> mushrooms = new ArrayList<>();

    private Player player;

    public Map(){
        uiController = new UIController(this);
    }
    public void updateLocations(){
        if(player!=null){
            player.updateLocation();
            player.move();
        }
        if(!thieves.isEmpty()){
            for(int i = 0; i< thieves.size(); i++){
                thieves.get(i).moveLeftRight();
            }
        }
        uiController.updateUI();
    }

    public void createMap(Stage stage){
        uiController.createMap(stage);
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public void setCoins(ArrayList<Coin> coins) {
        this.coins = coins;
    }

    public ArrayList<Thief> getThieves() {
        return thieves;
    }

    public void setThieves(ArrayList<Thief> thieves) {
        this.thieves = thieves;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public void remove(Object o){
        uiController.remove(o);
    }

    public ArrayList<Mushroom> getMushrooms() {
        return mushrooms;
    }

    public void setMushrooms(ArrayList<Mushroom> mushrooms) {
        this.mushrooms = mushrooms;
    }

    public UIController getUiController() {
        return uiController;
    }
}
