package com.example.elixirapp.GameEntity;

import com.example.elixirapp.GameEntity.Coin;
import com.example.elixirapp.GameEntity.GameObject;
import com.example.elixirapp.GameEntity.Herb;

import java.util.ArrayList;

public class Player extends GameObject {
    private int coins = 0;
    private boolean isDead;
    private ArrayList<Herb> herbs;

    private String right1, right2, left1, left2;

    private boolean right = false;
    private boolean left = false;
    private boolean jumping;

    private boolean falling;
    private double velY;
    private double gravityAcc;

    private double min, max;
    public Player(double x, double y, int velX) {
        super("/player_right1.png", x, y);
        right1 = "/player_right1.png";
        right2 = "/player_right2.png";
        left1 = "/player_left1.png";
        left2 = "/player_left2.png";
        setVelX(velX);
        setGravityAcc(0.4);
        jumping = false;
        falling = false;
        herbs = new ArrayList<>();
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
        if (right) {
            if (getX() < max){
                setX(getX()+ getVelX());
            }else{
                setX(max);
            }
        }
        else if (left){
            if(min < getX()){
                setX(getX()- getVelX());
            }else{
                setX(min);
            }
        }
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

    public ArrayList<Herb> getHerbs() {
        return herbs;
    }


    public String getRight1() {
        return right1;
    }

    public String getRight2() {
        return right2;
    }

    public String getLeft1() {
        return left1;
    }

    public String getLeft2() {
        return left2;
    }
}
