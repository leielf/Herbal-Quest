package com.example.elixirapp.GameEntity;

/**
 * The Coin class represents a collectible coin in the game world.
 * The player can collect coins and spend them on buying herbs.
 */
public class Coin extends GameObject {
    private final int value;

    public Coin(int value, double x, double y) {
        super("/coin.png", x, y);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
