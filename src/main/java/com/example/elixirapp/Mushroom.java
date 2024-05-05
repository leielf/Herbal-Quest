package com.example.elixirapp;

public class Mushroom extends GameObject{
    public Mushroom(double x, double y) {
        super(x, y);
        setImagePath("/mushroom.png");
        setFalling(true);
    }
}
