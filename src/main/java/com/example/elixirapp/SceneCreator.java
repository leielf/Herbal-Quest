package com.example.elixirapp;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
public class SceneCreator {
    private final Font font = Font.loadFont(getClass().getResourceAsStream("/font.ttf"), 50);
    static final int SCENE_WIDTH = 1260;
    static final int SCENE_HEIGHT = 700;

    private Text acquiredCoins;
    private ImageView acquiredCoinsImg;
    public SceneCreator() {
    }

    public Pane createPane() {
        Pane pane = new Pane();
        pane.setMinSize(SCENE_WIDTH*5-5, SCENE_HEIGHT);
        pane.setPrefSize(SCENE_WIDTH*5-5, SCENE_HEIGHT);
        pane.setMaxSize(SCENE_WIDTH*5-5, SCENE_HEIGHT);
        ImageView backgroundView = new ImageView(new Image("/pixel_background_long_blue.png"));
        pane.getChildren().add(backgroundView);
        return pane;
    }

    /**
     * Setting Clip to the Pane to move the background with the objects
     * as the player moves. Clip's x value is dependent on the player's
     * x value.
     */
    public Rectangle addClip(Scene scene, Pane pane){
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(scene.widthProperty());
        clip.heightProperty().bind(scene.heightProperty());
        pane.setClip(clip);
        pane.translateXProperty().bind(clip.xProperty().multiply(-1));
        return clip;
    }

    public Scene createScene(Pane pane) {
        Scene scene = new Scene(new BorderPane(pane), SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }

    public Pane createPaneWithText(String text) {
        Pane pane  = createPane();
        pane.getChildren().add(addPhrase(text, SCENE_WIDTH/6, SCENE_HEIGHT/2-15));
        return pane;
    }

    public Text addPhrase(String text, double x, double y){
        Text phrase = new Text();
        phrase.setText(text);
        phrase.setFont(font);
        phrase.setFill(Color.BEIGE);
        phrase.setX(x);
        phrase.setY(y);
        return phrase;
    }

    public void addCoinsCounter(Pane pane){
        acquiredCoinsImg = new ImageView(new Image("/coin.png"));
        acquiredCoinsImg.setFitWidth(30);
        acquiredCoinsImg.setFitHeight(30);
        acquiredCoinsImg.setX(SCENE_WIDTH-118);
        acquiredCoinsImg.setY(25);
        acquiredCoins = new Text();
        acquiredCoins.setText("0");
        Font font = Font.loadFont(getClass().getResourceAsStream("/font.ttf"), 30);
        acquiredCoins.setFont(font);
        acquiredCoins.setFill(Color.BEIGE);
        acquiredCoins.setX(acquiredCoinsImg.getX()+acquiredCoinsImg.getFitWidth()+5);
        acquiredCoins.setY(50);
        pane.getChildren().add(acquiredCoins);
        pane.getChildren().add(acquiredCoinsImg);
    }

    public Text getAcquiredCoins() {
        return acquiredCoins;
    }

    public ImageView getAcquiredCoinsImg() {
        return acquiredCoinsImg;
    }
}
