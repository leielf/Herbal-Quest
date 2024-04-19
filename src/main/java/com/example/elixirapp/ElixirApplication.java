package com.example.elixirapp;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.round;


public class ElixirApplication extends Application {

    private static final Logger logger = Logger.getLogger(ElixirApplication.class.getName());

    private final int speed = 400 ; // pixels / second
    private boolean up ;
    private boolean down ;
    private boolean left ;
    private int starting_point = 0 ;
//    private boolean right = false;
    private BooleanProperty right = new SimpleBooleanProperty(false);
    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        pane.setMinSize(6335, 708);
        pane.setPrefSize(6335, 708);
        pane.setMaxSize(6335, 708);
        javafx.scene.shape.Rectangle player = new javafx.scene.shape.Rectangle(1268/2, 708/2, 40, 40);
        Image img = new Image("/background_long.png");
        ImageView backgroundView = new ImageView(img);
        pane.getChildren().add(backgroundView);
        player.setFill(Color.BLUE);
        pane.getChildren().add(player);

        Scene scene = new Scene(new BorderPane(pane), 1268, 708);
        javafx.scene.shape.Rectangle clip = new Rectangle();
        clip.widthProperty().bind(scene.widthProperty());
        clip.heightProperty().bind(scene.heightProperty());

        clip.xProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(player.getX() - scene.getWidth() / 2, starting_point, pane.getWidth() - scene.getWidth()),
                player.xProperty(), scene.widthProperty(), pane.widthProperty())
        );
        pane.setClip(clip);
        System.out.println("player.getX() - scene.getWidth() / 2 = " + (player.getX() - scene.getWidth() / 2));
//        pane.translateXProperty().bind(Bindings.when(right).then(clip.xProperty().multiply(-1)).otherwise(0));
        pane.translateXProperty().bind(clip.xProperty().multiply(-1));

        scene.setOnKeyPressed(e -> processKey(e.getCode(), true));
        scene.setOnKeyReleased(e -> processKey(e.getCode(), false));
        final double[] diffX = {0};

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = -1 ;
            @Override
            public void handle(long now) {
                long elapsedNanos = now - lastUpdate;
                if (lastUpdate < 0) {
                    lastUpdate = now ;
                    return ;
                }
                double elapsedSeconds = elapsedNanos / 1_000_000_000.0 ;
                double deltaX = 0;
                if (right.get()) {
                    deltaX += speed ;
//                    starting_point += speed;
                    if ((player.getX() - clip.getX()) == scene.getWidth()/2){
                        starting_point += deltaX * elapsedSeconds;
                    }

                }
                if (left) deltaX -= speed ;
//                if (up) deltaY -= speed ;
//                logger.log(Level.INFO,"player's X before: " + player.getX());
                player.setX(clampRange(player.getX() + deltaX * elapsedSeconds, starting_point, pane.getWidth() - player.getWidth()));
                logger.log(Level.INFO,"player's X: " + player.getX()+", starting point = " + starting_point);
                logger.log(Level.INFO, "clip X: " + clip.getX());
                lastUpdate = now ;
                if (right.get()){
                    diffX[0] = player.getX() - deltaX * elapsedSeconds;
                }
            }
        };

        stage.setScene(scene);
        stage.show();

        timer.start();

//        Group root = new Group();
//        Image img = new Image("/background_long.png");
//        ImageView backgroundView = new ImageView(img);
//        root.getChildren().add(backgroundView);
//        LevelManager lvlManager = new LevelManager("/Users/leielf/Downloads/first_json.json");
//        lvlManager.addToLevel(root);
//        Scene scene2 = new Scene(root, 1268, 708);
//        stage.setScene(scene2);
//        stage.show();
//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long l) {
//                scene2.setOnKeyPressed(e -> {
//                    if(e.getCode() == KeyCode.RIGHT){
//                        backgroundView.setLayoutX(backgroundView.getLayoutX()-15);
//                    }
//
//                });
//            }
//        };
//        timer.start();


//        LevelLoader lvlLoader = new LevelLoader();
//        lvlLoader.loadLevelData("/Users/leielf/Downloads/first_json.json");

//        scene2.setOnKeyPressed(e -> {
//            if(e.getCode() == KeyCode.RIGHT){
////                ElixirController.player.setRight(true);
//                backgroundView.setLayoutX(backgroundView.getLayoutX()-15);
//            }
//
//        });
//        scene.setOnKeyPressed(e -> {
//            if(e.getCode() == KeyCode.UP && !ElixirController.player.isJumping()){
//                ElixirController.player.setJumping(true);
//            }
//            if(e.getCode() == KeyCode.LEFT){
//                ElixirController.player.setLeft(true);
//            }
//            if(e.getCode() == KeyCode.RIGHT){
//                ElixirController.player.setRight(true);
//            }
//        });
//        scene.setOnKeyReleased(e -> {
//            if(e.getCode() == KeyCode.SPACE){
//                ElixirController.player.setJumping(false);
//            }
//            if(e.getCode() == KeyCode.LEFT){
//                ElixirController.player.setLeft(false);
//            }
//            if(e.getCode() == KeyCode.RIGHT){
//                ElixirController.player.setRight(false);
//            }
//        });
    }

    private double clampRange(double value, double min, double max) {
        if (value < min) return min ;
        if (value > max) return max ;
        return value ;
    }

    private void processKey(KeyCode code, boolean on) {
        switch (code) {
            case LEFT:
                left = on ;
                break ;
            case RIGHT:
                right.set(on);
                break ;
//            case UP:
//                up = on ;
//                break ;
            default:
                break ;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}