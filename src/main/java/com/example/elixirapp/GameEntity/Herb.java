package com.example.elixirapp.GameEntity;

import com.example.elixirapp.GameEntity.GameObject;

public class Herb extends GameObject {
    private String name;
    private boolean isCollected;

    public Herb(String name, double x, double y) {
        super("/red_circle.png", x, y);
        this.name = name;
        isCollected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
