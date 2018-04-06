/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chaosgame.domain;


public class Node {
    private int x;
    private int y;
    private Nodetype type;

    public Node(int x, int y, Nodetype type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Nodetype getType() {
        return type;
    }

    public void setType(Nodetype type) {
        this.type = type;
    }
    
    
}
