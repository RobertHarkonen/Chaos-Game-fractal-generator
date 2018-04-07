/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chaosgame.domain;

public class Fractal {
    
    private Settings settings;
    private Node[][] grid;
    private Node current;
    
    public Fractal(int width, int height) {
        this.settings = new Settings(0.5);
        this.grid = new Node[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Node(i, j, Nodetype.EMPTY);
            }
        }
        
        this.current = grid[0][0];
    }
    
    public Fractal(int width, int height, Settings set) {
        this.settings = set;
        this.grid = new Node[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Node(i, j, Nodetype.EMPTY);
            }
        }
        
        this.current = settings.getFirstAnchor();       //empty node (0,0) if not found
    }
    
    public void addAnchor(int x, int y) {
        settings.addAnchor(x, y);
        setType(x, y, Nodetype.ANCHOR);
        
        current = settings.getFirstAnchor();        //starting from an anchor makes the drawing cleaner
    }
    
    public void setType(int x, int y, Nodetype type) {
        grid[x][y].setType(type);
    }
    
    public void setTypeAll(Nodetype type) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                setType(i, j, type);
            }
        }
    }
    
    /** Moves current node toward the next randomly chosen anchor
     * by the current ratio.
     */
    public void iterate() {
        Node nextAnchor = settings.getRandomAnchor();
        long nextX = Math.round(current.getX() 
                + settings.getRatio()*(nextAnchor.getX() - current.getX()));
        long nextY = Math.round(current.getY() 
                + settings.getRatio()*(nextAnchor.getY() - current.getY()));
        current = grid[(int) nextX][(int) nextY];               //coordinates are always within int bounds
        
        if (current.getType() == Nodetype.EMPTY) {
            current.setType(Nodetype.FILLED);
        }
    }
}
