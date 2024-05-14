package com.example.elixirapp;

public class Herb extends GameObject{
    private String name;
    private boolean isCollected;

    public Herb(String name, double x, double y) {
        super(x, y);
        this.name = name;
        isCollected = false;
        this.setImagePath("/red_circle.png");
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
