package com.example.elixirapp.GameEntity;

/**
 * The Thief class represents a thief character in the game.
 * The thief moves back and forth within a specified range and can interact with the player.
 */
public class Thief extends GameObject {
    private static final int RANGE = 200;
    boolean right;
    private final int max, min;
    private final String imgRight, imgLeft;
    private int direction = 1;

    public Thief(double x, double y) {
        super("/thief1.png", x, y);
        imgRight = "/thief1.png";
        imgLeft = "/thief2.png";
        max = (int)x+RANGE;
        min = (int)x;
        setVelX(3);
    }

    public void moveLeftRight(){
        right = direction == 1;
        if(getX() >= min && getX() <= max) {
            setX(getX() + getVelX()*direction);
        }else{
            direction = direction*(-1);
            if(getX() >= max) {
                setX(max);
            }else{
                setX(min);
            }
        }
    }

    public boolean isRight() {
        return right;
    }

    public String getImgRight() {
        return imgRight;
    }

    public String getImgLeft() {
        return imgLeft;
    }
}
