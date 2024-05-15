package com.example.elixirapp.GameEntity;

public class Stall extends GameObject{


    private boolean isEntered;
    private final int herbCost = 2;
    private boolean isBought;

    private boolean isExited;
    public Stall(double x, double y) {
        super("/pixel_stall.png", x, y);
        isEntered = false;
        isExited = false;
    }

    public boolean isEntered() {
        return isEntered;
    }

    public void setEntered(boolean entered) {
        isEntered = entered;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public int getHerbCost() {
        return herbCost;
    }

    public boolean isExited() {
        return isExited;
    }

    public void setExited(boolean exited) {
        isExited = exited;
    }
}
