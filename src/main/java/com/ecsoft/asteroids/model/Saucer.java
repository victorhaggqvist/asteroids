/**
 * 
 */
package com.ecsoft.asteroids.model;

import java.awt.Polygon;
import java.awt.geom.Point2D;

/**
 * @author Albin Karlquist
 * This class represents a saucer, an AI-controlled enemy.
 */
public class Saucer {
    
    private static final float velocity = 3;
    private static final int size = 20;
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;
    
    private Point2D.Float position;
    private double direction;
    private long shootTimer;
    private long turnTimer;
    
    /**
     * @author Albin Karlquist
     * Spawns an evil saucer at a random location.
     */
    public Saucer() {       
        
        this.position = new Point2D.Float(500,300);
        this.direction = Math.random()*2*Math.PI;        
        this.shootTimer = System.currentTimeMillis();
        this.turnTimer = System.currentTimeMillis();
    }
    
    
    /**
     * @author Albin Karlquist
     * Updates the position of the saucer.
     */
    public void updatePos() {   
        
        double x = position.getX() + Math.cos(direction)*velocity;
        double y = position.getY() + Math.sin(direction)*velocity;
        position.setLocation(x,y);
        
        //Shoots at the player every 2 seconds. 
        if (System.currentTimeMillis()-shootTimer>2000) {
            shootTimer = System.currentTimeMillis();
            //Notify Controller ......
        }
        
        //Changes direction every 3 seconds
        if (System.currentTimeMillis()-turnTimer>3000) {
            turnTimer= System.currentTimeMillis();
            direction = Math.random()*2*Math.PI;
        }   
        
      //If the saucer moves out of bounds
        if(position.getX()<0-size/2) {
            position.setLocation(SCREEN_WIDTH+size/2, position.getY());
        }
        
        else if(position.getX()>SCREEN_WIDTH+size/2) {
            position.setLocation(0-size/2, position.getY());
        }
        
        else if(position.getY() < 0-size/2) {
            position.setLocation(position.getX() , SCREEN_HEIGHT+size/2);
        }
        
        else if(position.getY() > SCREEN_HEIGHT+size/2) {
            position.setLocation(position.getX() , 0-size/2);
        }
        
        
    }
    
    /**
     * @author Albin Karlquist
     * @return Returns a polygon representing the saucer
     */
    public Polygon getPolygon( ) {
        int [] x = new int[6];
        int [] y = new int[6];
        x[0] = (int)this.position.x;
        y[0] = (int)this.position.y;
        x[1] = (int)this.position.x+size;
        y[1] = (int)this.position.y;
        x[2] = (int)this.position.x+2*size;
        y[2] = (int)this.position.y+size/2;
        x[3] = (int)this.position.x+size;
        y[3] = (int)this.position.y+size;
        x[4] = (int)this.position.x;
        y[4] = (int)this.position.y+size;
        x[5] = (int)this.position.x-size;
        y[5] = (int)this.position.y+size/2;
        
        return new Polygon(x,y,6);
    }

}
