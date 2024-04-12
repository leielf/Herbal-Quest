package com.example.elixirapp;

import javafx.scene.image.ImageView;

public abstract class GameObject {
    private ImageView img;

    public GameObject(ImageView img) {
        this.img = img;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public void setX(double x){
        img.setLayoutX(x);
    }

    public void setY(double y){
        img.setLayoutY(y);
    }

    public double getX(){
        return img.getLayoutX();
    }

    public double getY(){
        return img.getLayoutY();
    }

    public void update(){}

}
