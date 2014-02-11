package com.ecsoft.asteroids.model;

import java.awt.Polygon;
import java.awt.geom.Point2D;

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
    private static double friction = 0.95;
    private final double MAX_SPEED = 5;
    private final double ACC = 0.2;
    private final double TURN_SPEED = 0.05;
    private final int SHIP_SIZE = 20;
    
    private Point2D position;
    private double velocity;
    private double direction;
    
    public boolean turningL;
    public boolean turningR;
    public boolean accelerating;
    
    Point2D[] corners;
    
   /**
    * Creates a player-controlled ship at the center position
    * @param x The width of the gamescreen
    * @param y The height of the gamescreen
    */
    
    public Player(int x, int y) {
    	createPlayerPoints(x, y, SHIP_SIZE);
    	
        position = new Point2D.Double(x,y);
        velocity = 0;
        direction = 0;
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
    	corners = new Point2D[3];
    	corners[0] = new Point2D.Double(x+Math.cos(0)*size, y+Math.sin(0)*size);
    	corners[1] = new Point2D.Double(x+Math.cos(Math.toRadians(120))*size, y+Math.sin(Math.toRadians(120))*size);
    	corners[2] = new Point2D.Double(x+Math.cos(Math.toRadians(240))*size, y+Math.sin(Math.toRadians(240))*size);
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
    		direction += TURN_SPEED;
    	if(turningR)
    		direction -= TURN_SPEED;
    	
    	direction %= 2*Math.PI;
    	
    	double xMov = Math.cos(direction)*velocity;
    	double yMov = Math.sin(direction)*velocity;
    	position.setLocation(position.getX()+xMov, position.getY()+yMov);
    	for(Point2D p : corners)
    		p.setLocation(p.getX()+xMov, p.getY()+yMov);
    	//TODO: ROTATE POINTS
    }
    
    /**
     * Returns a polygon based on the ships corners for drawing.
     * 
     * @return Polygon based on the ships corners.
     */
    public Polygon getPolygon() {
    	int[] x = new int[3];
    	int[] y = new int[3];
    	
    	x[0] = (int)corners[0].getX();
    	x[1] = (int)corners[1].getX();
    	x[2] = (int)corners[2].getX();
    	y[0] = (int)corners[0].getY();
    	y[1] = (int)corners[1].getY();
    	y[2] = (int)corners[2].getY();
    	
    	System.out.println(corners[0]);
    	System.out.println(corners[1]);
    	System.out.println(corners[2]);
    	
    	return new Polygon(x, y, x.length);
    }
}
