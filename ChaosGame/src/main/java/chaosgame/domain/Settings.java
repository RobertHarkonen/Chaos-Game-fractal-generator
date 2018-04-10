

package chaosgame.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Settings {
    private int width;
    private int height;
    private List<Node> anchors;
    private double ratio;
    private Random rand;
    
    public Settings(double rat) {
        this.width = 800;
        this.height = 800;
        this.anchors = new ArrayList<>();
        this.ratio = rat;
        this.rand = new Random();
    }
    
    public void addAnchor(int x, int y) {           //can create duplicate anchors
        anchors.add(new Node(x, y, Nodetype.ANCHOR));
    }
    
    public void addAnchor(Node anchor) {
        if (!anchors.contains(anchor) && anchor.getType() == Nodetype.ANCHOR) {
            anchors.add(anchor);
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public double getRatio() {
        return this.ratio;
    }
    
    public void setRatio(double rat) {
        this.ratio = rat;
    }
    
    public void removeAnchors() {
        anchors = new ArrayList<>();
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
