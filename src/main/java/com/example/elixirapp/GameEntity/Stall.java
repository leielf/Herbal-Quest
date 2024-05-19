package com.example.elixirapp.GameEntity;

/**
 * The Stall class represents a stall in the game world where the player can purchase missing herbs.
 * The player can interact with the stall if they do not have enough collected herbs.
 */
public class Stall extends GameObject{
    private boolean isEntered;
    private boolean isBought;

    private boolean isExited;
    public Stall(double x, double y) {
        super("/stall.png", x, y);
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
