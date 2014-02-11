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

    private static double velocity = 20;
    private static int screenWidth = 1000;
    private static int screenHeight = 800;
    private static int timeToLive = 3000;
   
    private Point2D position;
    private double direction;
    private long time;
    

    /**
     * Creates a projectile at the specified location
     * @param x 
     * @param y
     * @param direction The direction specified in radians
     */
    public Projectile(float x, float y, double direction) {
        position = new Point2D.Float(x,y);
        this.direction = direction;
        time = System.currentTimeMillis();
    }
    
    /**
     * Updates the bullet position
     * @throws BulletExpired if the bullet has expired
     */
    public void updatePos() throws BulletExpired {
        
        if (System.currentTimeMillis() - time > timeToLive) {
            throw new BulletExpired();
        }
        
        double x = position.getX() + Math.cos(direction)*velocity;
        double y = position.getY() + Math.sin(direction)*velocity;
        position.setLocation(x,y);
        
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
    
    /**
     * @return Returns the bullets position
     */
    public Point2D getPos() {
        return position;
    }
}
