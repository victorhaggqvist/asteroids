package com.ecsoft.asteroids.model;

import java.awt.Polygon;
import java.awt.geom.Point2D;

public class Heart extends Polygon {
    
    private int size;
    private Point2D.Float position;
    
    /**
     * @author Albin Karlquist
     * @param x X-position of the heart
     * @param y Y-position of the heart
     * @param size Size of the heart
     */
    public Heart(int x, int y, int size) {
        int [] xPos = new int[8];
        int [] yPos = new int[8];
        
        
        xPos [0] = 0;
        yPos [0] = 0;
        
        xPos [1] = 5*size;
        yPos [1] = -7*size;
        
        xPos [2] = 7*size;
        yPos [2] = -7*size;
        
        xPos [3] = 12*size;
        yPos [3] = 0;
        
        xPos [4] = 0;
        yPos [4] = 20*size;
        
        xPos [5] = -12*size;
        yPos [5] = 0;
        
        xPos [6] = -7*size;
        yPos [6] = -7*size;
        
        xPos [7] = -5*size;
        yPos [7] = -7*size;
        
        for (int i = 0; i < yPos.length; i++) {
            xPos[i] += x;
            yPos[i] += y;
        }
        
        this.xpoints = xPos;
        this.ypoints = yPos;
        this.npoints = 8;
         
    }

}
