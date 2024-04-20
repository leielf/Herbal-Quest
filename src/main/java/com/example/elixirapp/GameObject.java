package com.example.elixirapp;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

}
