package com.example.elixirapp;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;

import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class ElixirController {
    @FXML
    private ImageView img_player, background, img_mushroom1, img_mushroom2;
    private static Mushroom mushroom1, mushroom2;
    public static Player player;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            player.moveLeftRight();
            player.jump();
            player.jumpUpdate();
            if(player.isRight() && Math.abs(background.getLayoutX()-player.getSpeed())<5068){
                background.setLayoutX(background.getLayoutX()-player.getSpeed());
            }
        }
    };

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    void initialize() {
        player = new Player(img_player, 8);
        mushroom1 = new Mushroom(img_mushroom1, 5);
        mushroom2 = new Mushroom(img_mushroom2, 5);
        timer.start();

    }
}