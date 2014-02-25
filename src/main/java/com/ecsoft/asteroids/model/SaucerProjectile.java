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

public class SaucerProjectile extends Projectile{    
   
    private Point2D.Float position;
    private double direction;
    private long time;
    

    /**
     * @author: Albin Karlquist
     * Creates a projectile at the specified location
     * @param x 
     * @param y
     * @param direction The direction specified in radians
     */
    public SaucerProjectile(float x, float y, double direction) {
    	super(x, y, direction);
    	super.velocity = 15;
    	super.timeToLive = 800;
    }    
}
