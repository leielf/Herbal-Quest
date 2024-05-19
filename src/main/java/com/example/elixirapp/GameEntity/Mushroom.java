package com.example.elixirapp.GameEntity;

/**
 * The Mushroom class represents a mushroom in the game world.
 * The player must avoid touching the mushroom, otherwise the game ends.
 * The mushroom automatically falls to the ground.
 */
public class Mushroom extends GameObject {
    public Mushroom(double x, double y) {
        super("/mushroom.png", x, y);
        setFalling(true);
    }
}
