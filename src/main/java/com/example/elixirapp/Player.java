package com.example.elixirapp;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Player extends GameObject{

    private int JUMP_HEIGHT = 20;
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

    public void moveLeftRight() {
        if (right && getX() + speed + getImg().getFitWidth() < 1268)
            setX(getX() + speed);
        if (left && getX() - speed > 0)
            setX(getX() - speed);
    }

//    public void jumpUpdate() {
//        if(jumping && velY < 0){
//            jumping = false;
//            falling = true;
//        }
//        else if(jumping){
//            velY = velY - gravityAcc;
//            setY(getY() - velY);
//        }
//
////        if(falling){
////            setY(getY() + velY);
////            velY = velY + gravityAcc;
////        }
//    }

    public void jump() {
        if(!isJumping() && !isFalling()){
            setJumping(true);
            velY = 10;
        }
    }
//    public void jump() {
//        if (jumping && getY() >= this.initialY - getImg().getFitHeight()){
//            setY(getY()-JUMP_HEIGHT);
////            up = false;
//        }else{
////            up = false;
//            if (getY()<this.initialY){
//                setY(this.getY()+JUMP_HEIGHT);
//            }
//        }
//    }

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
    public Player(ImageView img, int speed) {
        super(img);
        this.speed = speed;
        setVelX(0);
        setVelY(0);
        setGravityAcc(0.38);
        jumping = false;
        falling = true;
    }

    public void collideWith(Object obj){

    }


    public void collectCoin(Coin coin){
        this.coins+=coin.getValue();
    }

    public void collectHerb(Herb herb){
        this.herbs.add(herb);
    }



}
