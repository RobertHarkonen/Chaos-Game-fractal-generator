

package chaosgame.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An object type storing all information needed to generate
 * a specific fractal. Note that not all fields are database persisted,
 * some are generated upon construction or when fetching from the database.
 */
@DatabaseTable(tableName = "settings")
public class Settings {
    @DatabaseField(id=true)
    private String key;
    @DatabaseField
    private int width;
    @DatabaseField
    private int height;
    private List<Node> anchors;
    private Node prev;
    @DatabaseField
    private double ratio;
    @DatabaseField
    private double grainSize;
    @DatabaseField
    private boolean repeatRule;
    private Random rand;
    
    /**
     * Empty constructor, needed for ORMlite functionality
     */
    public Settings() {
        
    }
    
    /**
     * Creates default Settings with the given ratio and no anchor points.
     * 
     * @param rat The fractal ratio to be used
     */
    public Settings(double rat) {
        this.key = "default";
        this.width = 800;
        this.height = 800;
        this.anchors = new ArrayList<>();
        this.prev = new Node(0, 0, Nodetype.EMPTY);
        this.ratio = rat;
        this.repeatRule = false;
        this.rand = new Random();
    }
    
    public void addAnchor(int x, int y) {         //can create duplicate anchors, not used
        anchors.add(new Node(x, y, Nodetype.ANCHOR));
    }
    
    /**
     * Adds the given Node to the list of anchors, as long as
     * it is of the right type.
     * @param anchor the Node to be added.
     * @see chaosgame.domain.Fractal#addAnchor(int, int) 
     */
    public void addAnchor(Node anchor) {
        if (!anchors.contains(anchor) && anchor.getType() == Nodetype.ANCHOR) {
            anchors.add(anchor);
            prev = anchor;
        }
    }

    public String getKey() {
        return this.key;
    }
    
    public void setKey(String key) {
        this.key = key;
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

    public double getGrainSize() {
        return grainSize;
    }

    public void setGrainSize(double grainSize) {
        this.grainSize = grainSize;
    }

    public boolean getRepeatRule() {
        return repeatRule;
    }
    
    /**
     * Changes the repeat rule setting from true to false,
     * or vice versa.
     */
    public void toggleRepeatRule() {
        repeatRule = !repeatRule;
    }

    public Node getPrev() {
        return prev;
    }
    
    public void setPrev(Node prev) {
        this.prev = prev;
    }
    
    /**
     * Sets anchor points to a given list.
     * @param anchors The list of anchor points to be used.
     */
    public void setAnchors(List<Node> anchors) {
        this.anchors = anchors;
    }
    
    public void setRandom(Random random) {
        this.rand = random;
    }
    
    /**
     * Sets the list of anchors to an empty list, effectively removing
     * all anchor points.
     */
    public void removeAnchors() {
        anchors = new ArrayList<>();
        prev = new Node(0, 0, Nodetype.EMPTY);
    }
    
    /**
     * Gets the first anchor point.
     * @return The first Node in the list of anchor points, or an empty node
     * a coordinates (0, 0) if the list is empty.
     */
    public Node getFirstAnchor() {
        if (anchors.isEmpty()) {
            return new Node(0, 0, Nodetype.EMPTY);
        }
        return anchors.get(0);
    }
    
    public List<Node> getAnchors() {
        return this.anchors;
    }
    
    /**
     * Gives a random Node from the list of anchor points, or a default empty Node
     * if the list is empty. The random Node is picked using the local Random
     * variable.
     * @return A random Node from the list of anchors, or a default empty Node
     * if the list is empty.
     */
    public Node getRandomAnchor() {
        if (anchors.isEmpty()) {
            return new Node(0, 0, Nodetype.EMPTY);
        }
        
        if (repeatRule && anchors.size() > 1) {
            int i = anchors.indexOf(prev);
            Node temp = anchors.remove(i);
            anchors.add(temp);
            prev = anchors.get(rand.nextInt(anchors.size() - 1));
            return prev;
        }
        prev = anchors.get(rand.nextInt(anchors.size()));
        return prev;
    }
}
