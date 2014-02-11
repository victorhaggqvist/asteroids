package com.ecsoft.asteroids.model;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * Name: Asteroids
 * Description: Asteroid
 *
 * @author: Albin Karlquist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.controller
 */
public class Asteroid {
    
    private static int scale = 20;

    private Point2D position;
    private Point2D velocity;
    private int size;    
    private Point2D polygon[] = new Point2D[8];



    /**
     * Creates a new asteroid at a random location with a random velocity
     * @param x Width of the game screen
     * @param y Height of the game screen
     */
    public Asteroid(int x, int y) {
        this.size = 4;
        this.position = new Point2D.Float((float)(Math.random()*x),(float)(Math.random()*y));
        this.velocity = new Point2D.Float((float)(Math.random()),(float)(Math.random()));
        randomPolygon();
    }

    /**
     * Creates a new asteroid at the specified location and size
     * @param x X-position of the asteroid
     * @param y Y-position of the asteroid
     * @param size Size of the asteroid
     */
    public Asteroid(int x, int y, int size) {
        this.size = size;
        this.position = new Point2D.Float(x,y);
        this.velocity = new Point2D.Float((float)(Math.random()),(float)(Math.random()));
        randomPolygon();
    }

    /**
     * Creates a randomized polygon
     */
    private void randomPolygon() {        
        
        Point2D polygon [][] = new Point2D[3][3];
        
        int sectorSize = (this.size*20)/3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                polygon[i][j] = new Point2D.Float((float)(Math.random()*sectorSize+(j*sectorSize)), (float)(Math.random()*sectorSize + (i*sectorSize)));
            }
        }

        //Reformats the polygon to a single dimensional array and removes the middle point
        for (int i = 0; i < 3; i++) {
           this.polygon[i] = polygon[0][i]; 
        }
        
        this.polygon[3] = polygon[1][0];
        this.polygon[4] = polygon[1][2];
        for (int i = 0; i < 3; i++) {
            this.polygon[i+5] = polygon[2][i];
        }
    }

    /**
     * Updates the position of the asteroid
     */
    public void updatePos () {
        position.setLocation(position.getX() + velocity.getX(), position.getY() + velocity.getY());                        
    }
    
    /**
     * 
     * @return Returns a drawable polygon
     */
    public Polygon getPolygon() {
        Polygon p = new Polygon();
        
        p.npoints = 8;
        //p.bounds = new Rectangle((int)position.getX(), (int)position.getY(), size*scale, size*scale);        
        
        for (int i = 0; i < polygon.length; i++) {
            p.addPoint((int)(position.getX() + polygon[i].getX()), (int)(position.getY() + polygon[i].getY()));
        }
        
        return p;
    }





}
