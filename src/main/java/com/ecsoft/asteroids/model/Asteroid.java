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
        
        int [] x = new int[8];
        int [] y = new int[8];        
        //p.bounds = new Rectangle((int)position.getX(), (int)position.getY(), size*scale, size*scale);        
        
        for (int i = 0; i < 3; i++) {
            x[i] = (int)(position.getX() + polygon[i].getX());
            y[i] =  (int)(position.getY() + polygon[i].getY());
        }
        x[3] = (int)(position.getX() + polygon[4].getX());
        y[3] = (int)(position.getY() + polygon[4].getY());
        x[4] = (int)(position.getX() + polygon[7].getX());
        y[4] = (int)(position.getY() + polygon[7].getY());
        x[5] = (int)(position.getX() + polygon[6].getX());
        y[5] = (int)(position.getY() + polygon[6].getY());
        x[6] = (int)(position.getX() + polygon[5].getX());
        y[6] = (int)(position.getY() + polygon[5].getY());
        x[7] = (int)(position.getX() + polygon[3].getX());
        y[7] = (int)(position.getY() + polygon[3].getY());
        
        Polygon p = new Polygon(x,y,8);
        
        
        return p;
    }





}
