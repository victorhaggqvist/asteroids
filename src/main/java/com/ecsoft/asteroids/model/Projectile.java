package com.ecsoft.asteroids.model;

import java.awt.geom.Point2D;

/**
 * Name: Asteroids
 * Description: Projectile
 *
 * @author: Victor Häggqvist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.model
 */
public class Projectile {
    
    private static double velocity = 10;
    
    private float x;
    private float y;
    private Point2D position;
    private double direction;
    

    /**
     * Creates a projectile at the specified location
     * @param x 
     * @param y
     * @param direction The direction specified in radians
     */
    public Projectile(float x, float y, double direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;        
    }
    
    public void updatePos() {
        this.x += Math.cos(direction)*velocity;
        this.y += Math.sin(direction)*velocity;
        
        //If the asteroid moves out of bounds
        if(position.getX()<0) {
            position.setLocation(screenWidth, position.getY());
        }
        
        else if(position.getX()>screenWidth) {
            position.setLocation(0, position.getY());
        }
        
        else if(position.getY() < 0) {
            position.setLocation(position.getX() , screenHeight);
        }
        
        else if(position.getY() > screenHeight) {
            position.setLocation(position.getX() , 0);
        }
        
        
    }
    
    public Point2D.Float getPos() {
        return new Point2D.Float(x,y);
    }
}
