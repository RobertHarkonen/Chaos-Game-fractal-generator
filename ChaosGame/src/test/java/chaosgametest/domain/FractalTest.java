
package chaosgametest.domain;

import chaosgame.domain.*;
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
        assertEquals(800, fractal.getSettings());
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
}
