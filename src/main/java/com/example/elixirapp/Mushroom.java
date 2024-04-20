package com.example.elixirapp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Mushroom extends GameObject{
    private boolean isSteppedOn;


    public Mushroom(Image img, double x, double y) {
        super(img, x, y);
    }
}
