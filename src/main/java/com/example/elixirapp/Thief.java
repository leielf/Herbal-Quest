package com.example.elixirapp;


public class Thief extends GameObject{
    private static final int RANGE = 200;
    private int max, min;
    private int direction = 1;

    public Thief(double x, double y) {
        super(x, y);
        max = (int)x+RANGE;
        min = (int)x;
        setVelX(3);
        this.setImagePath("/thief.png");
    }

    public void moveLeftRight(){
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

}
