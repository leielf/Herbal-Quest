package com.example.elixirapp;

import com.example.elixirapp.GameEntity.Herb;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;


public class SceneCreator {
    private final Font font = Font.loadFont(getClass().getResourceAsStream("/pixel_font.ttf"), 50);
    static final int BORDER = 601;
    static final int SCENE_WIDTH = 1260;
    static final int SCENE_HEIGHT = 700;

    private Text acquiredCoins;
    private ImageView acquiredCoinsImg;

    private Popup thiefPopup;

    private Popup description;

    private ImageView stall;
    public SceneCreator() {
    }

    public Pane createPane() {
        Pane pane = new Pane();
        pane.setMinSize(SCENE_WIDTH*3-5, SCENE_HEIGHT);
        pane.setPrefSize(SCENE_WIDTH*3-5, SCENE_HEIGHT);
        pane.setMaxSize(SCENE_WIDTH*3-5, SCENE_HEIGHT);
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

    public Pane createPaneWithText(String text, double x) {
        Pane pane = new Pane();
        changeDialogPane(pane, x);
        Text phrase = addPhrase(text);
        phrase.setX((SCENE_WIDTH-phrase.getWrappingWidth())/2);
        phrase.setY((SCENE_HEIGHT-phrase.getBoundsInLocal().getHeight())/2);
        pane.getChildren().add(phrase);
        return pane;
    }

    public Text addPhrase(String text){
        Text phrase = new Text();
        phrase.setText(text);
        phrase.setFont(font);
        phrase.setFill(Color.BEIGE);
        phrase.setWrappingWidth(600);
        phrase.setTextAlignment(TextAlignment.CENTER);
        return phrase;
    }

    public void addCoinsCounter(Pane pane){
        acquiredCoinsImg = new ImageView(new Image("/pixel_coin.png"));
        acquiredCoinsImg.setFitWidth(30);
        acquiredCoinsImg.setFitHeight(30);
        acquiredCoinsImg.setX(SCENE_WIDTH-118);
        acquiredCoinsImg.setY(25);
        acquiredCoins = new Text();
        acquiredCoins.setText("0");
        Font font = Font.loadFont(getClass().getResourceAsStream("/pixel_font.ttf"), 30);
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

    public Popup addDescriptionPopUp(){
        description = new Popup();
        Rectangle background = addRectangle(730, 420, (SCENE_WIDTH-730)/2-250, (SCENE_HEIGHT/2-250));
        description.getContent().add(background);
        //add welcoming phrase
        String welcomeStr = "WELCOME TO HERBAL QUEST! HERE IS A SHORT DESCRIPTION OF GAME OBJECTS:";
        Text welcomeText = addDescriptionText(welcomeStr, background.getX()+60, background.getY()+30);
        welcomeText.setWrappingWidth(600);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        description.getContent().add(welcomeText);
        //add thief image and description
        ImageView thief = addImg("/pixel_thief1.png", welcomeText.getX()-50, welcomeText.getY()+70);
        description.getContent().add(thief);
        String thiefStr = "- on touch with THEIF you lose all the coins you collected.";
        description.getContent().add(addDescriptionText(thiefStr, thief.getX()+70, thief.getY()));
        //add coin image and description
        ImageView coin = addImg("/pixel_coin.png",thief.getX()+10, thief.getY()+120);
        description.getContent().add(coin);
        String coinStr = "- you collect COINS to buy herbs if you were not able to pick up some herbs.";
        description.getContent().add(addDescriptionText(coinStr, coin.getX()+60, coin.getY()-10));
        //add mushroom image and description
        ImageView mushroom = addImg("/pixel_mushroom.png", thief.getX(), coin.getY()+100);
        description.getContent().add(mushroom);
        String mushroomStr = "- on touch with MUSHROOM game is over, you need to start all over.";
        description.getContent().add(addDescriptionText(mushroomStr,mushroom.getX()+70, mushroom.getY()));
        //add player image and description
        ImageView player = addImg("/player_right1.png", thief.getX()+400, thief.getY()-20);
        description.getContent().add(player);
        description.getContent().add(addDescriptionText("- YOU", player.getX()+50, player.getY()+50));
        //add stall image and description
        ImageView stall = addImg("pixel_stall.png", player.getX()-10, player.getY()+160);
        description.getContent().add(stall);
        String stallStr = "- if you do not have some herbs you can purchase them at the STALL.";
        Text stallTxt = addDescriptionText(stallStr, stall.getX()+150, stall.getY());
        stallTxt.setWrappingWidth(190);
        description.getContent().add(stallTxt);
        //add closing button
        ImageView closeImg = new ImageView(new Image("/pixel_cross.png"));
        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                description.hide();
            }
        });
        closeImg.setY(welcomeText.getY()-10);
        closeImg.setX(welcomeText.getX()+620);
        description.getContent().add(closeImg);
        return description;
    }



    public Popup addStallPopup(Map map){
        Popup stallPopup = new Popup();
        Rectangle background = addRectangle(400, 600, 70, SCENE_HEIGHT/2-300);
        stallPopup.getContent().add(background);
        int cost = 0;
        for(Herb herb : map.getHerbs()){
            cost += herb.getCost();
        }
        String startStr = "Hello! You are at the stall where you can find all desired herbs no matter how rare they are!";
        Text startingPhrase = addDescriptionText(startStr, background.getX() +20, background.getY()+20);
        startingPhrase.setWrappingWidth(360);
        if(!map.getHerbs().isEmpty()){
            String addition = " It seems like you do not have some of the necessary herbs, which are: ";
            for(Herb herb: map.getHerbs()){
                addition += "\n" + herb.getName();
            }
            addition += "\nBut don't be sad, I have those in stock. "+
                    "Would you like to purchase the herbs each costing ?? "
                    + " coins? In total will be only " +cost+ " coins!";
            startingPhrase.setText(startingPhrase.getText()+addition);
        }
        startingPhrase.setTextAlignment(TextAlignment.CENTER);
        startingPhrase.setY((SCENE_HEIGHT - startingPhrase.getBoundsInLocal().getHeight())/2);
        stallPopup.getContent().add(startingPhrase);
        Text purchaseTxt = addDescriptionText("PURCHASE", background.getX()+130,startingPhrase.getBoundsInLocal().getHeight()+startingPhrase.getY()+20);
        purchaseTxt.setWrappingWidth(160);
        purchaseTxt.setFill(Color.DARKRED);
        stallPopup.getContent().add(purchaseTxt);
        int finalCost = cost;
        purchaseTxt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(map.getPlayer().getTotalCoins() >= finalCost){
                    map.getStall().setBought(true);
                    map.getPlayer().setTotalCoins(map.getPlayer().getTotalCoins()- finalCost);
                }else{
                    map.getStall().setBought(false);
                }
                map.getStall().setExited(true);
                stallPopup.hide();
            }
        });
        return stallPopup;
    }

    public ImageView addImg(String imgPath, double x, double y){
        ImageView view = new ImageView(new Image(imgPath));
        view.setX(x);
        view.setY(y);
        return view;

    }
    public Text addDescriptionText (String txt, double x, double y){
        Font font = Font.loadFont(getClass().getResourceAsStream("/pixel_font.ttf"), 25);
        Text text = new Text(txt);
        text.setFont(font);
        text.setFill(Color.BEIGE);
        text.setWrappingWidth(280);
        text.setX(x);
        text.setY(y);
        return text;
    }

    public void changeDialogPane(Pane root, double x){
        root.setMinSize(SCENE_WIDTH, SCENE_HEIGHT);
        root.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        root.setMaxSize(SCENE_WIDTH, SCENE_HEIGHT);
        ImageView backgroundView = new ImageView(new Image("/pixel_background_long_blue.png"));
        backgroundView.setX(backgroundView.getX()-x);
        root.getChildren().add(backgroundView);
    }

    public Rectangle addRectangle(double recWidth, double recHeight, double x, double y){
        Rectangle background = new Rectangle(recWidth, recHeight);
        background.setFill(Color.WHEAT);
        background.setOpacity(0.4);
        background.setX(x);
        background.setY(y);
        return background;
    }

    public String choosePhrase(boolean isBought, boolean isExited, GameStatus gameStatus){
        if(gameStatus == GameStatus.FAIL){
            if(isExited){
                return "SORRY, YOU DO NOT HAVE ENOUGH COINS TO PURCHASE NEEDED HERBS. PRESS SPACE TO START AGAIN.";
            }
            return "YOU STEPPED ON THE MUSHROOM AND DIED! PRESS SPACE TO TRY AGAIN!";
        }else if(gameStatus == GameStatus.WIN){
            return "CONGRATS! YOU PICKED ALL NEEDED HERBS!";
        }
        return "PRESS SPACE TO START";
    }

    public void thiefPopUp(){
        thiefPopup = new Popup();
        Text txt = new Text("thief stole all of your coins!");
        txt.setWrappingWidth(230);
        txt.setFill(Color.WHITE);
        Font font = Font.loadFont(getClass().getResourceAsStream("/pixel_font.ttf"), 25);
        txt.setFont(font);
        txt.setTextAlignment(TextAlignment.CENTER);
        thiefPopup.getContent().add(txt);
    }

    public Popup getThiefPopup() {
        return thiefPopup;
    }

    public Popup getDescription() {
        return description;
    }




//    public Pane addStallPopupWindow(Map map, double x){
//        Stage stallStage = addDialogWindow();
//        Pane root = new Pane();
//        changeDialogPane(root, x);
//        root.getChildren().add(addRectangle(400, 600));
//        int cost = map.getHerbs().size();
//        String startStr = "Hello! You are at the stall where you can find all desired herbs no matter how rare they are!";
//        Text startingPhrase = addDdescriptionText(startStr, SCENE_WIDTH/2 -180, (SCENE_HEIGHT-400)/2);
//        startingPhrase.setWrappingWidth(360);
//        if(!map.getHerbs().isEmpty()){
//            String addition = " It seems like you do not have some of the necessary herbs, which are: ";
//            for(Herb herb: map.getHerbs()){
//                addition += "\n" + herb.getName();
//            }
//            addition += "\nBut don't be sad, I have those in stock. "+
//                    "Would you like to purchase the herbs each costing ?? "
//                    + " coins? In total will be only " +cost+ " coins!";
//            startingPhrase.setText(startingPhrase.getText()+addition);
//        }
//        startingPhrase.setTextAlignment(TextAlignment.CENTER);
//        startingPhrase.setY((SCENE_HEIGHT - startingPhrase.getBoundsInLocal().getHeight())/2);
//        root.getChildren().add(startingPhrase);
//        Text purchaseTxt = addDdescriptionText("PURCHASE", SCENE_WIDTH/2-80,startingPhrase.getBoundsInLocal().getHeight()+startingPhrase.getY()+20);
//        purchaseTxt.setWrappingWidth(160);
//        purchaseTxt.setFill(Color.DARKRED);
//        root.getChildren().add(purchaseTxt);
//        purchaseTxt.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                if(map.getPlayer().getTotalCoins() >= cost){
//                    map.getStall().setBought(true);
//                    map.getPlayer().setTotalCoins(map.getPlayer().getTotalCoins()-cost);
//                }else{
//                    map.getStall().setBought(false);
//                }
//                map.getStall().setExited(true);
//                stallStage.close();
//            }
//        });
//        return root;
//    }

//    public Stage addDialogWindow(){
//        Stage dialogStage = new Stage();
//        dialogStage.initStyle(StageStyle.UNDECORATED);
//        dialogStage.initModality(Modality.WINDOW_MODAL);
//        Pane pane = new Pane();
//        dialogStage.setScene(new Scene(pane));
//        return dialogStage;
//    }
}
