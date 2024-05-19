package com.example.elixirapp.GameEntity;

/**
 * The Block class represents a static block in the game world, which can be used as a platform by the player and other game objects.
 */
public class Block extends GameObject {

    public Block(double x, double y) {
        super("/block.png", x, y);
    }
}
