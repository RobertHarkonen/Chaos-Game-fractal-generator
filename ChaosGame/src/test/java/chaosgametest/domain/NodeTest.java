/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chaosgametest.domain;

import chaosgame.domain.Node;
import chaosgame.domain.Nodetype;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert
 */
public class NodeTest {
    
    Node node;
    
    @Before
    public void setUp() {
        node = new Node(0, 0, Nodetype.EMPTY);
    }
    
    @Test
    public void getXAndSetXWorkProperly() {
        node.setX(1000);
        assertEquals(1000, node.getX());
    }
    
    @Test
    public void getYAndSetYWorkProperly() {
        node.setY(1000);
        assertEquals(1000, node.getY());
    }
    
    @Test
    public void startingTypeIsEmpty() {
        assertEquals(Nodetype.EMPTY, node.getType());
    }
    
    @Test
    public void settingTypeToFilledCorrectlyChangesType() {
        node.setType(Nodetype.FILLED);
        assertEquals(Nodetype.FILLED, node.getType());
    }
    
    @Test
    public void settingTypeToAnchorCorrectlyChangesType() {
        node.setType(Nodetype.ANCHOR);
        assertEquals(Nodetype.ANCHOR, node.getType());
    }
    
    
}
