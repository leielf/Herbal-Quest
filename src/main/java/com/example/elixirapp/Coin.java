package com.example.elixirapp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Coin extends GameObject{
    private int value;
    private boolean collected;

    public Coin(Image img, int value, double x, double y) {
        super(img, x, y);
        this.value = value;
    }

    public void onTouch(Player p){

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
