package com.example.elixirapp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.logging.Level;

public class Player extends GameObject{
    private int coins;
    private boolean isDead;
    private int speed;
    private ArrayList<Herb> herbs;

    private boolean right = false;
    private boolean left = false;
    private boolean jumping = false;

    private boolean falling = false;
    private double velX, velY;
    private double gravityAcc;
    public Player(Image img, double x, double y, int speed) {
        super(img, x, y);
        this.speed = speed;
        setVelX(0);
        setVelY(15);
        getImg().setFitWidth(80);
        getImg().setFitHeight(100);
        setGravityAcc(0.4);
        jumping = false;
        falling = true;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public double getGravityAcc() {
        return gravityAcc;
    }

    public void setGravityAcc(double gravityAcc) {
        this.gravityAcc = gravityAcc;
    }

    public void moveLeftRight(int min, double max) {
        if (right) {
            if (getX() < max){
                setX(getX()+speed);
            }else{
                setX(max);
            }
        }
        else if (left && min < getX()){
            setX(getX()-speed);
        }
    }

    public void jump() {
        if(jumping && velY <= 0){
            jumping = false;
            falling = true;
        }
        else if(jumping){
            velY = velY - gravityAcc;
            setY(getY() - velY);
        }
        if(falling){
            setY(getY() + velY);
            velY = velY + gravityAcc;
        }
        if(falling && getY() > 500){
            jumping = false;
            falling = false;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void collideWith(Object obj){
//        getImg().getLayoutBounds().
    }


    public void collectCoin(Coin coin){
        this.coins+=coin.getValue();
    }

    public void collectHerb(Herb herb){
        this.herbs.add(herb);
    }
}
