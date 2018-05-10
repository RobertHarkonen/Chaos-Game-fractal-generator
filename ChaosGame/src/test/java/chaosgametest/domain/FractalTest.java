
package chaosgametest.domain;

import chaosgame.domain.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FractalTest {
    
    Fractal fractal;
    
    @Before
    public void setUp() {
        fractal = new Fractal(800, 800);
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
    public void constructorSetsIterationCoordinatesCorrectly() {
        assertEquals(0, fractal.getCurrentX(), 0.00000002);
        assertEquals(0, fractal.getCurrentY(), 0.00000002);
    }
    
    @Test
    public void defaultConstructorSetsDefaultRatio() {
        assertEquals(0.5, fractal.getSettings().getRatio(), 0.00000002);
    }
    
    @Test
    public void defaultConstructorHasEmptyAnchors() {
        Node n = fractal.getSettings().getFirstAnchor();
        assertEquals(0, n.getX());
        assertEquals(0, n.getY());
        assertEquals(Nodetype.EMPTY, n.getType());
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
    public void resetTest() {
        fractal.getSettings().setRatio(1.25);
        fractal.addAnchor(123, 246);
        fractal.addAnchor(300, 45);
        fractal.iterate();
        fractal.reset();
        
        assertEquals(0, fractal.getCurrentX(), 0.0000002);
        assertEquals(0, fractal.getSettings().getAnchors().size());
    }
    
    @Test
    public void loadSettingsGetsRightSettings() {
        
    }
    
    @Test
    public void addAnchorCorrectlyAddsAnchor() {
        fractal.addAnchor(100, 100);
        assertEquals(1, fractal.getSettings().getAnchors().size());
    }
    
    @Test
    public void addAnchorUpdatesCurrentCoordinates() {
        fractal.addAnchor(50, 50);
        assertEquals(50, fractal.getCurrentX(), 0.00000002);
        assertEquals(50, fractal.getCurrentY(), 0.00000002);
    }
    
    @Test
    public void addingTheSameAnchorDoesntChangeTotal() {
        fractal.addAnchor(0, 0);
        fractal.addAnchor(0, 0);
        assertEquals(1, fractal.getSettings().getAnchors().size());
    }
    
    @Test
    public void addAnchorOutOfBoundsTest() {
        fractal.addAnchor(-10, 200);
        assertTrue(fractal.getSettings().getAnchors().isEmpty());
    }
    
    @Test
    public void getAndSetGrainSizeTest() {
        fractal.setGrainSize(0.65);
        assertEquals(0.65, fractal.getGrainSize(), 0.0000002);
    }
    
    @Test
    public void getAnchorCoordsTest() {
        fractal.addAnchor(4, 8);
        fractal.addAnchor(15, 16);
        fractal.addAnchor(23, 42);
        int[][] coords = fractal.getAnchorCoords();
        int[][] testcoords = new int[][]{{4,8},{15,16},{23,42}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(testcoords[i][j], coords[i][j]);
            }
        }
    }
    
    @Test
    public void iterateTest1() {
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
        fractal.addAnchor(0, 0);
        fractal.addAnchor(0, 50);
        Nodetype[][] nodes = new Nodetype[60][60];
        for (int i = 0; i < 100; i++) {
            fractal.iterate();
            int roundX = (int) Math.round(fractal.getCurrentX());
            int roundY = (int) Math.round(fractal.getCurrentY());
            nodes[roundX][roundY] = Nodetype.FILLED;
        }
        assertEquals(Nodetype.FILLED, nodes[0][25]);
    }
    
    @Test
    public void iterateTest3() {
        fractal.addAnchor(0, 0);
        fractal.iterate();
        assertEquals(0, fractal.getCurrentX(), 0.000000002);
        assertEquals(0, fractal.getCurrentY(), 0.000000002);
    }
    
    @Test
    public void iterateTest4() {        //draws a sierpinski triangle, checks that the centre is empty
        fractal.addAnchor(10, 10);
        fractal.addAnchor(90, 10);
        fractal.addAnchor(45, 90);
        Nodetype[][] grid = new Nodetype[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                grid[i][j] = Nodetype.EMPTY;
            }
        }
        for (int i = 0; i < 20000; i++) {
            fractal.iterate();
            int roundX = (int) Math.round(fractal.getCurrentX());
            int roundY = (int) Math.round(fractal.getCurrentY());
            grid[roundX][roundY] = Nodetype.FILLED;
        }
        assertEquals(Nodetype.EMPTY, grid[45][40]);
        assertEquals(Nodetype.EMPTY, grid[40][45]);
        assertEquals(Nodetype.EMPTY, grid[35][43]);
    }
    
    @Test
    public void iterateTest5() {
        fractal.getSettings().setRatio(1.5);
        fractal.addAnchor(0, 0);
        fractal.addAnchor(0, 20);
        boolean currentMoved = false;
        for (int i = 0; i < 100; i++) {
            fractal.iterate();
            if (fractal.getCurrentY() >= 29.9995) {
                currentMoved = true;
            }
        }
        assertTrue(currentMoved);
    }
}
