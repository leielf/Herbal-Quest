package com.example.elixirapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    public Pane createPaneWithText(String text) {
        Pane pane  = createPane();
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

    public void addDescriptionWindow(){
        Stage descriptionStage = addDialogWindow();
        Pane root = (Pane) descriptionStage.getScene().getRoot();
        editDialogPane(root,730, 420, 0);
        //add welcoming phrase
        Text welcomeText = new Text("WELCOME TO HERBAL QUEST! HERE IS A SHORT DESCRIPTION OF GAME OBJECTS:");
        welcomeText.setWrappingWidth(600);
        welcomeText.setX(SCENE_WIDTH/2 -300);
        Font font = Font.loadFont(getClass().getResourceAsStream("/pixel_font.ttf"), 25);
        welcomeText.setFont(font);
        welcomeText.setFill(Color.BEIGE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setY((SCENE_HEIGHT-420)/2+30);
        root.getChildren().add(welcomeText);
        //add thief image and description
        ImageView thief = addImg("/pixel_thief1.png", welcomeText.getX()-50, welcomeText.getY()+70);
        root.getChildren().add(thief);
        String thiefStr = "- on touch with THEIF you lose all the coins you collected.";
        root.getChildren().add(addDdescriptionText(thiefStr, thief.getX()+70, thief.getY()));
        //add coin image and description
        ImageView coin = addImg("/pixel_coin.png",thief.getX()+10, thief.getY()+120);
        root.getChildren().add(coin);
        String coinStr = "- you collect COINS to buy herbs if you were not able to pick up some herbs.";
        root.getChildren().add(addDdescriptionText(coinStr, coin.getX()+60, coin.getY()-10));
        //add mushroom image and description
        ImageView mushroom = addImg("/pixel_mushroom.png", thief.getX(), coin.getY()+100);
        root.getChildren().add(mushroom);
        String mushroomStr = "- on touch with MUSHROOM game is over, you need to start all over.";
        root.getChildren().add(addDdescriptionText(mushroomStr,mushroom.getX()+70, mushroom.getY()));
        //add player image and description
        ImageView player = addImg("/player_right1.png", SCENE_WIDTH/2+50, thief.getY()-20);
        root.getChildren().add(player);
        root.getChildren().add(addDdescriptionText("- YOU", player.getX()+50, player.getY()+50));
        //add stall image and description
        ImageView stall = addImg("pixel_stall.png", player.getX()-10, player.getY()+160);
        root.getChildren().add(stall);
        String stallStr = "- if you do not have some herbs you can purchase them at the STALL.";
        Text stallTxt = addDdescriptionText(stallStr, stall.getX()+150, stall.getY());
        stallTxt.setWrappingWidth(190);
        root.getChildren().add(stallTxt);
        //add closing button
        ImageView closeImg = new ImageView(new Image("/pixel_cross.png"));
        closeImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                descriptionStage.close();
            }
        });
        closeImg.setY(welcomeText.getY()-10);
        closeImg.setX(welcomeText.getX()+620);
        root.getChildren().add(closeImg);
        descriptionStage.show();
    }

    public void addStallPopupWindow(Map map){
        Stage stallStage = addDialogWindow();
        Pane root = (Pane)stallStage.getScene().getRoot();
        int cost = map.getHerbs().size()*map.getStall().getHerbCost();
        root.getChildren().add(new Text("You are at stall with all the herbs \nyou'll ever need in your life.\nIt seems that you are missing certain herbs from your list.\nWould you like to buy them for only "+cost + " coins?"));
        Button button = new Button("PURCHASE");
        button.setEffect(null);
        button.setStyle("-fx-background-color: transparent; -fx-border-color:black;");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(map.getPlayer().getCoins() >= cost){
                    map.getStall().setBought(true);
                    map.getPlayer().setCoins(map.getPlayer().getCoins()-cost);
                }else{
                    map.getStall().setBought(false);
                }
                map.getStall().setExited(true);
                stallStage.close();
            }
        });
        root.getChildren().add(button);
        stallStage.show();
    }

    public ImageView addImg(String imgPath, double x, double y){
        ImageView view = new ImageView(new Image(imgPath));
        view.setX(x);
        view.setY(y);
        return view;

    }
    public Text addDdescriptionText(String txt, double x, double y){
        Font font = Font.loadFont(getClass().getResourceAsStream("/pixel_font.ttf"), 25);
        Text text = new Text(txt);
        text.setFont(font);
        text.setFill(Color.BEIGE);
        text.setWrappingWidth(280);
        text.setX(x);
        text.setY(y);
        return text;
    }
    public Stage addDialogWindow(){
        Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Pane pane = new Pane();
        dialogStage.setScene(new Scene(pane));
        return dialogStage;
    }

    public void editDialogPane(Pane root, double recWidth, double recHeight, double x){
        root.setMinSize(SCENE_WIDTH, SCENE_HEIGHT);
        root.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        root.setMaxSize(SCENE_WIDTH, SCENE_HEIGHT);
        ImageView backgroundView = new ImageView(new Image("/pixel_background_long_blue.png"));
        backgroundView.setX(backgroundView.getX()-x);
        root.getChildren().add(backgroundView);
        Rectangle background = new Rectangle(recWidth, recHeight);
        background.setFill(Color.WHEAT);
        background.setOpacity(0.4);
        background.setX((SCENE_WIDTH-recWidth)/2);
        background.setY((SCENE_HEIGHT-recHeight)/2);
        root.getChildren().add(background);
    }

    public void addStall(Pane pane){
        stall = new ImageView(new Image("/pixel_stall.png"));
        stall.setY(500);
        stall.setX(SceneCreator.SCENE_WIDTH-100);
        pane.getChildren().add(stall);
    }

    public void choosePhrase(GameStatus gameStatus){

    }
}
