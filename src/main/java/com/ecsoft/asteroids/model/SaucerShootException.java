package com.ecsoft.asteroids.model;

import java.awt.geom.Point2D;

/**
 * @author Albin
 * Throw this exception when the saucer should shoot
 */
public class SaucerShootException extends Exception {
	
	private Point2D.Float saucerPos;
	
    public SaucerShootException(Point2D.Float pos) {
    	this.saucerPos = pos;
    }
    
    /**
     * @author Albin Karlquist
     * @return Returns the position of the saucer causing the exception
     */
    public Point2D.Float getSaucerPos() {
    	return saucerPos;
    }
}
