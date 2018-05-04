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

    @Test
    public void hashCodeTest1() {
        Node node2 = new Node(0, 0, Nodetype.EMPTY);
        assertEquals(node.hashCode(), node2.hashCode());
    }

    @Test
    public void hashCodeTest2() {
        Node node2 = new Node(10, 10, Nodetype.EMPTY);
        assertNotEquals(node.hashCode(), node2.hashCode());
    }

    @Test
    public void hashCodeTest3() {
        Node node2 = new Node(0, 0, Nodetype.ANCHOR);
        assertNotEquals(node.hashCode(), node2.hashCode());
    }

    @Test
    public void equalsTest1() {
        Node node2 = new Node(0, 0, Nodetype.EMPTY);
        assertTrue(node.equals(node2));
    }

    @Test
    public void equalsTest2() {
        Node node2 = new Node(45, 21, Nodetype.EMPTY);
        assertFalse(node.equals(node2));
    }
    
    @Test
    public void equalsTest3() {
        Node node2 = null;
        assertFalse(node.equals(node2));
        assertFalse(node.equals(Nodetype.EMPTY));
    }   
    
    @Test
    public void equalsTest4() {
        Node node2 = new Node(0, 0, Nodetype.ANCHOR);
        assertFalse(node.equals(node2));
    }
}
