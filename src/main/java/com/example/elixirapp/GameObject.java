package com.example.elixirapp;

import javafx.scene.image.ImageView;

public abstract class GameObject {
    private double x, y;
    private ImageView img;

    public GameObject(ImageView img, double x, double y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public void setImgX(double x){
        img.setLayoutX(x);
    }

    public void setImgY(double y){
        img.setLayoutY(y);
    }

    public double getImgX(){
        return img.getLayoutX();
    }

    public double getImgY(){
        return img.getLayoutY();
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

    public void update(){}

}
