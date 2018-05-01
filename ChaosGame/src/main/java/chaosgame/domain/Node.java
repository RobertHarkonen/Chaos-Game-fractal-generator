

package chaosgame.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
    
    public Node() {
        
    }
    
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
