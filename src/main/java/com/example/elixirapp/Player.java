package com.example.elixirapp;

import java.util.ArrayList;

public class Player extends GameObject{
    private int coins = 0;
    private boolean isDead;
    private int velX;
    private ArrayList<Herb> herbs;

    private boolean right = false;
    private boolean left = false;
    private boolean jumping = false;

    private boolean falling = false;
    private double velY;
    private double gravityAcc;

    private double min, max;
    public Player(double x, double y, int velX) {
        super(x, y);
        this.velX = velX;
        setGravityAcc(0.4);
        jumping = false;
        falling = false;
        this.setImagePath("/masculine-user.png");
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

    public double getGravityAcc() {
        return gravityAcc;
    }

    public void setGravityAcc(double gravityAcc) {
        this.gravityAcc = gravityAcc;
    }

    public void move() {
        velX = 7;
        if (right) {
            if (getX() < max){
                setX(getX()+ velX);
            }else{
                setX(max);
            }
        }
        else if (left){
            if(min < getX()){
                setX(getX()- velX);
            }else{
                setX(min);
            }
        }
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
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


    public void collectCoin(Coin coin){
        this.coins+=coin.getValue();
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void collectHerb(Herb herb){
        this.herbs.add(herb);
    }

    public void jump() {
        if(!isJumping() && !isFalling()){
            setJumping(true);
            setVelY(12);
        }
    }
    @Override
    public void updateLocation() {
        if(jumping && velY <= 0){
            jumping = false;
            falling = true;
        }
        else if(jumping){
            velY = velY - gravityAcc;
            setY(getY()  - velY);
        }
        if(falling){
            setY(getY()  + velY);
            velY = velY + gravityAcc;
        }
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
