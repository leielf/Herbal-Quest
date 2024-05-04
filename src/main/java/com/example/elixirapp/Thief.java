package com.example.elixirapp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Thief extends GameObject{
    private static final int RANGE = 200;
    private int max, min;
    private int speed = 3;
    private int direction = 1;

    public Thief(double x, double y) {
        super(x, y);
        max = (int)x+RANGE;
        min = (int)x;
        this.setImagePath("/thief.png");
    }


    public void updateLocation(boolean intersects, double blockY, int i) {
        if(intersects) {
            setFalling(false);
            setY(blockY - getHeight());
        }
        if(getY() > 600 - getHeight()){
            setFalling(false);
            setY(blockY - getHeight());
        }
        if(isFalling() && !intersects){
            setY(getY()  + getVelY());
        }
    }

    public void moveLeftRight(){
        if(getX() >= min && getX() <= max) {
            setX(getX() + speed*direction);
        }else{
            direction = direction*(-1);
            if(getX() >= max) {
                setX(max);
            }else{
                setX(min);
            }
        }
    }

}
