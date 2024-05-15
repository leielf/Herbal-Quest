package com.example.elixirapp.GameEntity;


import com.example.elixirapp.GameEntity.GameObject;

public class Thief extends GameObject {
    private static final int RANGE = 200;

    boolean right;
    private int max, min;

    private String imgRight, imgLeft;
    private int direction = 1;

    public Thief(double x, double y) {
        super("/pixel_thief1.png", x, y);
        imgRight = "/pixel_thief1.png";
        imgLeft = "/pixel_thief2.png";
        max = (int)x+RANGE;
        min = (int)x;
        setVelX(3);
    }

    public void moveLeftRight(){
        if(direction == 1){
            right = true;
        }else{
            right = false;
        }
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
