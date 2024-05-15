package com.example.elixirapp;
import com.example.elixirapp.GameEntity.*;

import java.util.ArrayList;

public class Map{

    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private ArrayList<Thief> thieves = new ArrayList<>();
    private ArrayList<Mushroom> mushrooms = new ArrayList<>();
    private final Stall stall = new Stall(SceneCreator.SCENE_WIDTH-100, 500);

    private ArrayList<Herb> herbs = new ArrayList<>();
    private Player player;

    public Map(){
    }
    public void updateLocations(){
        if(player!=null){
            player.updateLocation();
            player.move();
        }
        if(!thieves.isEmpty()){
            for (Thief thief : thieves) {
                thief.updateLocation();
                thief.moveLeftRight();
            }
        }
        if(!mushrooms.isEmpty()){
            for (Mushroom mushroom : mushrooms) {
                mushroom.updateLocation();
            }
        }
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

    public ArrayList<Mushroom> getMushrooms() {
        return mushrooms;
    }

    public void setMushrooms(ArrayList<Mushroom> mushrooms) {
        this.mushrooms = mushrooms;
    }

    public ArrayList<Herb> getHerbs() {
        return herbs;
    }

    public void setHerbs(ArrayList<Herb> herbs) {
        this.herbs = herbs;
    }

    public Stall getStall() {
        return stall;
    }
}
