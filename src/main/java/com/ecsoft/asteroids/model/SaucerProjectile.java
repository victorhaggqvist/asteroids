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
    private SettingsManager settings;
    

    /**
     * @author: Albin Karlquist
     * Creates a projectile at the specified location
     * @param x 
     * @param y
     * @param direction The direction specified in radians
     */
    public SaucerProjectile(float x, float y, double direction) {        
    	super(x, y, direction);
    	settings = SettingsManager.getInstance();
    	super.velocity = 10+(settings.getDifficulty()*2);
    	super.timeToLive = 1000+settings.getDifficulty()*400;
    	
    }    
}
