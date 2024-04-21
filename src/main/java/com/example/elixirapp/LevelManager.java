package com.example.elixirapp;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
public class LevelManager {

    private static final Logger logger = Logger.getLogger(LevelManager.class.getName());

    private static final int SCENE_WIDTH = 1268;
    private static final int SCENE_HEIGHT = 708;

    private Player player;
    private ArrayList<Block> blocks;
    private ArrayList<Coin> coins;
    private ArrayList<Thief> thieves;

    private int startingPoint = 0;
    private Pane pane;

    private Scene scene;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            checkCollisions();
            if (player.isRight()) {
                if ((player.getX() - ((Rectangle)pane.getClip()).getX()) == scene.getWidth()/2-80){
                    startingPoint += player.getVelX();
                }
            }
            player.moveLeftRight(startingPoint, pane.getWidth() - player.getImg().getFitWidth());
            player.jump();
//                logger.log(Level.INFO,"player's X: " + player.getX()+", starting point = " + starting_point);
        }
    };
    public LevelManager() {
    }

    public void addPlayer(){
        Image playerImg = new Image("/masculine-user.png");
        player = new Player(playerImg, SCENE_WIDTH/2, 500, 10);
        player.setX(player.getX()-player.getImg().getFitWidth());
        pane.getChildren().add(player.getImg());
    }

    public void addClip(){
        javafx.scene.shape.Rectangle clip = new Rectangle();
        clip.widthProperty().bind(scene.widthProperty());
        clip.heightProperty().bind(scene.heightProperty());

        clip.xProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(player.getImg().getX() - scene.getWidth() / 2 +80, startingPoint, pane.getWidth() - scene.getWidth()),
                player.getImg().xProperty(), scene.widthProperty(), pane.widthProperty())
        );
        pane.setClip(clip);
        pane.translateXProperty().bind(clip.xProperty().multiply(-1));
    }

    public void addObjects(String filePath){
        LevelLoader lvlLoader = new LevelLoader();
        lvlLoader.addToLevel(pane, filePath);
        this.blocks = lvlLoader.getBlocks();
        this.coins = lvlLoader.getCoins();
        this.thieves = lvlLoader.getThieves();
        addPlayer();
    }

    public void createLevel(String filePath){
        pane = new Pane();
        pane.setMinSize(SCENE_WIDTH*5-5, SCENE_HEIGHT);
        pane.setPrefSize(SCENE_WIDTH*5-5, SCENE_HEIGHT);
        pane.setMaxSize(SCENE_WIDTH*5-5, SCENE_HEIGHT);

        Image img = new Image("/background_long.png");
        ImageView backgroundView = new ImageView(img);
        pane.getChildren().add(backgroundView);

        scene = new Scene(new BorderPane(pane), SCENE_WIDTH, SCENE_HEIGHT);
        scene.setOnKeyPressed(e -> processKey(e.getCode(), true));
        scene.setOnKeyReleased(e -> processKey(e.getCode(), false));
        addObjects(filePath);
        addClip();
    }

    public void startStage(Stage stage, String filePath){
        createLevel(filePath);
        stage.setScene(scene);
        timer.start();
        stage.show();
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

    public void checkCollisions(){
        ArrayList<Coin> toBeRemoved = new ArrayList<>();
        for (Coin coin: coins){
            if(coin.getBounds().intersects(player.getBounds())){
                pane.getChildren().remove(coin.getImg());
                logger.log(Level.INFO, "Coin was collected");
                toBeRemoved.add(coin);
//                player.collectCoin(coin);
//                logger.log(Level.INFO, "Player currently has " + player.getCoins());
            }
        }
        for (Coin c: toBeRemoved){
            player.collectCoin(c);
            logger.log(Level.INFO, "Player currently has " + player.getCoins());
        }
    }
}
