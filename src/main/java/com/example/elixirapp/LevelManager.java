package com.example.elixirapp;

import java.util.List;

public class LevelManager {

    private LevelLoader levelLoader;
    private List<BlockData> blocks;
    private List<CoinData> coins;
    private List<ThiefData> thiefs;
    public LevelManager() {
//        levelLoader = new LevelLoader();
//        levelLoader.loadLevel(fileName);
//        blocks = lev
    }

    public void addToLevel(){
        createBlocks();
        createCoins();
        createThiefs();
    }

    public void createBlocks(){

    }

    public void createCoins(){

    }

    public void createThiefs(){

    }

}
