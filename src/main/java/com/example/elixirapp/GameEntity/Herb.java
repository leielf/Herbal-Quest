package com.example.elixirapp.GameEntity;

/**
 * The Herb class represents a collectible herb in the game world.
 * The player can collect herbs, each having a specific name and cost.
 */
public class Herb extends GameObject {
    private final String name;
    private final int cost;

    public Herb(String name, double x, double y, int cost) {
        super("/"+name+".png", x, y);
        this.name = name;
        this.cost = cost;
        setWidthHeight();
    }

    public String getName() {
        return name;
    }
    public int getCost() {
        return cost;
    }
}
