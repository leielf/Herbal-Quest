import com.example.elixirapp.*;
import com.example.elixirapp.GameEntity.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SceneControllerTest extends ApplicationTest {

    private SceneController sceneController;
    private GameEngine gameEngine;
    private Map map;
    private Stage stage;
    private Player player;
    private Coin coin;
    private Block block;
    private Thief thief;
    private Mushroom mushroom;
    private Herb herb;
    private Stall stall;

    @Start
    public void start(Stage stage) {
        this.stage = stage;
    }

    @BeforeEach
    public void setUp() {
        gameEngine = new GameEngine(stage);
        map = new Map();
        player = new Player(50, 50, 10);
        coin = new Coin(10, 10, 10);
        block = new Block(100, 100);
        thief = new Thief(150, 150);
        mushroom = new Mushroom(200, 200);
        herb = new Herb("mint", 10, 10, 10);
        stall = new Stall(250, 250);

        map.setPlayer(player);
        map.setCoins(new ArrayList<>() {{ add(coin); }});
        map.setBlocks(new ArrayList<>() {{ add(block); }});
        map.setThieves(new ArrayList<>() {{ add(thief); }});
        map.setMushrooms(new ArrayList<>() {{ add(mushroom); }});
        map.setHerbs(new ArrayList<>() {{ add(herb); }});
        map.getStall().setY(250);
        map.getStall().setX(250);
        sceneController = new SceneController(map, gameEngine);
        sceneController.fillImgArrays();
    }

    @Test
    public void testAddGameObject() {
        Platform.runLater(() -> {
            stage.setScene(new Scene(new Pane()));
            sceneController.addGameObject(coin);
            assertEquals(2, sceneController.getCoinsImgView().size());
        });
    }

    @Test
    public void testSwitchScreen() {
        Platform.runLater(() -> {
            stage.setScene(new Scene(new Pane()));
            gameEngine.setGameStatus(GameStatus.START_SCREEN);
            sceneController.switchScreen();
            assertEquals(GameStatus.START_SCREEN, gameEngine.getGameStatus());

            gameEngine.setGameStatus(GameStatus.FAIL);
            sceneController.switchScreen();
            assertEquals(GameStatus.FAIL, gameEngine.getGameStatus());

            gameEngine.setGameStatus(GameStatus.WIN);
            sceneController.switchScreen();
            assertEquals(GameStatus.WIN, gameEngine.getGameStatus());
        });
    }

    @Test
    public void testUpdateUI() {
        Platform.runLater(() -> {
            stage.setScene(new Scene(new Pane()));
            sceneController.createMap();
            sceneController.addGameObject(map.getPlayer());
            sceneController.getMap().getPlayer().setX(100);
            sceneController.getMap().getPlayer().setY(100);
            sceneController.updateUI();
            assertEquals(100, sceneController.getPlayerImgView().getX());
            assertEquals(100, sceneController.getPlayerImgView().getY());
        });
    }

    @Test
    public void testProcessKey() {
        Platform.runLater(() -> {
            stage.setScene(new Scene(new Pane()));
            sceneController.createMap();

            sceneController.processKey(KeyCode.RIGHT, false);
            assertFalse(player.isRight());

            sceneController.processKey(KeyCode.LEFT, false);
            assertFalse(player.isLeft());
        });
    }

    @Test
    public void testClearImgArrays() {
        Platform.runLater(() -> {
            stage.setScene(new Scene(new Pane()));
            sceneController.createMap();
            sceneController.clearImgArrays();
            assertTrue(sceneController.getCoinsImgView().isEmpty());
            assertTrue(sceneController.getThievesImgView().isEmpty());
            assertTrue(sceneController.getMushroomsImgView().isEmpty());
            assertTrue(sceneController.getBlocksImgView().isEmpty());
            assertTrue(sceneController.getHerbsImgView().isEmpty());
        });
    }

    @Test
    public void testShowDescriptionPopup() {
        Platform.runLater(() -> {
            stage.setScene(new Scene(new Pane()));
            sceneController.showDescriptionPopup();
            assertTrue(sceneController.isDescriptionShowing());
        });
    }

    @Test
    public void testShowThiefPopup() {
        Platform.runLater(() -> {
            stage.setScene(new Scene(new Pane()));
            sceneController.createMap();
            sceneController.getSceneCreator().thiefPopUp();
            sceneController.showThiefPopup();
            Popup thiefPopup = sceneController.getSceneCreator().getThiefPopup();
            // Wait for the popup to hide
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assertFalse(thiefPopup.isShowing());
        });
    }

    @Test
    public void testPlayerMovement() {
        Platform.runLater(() -> {
            stage.setScene(new Scene(new Pane()));
            sceneController.createMap();
            player.setRight(true);
            sceneController.playerMovement();
            assertTrue(sceneController.getPlayerImgView().getImage().getUrl().contains("/player_right"));
            player.setLeft(true);
            sceneController.playerMovement();
            assertTrue(sceneController.getPlayerImgView().getImage().getUrl().contains("/player_left"));
        });
    }
}
