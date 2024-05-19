package com.example.elixirapp;
import com.example.elixirapp.GameEntity.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The SceneController class connects the logic and UI together, drawing the level itself
 * based on the map it receives in the constructor.
 * It handles adding game objects to the scene, updating the UI, processing user input,
 * and managing the game state transitions.
 * <p>
 * Pane contains instances of ImageView, each ImageView corresponds to the objects
 * of the map and maintains the same order as in the map's arraylists (blocks, thieves, mushrooms, coins).
 * </p>
 *
 * {@link com.example.elixirapp.Map}
 * {@link com.example.elixirapp.SceneCreator}
 *
 * Author: Leila Babayeva
 */
public class SceneController {

    private final SceneCreator sceneCreator = new SceneCreator();
    private Map map;
    private boolean loadMap = false;
    private final GameEngine gameEngine;
    private static final Logger logger = Logger.getLogger(SceneController.class.getName());
    private final ArrayList<ImageView> blocksImgView = new ArrayList<>();
    private final ArrayList<ImageView> coinsImgView = new ArrayList<>();
    private final ArrayList<ImageView> thievesImgView = new ArrayList<>();
    private final ArrayList<ImageView> mushroomsImgView = new ArrayList<>();
    private final ArrayList<ImageView> herbsImgView = new ArrayList<>();
    javafx.scene.shape.Rectangle clip = new Rectangle();
    private ImageView playerImgView = new ImageView();
    private ImageView stallView = new ImageView();
    private Pane pane = new Pane();
    private Scene scene = new Scene(pane);

    private int imgCounter = 0;
    private int imgNum = 1;

    public SceneController(Map map, GameEngine gameEngine){
        this.map = map;
        this.gameEngine = gameEngine;
    }

    /**
     * Moves the background based on the player's movement direction and velocity.
     *
     * @param isRight true if the player is moving right, false otherwise
     * @param velX the horizontal velocity of the player
     */
    public void moveBackground(boolean isRight, int velX){
        if (isRight) {
            if(clip.getX() <= pane.getMaxWidth()*2/3){
                if ((this.playerImgView.getX() - ((Rectangle)this.pane.getClip()).getX()) > this.scene.getWidth()/2-this.playerImgView.getFitWidth()) {
                    clip.setX(clip.getX()+velX);
                }
            }
        }
    }

    /**
     * Adds all game objects' ImageViews to the pane.
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
        if(stallView != null){
            this.pane.getChildren().add(stallView);
        }
        if(!herbsImgView.isEmpty()){
            this.pane.getChildren().addAll(herbsImgView);
        }
    }

    /**
     * Fills the image arrays with ImageViews corresponding to the game objects in the map.
     */
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
     * Adds a game object to the pane by creating an ImageView for it.
     *
     * @param obj the game object to be added to the pane
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
     * Adds game objects to the SceneController's arraylists with ImageViews to be able to update their positions later on.
     *
     * @param obj the game object that is an instance of one of the subclasses of the class GameObject {@link GameObject}
     * @param view the respective ImageView for the obj
     */
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
        if(obj instanceof Herb) {
            this.herbsImgView.add(view);
            logger.log(Level.INFO, "Herb was added. X: "+obj.getX() + "; Y: " + obj.getY());
        }
        if(obj instanceof Stall){
            this.stallView = view;
            logger.log(Level.INFO, "Stall was added. X: "+obj.getX() + "; Y: " + obj.getY());
        }
    }

    /**
     * Switches the screen based on the current game status and updates the scene accordingly.
     */
    public void switchScreen(){
        String txt = sceneCreator.choosePhrase(map.getStall().isExited(), gameEngine.getGameStatus());
        switch (gameEngine.getGameStatus()){
            case GameStatus.START_SCREEN:
                scene.setRoot(sceneCreator.createPaneWithText(txt, 0));
                scene.setOnKeyPressed(e -> processKey(e.getCode(), true));
                scene.setOnKeyReleased(e -> processKey(e.getCode(), false));
                gameEngine.getStage().setScene(scene);
                gameEngine.getStage().show();
                break;
            case GameStatus.FAIL, GameStatus.WIN:
                clearImgArrays();
                scene.setRoot(sceneCreator.createPaneWithText(txt, clip.getX()));
                gameEngine.getStage().setScene(scene);
                gameEngine.getStage().show();
                break;
            default:
                sceneCreator.thiefPopUp();
                if(map.getStall().isEntered()){
                    map.getStall().setEntered(false);
                    imgNum = 0;
                    scene.setRoot(sceneCreator.createPaneWithText("", clip.getX()));
                    gameEngine.getStage().setScene(scene);
                    gameEngine.getStage().show();
                    Popup stallPopup = sceneCreator.addStallPopup(map);
                    stallPopup.show(gameEngine.getStage());
                }
                break;
        }
    }

    /**
     * Creates the map and initializes the scene with game objects and event handlers.
     */
    public void createMap(){
        if(loadMap){
            fillImgArrays();
            map.getPlayer().setDead(false);
            pane = sceneCreator.createPane(0);
            sceneCreator.addCoinsCounter(pane);
            scene.setRoot(pane);
            scene.setOnKeyPressed(e -> processKey(e.getCode(), true));
            scene.setOnKeyReleased(e -> processKey(e.getCode(), false));
            clip = sceneCreator.addClip(scene, pane);
            addGameObject(map.getPlayer());
            addGameObject(map.getStall());
            addObjects();
            gameEngine.getStage().setScene(scene);
            map.getPlayer().setMax(pane.getMaxWidth()-map.getPlayer().getWidth());
            map.getStall().setExited(false);
            map.getStall().setBought(false);
            map.getStall().setEntered(false);
            gameEngine.getStage().show();
            gameEngine.setGameStatus(GameStatus.RUNNING);
            loadMap = false;
        }
    }

    public void remove(Object o){
        pane.getChildren().remove(o);
    }

    /**
     * Updates the positions of the objects in the pane, shifting the background according to the player's position.
     */
    public void updateUI(){
        try {
            imgCounter++;
            playerMovement();
            map.getPlayer().setMin(clip.getX());
            map.getPlayer().setMax(pane.getMaxWidth()-map.getPlayer().getWidth());
            playerImgView.setX(map.getPlayer().getX());
            playerImgView.setY(map.getPlayer().getY());
            moveBackground(map.getPlayer().isRight(), (int)map.getPlayer().getVelX());
            sceneCreator.getAcquiredCoins().setText(""+map.getPlayer().getTotalCoins());
            sceneCreator.getAcquiredCoinsImg().setX(clip.getX()+1150);
            sceneCreator.getAcquiredCoins().setX(sceneCreator.getAcquiredCoinsImg().getX()+
                    sceneCreator.getAcquiredCoinsImg().getFitWidth()+10);
            if(!thievesImgView.isEmpty()){
                for(int i = 0; i< map.getThieves().size(); i++){
                    thievesImgView.get(i).setX(map.getThieves().get(i).getX());
                    thievesImgView.get(i).setY(map.getThieves().get(i).getY());
                    thiefMovement(map.getThieves().get(i), thievesImgView.get(i));
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
            }
        }
    }

    public ArrayList<ImageView> getCoinsImgView() {
        return coinsImgView;
    }

    public void setMap(Map map){
        this.map = map;
    }

    /**
     * Clears all ImageView arrays.
     */
    public void clearImgArrays(){
        imgNum =1;
        imgCounter = 0;
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

    /**
     * Handles the player's movement animation.
     */
    public void playerMovement(){
        if(imgCounter>7){
            if(imgNum == 1) imgNum = 2;
            if(imgNum == 2) imgNum = 1;
            imgCounter = 0;
        }
        if(map.getPlayer().isRight()){
            if(imgNum == 1){
                playerImgView.setImage(new Image(map.getPlayer().getRight1()));
                imgNum = 2;
            }else if (imgNum == 2){
                playerImgView.setImage(new Image(map.getPlayer().getRight2()));
            }
        }
        if(map.getPlayer().isLeft()){
            if(imgNum == 1){
                playerImgView.setImage(new Image(map.getPlayer().getLeft1()));
                imgNum = 2;
            }else if (imgNum == 2){
                playerImgView.setImage(new Image(map.getPlayer().getLeft2()));
            }
        }
    }

    /**
     * Handles the thief's movement animation.
     *
     * @param thief the thief object
     * @param thiefView the ImageView corresponding to the thief
     */
    public void thiefMovement(Thief thief, ImageView thiefView){
        if(thief.isRight()){
            thiefView.setImage(new Image(thief.getImgRight()));
        }else{
            thiefView.setImage(new Image(thief.getImgLeft()));
        }
    }

    public boolean isDescriptionShowing(){
        return sceneCreator.getDescription().isShowing();
    }

    /**
     * Shows the description popup at the start of the game.
     */
    public void showDescriptionPopup(){
        scene = new Scene(new BorderPane(sceneCreator.createPane(0)),
                sceneCreator.SCENE_WIDTH, sceneCreator.SCENE_HEIGHT);
        gameEngine.getStage().setScene(scene);
        gameEngine.getStage().show();
        sceneCreator.addDescriptionPopUp();
        sceneCreator.getDescription().show(gameEngine.getStage());
    }

    /**
     * Shows a popup when the thief steals all the player's coins.
     */
    public void showThiefPopup(){
        ScheduledExecutorService executor =  Executors.newSingleThreadScheduledExecutor();
        Popup thiefPopup = sceneCreator.getThiefPopup();
        if(!thiefPopup.isShowing()){
            thiefPopup.setX(gameEngine.getStage().getX()+30);
            thiefPopup.setY(gameEngine.getStage().getY()+20);
            thiefPopup.show(gameEngine.getStage());
            executor.submit(() -> Platform.runLater(gameEngine.getStage()::show));
            executor.schedule(
                    () -> Platform.runLater(thiefPopup::hide)
                    , 2
                    , TimeUnit.SECONDS);
        }
    }

    public ImageView getPlayerImgView() {
        return playerImgView;
    }

    public SceneCreator getSceneCreator() {
        return sceneCreator;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<ImageView> getBlocksImgView() {
        return blocksImgView;
    }

    public ArrayList<ImageView> getThievesImgView() {
        return thievesImgView;
    }

    public ArrayList<ImageView> getMushroomsImgView() {
        return mushroomsImgView;
    }

}
