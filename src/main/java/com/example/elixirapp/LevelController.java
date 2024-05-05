package com.example.elixirapp;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * processing interactions between objects and checking
 * the game's status.
 */
public class LevelController{
    private static final Logger logger = Logger.getLogger(LevelController.class.getName());

    Map map;

    private LevelLoader levelLoader = new LevelLoader();
    public LevelController() {
    }
    public void createLevel(Stage stage, String filePath){
        levelLoader.loadLevelData(filePath);
        map = levelLoader.getMap();
        map.createMap(stage);
        stage.show();
    }

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
    }

    /**
     * checking whether the player is stepping on the block.
     * If so, player's Y value is changed for him to be on top of
     * the block
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
     * checking if the player collides with the bottom part of the blocks.
     * If so, falls down after collision.
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
     * checking if the player collides with the sides of the block.
     * If so, he cannot go through the block.
     */
    public void checkPlayerLeftRightCollisions(){
        java.awt.Rectangle playerSideBounds = map.getPlayer().isLeft() ? map.getPlayer().getLeftBounds() : map.getPlayer().getRightBounds();
        if (map.getPlayer().isLeft()){
            for (Block block: map.getBlocks()){
                java.awt.Rectangle blockSideBounds = !map.getPlayer().isLeft() ? block.getLeftBounds() : block.getRightBounds();
                if(blockSideBounds.intersects(playerSideBounds)){
//                    map.getPlayer().setVelX(0);
                    map.getPlayer().setFalling(true);
                    map.getPlayer().setJumping(false);
                }
            }
        }
    }

    /**
     * checking if the player touches the coin. If so, it disappears
     * from the Pane and the player's coin count is increase by the value
     * of the collected coin.
     */
    public void checkCoinsCollisions(){
        ArrayList<Coin> toBeRemoved = new ArrayList<>();
        ArrayList<ImageView> imgToBeRemoved = new ArrayList<>();
        ArrayList<ImageView> coinsView = map.getUiController().getCoinsImgView();
        for (int i =0; i < map.getCoins().size(); i++){
            if(map.getCoins().get(i).getBounds().intersects(map.getPlayer().getBounds())){
                ImageView coin = coinsView.get(i);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        map.remove(coin);
                    }
                });
                logger.log(Level.INFO, "Coin was collected");
                toBeRemoved.add(map.getCoins().get(i));
                imgToBeRemoved.add(coin);
                map.getPlayer().collectCoin(map.getCoins().get(i));
                logger.log(Level.INFO, "Player currently has " + map.getPlayer().getCoins());
            }
        }
        map.getCoins().removeAll(toBeRemoved);
        coinsView.removeAll(imgToBeRemoved);
    }

    /**
     * checking if the player collides with a thief. If so, the player's
     * coin count is equal to zero.
     */
    public void checkThiefCollisions(){
        for(Thief thief: map.getThieves()){
            if(thief.getBounds().intersects(map.getPlayer().getBounds())){
                map.getPlayer().setCoins(0);
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
                logger.log(Level.INFO, "Collision with mushroom");
            }
        }
    }

    /**
     * If a thief is (for some reason) not located on the block, then
     * it falls down on the closest block underneath him.
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
     * If a mushroom is (for some reason) not located on the block, then
     * it falls down on the closest block underneath him.
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

    public void gameUpdate(){
        checkCollisions();
        map.updateLocations();
    }

    public boolean isPlayerDead(){
        return map.getPlayer().isDead();
    }

}
