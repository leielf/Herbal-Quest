package com.example.elixirapp;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.logging.Logger;


public class ElixirApplication extends Application {
    private Player player;

    private static final Logger logger = Logger.getLogger(ElixirApplication.class.getName());

    private int starting_point = 0;
    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        pane.setMinSize(6335, 708);
        pane.setPrefSize(6335, 708);
        pane.setMaxSize(6335, 708);

        Image img = new Image("/background_long.png");
        ImageView backgroundView = new ImageView(img);
        pane.getChildren().add(backgroundView);

        Scene scene = new Scene(new BorderPane(pane), 1268, 708);
        javafx.scene.shape.Rectangle clip = new Rectangle();
        clip.widthProperty().bind(scene.widthProperty());
        clip.heightProperty().bind(scene.heightProperty());

        LevelManager lvlManager = new LevelManager("/Users/leielf/Downloads/second_json.json");
        lvlManager.addToLevel(pane);

        Image playerImg = new Image("/masculine-user.png");
        player = new Player(playerImg, 1268/2-80, 500, 10);
        pane.getChildren().add(player.getImg());

        clip.xProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(player.getImg().getX() - scene.getWidth() / 2 +80, starting_point, pane.getWidth() - scene.getWidth()),
                player.getImg().xProperty(), scene.widthProperty(), pane.widthProperty())
        );
        pane.setClip(clip);
        pane.translateXProperty().bind(clip.xProperty().multiply(-1));

        scene.setOnKeyPressed(e -> processKey(e.getCode(), true));
        scene.setOnKeyReleased(e -> processKey(e.getCode(), false));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (player.isRight()) {
                    if ((player.getX() - clip.getX()) == scene.getWidth()/2-80){
                        starting_point += player.getSpeed();
                    }
                }
                player.moveLeftRight(starting_point, pane.getWidth() - player.getImg().getFitWidth());
                player.jump();
//                logger.log(Level.INFO,"player's X: " + player.getX()+", starting point = " + starting_point);
            }
        };

        stage.setScene(scene);
        stage.show();
        timer.start();
    }

    private double clampRange(double value, double min, double max) {
        if (value < min) return min ;
        if (value > max) return max ;
        return value ;
    }

    private void processKey(KeyCode code, boolean on) {
        switch (code) {
            case LEFT:
                player.setLeft(on) ;
                break ;
            case RIGHT:
                player.setRight(on);
                break ;
            case UP:
                player.setJumping(on);
                player.setFalling(!on);
                break ;
            default:
                break ;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}