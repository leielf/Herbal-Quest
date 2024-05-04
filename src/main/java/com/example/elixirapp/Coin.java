package com.example.elixirapp;

public class Coin extends GameObject{
    private int value;
    private boolean collected;

    public Coin(int value, double x, double y) {
        super(x, y);
        this.value = value;
        this.setImagePath("/coin.png");
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
