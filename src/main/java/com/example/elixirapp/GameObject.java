package com.example.elixirapp;

import java.awt.*;

public abstract class GameObject {
    private double x, y;

    private double width, height;
    private boolean falling;
    private double velY;

    private String imagePath = "";

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.velY = 7;
        this.falling = true;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Rectangle getTopBounds(){
        return new Rectangle((int)(x+width/6), (int)y, (int)(2*width/3), (int)height/2);
    }

    public Rectangle getBottomBounds(){
        return new Rectangle((int)(x+width/6), (int)(y + height/2), (int)(2*width/3), (int)height/2);
    }

    public Rectangle getLeftBounds(){
        return new Rectangle((int)x, (int)y + (int)(y + height/2), (int)width/4, (int)height/2);
    }

    public Rectangle getRightBounds(){
        return new Rectangle((int)(x + 3*width/4), (int)(y + height/4), (int)width/4, (int)height/2);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }


    public void updateLocation() {
        if(falling && getY()<600-height-1){
            setY(getY()  + velY);
        }else{
            setY(600 - height);
        }
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }


    public double getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
