package com.example.elixirapp;
import com.example.elixirapp.GameEntity.*;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The LevelController class processes interactions between objects and checks the game's status.
 * It handles collision detection, level creation, and game state updates.
 */
public class LevelController{
    private static final Logger logger = Logger.getLogger(LevelController.class.getName());
    private final GameEngine gameEngine;
    Map map;
    private SceneController sceneController;

    public LevelController(GameEngine gameEngine, Map map) {
        this.gameEngine = gameEngine;
        this.map = map;
    }

    public void createLevel(){
        sceneController = new SceneController(map, gameEngine);
        sceneController.showDescriptionPopup();
    }

    /**
     * Checks for collisions between the player and various game entities, and processes the results.
     */
    public void checkCollisions(){
        if(!map.getBlocks().isEmpty()){
            checkBottomCollisions();
            checkTopCollisions();
            checkPlayerLeftRightCollisions();
        }
        if(!map.getCoins().isEmpty()){
            checkCoinsCollisions();
        }
        if(!map.getThieves().isEmpty()){
            checkThiefCollisions();
            if(!map.getBlocks().isEmpty()) checkIsThiefLevitating();
        }
        if(!map.getMushrooms().isEmpty()){
            checkMushroomCollisions();
            if(!map.getBlocks().isEmpty()) checkIsMushroomLevitating();
        }
        if(!map.getHerbs().isEmpty()){
            checkHerbsCollisions();
        }
        if(map.getStall()!=null){
            checkStallCollisions();
        }
    }

    /**
     * Checks for collisions between the player and herbs. If a collision occurs, the herb
     * is collected and removed from the map.
     */
    public void checkHerbsCollisions(){
        ArrayList<Herb> toBeRemoved = new ArrayList<>();
        ArrayList<ImageView> imgToBeRemoved = new ArrayList<>();
        for(int i = 0; i < map.getHerbs().size(); i++){
            if(map.getHerbs().get(i).getBounds().intersects(map.getPlayer().getBounds())){
                map.getPlayer().collectHerb(map.getHerbs().get(i));
                ImageView herbView = sceneController.getHerbsImgView().get(i);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        sceneController.remove(herbView);
                    }
                });
                logger.log(Level.INFO, "Herb " + map.getHerbs().get(i).getName() + " was collected");
                toBeRemoved.add(map.getHerbs().get(i));
                imgToBeRemoved.add(herbView);
                logger.log(Level.INFO, "Player currently has " + map.getPlayer().getHerbs().size()+ " herbs");
            }
        }
        map.getHerbs().removeAll(toBeRemoved);
        sceneController.getHerbsImgView().removeAll(imgToBeRemoved);
    }

    /**
     * Checks for collisions between the player and the stall. If a collision occurs,
     * processes the interaction based on the player's herb collection status.
     */
    public void checkStallCollisions(){
        if(!map.getStall().isEntered()){
            if(map.getPlayer().getBounds().intersects(map.getStall().getBounds())){
                if(!map.getHerbs().isEmpty()){
                    map.getStall().setEntered(true);
                    map.getPlayer().setVelX(0);
                    map.getPlayer().setX(map.getStall().getX()-map.getPlayer().getWidth()-2);
                }else{
                    gameEngine.setGameStatus(GameStatus.WIN);
                }
            }
        }
    }

    /**
     * Checks if the player is stepping on a block. If so, adjusts the player's Y value
     * to place them on top of the block.
     */
    public void checkBottomCollisions(){
        if (!map.getPlayer().isJumping()){
            map.getPlayer().setFalling(true);
        }
        for (Block block: map.getBlocks()) {
            if(block.getTopBounds().intersects(map.getPlayer().getBottomBounds())){
                map.getPlayer().setY(block.getY()-map.getPlayer().getHeight()+1);
                map.getPlayer().setFalling(false);
            }
        }
    }

    /**
     * Checks if the player collides with the bottom part of blocks. If so, the player falls down after collision.
     */
    public void checkTopCollisions(){
        for (Block block: map.getBlocks()) {
            if(block.getBottomBounds().intersects(map.getPlayer().getTopBounds())){
                map.getPlayer().setJumping(false);
                map.getPlayer().setFalling(true);
                map.getPlayer().setVelY(10);
            }
        }
    }

    /**
     * Checks if the player collides with the sides of blocks. If so, the player cannot go through the block.
     */
    public void checkPlayerLeftRightCollisions(){
        java.awt.Rectangle playerSideBounds = map.getPlayer().isLeft() ? map.getPlayer().getLeftBounds() : map.getPlayer().getRightBounds();
        if (map.getPlayer().isLeft()){
            for (Block block: map.getBlocks()){
                java.awt.Rectangle blockSideBounds = !map.getPlayer().isLeft() ? block.getLeftBounds() : block.getRightBounds();
                if(blockSideBounds.intersects(playerSideBounds)){
                    map.getPlayer().setFalling(true);
                    map.getPlayer().setJumping(false);
                }
            }
        }
    }

    /**
     * checking if the player touches the coin. If so, it disappears
     * and the player's coin count is increase by the coin's value
     */
    public void checkCoinsCollisions(){
        ArrayList<Coin> toBeRemoved = new ArrayList<>();
        ArrayList<ImageView> imgToBeRemoved = new ArrayList<>();
        for (int i =0; i < map.getCoins().size(); i++){
            if(map.getCoins().get(i).getBounds().intersects(map.getPlayer().getBounds())){
                ImageView coin = sceneController.getCoinsImgView().get(i);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        sceneController.remove(coin);
                    }
                });
                logger.log(Level.INFO, "Coin was collected");
                toBeRemoved.add(map.getCoins().get(i));
                imgToBeRemoved.add(coin);
                map.getPlayer().collectCoin(map.getCoins().get(i));
                logger.log(Level.INFO, "Player currently has " + map.getPlayer().getTotalCoins());
            }
        }
        map.getCoins().removeAll(toBeRemoved);
        sceneController.getCoinsImgView().removeAll(imgToBeRemoved);
    }

    /**
     * checking if the player collides with a thief. If so, the player's
     * coin count is equal to zero.
     */
    public void checkThiefCollisions(){
        for(Thief thief: map.getThieves()){
            if(thief.getBounds().intersects(map.getPlayer().getBounds())){
                map.getPlayer().setTotalCoins(0);
                map.getPlayer().getCoins().clear();
                sceneController.showThiefPopup();
            }
        }
    }

    /**
     * checking if the player collides with a mushroom. If so, the player is
     * dead and game is over.
     */
    public void checkMushroomCollisions(){
        for(Mushroom mushroom: map.getMushrooms()){
            if(mushroom.getBounds().intersects(map.getPlayer().getBounds())){
                map.getPlayer().setDead(true);
                gameEngine.setGameStatus(GameStatus.FAIL);
                logger.log(Level.INFO, "Collision with mushroom");
            }
        }
    }

    /**
     * Checks if a thief is levitating (not located on a block). If so, the thief falls
     * down to the closest block underneath.
     */
    public void checkIsThiefLevitating(){
        for(Block block: map.getBlocks()){
            for(Thief thief: map.getThieves()){
                if(!thief.getBottomBounds().intersects(block.getTopBounds())){
                    thief.setFalling(true);
                    thief.setVelY(4);
                }else{
                    thief.setFalling(false);
                    thief.setVelX(3);
                    thief.setY(block.getY()-thief.getHeight());
                }
            }
        }
    }

    /**
     * Checks if a mushroom is levitating (not located on a block). If so, the mushroom
     * falls down to the closest block underneath.
     */
    public void checkIsMushroomLevitating(){
        for(Block block: map.getBlocks()){
            for(Mushroom mushroom: map.getMushrooms()){
                if(mushroom.isFalling()){
                    if(!mushroom.getBottomBounds().intersects(block.getTopBounds())){
                        mushroom.setFalling(true);
                        mushroom.setVelY(5);
                    }else{
                        mushroom.setFalling(false);
                        mushroom.setY(block.getY()-mushroom.getHeight());
                    }
                }
            }
        }
    }

    /**
      * Updates the game state by checking collisions, updating locations, and refreshing the UI.
      */
    public void gameUpdate(){
        if(gameEngine.getGameStatus() == GameStatus.RUNNING){
            checkCollisions();
            map.updateLocations();
            sceneController.updateUI();
        }
        checkGameStatus();
    }

    public void checkGameStatus(){
        if(gameEngine.getGameStatus() == GameStatus.START_SCREEN
                || gameEngine.getGameStatus() == GameStatus.FAIL){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    sceneController.createMap();
                }
            });
        }
        if(map.getStall().isEntered()){
            sceneController.switchScreen();
        }
        if(map.getStall().isExited()){
            if(map.getStall().isBought()){
                gameEngine.setGameStatus(GameStatus.WIN);
            }else{
                gameEngine.setGameStatus(GameStatus.FAIL);
            }
        }
    }

    /**
     * Resets the level with a new map, updates the SceneController, and checks if the map should be loaded.
     * @param map the new map to be loaded
     */
    public void reset(Map map){
        this.map = map;
        sceneController.setMap(map);
        sceneController.switchScreen();
        checkIsLoadMap();
    }

    public void endLevel(){
        sceneController.switchScreen();
    }

    public void checkIsLoadMap(){
        if(sceneController.isLoadMap()){
            sceneController.createMap();
        }
    }

    /**
     * Checks if the description screen is being displayed. If not, switches the screen.
     */
    public void checkStartScreen(){
        if(!sceneController.isDescriptionShowing()){
            sceneController.switchScreen();
        }
    }

    public Map getMap() {
        return map;
    }
}
