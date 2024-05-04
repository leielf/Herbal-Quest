package com.example.elixirapp;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        }
        if(!map.getMushrooms().isEmpty()){
            checkMushroomCollisions();
        }
    }

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

    public void checkTopCollisions(){
        for (Block block: map.getBlocks()) {
            if(block.getBottomBounds().intersects(map.getPlayer().getTopBounds())){
                map.getPlayer().setJumping(false);
                map.getPlayer().setFalling(true);
                map.getPlayer().setVelY(10);
            }
        }
    }

    public void checkPlayerLeftRightCollisions(){
        java.awt.Rectangle playerSideBounds = map.getPlayer().isLeft() ? map.getPlayer().getLeftBounds() : map.getPlayer().getRightBounds();
        if (map.getPlayer().isLeft()){
            for (Block block: map.getBlocks()){
                java.awt.Rectangle blockSideBounds = !map.getPlayer().isLeft() ? block.getLeftBounds() : block.getRightBounds();
                if(blockSideBounds.intersects(playerSideBounds)){
                    map.getPlayer().setVelX(0);
                    map.getPlayer().setFalling(true);
                    map.getPlayer().setJumping(false);
                }
            }
        }
    }

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

    public void checkThiefCollisions(){
        for(Thief thief: map.getThieves()){
            if(thief.getBounds().intersects(map.getPlayer().getBounds())){
                map.getPlayer().setCoins(0);
                logger.log(Level.INFO, "Collision with thief and player's coins = " + map.getPlayer().getCoins());
            }
        }
    }

    public void checkMushroomCollisions(){
        for(Mushroom mushroom: map.getMushrooms()){
            if(mushroom.getBounds().intersects(map.getPlayer().getBounds())){
                map.getPlayer().setDead(true);
                logger.log(Level.INFO, "Collision with mushroom");
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
