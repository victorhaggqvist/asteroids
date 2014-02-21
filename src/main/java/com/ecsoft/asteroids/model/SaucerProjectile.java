package com.ecsoft.asteroids.model;

import java.awt.geom.Ellipse2D.Float;
import java.awt.geom.Ellipse2D;
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

    /**
     * Creates a projectile at the specified location
     * @param x 
     * @param y
     * @param direction The direction specified in radians
     */
    public SaucerProjectile(float x, float y, double direction) {
    	super(x, y, direction);
    	super.velocity = 10;
    	super.timeToLive = 750;
    }


	@Override
	public Ellipse2D getDrawable() {
		float x = (float) super.getPos().getX();
		float y = (float) super.getPos().getY();
		return new Ellipse2D.Float(x, y, 3, 3);
	}    

}
