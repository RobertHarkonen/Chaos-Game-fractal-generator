
package chaosgametest.domain;

import chaosgame.domain.Settings;
import chaosgame.domain.Node;
import chaosgame.domain.Nodetype;
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
    public void getAndSetRatioWorksCorrectly() {
        settings.setRatio(1.12);
        assertEquals(1.12, settings.getRatio(), 0.0000002);
    }
    
    @Test
    public void getAndSetWidthWorksCorrecly() {
        settings.setHeight(100);
        assertEquals(100, settings.getHeight());
        settings.setWidth(100);
        assertEquals(100, settings.getWidth());
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
}
