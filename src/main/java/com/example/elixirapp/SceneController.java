package com.example.elixirapp;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connects logic and UI together
 * Draws the level itself based on the map it receives in constructor
 * {@link com.example.elixirapp.Map}
 * @author Leila Babayeva
 * Pane contains instances of ImageView, each ImageView is in acordance
 * to the objects of the map (have the same order in arraylists as in the
 * arraylists (blocks, thieves, mushrooms, coins) of the map)
 *
 */

public class SceneController {

    private SceneCreator sceneCreator;
    private Map map;
    private boolean loadMap = false;
    private final GameEngine gameEngine;
    //lowest point of the scene
    static final double BORDER = 601;
    private static final Logger logger = Logger.getLogger(SceneController.class.getName());
    static final int SCENE_WIDTH = 1268;
    private final ArrayList<ImageView> blocksImgView = new ArrayList<>();
    private final ArrayList<ImageView> coinsImgView = new ArrayList<>();
    private final ArrayList<ImageView> thievesImgView = new ArrayList<>();
    private final ArrayList<ImageView> mushroomsImgView = new ArrayList<>();
    private final ArrayList<ImageView> herbsImgView = new ArrayList<>();
    javafx.scene.shape.Rectangle clip;
    private ImageView playerImgView;
    private Pane pane;
    private Scene scene;

    public SceneController(Map map, GameEngine gameEngine){
        this.map = map;
        this.gameEngine = gameEngine;
        sceneCreator = new SceneCreator();
    }

    public void moveBackground(boolean isRight, int velX){
        if (isRight) {
            if ((this.playerImgView.getX() - ((Rectangle)this.pane.getClip()).getX()) > this.scene.getWidth()/2-this.playerImgView.getFitWidth()) {
                clip.setX(clip.getX()+velX);
            }
        }
    }

    /**
     * Adding coins counter to the Pane for the player to know
     * how many coins the player collected.
     */

    public void addObjects(){
        if(!coinsImgView.isEmpty()){
            this.pane.getChildren().addAll(coinsImgView);
        }
        if(!blocksImgView.isEmpty()){
            this.pane.getChildren().addAll(blocksImgView);
        }
        if(!thievesImgView.isEmpty()){
            this.pane.getChildren().addAll(thievesImgView);
        }
        if(!mushroomsImgView.isEmpty()){
            this.pane.getChildren().addAll(mushroomsImgView);
        }
        if(playerImgView != null){
            this.pane.getChildren().add(playerImgView);
        }
        if(!herbsImgView.isEmpty()){
            this.pane.getChildren().addAll(herbsImgView);
        }
    }

    public void fillImgArrays(){
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
        for(Herb herb: map.getHerbs()){
            addGameObject(herb);
        }
    }

    /**
     * adding one object to the pane
     * @param obj object that is supposed to be added to the pane
     */
    public void addGameObject(GameObject obj){
        if (obj !=null && !obj.getImagePath().isEmpty()){
            ImageView view = new ImageView(new Image(obj.getImagePath()));
            view.setFitWidth(obj.getWidth());
            view.setFitHeight(obj.getHeight());
            view.setX(obj.getX());
            view.setY(obj.getY());
            addToImgArray(obj, view);
        }
    }

    /**
     * Adding game objects to the UIController's arraylists with ImageViews
     * to be able to update their positions later on in the code
     * @param obj object that is instance of one of the subclasses of the
     *            class GameObject {@link com.example.elixirapp.GameObject}
     * @param view respective ImageView to the obj
     */
    public void addToImgArray(GameObject obj, ImageView view){
        if(obj instanceof Block) {
            this.blocksImgView.add(view);
            logger.log(Level.INFO, "block was added. X: "+obj.getX() + "; Y: " + obj.getY());
        }
        if(obj instanceof Coin) {
            this.coinsImgView.add(view);
            logger.log(Level.INFO, "Coin was added. X: "+obj.getX() + "; Y: " + obj.getY() + ", array size: " + coinsImgView.size());
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
        if(obj instanceof Herb) {
            this.herbsImgView.add(view);
            logger.log(Level.INFO, "Herb was added. X: "+obj.getX() + "; Y: " + obj.getY());
        }
    }

    public void switchScreen(Stage stage){
        switch (gameEngine.getGameStatus()){
            case GameStatus.START_SCREEN:
                scene = sceneCreator.createScene(sceneCreator.createPaneWithText("PRESS SPACE TO START"));
                scene.setOnKeyPressed(e -> processKey(e.getCode(), true));
                scene.setOnKeyReleased(e -> processKey(e.getCode(), false));
                stage.setScene(scene);
                stage.show();
                break;
            case GameStatus.FAIL:
                clearImgArrays();
                scene.setRoot(sceneCreator.createPaneWithText("FAIL. PRESS SPACE TO TRY AGAIN"));
                stage.setScene(scene);
                stage.show();
                break;
            case GameStatus.WIN:
                clearImgArrays();
                scene.setRoot(sceneCreator.createPaneWithText("WIN"));
                stage.setScene(scene);
                stage.show();
                break;
            default:
                break;
        }
    }

    public void createMap(Stage stage){
        if(loadMap){
            fillImgArrays();
            map.getPlayer().setDead(false);
            pane = sceneCreator.createPane();
            sceneCreator.addCoinsCounter(pane);
            scene.setRoot(pane);
            scene.setOnKeyPressed(e -> processKey(e.getCode(), true));
            scene.setOnKeyReleased(e -> processKey(e.getCode(), false));
            clip = sceneCreator.addClip(scene, pane);
            addGameObject(map.getPlayer());
            map.getPlayer().setMax(pane.getMaxWidth()-map.getPlayer().getWidth());
            addObjects();
            stage.setScene(scene);
            stage.show();
            gameEngine.setGameStatus(GameStatus.RUNNING);
            loadMap = false;
        }
    }

    public void remove(Object o){
        pane.getChildren().remove(o);
    }

    /**
     * Updating the positions of the objects in the Pane, shifting
     * the background according to the values of the objects of the map
     */
    public void updateUI(){
        try {
            map.getPlayer().setMin(clip.getX());
            playerImgView.setX(map.getPlayer().getX());
            playerImgView.setY(map.getPlayer().getY());
            moveBackground(map.getPlayer().isRight(), (int)map.getPlayer().getVelX());
            sceneCreator.getAcquiredCoins().setText(""+map.getPlayer().getCoins());
            sceneCreator.getAcquiredCoinsImg().setX(clip.getX()+1150);
            sceneCreator.getAcquiredCoins().setX(sceneCreator.getAcquiredCoinsImg().getX()+
                    sceneCreator.getAcquiredCoinsImg().getFitWidth()+10);
            if(!thievesImgView.isEmpty()){
                for(int i = 0; i< map.getThieves().size(); i++){
                    thievesImgView.get(i).setX(map.getThieves().get(i).getX());
                    thievesImgView.get(i).setY(map.getThieves().get(i).getY());
                }
            }
            if(!mushroomsImgView.isEmpty()){
                for(int i = 0; i<map.getMushrooms().size(); i++){
                    mushroomsImgView.get(i).setY(map.getMushrooms().get(i).getY());

                }
            }
        }catch (NullPointerException e){
            logger.log(Level.INFO, "NULL POINTER EXCEPTION in UIController");

        }
    }

    public void processKey(KeyCode code, boolean on){
        if(gameEngine.getGameStatus() == GameStatus.RUNNING){
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
        }else if (gameEngine.getGameStatus() == GameStatus.START_SCREEN ||
                gameEngine.getGameStatus() == GameStatus.FAIL){
            if(code == KeyCode.SPACE){
                loadMap = true;
                logger.log(Level.INFO, "SPACE was pressed, loadMap = " + loadMap);
            }
        }
    }

    public ArrayList<ImageView> getCoinsImgView() {
        return coinsImgView;
    }

    public void setMap(Map map){
        this.map = map;
    }

    public void clearImgArrays(){
        if(!coinsImgView.isEmpty()) coinsImgView.clear();
        if(!thievesImgView.isEmpty()) thievesImgView.clear();
        if(!mushroomsImgView.isEmpty()) mushroomsImgView.clear();
        if(!blocksImgView.isEmpty()) blocksImgView.clear();
        if(!herbsImgView.isEmpty()) herbsImgView.clear();
    }

    public boolean isLoadMap() {
        return loadMap;
    }

    public ArrayList<ImageView> getHerbsImgView() {
        return herbsImgView;
    }
}
