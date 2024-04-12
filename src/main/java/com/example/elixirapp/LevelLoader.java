package com.example.elixirapp;
import java.util.List;

public class LevelLoader {

    private LevelData levelData;

    public LevelLoader() {
    }

    public void loadData(String filename){
    }
}
class LevelData {
    private List<BlockData> blocks;
    private List<CoinData> coins;
    private List<ThiefData> thiefs;

    // Getters and setters
}

class BlockData {
    private double x;
    private double y;
}

class CoinData {
    private double x;
    private double y;

    // Getters and setters
}

class ThiefData {
    private double x;
    private double y;
}
