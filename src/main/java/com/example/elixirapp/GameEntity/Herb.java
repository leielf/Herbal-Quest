package com.example.elixirapp.GameEntity;

import com.example.elixirapp.GameEntity.GameObject;

public class Herb extends GameObject {
    private String name;

    private int cost;
    private boolean isCollected;

    public Herb(String name, double x, double y, int cost) {
        super("/"+name+".png", x, y);
        this.name = name;
        this.cost = cost;
        isCollected = false;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
