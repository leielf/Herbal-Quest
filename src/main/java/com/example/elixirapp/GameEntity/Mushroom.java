package com.example.elixirapp.GameEntity;

import com.example.elixirapp.GameEntity.GameObject;

public class Mushroom extends GameObject {
    public Mushroom(double x, double y) {
        super("/pixel_mushroom.png", x, y);
        setFalling(true);
    }
}
