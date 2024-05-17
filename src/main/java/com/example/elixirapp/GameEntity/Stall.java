package com.example.elixirapp.GameEntity;

public class Stall extends GameObject{


    private boolean isEntered;
    private boolean isBought;

    private boolean isExited;
    public Stall(double x, double y) {
        super("/pixel_stall.png", x, y);
        isEntered = false;
        isExited = false;
        isBought = false;
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

    public boolean isExited() {
        return isExited;
    }

    public void setExited(boolean exited) {
        isExited = exited;
    }
}
