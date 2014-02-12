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
    private final double MAX_SPEED = 10;
    private final double ACC = 0.2;
    private final double TURN_SPEED = 0.2;
    private final int SHIP_SIZE = 20;
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;
    
    private double velocity;
    private double rotation;
    private int health;
    
    private Point2D.Float position;
    
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
    	createPlayerPoints(SHIP_SIZE);
    	position = new Point2D.Float(x,y);
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
    private void createPlayerPoints(double size) {
    	corners = new Point2D.Float[3];
    	
    	corners[0] = new Point2D.Float(0,0);
    	corners[1] = new Point2D.Float((float)(-2.5*size), (float)(size));
    	corners[2] = new Point2D.Float((float)(-2.5*size), (float)(-size));
    	
    	Point2D.Float sum = Matrix.Point2DSum(this.corners);
        Point2D.Float center = new Point2D.Float((float)sum.getX()/this.corners.length,(float)sum.getY()/this.corners.length);
        System.out.println(center);
        for (int i = 0; i < this.corners.length; i++) {
            this.corners[i].x -= center.x;
            this.corners[i].y -= center.y;
        }
    }
    
    /**
     * Updates the position of the player
     */
    public void updatePos() { 
  
    	if(accelerating && velocity < MAX_SPEED) 
    		velocity += ACC;
    	else 
    		velocity *= friction;
    	
    	//rotates left
    	if(turningL) {
    		rotation -= TURN_SPEED;
    		float transformationMatrix[][] = {{(float)Math.cos(-TURN_SPEED), (float)-Math.sin(-TURN_SPEED)}, {(float)Math.sin(-TURN_SPEED), (float)Math.cos(-TURN_SPEED)}};
            corners = Matrix.convert2DMatrixToPoint2DArray(Matrix.Transform(transformationMatrix, Matrix.convertPoint2DArrayTo2DMatrix(corners)));
    	}
    	
    	//rotates right
    	if(turningR) {
    		rotation += TURN_SPEED;
    		float transformationMatrix[][] = {{(float)Math.cos(TURN_SPEED), (float)-Math.sin(TURN_SPEED)}, {(float)Math.sin(TURN_SPEED), (float)Math.cos(TURN_SPEED)}};
            corners = Matrix.convert2DMatrixToPoint2DArray(Matrix.Transform(transformationMatrix, Matrix.convertPoint2DArrayTo2DMatrix(corners)));
    	}
    	rotation %= 2*Math.PI;
    	
    	
    	//Updates the position
    	double xMov = Math.cos(rotation)*velocity;
    	double yMov = Math.sin(rotation)*velocity;
    	position.setLocation((float)position.getX()+xMov, (float)position.getY()+yMov);
    	
    	//If the player moves out of bounds
        if(position.getX()<0) {
            position.setLocation(SCREEN_WIDTH, position.getY());
        }
        
        else if(position.getX()>SCREEN_WIDTH) {
            position.setLocation(0, position.getY());
        }
        
        else if(position.getY() < 0) {
            position.setLocation(position.getX() , SCREEN_HEIGHT);
        }
        
        else if(position.getY() > SCREEN_HEIGHT) {
            position.setLocation(position.getX() , 0);
        }
    }
    
    /**
     * @author Albin Karlquist
     * @return Returns a projectile at the players location
     */
    public Projectile shoot() {
        return new Projectile((float)position.getX(), (float) position.getY(),rotation);
    }
    
    
    
    /**
     * Returns a polygon based on the ships corners for drawing.
     * 
     * @return Polygon based on the ships corners.
     */
    public Polygon getPolygon() {
    	
    	int[] x = new int[3];
    	int[] y = new int[3];
    	
    	x[0] = (int)(position.getX() + corners[0].getX());
    	x[1] = (int)(position.getX() + corners[1].getX());
    	x[2] = (int)(position.getX() + corners[2].getX());
    	y[0] = (int)(position.getY() + corners[0].getY());
    	y[1] = (int)(position.getY() + corners[1].getY());
    	y[2] = (int)(position.getY() + corners[2].getY());

    	return new Polygon(x, y, x.length);
    }

}
