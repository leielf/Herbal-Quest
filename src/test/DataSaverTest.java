import com.example.elixirapp.GameEntity.Coin;
import com.example.elixirapp.DataSaver;
import com.example.elixirapp.GameEntity.Herb;
import com.example.elixirapp.GameEntity.Player;
import javafx.application.Platform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DataSaverTest extends ApplicationTest {

    private DataSaver dataSaver;
    private Player player;
    private Herb herb1, herb2;
    private Coin coin1, coin2;
    private File tempFile;

    @Start
    public void start() {
    }

    @BeforeEach
    public void setUp() {
        Platform.runLater(() -> {
            dataSaver = new DataSaver();
            player = new Player(50, 50, 10);
            herb1 = new Herb("mint", 100, 100, 12);
            herb2 = new Herb("ginger", 200, 230, 14);
            coin1 = new Coin(10, 112, 455);
            coin2 = new Coin(20, 123, 556);

            player.getHerbs().add(herb1);
            player.getHerbs().add(herb2);
            player.getCoins().add(coin1);
            player.getCoins().add(coin2);
        });

        try {
            tempFile = File.createTempFile("test", ".json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tempFile.deleteOnExit();
    }

    @Test
    public void testSaveHerbs() {
        Platform.runLater(() -> {
            JSONArray herbsArray = dataSaver.saveHerbs(player.getHerbs());

            assertEquals(2, herbsArray.size());

            JSONObject herbJson1 = (JSONObject) herbsArray.get(0);
            assertEquals("mint", herbJson1.get("name"));
            assertEquals(12, ((Number) herbJson1.get("cost")).intValue());

            JSONObject herbJson2 = (JSONObject) herbsArray.get(1);
            assertEquals("ginger", herbJson2.get("name"));
            assertEquals(14, ((Number) herbJson2.get("cost")).intValue());
        });
    }

    @Test
    public void testSaveData() {
        Platform.runLater(() -> {
            dataSaver.saveData(player);
            File file = new File("data.json");
            assertTrue(file.exists());
        });
    }

    @Test
    public void testSaveCoins() {
        Platform.runLater(() -> {
            JSONArray coinsArray = dataSaver.saveCoins(player.getCoins());

            assertEquals(2, coinsArray.size());

            JSONObject coinJson1 = (JSONObject) coinsArray.get(0);
            assertEquals(10, ((Number) coinJson1.get("value")).intValue());

            JSONObject coinJson2 = (JSONObject) coinsArray.get(1);
            assertEquals(20, ((Number) coinJson2.get("value")).intValue());
        });
    }
}
