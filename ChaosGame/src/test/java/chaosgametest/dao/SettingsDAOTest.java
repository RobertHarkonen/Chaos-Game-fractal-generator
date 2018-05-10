
package chaosgametest.dao;

import chaosgame.dao.SettingsDAO;
import chaosgame.domain.Node;
import chaosgame.domain.Nodetype;
import chaosgame.domain.Settings;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class SettingsDAOTest {
    
    static SettingsDAO dao;
    
    @BeforeClass
    public static void setUpClass() {
        dao = new SettingsDAO("TestDatabase");
    }
    
    @After
    public void setUp() {
        dao.removeFromDatabase("testSettings");
    }
    
    @Test
    public void savingAndLoadingSettingsRetainsAnchors() {
        Settings s = new Settings(0.5);
        s.addAnchor(new Node(100, 100, Nodetype.ANCHOR));
        s.addAnchor(new Node(200, 200, Nodetype.ANCHOR));
        s.addAnchor(new Node(300, 300, Nodetype.ANCHOR));
        s.setKey("testSettings");
        dao.saveToDatabase(s);
        Settings saved = dao.getFromDatabase("testSettings");
        
        assertEquals(3, saved.getAnchors().size());
    }
    
    @Test
    public void savingAndLoadingSettingsRetainsOtherParameters() {
        Settings s = new Settings(0.75);
        s.setGrainSize(0.9);
        s.toggleRepeatRule();
        s.setKey("testSettings");
        dao.saveToDatabase(s);
        Settings saved = dao.getFromDatabase("testSettings");
        
        assertEquals(0.75, saved.getRatio(), 0.0000002);
        assertEquals(0.9, saved.getGrainSize(), 0.0000002);
        assertTrue(saved.getRepeatRule());
    }
    
    @Test
    public void updatingSameSettingsWorks() {
        Settings s = new Settings(0.5);
        s.setKey("testSettings");
        dao.saveToDatabase(s);
        s.addAnchor(new Node(10, 10, Nodetype.ANCHOR));
        s.setRatio(1.5);
        dao.saveToDatabase(s);
        
        Settings saved = dao.getFromDatabase("testSettings");
        assertEquals(10, saved.getFirstAnchor().getX());
        assertEquals(1.5, saved.getRatio(), 0.0000002);
    }
    
    @Test
    public void removingNonexistentSettingsDoesNothing() {
        dao.removeFromDatabase("this shouldn't exist");     //only fails if the method causes an unhandled exception
    }
    
    @Test
    public void loadingNonexistentSettingsReturnsDefault() {
        Settings saved = dao.getFromDatabase("this shouldn't exist");
        assertEquals(0.5, saved.getRatio(), 0.0000002);
        assertEquals(0, saved.getAnchors().size());
    }
    
    @Test
    public void getSettingsKeysGivesEmptyListIfNoSettingsStored() {
        List<String> settingsKeys = dao.getSettingsKeys();
        assertTrue(settingsKeys.isEmpty());
    }
    
    @Test
    public void getSettingsKeysGivesCorrectKeys() {
        Settings s = new Settings(0.5);
        Settings w = new Settings(0.5);
        s.setKey("def");
        w.setKey("abc");
        dao.saveToDatabase(s);
        dao.saveToDatabase(w);
        List<String> settingsKeys = dao.getSettingsKeys();
        assertEquals(settingsKeys.get(0), "abc");
        assertEquals(settingsKeys.get(1), "def");
        dao.removeFromDatabase("abc");
        dao.removeFromDatabase("def");
    }
}
