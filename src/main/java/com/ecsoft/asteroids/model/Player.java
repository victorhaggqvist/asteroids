package com.ecsoft.asteroids.model;

import java.awt.Polygon;
import java.awt.geom.Point2D;

import com.ecsoft.asteroids.mathematics.Matrix;

/**
 * Name: Asteroids
 * Description: Player
 *
 * @author: Albin Karlquist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.model
 */
public class Player {
    
    //Deacceleration of the ship
    private final double friction = 0.95;
    private final double MAX_SPEED = 5;
    private final double ACC = 0.2;
    private final double TURN_SPEED = 0.05;
    private final int SHIP_SIZE = 20;
    
    private double velocity;
    private double rotation;
    private int health;
    
    public boolean turningL;
    public boolean turningR;
    public boolean accelerating;
    
    Point2D.Float[] corners;
    
   /**
    * Creates a player-controlled ship
    * @param x The width of the gamescreen
    * @param y The height of the gamescreen
    */
    public Player(int x, int y) {
    	createPlayerPoints(x, y, SHIP_SIZE);
    	
    	health = 3;
        velocity = 0;
        rotation = 0;
        turningL = false;
        turningR = false;
        accelerating = false;
    }
    
    /**
     * Creates the three corners of the player ship.
     * 
     * @param x Player position center, x-value 
     * @param y Player position center, y-value
     * @param size Length between points
     */
    private void createPlayerPoints(int x, int y, double size) {
    	corners = new Point2D.Float[3];
    	
    	corners[0] = new Point2D.Float(x,y);
    	corners[1] = new Point2D.Float((float)(x-size), (float)(y+2.5*size));
    	corners[2] = new Point2D.Float((float)(x+size), (float)(y+2.5*size));
    }
    
    /**
     * Updates the position of the player
     */
    public void updatePos() { 
  
    	if(accelerating && velocity < MAX_SPEED) 
    		velocity += ACC;
    	else 
    		velocity *= friction;
    	
    	if(turningL)
    		rotation += TURN_SPEED;
    	if(turningR)
    		rotation -= TURN_SPEED;
    	
    	rotation %= 2*Math.PI;
    	
    	double xMov = Math.cos(rotation)*velocity;
    	double yMov = Math.sin(rotation)*velocity;
    	for(Point2D p : corners)
    		p.setLocation(p.getX()+xMov, p.getY()+yMov);
    }
    
    
    
    /**
     * Returns a polygon based on the ships corners for drawing.
     * 
     * @return Polygon based on the ships corners.
     */
    public Polygon getPolygon() {
    		
    	//TODO: Rotate points based on variable 'rotation'
    	
    	int[] x = new int[3];
    	int[] y = new int[3];
    	
    	x[0] = (int)corners[0].getX();
    	x[1] = (int)corners[1].getX();
    	x[2] = (int)corners[2].getX();
    	y[0] = (int)corners[0].getY();
    	y[1] = (int)corners[1].getY();
    	y[2] = (int)corners[2].getY();

    	return new Polygon(x, y, x.length);
    }

}
