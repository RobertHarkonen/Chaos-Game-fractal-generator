
package chaosgametest.domain;

import chaosgame.domain.Settings;
import chaosgame.domain.Node;
import chaosgame.domain.Nodetype;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert
 */
public class SettingsTest {
    
    Settings settings;
    
    @Before
    public void setUp() {
        settings = new Settings(0.5);
    }
    
    @Test
    public void anchorsInitiallyEmpty() {
        assertTrue(settings.getAnchors().isEmpty());
    }
    
    @Test
    public void initialAspectRatioCorrect() {
        assertEquals(800, settings.getWidth());
        assertEquals(800, settings.getHeight());
    }
    
    @Test
    public void getAndSetRatioTest() {
        settings.setRatio(1.12);
        assertEquals(1.12, settings.getRatio(), 0.0000002);
    }
    
    @Test
    public void getAndSetWidthTest() {
        settings.setWidth(100);
        assertEquals(100, settings.getWidth());
    }
    
    @Test
    public void getAndSetHeightTest() {
        settings.setHeight(100);
        assertEquals(100, settings.getHeight());
    }
    
    @Test
    public void getAndSetKeyTest() {
        settings.setKey("Daim patukka");
        assertEquals("Daim patukka", settings.getKey());
    }
    
    @Test
    public void getAndSetGrainsizeTest() {
        settings.setGrainSize(0.9);
        assertEquals(0.9, settings.getGrainSize(), 0.000002);
    }
    
    @Test
    public void canToggleRepeatRule() {
        settings.toggleRepeatRule();
        assertTrue(settings.getRepeatRule());
        settings.toggleRepeatRule();
        assertFalse(settings.getRepeatRule());
    }
    
    @Test
    public void settingACustomRandomGivesDesiredValues() {
        Random rand = new Random(Long.MAX_VALUE/2); //nextInt(5) gives 3, 0, 4, 4,...
        settings.setRandom(rand);
        settings.addAnchor(new Node(10, 10, Nodetype.ANCHOR));
        settings.addAnchor(new Node(10, 20, Nodetype.ANCHOR));
        settings.addAnchor(new Node(30, 100, Nodetype.ANCHOR));
        settings.addAnchor(new Node(100, 130, Nodetype.ANCHOR));
        settings.addAnchor(new Node(400, 220, Nodetype.ANCHOR));
        
        assertEquals(new Node(100, 130, Nodetype.ANCHOR), settings.getRandomAnchor());
        assertEquals(new Node(10, 10, Nodetype.ANCHOR), settings.getRandomAnchor());
        assertEquals(new Node(400, 220, Nodetype.ANCHOR), settings.getRandomAnchor());
    }
    
    @Test
    public void getFirstAnchorWorksCorrectlyWhenNoAnchorsAdded() {
        Node n = settings.getFirstAnchor();
        assertEquals(Nodetype.EMPTY, n.getType());
        assertEquals(0, n.getX());
        assertEquals(0, n.getY());
    }
    
    @Test
    public void addAnchorCorrectlyAddsAnchors() {
        for (int i = 0; i < 1000; i++) {
            settings.addAnchor(i, 1000 - i);
        }
        assertEquals(1000, settings.getAnchors().size());
    }
    
    @Test
    public void addingTheSameAnchorTwiceDoesntChangeTotal() {
        Node anchor = new Node(0,0, Nodetype.ANCHOR);
        settings.addAnchor(anchor);
        settings.addAnchor(anchor);
        assertEquals(1, settings.getAnchors().size());
    }
    
    @Test
    public void addAnchorOutOfBoundsTest1() {
        Node anchor = new Node(-10, -10, Nodetype.ANCHOR);
        settings.addAnchor(anchor);
        assertTrue(settings.getAnchors().isEmpty());
    }
    
    @Test
    public void addAnchorOutOfBoundsTest2() {
        Node anchor = new Node(100000, -10, Nodetype.ANCHOR);
        settings.addAnchor(anchor);
        assertTrue(settings.getAnchors().isEmpty());
    }
    
    @Test
    public void addAnchorOutOfBoundsTest3() {
        Node anchor = new Node(-10, 100000, Nodetype.ANCHOR);
        settings.addAnchor(anchor);
        assertTrue(settings.getAnchors().isEmpty());
    }
    
    @Test
    public void addAnchorOutOfBoundsTest4() {
        Node anchor = new Node(100000, 100000, Nodetype.ANCHOR);
        settings.addAnchor(anchor);
        assertTrue(settings.getAnchors().isEmpty());
    }
    
    @Test
    public void cannotAddNodeOfWrongType() {
        Node empty = new Node(200, 200, Nodetype.EMPTY);
        settings.addAnchor(empty);
        assertTrue(settings.getAnchors().isEmpty());
    }
    
    @Test
    public void removeAnchorsRemovesAllAnchors() {
        for (int i = 0; i < 5; i++) {
            settings.addAnchor(i, 5 - i);
        }
        settings.removeAnchors();
        assertEquals(0, settings.getAnchors().size());
    }
    
    @Test
    public void getFirstAnchorWorksCorrectlyWhenAnchorsHaveBeenAdded() {
        settings.addAnchor(50, 50);
        Node n = settings.getFirstAnchor();
        assertEquals(Nodetype.ANCHOR, n.getType());
        assertEquals(50, n.getX());
        assertEquals(50, n.getY());
    }
    
    @Test
    public void getRandomAnchorReturnsEmptyWhenNoAnchorsAdded() {
        Node n = settings.getRandomAnchor();
        assertEquals(Nodetype.EMPTY, n.getType());
        assertEquals(0, n.getX());
        assertEquals(0, n.getY());
    }
    
    @Test
    public void getRandomAnchorReturnsAnAnchorWhenAnchorsAdded() {
        for (int i = 0; i < 10; i++) {
            settings.addAnchor(i, i);
        }
        Node n = settings.getRandomAnchor();
        assertEquals(Nodetype.ANCHOR, n.getType());
    }
    
    @Test
    public void getRandomAnchorReturnsMoreThanOneDifferentAnchor() {
        for (int i = 0; i < 1000; i++) {
            settings.addAnchor(i, i);
        }
        boolean equal = true;
        Node n = settings.getRandomAnchor();
        for (int i = 0; i < 999; i++) {
            int prevX = n.getX();
            n = settings.getRandomAnchor();
            if (prevX != n.getX()) {
                equal = false;
            }
        }
        
        assertFalse(equal);
    }
    
    @Test
    public void getRandomAnchorWithRepeatRuleTest1() {     //random node should alternate
        settings.addAnchor(new Node(0, 0, Nodetype.ANCHOR));
        settings.addAnchor(new Node(100, 100, Nodetype.ANCHOR));
        settings.toggleRepeatRule();
        int current = 0;
        for (int i = 0; i < 10; i++) {
            Node next = settings.getRandomAnchor();
            assertEquals(current, next.getX());
            current = 100 - current;
        }
    }
    
    @Test
    public void getRandomAnchorWithRepeatRuleTest2() {
        settings.addAnchor(new Node(0, 0, Nodetype.ANCHOR));
        settings.toggleRepeatRule();
        for (int i = 0; i < 10; i++) {
            Node next = settings.getRandomAnchor(); 
            assertEquals(0, next.getX());
        }
    }
}
