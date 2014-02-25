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

public class PlayerProjectile extends Projectile{    
   
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
    public PlayerProjectile(float x, float y, double direction) {
    	super(x, y, direction);
    }    
}
