package com.example.elixirapp;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UIController {
    private Map map;
    public UIController(Map map){
        this.map = map;
    }
    private static final Logger logger = Logger.getLogger(UIController.class.getName());
    static final int SCENE_WIDTH = 1268;
    static final int SCENE_HEIGHT = 708;
    private final ArrayList<ImageView> blocksImgView = new ArrayList<>();
    private final ArrayList<ImageView> coinsImgView = new ArrayList<>();
    private final ArrayList<ImageView> thievesImgView = new ArrayList<>();
    private final ArrayList<ImageView> mushroomsImgView = new ArrayList<>();
    private Text acquiredCoins;
    private ImageView acquiredCoinsImg;
    private ImageView playerImgView = new ImageView();
    private Pane pane;
    private Scene scene;
    private int startingPoint = 0;
    public void createPane() {
        pane = new Pane();
        pane.setMinSize(SCENE_WIDTH*5-5, SCENE_HEIGHT);
        pane.setPrefSize(SCENE_WIDTH*5-5, SCENE_HEIGHT);
        pane.setMaxSize(SCENE_WIDTH*5-5, SCENE_HEIGHT);
        ImageView backgroundView = new ImageView(new Image("/background_long.png"));
        pane.getChildren().add(backgroundView);
    }

    public void moveBackground(boolean isRight, int velX){
        if (isRight) {
            if ((this.playerImgView.getX() - ((Rectangle)this.pane.getClip()).getX()) == this.scene.getWidth()/2-this.playerImgView.getFitWidth()) {
                startingPoint += velX;
            }
        }
    }


    public void addClip(){
        javafx.scene.shape.Rectangle clip = new Rectangle();
        clip.widthProperty().bind(this.scene.widthProperty());
        clip.heightProperty().bind(this.scene.heightProperty());
        clip.xProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(this.playerImgView.getX() - this.scene.getWidth() / 2 +this.playerImgView.getFitWidth(), startingPoint, this.pane.getWidth() - this.scene.getWidth()),
                this.playerImgView.xProperty(), this.scene.widthProperty(), this.pane.widthProperty())
        );
        this.pane.setClip(clip);
        this.pane.translateXProperty().bind(clip.xProperty().multiply(-1));
    }

    private double clampRange(double value, double min, double max) {
        if (value < min) return min ;
        if (value > max) return max ;
        return value ;
    }

    public void addCoinsCounter(){
        acquiredCoins = new Text();
        acquiredCoinsImg = new ImageView(new Image("/coin.png"));
        acquiredCoinsImg.setFitWidth(30);
        acquiredCoinsImg.setFitHeight(30);
        acquiredCoinsImg.setX(startingPoint+1150);
        acquiredCoinsImg.setY(25);
        acquiredCoins.setFont(new Font(30));
        acquiredCoins.setX(acquiredCoinsImg.getX()+acquiredCoinsImg.getFitWidth()+5);
        acquiredCoins.setY(50);
        acquiredCoins.setText(""+0);
        this.pane.getChildren().add(acquiredCoins);
        this.pane.getChildren().add(acquiredCoinsImg);
    }

    public void addObjects(){
        for(Block block: map.getBlocks()){
            addGameObject(block);
        }
        for(Coin coin:map.getCoins()){
            addGameObject(coin);
        }
        for(Thief thief: map.getThieves()){
            addGameObject(thief);
        }
        for(Mushroom mushroom: map.getMushrooms()){
            addGameObject(mushroom);
        }
    }

    public void addGameObject(GameObject obj){
        if (obj !=null && !obj.getImagePath().isEmpty()){
            ImageView view = new ImageView(new Image(obj.getImagePath()));
            view.setFitWidth(obj.getWidth());
            view.setFitHeight(obj.getHeight());
            view.setX(obj.getX());
            view.setY(obj.getY());
            this.pane.getChildren().add(view);
            addToImgArray(obj, view);
        }
    }


    public void addToImgArray(GameObject obj, ImageView view){
        if(obj instanceof Block) {
            this.blocksImgView.add(view);
            logger.log(Level.INFO, "block was added. X: "+obj.getX() + "; Y: " + obj.getY());
        }
        if(obj instanceof Coin) {
            this.coinsImgView.add(view);
            logger.log(Level.INFO, "Coin was added. X: "+obj.getX() + "; Y: " + obj.getY());
        }
        if(obj instanceof Thief) {
            this.thievesImgView.add(view);
            logger.log(Level.INFO, "Thief was added. X: "+obj.getX() + "; Y: " + obj.getY());
        }
        if(obj instanceof Mushroom) {
            this.mushroomsImgView.add(view);
            logger.log(Level.INFO, "Mushroom was added. X: "+obj.getX() + "; Y: " + obj.getY());
        }
        if(obj instanceof Player) {
            this.playerImgView = view;
            logger.log(Level.INFO, "Player was added. X: "+obj.getX() + "; Y: " + obj.getY());
        }
    }
    public void createScene() {
        scene = new Scene(new BorderPane(pane), SCENE_WIDTH, SCENE_HEIGHT);
    }

    public void createMap(Stage stage){
        createPane();
        createScene();
        addObjects();
        addCoinsCounter();
        addGameObject(map.getPlayer());
        addClip();
        scene.setOnKeyPressed(e -> processKey(e.getCode(), true));
        scene.setOnKeyReleased(e -> processKey(e.getCode(), false));
        stage.setScene(scene);
    }

    public void remove(Object o){
        pane.getChildren().remove(o);
    }

    public void updateUI(){
        map.getPlayer().setMin(startingPoint);
        map.getPlayer().setMax(pane.getWidth() - map.getPlayer().getWidth());
        playerImgView.setX(map.getPlayer().getX());
        playerImgView.setY(map.getPlayer().getY());
        moveBackground(map.getPlayer().isRight(), map.getPlayer().getVelX());
        acquiredCoins.setText(""+map.getPlayer().getCoins());
        acquiredCoinsImg.setX(startingPoint+1150);
        acquiredCoins.setX(acquiredCoinsImg.getX()+acquiredCoinsImg.getFitWidth()+10);
        if(!thievesImgView.isEmpty()){
            for(int i = 0; i< map.getThieves().size(); i++){
                thievesImgView.get(i).setX(map.getThieves().get(i).getX());
                thievesImgView.get(i).setY(map.getThieves().get(i).getY());
            }
        }
    }

    public void processKey(KeyCode code, boolean on){
        switch (code){
            case UP:
                map.getPlayer().jump();
                break;
            case LEFT:
                map.getPlayer().setLeft(on);
                break;
            case RIGHT:
                map.getPlayer().setRight(on);
                break;
            default:
                break;
        }
    }

    public ArrayList<ImageView> getCoinsImgView() {
        return coinsImgView;
    }

    public ArrayList<ImageView> getThievesImgView() {
        return thievesImgView;
    }

    public ArrayList<ImageView> getMushroomsImgView() {
        return mushroomsImgView;
    }
}
