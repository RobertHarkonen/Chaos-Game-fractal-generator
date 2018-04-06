/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chaosgame.domain;

public class Fractal {
    
    private Settings settings;
    private Node[][] grid;
    private float currentX;
    private float currentY;
    
    public Fractal(int width, int height) {
        this.grid = new Node[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Node(i, j, Nodetype.EMPTY);
            }
        }
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
}
