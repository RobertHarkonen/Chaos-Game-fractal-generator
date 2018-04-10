
package chaosgametest.domain;

import chaosgame.domain.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert
 */
public class FractalTest {
    
    Fractal fractal;
    
    @Before
    public void setUp() {
        //fractal = new Fractal(800, 800);
    }
    
    @Test
    public void illegalSizeArgumentsCreateDefaultSize() {
        fractal = new Fractal(-10, -10);
        assertEquals(800, fractal.getWidth());
        assertEquals(800, fractal.getHeight());
        fractal = new Fractal(10000, 10000);
        assertEquals(800, fractal.getWidth());
        assertEquals(800, fractal.getHeight());
    }
    
    @Test
    public void constructorSetsParametersCorrectly() {
        fractal = new Fractal(100, 100);
        for (int i = 0; i < fractal.getWidth(); i++) {
            for (int j = 0; j < fractal.getHeight(); j++) {
                assertEquals(Nodetype.EMPTY, fractal.getNode(i, j).getType());
            }
        }
        
        assertEquals(0, fractal.getCurrentX(), 0.00000002);
        assertEquals(0, fractal.getCurrentY(), 0.00000002);
    }
    
    @Test
    public void defaultConstructorSetsDefaultSettings() {
        fractal = new Fractal(100, 100);
        assertEquals(0.5, fractal.getSettings().getRatio(), 0.00000002);
        Node n = fractal.getSettings().getFirstAnchor();
        assertEquals(0, n.getX());
        assertEquals(0, n.getY());
        assertEquals(Nodetype.EMPTY, n.getType());
        assertEquals(100, fractal.getSettings().getHeight());
        assertEquals(100, fractal.getSettings().getWidth());
    }
    
    @Test
    public void customConstructorSetsCustomSettings() {
        Settings s = new Settings(0.85);
        s.setHeight(700);
        s.setWidth(700);
        s.addAnchor(12, 30);
        s.addAnchor(300, 150);
        s.addAnchor(50, 450);
        
        fractal = new Fractal(s);
        assertEquals(700, fractal.getWidth());
        assertEquals(700, fractal.getHeight());
        assertEquals(Nodetype.ANCHOR, fractal.getSettings().getFirstAnchor().getType());
        assertEquals(3, fractal.getSettings().getAnchors().size());
    }
    
    @Test
    public void addAnchorAddsAnchorAndUpdatesCurrent() {
        fractal = new Fractal(100, 100);
        fractal.addAnchor(50, 50);
        assertEquals(Nodetype.ANCHOR, fractal.getNode(50, 50).getType());
        assertEquals(1, fractal.getSettings().getAnchors().size());
        assertEquals(50, fractal.getCurrentX(), 0.00000002);
        assertEquals(50, fractal.getCurrentY(), 0.00000002);
    }
    
    @Test
    public void addingTheSameAnchorDoesntChangeTotal() {
        fractal = new Fractal(100, 100);
        fractal.addAnchor(0, 0);
        fractal.addAnchor(0, 0);
        assertEquals(1, fractal.getSettings().getAnchors().size());
    }
    
    @Test
    public void setTypeOutOfBoundsTest() {
        fractal = new Fractal(100, 100);
        boolean b = fractal.setType(-10, 200, Nodetype.FILLED);
        assertFalse(b);
    }
    
    @Test
    public void addAnchorOutOfBoundsTest() {
        fractal = new Fractal(100, 100);
        fractal.addAnchor(-10, 200);
        assertTrue(fractal.getSettings().getAnchors().isEmpty());
    }
    
    @Test
    public void setTypeTest() {
        fractal = new Fractal(100, 100);
        fractal.setType(10, 10, Nodetype.FILLED);
        assertEquals(Nodetype.FILLED, fractal.getNode(10, 10).getType());
    }
    
    @Test
    public void setTypeAllTest() {
        fractal = new Fractal(100, 100);
        fractal.setTypeAll(Nodetype.FILLED);
        for (int i = 0; i < fractal.getWidth(); i++) {
            for (int j = 0; j < fractal.getHeight(); j++) {
                assertEquals(Nodetype.FILLED, fractal.getNode(i, j).getType());
            }
        }
    }
    
    @Test
    public void getNodesWithTypeReturnsCorrectNodes() {
        fractal = new Fractal(100, 100);
        fractal.setType(0, 0, Nodetype.FILLED);
        fractal.setType(10, 10, Nodetype.FILLED);
        fractal.setType(10, 10, Nodetype.FILLED);
        fractal.setType(20, 20, Nodetype.FILLED);
        ArrayList<Node> filled = fractal.getNodesWithType(Nodetype.FILLED);
        assertEquals(3, filled.size());
        for (Node node : filled) {
            assertEquals(Nodetype.FILLED, node.getType());
        }
    }
    
    @Test
    public void removeFilledCorrectlyRemovesFilledNodes() {
        fractal = new Fractal(100, 100);
        fractal.setTypeAll(Nodetype.FILLED);
        fractal.removeFilled();
        for (int i = 0; i < fractal.getWidth(); i++) {
            for (int j = 0; j < fractal.getHeight(); j++) {
                assertEquals(Nodetype.EMPTY, fractal.getNode(i, j).getType());
            }
        }
    }
    
    @Test
    public void iterateTest1() {
        fractal = new Fractal(100, 100);
        fractal.addAnchor(0, 0);
        fractal.addAnchor(0, 50);
        fractal.addAnchor(50, 0);
        fractal.addAnchor(50, 50);
        for (int i = 0; i < 100; i++) {
            fractal.iterate();
        }
        assertTrue(fractal.getCurrentX() > 0);
        assertTrue(fractal.getCurrentY() > 0);
    }
    
    @Test
    public void iterateTest2() {
        fractal = new Fractal(100, 100);
        fractal.addAnchor(0, 0);
        fractal.addAnchor(0, 50);
        for (int i = 0; i < 100; i++) {
            fractal.iterate();
        }
        assertEquals(Nodetype.FILLED, fractal.getNode(0, 25).getType());
    }
    
    @Test
    public void iterateTest3() {
        fractal = new Fractal(100, 100);
        fractal.addAnchor(0, 0);
        fractal.iterate();
        assertEquals(Nodetype.ANCHOR, fractal.getNode(0, 0).getType());
    }
    
    @Test
    public void iterateTest4() {
        fractal = new Fractal(100, 100);
        fractal.iterate();
        ArrayList<Node> filled = fractal.getNodesWithType(Nodetype.FILLED);
        assertTrue(filled.isEmpty());
    }
    
    @Test
    public void iterateTest5() {        //draws a sierpinski triangle, checks that the centre is empty
        fractal = new Fractal(800, 800);
        fractal.addAnchor(10, 10);
        fractal.addAnchor(790, 10);
        fractal.addAnchor(400, 600);
        for (int i = 0; i < 10000; i++) {
            fractal.iterate();
        }
        assertEquals(Nodetype.EMPTY, fractal.getNode(400, 200).getType());
    }
    
    @Test
    public void iterateTest6() {
        fractal = new Fractal(100, 100);
        fractal.getSettings().setRatio(1.5);
        fractal.addAnchor(0, 0);
        fractal.addAnchor(0, 20);
        for (int i = 0; i < 100; i++) {
            fractal.iterate();
        }
        assertEquals(Nodetype.FILLED, fractal.getNode(0, 30).getType());
    }
}
