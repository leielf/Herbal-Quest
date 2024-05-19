package com.example.elixirapp.GameEntity;

import com.example.elixirapp.GameStatus;
import com.example.elixirapp.Map;
import com.example.elixirapp.SceneCreator;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

public class SceneCreatorTest extends ApplicationTest {

    private SceneCreator sceneCreator;

    @BeforeEach
    public void setUp() {
        sceneCreator = new SceneCreator();
    }

    @Test
    public void testCreatePane() {
        Pane pane = sceneCreator.createPane(100);
        assertNotNull(pane);
        assertEquals(SceneCreator.SCENE_WIDTH * 3 - 5, pane.getMinWidth());
        assertEquals(SceneCreator.SCENE_HEIGHT, pane.getMinHeight());
    }

    @Test
    public void testAddClip() {
        Scene scene = new Scene(new Pane(), SceneCreator.SCENE_WIDTH, SceneCreator.SCENE_HEIGHT);
        Pane pane = new Pane();
        Rectangle clip = sceneCreator.addClip(scene, pane);
        assertNotNull(clip);
        assertEquals(SceneCreator.SCENE_WIDTH, clip.getWidth());
        assertEquals(SceneCreator.SCENE_HEIGHT, clip.getHeight());
    }

    @Test
    public void testCreatePaneWithText() {
        Pane pane = sceneCreator.createPaneWithText("Test Text", 100);
        assertNotNull(pane);
        assertTrue(pane.getChildren().stream().anyMatch(node -> node instanceof Text));
    }

    @Test
    public void testAddCoinsCounter() {
        Pane pane = new Pane();
        sceneCreator.addCoinsCounter(pane);
        assertNotNull(sceneCreator.getAcquiredCoins());
        assertNotNull(sceneCreator.getAcquiredCoinsImg());
        assertTrue(pane.getChildren().contains(sceneCreator.getAcquiredCoins()));
        assertTrue(pane.getChildren().contains(sceneCreator.getAcquiredCoinsImg()));
    }

    @Test
    public void testAddDescriptionPopUp() {
        sceneCreator.addDescriptionPopUp();
        Popup descriptionPopup = sceneCreator.getDescription();
        assertNotNull(descriptionPopup);
        assertFalse(descriptionPopup.isShowing());
    }

    @Test
    public void testAddStallPopup() {
        // Assume Map and Herb classes are implemented
        Map map = new Map();
        map.getHerbs().add(new Herb("lavender", 10, 7, 10));
        map.getHerbs().add(new Herb("mint", 20, 6, 11));
        Popup stallPopup = sceneCreator.addStallPopup(map);
        assertNotNull(stallPopup);
    }

    @Test
    public void testThiefPopUp() {
        sceneCreator.thiefPopUp();
        Popup thiefPopup = sceneCreator.getThiefPopup();
        assertNotNull(thiefPopup);
        assertFalse(thiefPopup.isShowing());
    }

    @Test
    public void testChoosePhrase() {
        String failPhrase = sceneCreator.choosePhrase(false, GameStatus.FAIL);
        assertEquals("YOU STEPPED ON THE MUSHROOM AND DIED! PRESS SPACE TO TRY AGAIN!", failPhrase);

        String winPhrase = sceneCreator.choosePhrase(false, GameStatus.WIN);
        assertEquals("CONGRATS! YOU PICKED ALL NEEDED HERBS!", winPhrase);

        String startPhrase = sceneCreator.choosePhrase(false, GameStatus.START_SCREEN);
        assertEquals("PRESS SPACE TO START", startPhrase);
    }
}
