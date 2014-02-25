/**
 * 
 */
package com.ecsoft.asteroids.mathematics;

import java.awt.geom.Point2D;

/**
 * @author Albin
 * A class containing trigonometry methods 
 */
public class Trigonometry {

	/**
	 * @author Albin Karlquist
	 * @edited Peter Lundberg
	 * Returns the angle between two points in radians
	 * @param p1 First point
	 * @param p2 Second point
	 * @return returns the angle in radians
	 */
	public static double angle (Point2D p1, Point2D p2) {
			
		double angle = 0;
		
		//Calculates distance in the x-axis and y-axis
		double xDist = p1.getX()-p2.getX();
		double yDist = p1.getY()-p2.getY();
		
		if(xDist == 0) {
			xDist = 0.1;
		}
		
		//Math stuff
		if (p1.getX() > p2.getX()) {
			angle = -Math.atan(-yDist / xDist) + Math.PI;
		}
		else
			angle = -Math.atan(-yDist / xDist);
		
		return angle;		
	}
	
	/**
	 * Calculates the vector between two points
	 * @param p1 First point
	 * @param p2 Second point
	 * @return Returns the vector as a point
	 */
	public static Point2D.Float getVector (Point2D.Float p1, Point2D.Float p2) {
		float x = (float)(p1.getX()-p2.getX())%1;
		float y = (float)(p1.getY()-p2.getY())%1;	
		
		return new Point2D.Float(x, y);		
	}

}
