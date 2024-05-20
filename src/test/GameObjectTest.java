import com.example.elixirapp.GameEntity.GameObject;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectTest extends ApplicationTest {

    private GameObject gameObject;

    private class TestGameObject extends GameObject {
        public TestGameObject(String imagePath, double x, double y) {
            super(imagePath, x, y);
        }
    }

    @Override
    public void start(Stage stage) {
    }

    @BeforeEach
    public void setUp() {
        Platform.runLater(() -> {
            gameObject = new TestGameObject("/cross.png", 100, 100);
        });
    }

    @Test
    public void testGetX() {
        Platform.runLater(() -> assertEquals(100, gameObject.getX(), 0));
    }

    @Test
    public void testSetX() {
        Platform.runLater(() -> {
            gameObject.setX(200);
            assertEquals(200, gameObject.getX(), 0);
        });
    }

    @Test
    public void testGetY() {
        Platform.runLater(() -> assertEquals(100, gameObject.getY(), 0));
    }

    @Test
    public void testSetY() {
        Platform.runLater(() -> {
            gameObject.setY(200);
            assertEquals(200, gameObject.getY(), 0);
        });
    }

    @Test
    public void testIsFalling() {
        Platform.runLater(() -> assertFalse(gameObject.isFalling()));
    }

    @Test
    public void testSetFalling() {
        Platform.runLater(() -> {
            gameObject.setFalling(true);
            assertTrue(gameObject.isFalling());
        });
    }

    @Test
    public void testSetVelY() {
        Platform.runLater(() -> {
            gameObject.setVelY(5);
            assertEquals(5, gameObject.getVelY(), 0);
        });
    }

    @Test
    public void testSetVelX() {
        Platform.runLater(() -> {
            gameObject.setVelX(5);
            assertEquals(5, gameObject.getVelX(), 0);
        });
    }

    @Test
    public void testUpdateLocationWhenFalling() {
        Platform.runLater(() -> {
            gameObject.setFalling(true);
            gameObject.setVelY(1);
            gameObject.updateLocation();
            assertEquals(101, gameObject.getY(), 0);
        });
    }

    @Test
    public void testGetImagePath() {
        Platform.runLater(() -> assertEquals("/cross.png", gameObject.getImagePath()));
    }

    @Test
    public void testSetImagePath() {
        Platform.runLater(() -> {
            gameObject.setImagePath("new/path/to/your/image.png");
            assertEquals("new/path/to/your/image.png", gameObject.getImagePath());
        });
    }

    @Test
    public void testGetTopBounds() {
        Platform.runLater(() -> {
            Rectangle topBounds = gameObject.getTopBounds();
            assertNotNull(topBounds);
        });
    }

    @Test
    public void testGetBottomBounds() {
        Platform.runLater(() -> {
            Rectangle bottomBounds = gameObject.getBottomBounds();
            assertNotNull(bottomBounds);
        });
    }

    @Test
    public void testGetLeftBounds() {
        Platform.runLater(() -> {
            Rectangle leftBounds = gameObject.getLeftBounds();
            assertNotNull(leftBounds);
        });
    }

    @Test
    public void testGetRightBounds() {
        Platform.runLater(() -> {
            Rectangle rightBounds = gameObject.getRightBounds();
            assertNotNull(rightBounds);
        });
    }

    @Test
    public void testGetBounds() {
        Platform.runLater(() -> {
            Rectangle bounds = gameObject.getBounds();
            assertNotNull(bounds);
        });
    }

    @Test
    public void testSetGravityAcc() {
        Platform.runLater(() -> {
            gameObject.setGravityAcc(0.5);
            assertEquals(0.5, gameObject.getGravityAcc(), 0);
        });
    }

    @Test
    public void testSetWidthHeight() {
        Platform.runLater(() -> {
            gameObject.setWidthHeight();
            assertTrue(gameObject.getWidth() > 0);
            assertTrue(gameObject.getHeight() > 0);
        });
    }
}
