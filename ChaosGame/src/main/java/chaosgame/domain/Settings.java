/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chaosgame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Settings {
    private List<Node> anchors;
    private double ratio;
    private Random rand;
    
    public Settings(double rat) {
        this.anchors = new ArrayList<>();
        this.ratio = rat;
        this.rand = new Random();
    }
    
    public void addAnchor(int x, int y) {
        anchors.add(new Node(x, y, Nodetype.ANCHOR));
    }
    
    public void removeAnchors() {
        anchors = new ArrayList<>();
    }
    
    public double getRatio() {
        return this.ratio;
    }
    
    public void setRatio(double rat) {
        this.ratio = rat;
    }
    
    public Node getFirstAnchor() {
        if (anchors.isEmpty()) {
            return new Node(0, 0, Nodetype.EMPTY);
        }
        return anchors.get(0);
    }
    
    public List<Node> getAnchors() {
        return this.anchors;
    }
    
    public Node getRandomAnchor() {
        if (anchors.isEmpty()) {
            return new Node(0, 0, Nodetype.EMPTY);
        }
        return anchors.get(rand.nextInt(anchors.size()));
    }
}
