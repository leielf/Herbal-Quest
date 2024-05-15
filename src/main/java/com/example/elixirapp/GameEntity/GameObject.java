package com.example.elixirapp.GameEntity;

import java.awt.*;
import javafx.scene.image.Image;

public abstract class GameObject {
    private double x, y;

    private double width, height;
    private boolean falling;
    private double velY;
    private double velX;
    private double gravityAcc = 0.3;
    private String imagePath = "";
    public GameObject(String imagePath, double x, double y) {
        this.x = x;
        this.y = y;
        this.imagePath = imagePath;
        this.falling = false;
        setWidthHeight();
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

    /**
     * calculating top bounds of the object to check possible collisions
     * with other game objects.
     * @return an instance of the Rectangle class (to use method intersects
     * later on)
     */
    public Rectangle getTopBounds(){
        return new Rectangle((int)(x+width/6), (int)y, (int)(2*width/3), (int)height/2);
    }

    /**
     * calculating bottom bounds of the object to check possible collisions
     * with other game objects.
     * @return an instance of the Rectangle class created basing on the width,
     * height, x and y values of this object
     */
    public Rectangle getBottomBounds(){
        return new Rectangle((int)(x+width/6), (int)(y + height/2), (int)(2*width/3), (int)height/2);
    }

    /**
     * calculating left bounds of the object to check possible collisions
     * with other game objects.
     * @return an instance of the Rectangle class created basing on the width,
     * height, x and y values of this object
     */
    public Rectangle getLeftBounds(){
        return new Rectangle((int)x+5, (int)y + (int)(y + height/2), (int)width/4, (int)height/2);
    }

    /**
     * calculating right bounds of the object to check possible collisions
     * with other game objects.
     * @return an instance of the Rectangle class created basing on the width,
     * height, x and y values of this object
     */
    public Rectangle getRightBounds(){
        return new Rectangle((int)(x + 3*width/4), (int)(y + height/4), (int)width/4-5, (int)height/2);
    }

    /**
     * calculating the smallest rectangle in which the object fits to check
     * possible collisions with other game objects.
     * @return an instance of the Rectangle class created basing on the width,
     * height, x and y values of this object
     */
    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }

    /**
     * updating Y value of the object basing on the value
     * of the boolean variable falling.
     */
    public void updateLocation() {
        if(falling){
            setY(getY()  + velY);
            velY = velY + gravityAcc;
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

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setWidthHeight(){
        Image img = new Image(imagePath);
        height = img.getHeight();
        width = img.getWidth();
    }
}
