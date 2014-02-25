package com.ecsoft.asteroids.model;

import java.awt.geom.Point2D;

/**
 * Name: Asteroids
 * Description: Projectile
 *
 * @author: Albin Karlquist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.model
 */

public class Projectile {

    private static double velocity = 25;
    private static int screenWidth = 1000;
    private static int screenHeight = 600;
    private static int timeToLive = 1000;
   
    private Point2D.Float position;
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
    public void updatePos() throws ObjectExpiredException {

        
        if (System.currentTimeMillis() - time > timeToLive) {
            throw new ObjectExpiredException();
        }
        
        double x = position.getX() + Math.cos(direction)*velocity;
        double y = position.getY() + Math.sin(direction)*velocity;
        position.setLocation(x,y);       
        
    }
    
    /**
     * @return Returns the bullets position
     */
    public Point2D.Float getPos() {
        return position;
    }
}
