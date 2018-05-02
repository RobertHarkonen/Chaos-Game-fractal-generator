

package chaosgame.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The most basic building block of the fractal. Contains coordinates, type
 * and a reference to associated Settings.
 * @see chaosgame.domain.Nodetype
 */
@DatabaseTable(tableName = "anchors")
public class Node {
    @DatabaseField(generatedId = true)
    private int key;
    @DatabaseField
    private int x;
    @DatabaseField
    private int y;
    @DatabaseField
    private Nodetype type;
    @DatabaseField(foreign = true)
    private Settings settings;
    
    /**
     * An empty constructor is needed for ORMlite functionality.
     */
    public Node() {
        
    }
    
    /**
     * Creates a Node with the supplied values.
     * @param x X-coordinate of the node.
     * @param y Y-coordinate of the node.
     * @param type The Nodetype to be set.
     */
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
    
    public void setSettings(Settings s) {
        this.settings = s;
    }
}
