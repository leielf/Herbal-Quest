package com.example.elixirapp;

import com.example.elixirapp.GameEntity.Herb;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;

/**
 * The SceneCreator class is responsible for creating and managing various scenes and visual elements for the game.
 * It handles the creation of panes, scenes, and popups, as well as adding and configuring visual components such as
 * text, images, and rectangles.
 */
public class SceneCreator {
    private final Font font = Font.loadFont(getClass().getResourceAsStream("/pixel_font.ttf"), 50);
    public static final int BORDER = 601;
    public static final int SCENE_WIDTH = 1260;
    public static final int SCENE_HEIGHT = 700;

    // Visual components for displaying acquired coins
    private Text acquiredCoins;
    private ImageView acquiredCoinsImg;

    // Popups used in the game
    private Popup thiefPopup;
    private Popup description;

    public SceneCreator() {
    }

    /**
     * Creates a Pane with a specified horizontal shift applied to its background image.
     * @param shift the horizontal shift to be applied to the background image
     * @return the created Pane
     */
    public Pane createPane(double shift) {
        Pane pane = new Pane();
        pane.setMinSize(SCENE_WIDTH*3-5, SCENE_HEIGHT);
        pane.setPrefSize(SCENE_WIDTH*3-5, SCENE_HEIGHT);
        pane.setMaxSize(SCENE_WIDTH*3-5, SCENE_HEIGHT);
        ImageView backgroundView = new ImageView(new Image("/background_long.png"));
        backgroundView.setX(backgroundView.getX()-shift);
        pane.getChildren().add(backgroundView);
        return pane;
    }

    /**
     * Sets a clipping rectangle to the specified Pane, enabling movement of the background with objects
     * as the player moves. The clip's x value is dependent on the player's x value.
     *
     * @param scene the Scene containing the Pane
     * @param pane the Pane to which the clip is added
     * @return the created Rectangle used as a clip
     */
    public Rectangle addClip(Scene scene, Pane pane){
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(scene.widthProperty());
        clip.heightProperty().bind(scene.heightProperty());
        pane.setClip(clip);
        pane.translateXProperty().bind(clip.xProperty().multiply(-1));
        return clip;
    }

    /**
     * Creates a Pane with a specified text message displayed in the center, with a horizontal shift applied to its background image.
     *
     * @param text the text message to be displayed
     * @param shift the horizontal shift to be applied to the background image
     * @return the created Pane
     */
    public Pane createPaneWithText(String text, double shift) {
        Pane root = createPane(shift);
        Text phrase = new Text();
        phrase.setText(text);
        phrase.setFont(font);
        phrase.setFill(Color.BEIGE);
        phrase.setWrappingWidth(600);
        phrase.setTextAlignment(TextAlignment.CENTER);
        phrase.setX((SCENE_WIDTH-phrase.getWrappingWidth())/2);
        phrase.setY((SCENE_HEIGHT-phrase.getBoundsInLocal().getHeight())/2);
        root.getChildren().add(phrase);
        return root;
    }

    /**
     * Adds a coins counter to the specified Pane, allowing the player to see the number of collected coins.
     * @param pane the Pane to which the coins counter is added
     */
    public void addCoinsCounter(Pane pane){
        acquiredCoinsImg = new ImageView(new Image("/coin.png"));
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
    /**
     * adding popup at the beginning of the game
     * to describe each game object before the start of the game
     */
    public void addDescriptionPopUp(){
        description = new Popup();
        Rectangle background = addRectangle(730, 420,
                (double) (SCENE_WIDTH - 730) /2-250, ((double) SCENE_HEIGHT /2-250));
        description.getContent().add(background);
        //add welcoming phrase
        String welcomeStr = "WELCOME TO HERBAL QUEST! HERE IS A SHORT DESCRIPTION OF GAME OBJECTS:";
        Text welcomeText = addDescriptionText(welcomeStr, background.getX()+60, background.getY()+30);
        welcomeText.setWrappingWidth(600);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        description.getContent().add(welcomeText);
        //add thief image and description
        ImageView thief = addImg("/thief1.png", welcomeText.getX()-50, welcomeText.getY()+70);
        description.getContent().add(thief);
        String thiefStr = "- on touch with THIEF you lose all the coins you collected.";
        description.getContent().add(addDescriptionText(thiefStr, thief.getX()+70, thief.getY()));
        //add coin image and description
        ImageView coin = addImg("/coin.png",thief.getX()+10, thief.getY()+120);
        description.getContent().add(coin);
        String coinStr = "- you collect COINS to buy herbs if you were not able to pick up some herbs.";
        description.getContent().add(addDescriptionText(coinStr, coin.getX()+60, coin.getY()-10));
        //add mushroom image and description
        ImageView mushroom = addImg("/mushroom.png", thief.getX(), coin.getY()+100);
        description.getContent().add(mushroom);
        String mushroomStr = "- on touch with MUSHROOM game is over, you need to start all over.";
        description.getContent().add(addDescriptionText(mushroomStr,mushroom.getX()+70, mushroom.getY()));
        //add player image and description
        ImageView player = addImg("/player_right1.png", thief.getX()+400, thief.getY()-20);
        description.getContent().add(player);
        description.getContent().add(addDescriptionText("- YOU", player.getX()+50, player.getY()+50));
        //add stall image and description
        ImageView stall = addImg("stall.png", player.getX()-10, player.getY()+160);
        description.getContent().add(stall);
        String stallStr = "- if you do not have some herbs you can purchase them at the STALL.";
        Text stallTxt = addDescriptionText(stallStr, stall.getX()+150, stall.getY());
        stallTxt.setWrappingWidth(190);
        description.getContent().add(stallTxt);
        //add closing button
        ImageView closeImg = new ImageView(new Image("/cross.png"));
        closeImg.setOnMouseClicked(mouseEvent -> description.hide());
        closeImg.setY(welcomeText.getY()-10);
        closeImg.setX(welcomeText.getX()+620);
        description.getContent().add(closeImg);
    }


    /**
     * adding popup with the option to buy missing herbs
     * @param map to know how much money the player has and the
     *            cost of each herb he missed
     * @return popup with the PURCHASE button
     */
    public Popup addStallPopup(Map map){
        Popup stallPopup = new Popup();
        Rectangle background = addRectangle(400, 600,
                70, (double) SCENE_HEIGHT /2-300);
        stallPopup.getContent().add(background);
        int cost = 0;
        for(Herb herb : map.getHerbs()){
            cost += herb.getCost();
        }
        String startStr = "Hello! You are at the stall where you can find all desired herbs no matter how rare they are!";
        Text startingPhrase = addDescriptionText(startStr, background.getX() +20, background.getY()+20);
        startingPhrase.setWrappingWidth(360);
        if(!map.getHerbs().isEmpty()){
            StringBuilder addition = new StringBuilder(" It seems like you do not have some of the necessary herbs, which are: ");
            for(Herb herb: map.getHerbs()){
                addition.append("\n").append(herb.getName());
            }
            addition.append("\nBut don't be sad, I have those in stock. " + "Would you like to purchase the herbs each costing ?? " + " coins? In total will be only ").append(cost).append(" coins!");
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
        purchaseTxt.setOnMouseClicked(mouseEvent -> {
            if(map.getPlayer().getTotalCoins() >= finalCost){
                map.getStall().setBought(true);
                map.getPlayer().setTotalCoins(map.getPlayer().getTotalCoins()- finalCost);
            }else{
                map.getStall().setBought(false);
            }
            map.getStall().setExited(true);
            stallPopup.hide();
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

    public Rectangle addRectangle(double recWidth, double recHeight, double x, double y){
        Rectangle background = new Rectangle(recWidth, recHeight);
        background.setFill(Color.WHEAT);
        background.setOpacity(0.4);
        background.setX(x);
        background.setY(y);
        return background;
    }

    /**
     * adding phrase to the pane to know if the player won or nor
     * @param isExited to know if the player bought missing herbs in case
     *                 of gameStatus being equal to FAIL
     * @param gameStatus to choose the phrase according to the game status
     * @return chosen phrase
     */
    public String choosePhrase(boolean isExited, GameStatus gameStatus){
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

    //to het the player know that thief stole all of his money
    public void thiefPopUp(){
        thiefPopup = new Popup();
        String phrase = "thief stole all of your coins!";
        Text txt = new Text(phrase);
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

    public Text getAcquiredCoins() {
        return acquiredCoins;
    }

    public ImageView getAcquiredCoinsImg() {
        return acquiredCoinsImg;
    }
}
