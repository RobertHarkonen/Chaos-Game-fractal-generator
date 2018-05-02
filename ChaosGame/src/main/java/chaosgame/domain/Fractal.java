/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chaosgame.domain;

import chaosgame.dao.SettingsDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the highest level application logic just beneath the UI.
 * Keeps track of settings, database connection and iterating
 * the fractal drawing process. Also keeps a grid of Nodes for the current
 * fractal, but this is not used at the moment.
 */
public class Fractal {

    private Settings settings;
    private SettingsDAO sd;
    private Node[][] grid;
    private double currentX;
    private double currentY;

    /**
     * Creates a default empty fractal (and settings) and initializes
     * a SettingsDAO for database connection.
     * @param width Width of the fractal area.
     * @param height Height of the fractal area.
     */
    public Fractal(int width, int height) {
        int tempWidth = width, tempHeight = height;
        if (width < 100 || width > 800) {    //default size 800x800
            tempWidth = 800;
        }
        if (height < 100 || height > 800) {
            tempHeight = 800;
        }
        this.settings = new Settings(0.5);
        this.sd = new SettingsDAO();
        settings.setHeight(tempHeight);
        settings.setWidth(tempWidth);
        this.grid = new Node[tempWidth][tempHeight];
        for (int i = 0; i < tempWidth; i++) {
            for (int j = 0; j < tempHeight; j++) {
                grid[i][j] = new Node(i, j, Nodetype.EMPTY);
            }
        }

        this.currentX = 0;
        this.currentY = 0;
    }
    
    /**
     * Constructs a fractal with the given Settings.
     * This includes updating the Fractal's local grid. The current iteration
     * coordinates are also updated to those of one of the supplied anchor points.
     * @param set
     */
    public Fractal(Settings set) {
        this.settings = set;
        this.sd = new SettingsDAO();
        this.grid = new Node[settings.getWidth()][settings.getHeight()];
        for (int i = 0; i < settings.getWidth(); i++) {
            for (int j = 0; j < settings.getHeight(); j++) {
                grid[i][j] = new Node(i, j, Nodetype.EMPTY);
            }
        }

        for (Node anchor : settings.getAnchors()) {
            grid[anchor.getX()][anchor.getY()] = anchor;
        }

        this.currentX = settings.getFirstAnchor().getX();
        this.currentY = settings.getFirstAnchor().getY();        //0 if no anchors added
    }
    
    /**
     * Resets the fractal to it's default state. Same as calling the constructor,
     * but without creating a new database connection.
     * @see chaosgame.domain.Fractal#Fractal(int, int) 
     */
    public void reset() {
        settings = new Settings(0.5);
        settings.setHeight(getHeight());
        settings.setWidth(getWidth());
        grid = new Node[getWidth()][getHeight()];
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                grid[i][j] = new Node(i, j, Nodetype.EMPTY);
            }
        }
        currentX = 0;
        currentY = 0;
    }
    
    /**
     * Finds Settings from database with the given key.
     * @param key The primary key (string) to query with.
     * @return The queried settings
     * @see chaosgame.dao.SettingsDAO#getFromDatabase(java.lang.String) 
     */
    public Settings loadSettings(String key) {
        return sd.getFromDatabase(key);
    }
    
    /**
     * Finds a list of names of stored Settings.
     * @return A list of names of stored Settings
     * @see chaosgame.dao.SettingsDAO#getSettingsKeys() 
     */
    public List<String> storedSettings() {
        return sd.getSettingsKeys();
    }
    
    /**
     * Saves or updates the current Settings to the database with the given name.
     * @param key The name (primary key) by which the Settings are persisted.
     * @see chaosgame.dao.SettingsDAO#saveToDatabase(chaosgame.domain.Settings) 
     */
    public void saveSettings(String key) {
        settings.setKey(key);
        sd.saveToDatabase(settings);
    }
    
    /**
     * Removes settings with the given key from the database.
     * @param key The key by which Settings are removed.
     * @see chaosgame.dao.SettingsDAO#removeFromDatabase(java.lang.String) 
     */
    public void removeSettings(String key) {
        sd.removeFromDatabase(key);
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    public Node getNode(int x, int y) {
        return grid[x][y];
    }

    public Settings getSettings() {
        return settings;
    }

    public double getCurrentX() {
        return currentX;
    }

    public double getCurrentY() {
        return currentY;
    }
    
    public double getGrainSize() {
        return settings.getGrainSize();
    }
    
    public void setGrainSize(double size) {
        settings.setGrainSize(size);
    }
    
    /**
     * Adds an anchor point to the given coordinates. The current iteration
     * coordinates are updated to those of an anchor point. This is because
     * starting from an anchor point makes the drawing cleaner.
     * @param x X-coordinate for the new anchor.
     * @param y Y-coordinate for the new anchor.
     */
    public void addAnchor(int x, int y) {
        boolean validCoords = setType(x, y, Nodetype.ANCHOR);
        if (validCoords) {
            settings.addAnchor(grid[x][y]);
            Node a = settings.getFirstAnchor(); 
            currentX = a.getX();
            currentY = a.getY();        //drawing is cleaner when starting from an anchor
        }
    }

    /**
     * Sets the Nodetype of a Node at the given coordinates. Only works with
     * coordinates within bounds.
     * @param x X-coordinate of the Node.
     * @param y Y-coordinate of the Node.
     * @param type The Nodetype to be set
     * @return True if the given coordinates are in bounds, false otherwise.
     * @see chaosgame.domain.Nodetype
     */
    public boolean setType(int x, int y, Nodetype type) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            grid[x][y].setType(type);
            return true;
        }
        return false;
    }

    /**
     * Sets all Nodes in the current Fractal to the given Nodetype.
     * @param type The Nodetype to be set.
     */
    public void setTypeAll(Nodetype type) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                setType(i, j, type);
            }
        }
    }

    /**
     * Gets a list of all Nodes of a given Nodetype in the current grid.
     * This operation is heavy. To get a list of anchor points, use the Settings.
     * @param type The Nodetype searched for.
     * @return A list of all nodes with the supplied Nodetype.
     * @see chaosgame.domain.Settings#getAnchors() 
     */
    public ArrayList<Node> getNodesWithType(Nodetype type) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getType() == type) {
                    nodes.add(grid[i][j]);
                }
            }
        }
        return nodes;
    }
    
    /**
     * Creates a double array containing coordinates for the current anchor points.
     * @return A double array of anchor coordinates.
     */
    public int[][] getAnchorCoords() {
        List<Node> anchors = settings.getAnchors();
        int[][] coords = new int[anchors.size()][2];
        for (int i = 0; i < anchors.size(); i++) {
            coords[i][0] = anchors.get(i).getX();
            coords[i][1] = anchors.get(i).getY();
        }
        return coords;
    }

    /**
     * Sets all FILLED Nodes in the grid to EMPTY.
     */
    public void removeFilled() {
        ArrayList<Node> filledNodes = getNodesWithType(Nodetype.FILLED);
        for (Node filledNode : filledNodes) {
            filledNode.setType(Nodetype.EMPTY);
        }
    }

    /**
     * Moves current iteration coordinates toward the next randomly chosen anchor 
     * by the current ratio. If the list of anchor points is empty, this method
     * doesn't do anything. If the coordinates moved, the Nodetype at that location
     * is set to FILLED, if that Node was previously EMPTY.
     * 
     * Most of the fractal drawing logic is based around this method.
     */
    public void iterate() {
        Node nextAnchor = settings.getRandomAnchor();
        if (nextAnchor.getType() == Nodetype.EMPTY) {
            return;
        }
        currentX = currentX + settings.getRatio() * (nextAnchor.getX() - currentX);
        currentY = currentY + settings.getRatio() * (nextAnchor.getY() - currentY);
        int tempX = (int) Math.round(currentX);
        int tempY = (int) Math.round(currentY);
        if (tempX >= 0 && tempX < grid.length && tempY >= 0 && tempY < grid[0].length) {
            if (grid[tempX][tempY].getType() == Nodetype.EMPTY) {
                setType(tempX, tempY, Nodetype.FILLED);
            }
        }
    }
}
