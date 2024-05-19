import com.example.elixirapp.*;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class LevelControllerTest extends ApplicationTest {

//    private LevelController levelController;
//    private Stage stage;
//
//    @Start
//    public void start(Stage stage) {
//        this.stage = stage;
//
//    }
//
//    @BeforeEach
//    public void setUp() {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                stage = new Stage();
//                levelController= new LevelController(new GameEngine(stage), new Map());
//                levelController.createLevel(levelController.getStage());
//            }
//        });
//    }
//
//    @Test
//    public void testCheckCollisions() {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                levelController= new LevelController(new GameEngine(new Stage()), new Map());
//                levelController.createLevel(levelController.getStage());
//                levelController.getMap().setBlocks(new ArrayList<>());
//                levelController.getMap().setCoins(new ArrayList<>());
//                levelController.getMap().setThieves(new ArrayList<>());
//                levelController.getMap().setMushrooms(new ArrayList<>());
//                levelController.getMap().setHerbs(new ArrayList<>());
//
//                levelController.checkCollisions();
//                assertTrue(levelController.getMap().getBlocks().isEmpty());
//                assertTrue(levelController.getMap().getCoins().isEmpty());
//                assertTrue(levelController.getMap().getThieves().isEmpty());
//                assertTrue(levelController.getMap().getMushrooms().isEmpty());
//                assertTrue(levelController.getMap().getHerbs().isEmpty());
//            }
//        });
//    }
//
//    @Test
//    public void testCheckHerbsCollisions() {
//        Player player = new Player(11, 12, 13);
//        levelController.getMap().setPlayer(player);
//        Herb herb = new Herb("mint", 10, 6, 3);
//        levelController.getMap().setHerbs(new ArrayList<>() {{ add(herb); }});
//        ImageView herbView = new ImageView();
//        levelController.getSceneController().getHerbsImgView().add(herbView);
//        levelController.checkHerbsCollisions();
//        assertTrue(player.getHerbs().contains(herb));
//        assertFalse(levelController.getMap().getHerbs().contains(herb));
//        assertFalse(levelController.getSceneController().getHerbsImgView().contains(herbView));
//    }
//
//    @Test
//    public void testCheckStallCollisions() {
//        Player player = new Player(10, 12, 11);
//        levelController.getMap().setPlayer(player);
//        levelController.getMap().getStall().setX(9);
//        levelController.getMap().getStall().setY(10);
//        levelController.getMap().setHerbs(new ArrayList<>());
//        levelController.checkStallCollisions();
//        assertEquals(GameStatus.WIN, levelController.getGameEngine().getGameStatus());
//    }
//
//    @Test
//    public void testCheckBottomCollisions() {
//        Player player = new Player(10, 12, 11);
//        levelController.getMap().setPlayer(player);
//        levelController.getMap().setBlocks(new ArrayList<>());
//
//        player.setJumping(false);
//        levelController.checkBottomCollisions();
//        assertTrue(player.isFalling());
//    }
//
//    @Test
//    public void testCheckCoinsCollisions() {
//        Player player = new Player(10, 12, 11);
//        levelController.getMap().setPlayer(player);
//        Coin coin = new Coin(8,10,10);
//        levelController.getMap().setCoins(new ArrayList<>() {{ add(coin); }});
//        ImageView coinView = new ImageView();
//        levelController.getSceneController().getCoinsImgView().add(coinView);
//        levelController.checkCoinsCollisions();
//        assertTrue(player.getCoins().contains(coin));
//        assertFalse(levelController.getMap().getCoins().contains(coin));
//        assertFalse(levelController.getSceneController().getCoinsImgView().contains(coinView));
//    }
//
//    @Test
//    public void testCheckThiefCollisions() {
//        Player player = new Player(10, 12, 11);
//        levelController.getMap().setPlayer(player);
//        Thief thief = new Thief(10, 12);
//        levelController.getMap().setThieves(new ArrayList<>() {{ add(thief); }});
//        levelController.checkThiefCollisions();
//        assertEquals(0, player.getTotalCoins());
//        assertTrue(player.getCoins().isEmpty());
//    }
//
//    @Test
//    public void testCheckMushroomCollisions() {
//        Player player = new Player(10, 12, 11);
//        levelController.getMap().setPlayer(player);
//        Mushroom mushroom = new Mushroom(10, 8);
//        levelController.getMap().setMushrooms(new ArrayList<>() {{ add(mushroom); }});
//        levelController.checkMushroomCollisions();
//        assertTrue(player.isDead());
//        assertEquals(GameStatus.FAIL, levelController.getGameEngine().getGameStatus());
//    }
//
//    @Test
//    public void testGameUpdate() {
//        levelController.getGameEngine().setGameStatus(GameStatus.RUNNING);
//
//        levelController.gameUpdate();
//        // Assuming map.updateLocations() and sceneController.updateUI() are void methods
//        assertEquals(GameStatus.RUNNING, levelController.getGameEngine().getGameStatus());
//    }
//
//    @Test
//    public void testCheckGameStatus() {
//        levelController.getGameEngine().setGameStatus(GameStatus.START_SCREEN);
//        levelController.checkGameStatus();
//        assertTrue(levelController.getSceneController().isLoadMap());
//
//        levelController.getGameEngine().setGameStatus(GameStatus.FAIL);
//        levelController.checkGameStatus();
//        assertTrue(levelController.getSceneController().isLoadMap());
//
//        levelController.getMap().getStall().setEntered(true);
//
//        levelController.checkGameStatus();
//        assertEquals(GameStatus.WIN, levelController.getGameEngine().getGameStatus());
//
//        levelController.getMap().getStall().setExited(true);
//        levelController.getMap().getStall().setBought(true);
//        levelController.checkGameStatus();
//        assertEquals(GameStatus.WIN, levelController.getGameEngine().getGameStatus());
//    }
//
//    @Test
//    public void testReset() {
//        Map newMap = new Map();
//        levelController.reset(newMap);
//        assertEquals(newMap, levelController.getMap());
//        assertTrue(levelController.getSceneController().isLoadMap());
//    }
//
//    @Test
//    public void testEndLevel() {
//        levelController.endLevel();
//        assertTrue(levelController.getSceneController().isLoadMap());
//    }
}
