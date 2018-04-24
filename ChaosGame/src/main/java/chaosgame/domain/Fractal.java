/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chaosgame.domain;

import java.util.ArrayList;
import java.util.List;

public class Fractal {

    private Settings settings;
    private Node[][] grid;
    private double currentX;
    private double currentY;

    public Fractal(int width, int height) {
        int tempWidth = width, tempHeight = height;
        if (width < 100 || width > 800) {    //default size 800x800
            tempWidth = 800;
        }
        if (height < 100 || height > 800) {
            tempHeight = 800;
        }
        this.settings = new Settings(0.5);
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

    public Fractal(Settings set) {
        this.settings = set;
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

    public void addAnchor(int x, int y) {
        boolean validCoords = setType(x, y, Nodetype.ANCHOR);
        if (validCoords) {
            settings.addAnchor(grid[x][y]);
            currentX = settings.getFirstAnchor().getX();
            currentY = settings.getFirstAnchor().getY();        //drawing is cleaner when starting from an anchor
        }
    }

    public boolean setType(int x, int y, Nodetype type) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            grid[x][y].setType(type);
            return true;
        }
        return false;
    }

    public void setTypeAll(Nodetype type) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                setType(i, j, type);
            }
        }
    }

    public ArrayList<Node> getNodesWithType(Nodetype type) {    //heavy operation, get anchors through settings instead
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
    
    public int[][] getAnchorCoords() {
        List<Node> anchors = settings.getAnchors();
        int[][] coords = new int[anchors.size()][2];
        for (int i = 0; i < anchors.size(); i++) {
            coords[i][0] = anchors.get(i).getX();
            coords[i][1] = anchors.get(i).getY();
        }
        return coords;
    }

    public void removeFilled() {                //sets filled nodes to empty
        ArrayList<Node> filledNodes = getNodesWithType(Nodetype.FILLED);
        for (Node filledNode : filledNodes) {
            filledNode.setType(Nodetype.EMPTY);
        }
    }

    /**
     * Moves current node toward the next randomly chosen anchor by the current
     * ratio.
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
