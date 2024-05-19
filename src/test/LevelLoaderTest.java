import com.example.elixirapp.*;
import com.example.elixirapp.GameEntity.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class LevelLoaderTest extends ApplicationTest {

    private LevelLoader levelLoader;
    private final String testFilePath = "test_level.json";

    @Start
    public void start(Stage stage) {
    }

    @Test
    public void testLoadLevelData() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                levelLoader = new LevelLoader();
                createTestLevelFile();
                levelLoader.loadLevelData(testFilePath);
                Map map = levelLoader.getMap();

                assertNotNull(map);
                assertEquals(1, map.getCoins().size());
                assertEquals(1, map.getBlocks().size());
                assertEquals(1, map.getThieves().size());
                assertEquals(1, map.getMushrooms().size());
                assertEquals(1, map.getHerbs().size());
                assertNotNull(map.getPlayer());

                Coin coin = map.getCoins().get(0);
                assertEquals(10, coin.getValue());
                assertEquals(100, coin.getX(), 0);
                assertEquals(150, coin.getY(), 0);

                Block block = map.getBlocks().get(0);
                assertEquals(200, block.getX(), 0);
                assertEquals(250, block.getY(), 0);

                Thief thief = map.getThieves().get(0);
                assertEquals(300, thief.getX(), 0);
                assertEquals(350, thief.getY(), 0);

                Mushroom mushroom = map.getMushrooms().get(0);
                assertEquals(400, mushroom.getX(), 0);
                assertEquals(450, mushroom.getY(), 0);

                Herb herb = map.getHerbs().get(0);
                assertEquals("Herb1", herb.getName());
                assertEquals(500, herb.getX(), 0);
                assertEquals(550, herb.getY(), 0);
                assertEquals(20, herb.getCost());

                Player player = map.getPlayer();
                assertEquals(100, player.getX(), 0);
                assertEquals(500, player.getY(), 0);
            }
        });
    }

    private void createTestLevelFile() {
        JSONObject jsonLevel = new JSONObject();

        JSONArray coinsArray = new JSONArray();
        JSONObject coin = new JSONObject();
        coin.put("x", 100);
        coin.put("y", 150);
        coin.put("value", 10);
        coinsArray.add(coin);
        jsonLevel.put("coins", coinsArray);

        JSONArray blocksArray = new JSONArray();
        JSONObject block = new JSONObject();
        block.put("x", 200);
        block.put("y", 250);
        blocksArray.add(block);
        jsonLevel.put("blocks", blocksArray);

        JSONArray thievesArray = new JSONArray();
        JSONObject thief = new JSONObject();
        thief.put("x", 300);
        thief.put("y", 350);
        thievesArray.add(thief);
        jsonLevel.put("thieves", thievesArray);

        JSONArray mushroomsArray = new JSONArray();
        JSONObject mushroom = new JSONObject();
        mushroom.put("x", 400);
        mushroom.put("y", 450);
        mushroomsArray.add(mushroom);
        jsonLevel.put("mushrooms", mushroomsArray);

        JSONArray herbsArray = new JSONArray();
        JSONObject herb = new JSONObject();
        herb.put("name", "Herb1");
        herb.put("x", 500);
        herb.put("y", 550);
        herb.put("cost", 20);
        herbsArray.add(herb);
        jsonLevel.put("herbs", herbsArray);

        try (FileWriter file = new FileWriter(testFilePath)) {
            file.write(jsonLevel.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadData() {
        Platform.startup(new Runnable() {
            @Override
            public void run() {
                levelLoader = new LevelLoader();
                JSONObject jsonLevel = new JSONObject();

                JSONArray coinsArray = new JSONArray();
                JSONObject coin = new JSONObject();
                coin.put("x", 100);
                coin.put("y", 150);
                coin.put("value", 10);
                coinsArray.add(coin);
                jsonLevel.put("coins", coinsArray);

                JSONArray blocksArray = new JSONArray();
                JSONObject block = new JSONObject();
                block.put("x", 200);
                block.put("y", 250);
                blocksArray.add(block);
                jsonLevel.put("blocks", blocksArray);

                JSONArray thievesArray = new JSONArray();
                JSONObject thief = new JSONObject();
                thief.put("x", 300);
                thief.put("y", 350);
                thievesArray.add(thief);
                jsonLevel.put("thieves", thievesArray);

                JSONArray mushroomsArray = new JSONArray();
                JSONObject mushroom = new JSONObject();
                mushroom.put("x", 400);
                mushroom.put("y", 450);
                mushroomsArray.add(mushroom);
                jsonLevel.put("mushrooms", mushroomsArray);

                JSONArray herbsArray = new JSONArray();
                JSONObject herb = new JSONObject();
                herb.put("name", "Herb1");
                herb.put("x", 500);
                herb.put("y", 550);
                herb.put("cost", 20);
                herbsArray.add(herb);
                jsonLevel.put("herbs", herbsArray);

                levelLoader.loadData(jsonLevel);
                Map map = levelLoader.getMap();

                assertNotNull(map);
                assertEquals(1, map.getCoins().size());
                assertEquals(1, map.getBlocks().size());
                assertEquals(1, map.getThieves().size());
                assertEquals(1, map.getMushrooms().size());
                assertEquals(1, map.getHerbs().size());

                Coin loadedCoin = map.getCoins().get(0);
                assertEquals(10, loadedCoin.getValue());
                assertEquals(100, loadedCoin.getX(), 0);
                assertEquals(150, loadedCoin.getY(), 0);

                Block loadedBlock = map.getBlocks().get(0);
                assertEquals(200, loadedBlock.getX(), 0);
                assertEquals(250, loadedBlock.getY(), 0);

                Thief loadedThief = map.getThieves().get(0);
                assertEquals(300, loadedThief.getX(), 0);
                assertEquals(350, loadedThief.getY(), 0);

                Mushroom loadedMushroom = map.getMushrooms().get(0);
                assertEquals(400, loadedMushroom.getX(), 0);
                assertEquals(450, loadedMushroom.getY(), 0);

                Herb loadedHerb = map.getHerbs().get(0);
                assertEquals("Herb1", loadedHerb.getName());
                assertEquals(500, loadedHerb.getX(), 0);
                assertEquals(550, loadedHerb.getY(), 0);
                assertEquals(20, loadedHerb.getCost());
            }
        });
    }
}
