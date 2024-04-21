package com.example.elixirapp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public abstract class GameObject {
    private double x, y;
    private ImageView img;

    public GameObject(Image img, double x, double y) {
        this.img = new ImageView(img);
        this.x = x;
        this.y = y;
        setImgX(x);
        setImgY(y);
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public void setImgX(double x){
        img.setX(x);
    }

    public void setImgY(double y){
        img.setY(y);
    }

    public double getImgX(){
        return img.getX();
    }

    public double getImgY(){
        return img.getY();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        setImgX(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        setImgY(y);
    }

    public void update(){}

    public Rectangle getTopBounds(){
        return new Rectangle((int)(x+img.getFitWidth()/6), (int)y, (int)(2*img.getFitWidth()/3), (int)img.getFitHeight()/2);
    }

    public Rectangle getBottomBounds(){
        return new Rectangle((int)(x+img.getFitWidth()/6), (int)(y + img.getFitHeight()/2), (int)(2*img.getFitWidth()/3), (int)img.getFitHeight()/2);
    }

    public Rectangle getLeftBounds(){
        return new Rectangle((int)x, (int)y + (int)(y + img.getFitHeight()/2), (int)img.getFitWidth()/4, (int)img.getFitHeight()/2);
    }

    public Rectangle getRightBounds(){
        return new Rectangle((int)(x + 3*img.getFitWidth()/4), (int)(y + img.getFitHeight()/4), (int)img.getFitWidth()/4, (int)img.getFitHeight()/2);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, (int)img.getFitWidth(), (int)img.getFitHeight());
    }

}
