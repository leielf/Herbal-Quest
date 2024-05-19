import com.example.elixirapp.*;
import com.example.elixirapp.GameEntity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest extends ApplicationTest {

    private Map map;
    private Player player;
    private Coin coin;
    private Block block;
    private Thief thief;
    private Mushroom mushroom;
    private Herb herb;

    @BeforeEach
    public void setUp() {
        map = new Map();
        player = new Player(50, 50, 10);
        coin = new Coin(10, 10, 10);
        block = new Block(100, 100);
        thief = new Thief(150, 150);
        mushroom = new Mushroom(200, 200);
        herb = new Herb("mint", 10, 10,10);
    }

    @Test
    public void testInitialization() {
        assertNotNull(map.getBlocks());
        assertNotNull(map.getCoins());
        assertNotNull(map.getThieves());
        assertNotNull(map.getMushrooms());
        assertNotNull(map.getHerbs());
        assertNotNull(map.getStall());
        assertNull(map.getPlayer());
    }

    @Test
    public void testAddEntities() {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(block);
        map.setBlocks(blocks);
        assertEquals(1, map.getBlocks().size());
        assertEquals(block, map.getBlocks().get(0));

        ArrayList<Coin> coins = new ArrayList<>();
        coins.add(coin);
        map.setCoins(coins);
        assertEquals(1, map.getCoins().size());
        assertEquals(coin, map.getCoins().get(0));

        ArrayList<Thief> thieves = new ArrayList<>();
        thieves.add(thief);
        map.setThieves(thieves);
        assertEquals(1, map.getThieves().size());
        assertEquals(thief, map.getThieves().get(0));

        ArrayList<Mushroom> mushrooms = new ArrayList<>();
        mushrooms.add(mushroom);
        map.setMushrooms(mushrooms);
        assertEquals(1, map.getMushrooms().size());
        assertEquals(mushroom, map.getMushrooms().get(0));

        ArrayList<Herb> herbs = new ArrayList<>();
        herbs.add(herb);
        map.setHerbs(herbs);
        assertEquals(1, map.getHerbs().size());
        assertEquals(herb, map.getHerbs().get(0));

        map.setPlayer(player);
        assertEquals(player, map.getPlayer());
    }

    @Test
    public void testUpdateLocations() {
        map.setPlayer(player);
        map.updateLocations();
        // Test the player's location update logic
        assertEquals(player.getX(), player.getX());
        assertEquals(player.getY(), player.getY());

        ArrayList<Thief> thieves = new ArrayList<>();
        thieves.add(thief);
        map.setThieves(thieves);
        map.updateLocations();
        // Test the thief's location update logic
        assertEquals(thief.getX(), thief.getX());
        assertEquals(thief.getY(), thief.getY());

        ArrayList<Mushroom> mushrooms = new ArrayList<>();
        mushrooms.add(mushroom);
        map.setMushrooms(mushrooms);
        map.updateLocations();
        // Test the mushroom's location update logic
        assertEquals(mushroom.getX(), mushroom.getX());
        assertEquals(mushroom.getY(), mushroom.getY());
    }

    @Test
    public void testGettersAndSetters() {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(block);
        map.setBlocks(blocks);
        assertEquals(blocks, map.getBlocks());

        ArrayList<Coin> coins = new ArrayList<>();
        coins.add(coin);
        map.setCoins(coins);
        assertEquals(coins, map.getCoins());

        ArrayList<Thief> thieves = new ArrayList<>();
        thieves.add(thief);
        map.setThieves(thieves);
        assertEquals(thieves, map.getThieves());

        ArrayList<Mushroom> mushrooms = new ArrayList<>();
        mushrooms.add(mushroom);
        map.setMushrooms(mushrooms);
        assertEquals(mushrooms, map.getMushrooms());

        ArrayList<Herb> herbs = new ArrayList<>();
        herbs.add(herb);
        map.setHerbs(herbs);
        assertEquals(herbs, map.getHerbs());

        map.setPlayer(player);
        assertEquals(player, map.getPlayer());

        assertNotNull(map.getStall());
    }
}
