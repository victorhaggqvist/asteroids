package com.ecsoft.asteroids.model;

import java.awt.Polygon;
import java.awt.geom.Point2D;

import com.ecsoft.asteroids.controller.Sound;
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
    private final double friction = 0.92;
    private final double MAX_SPEED = 15;
    private final double ACC = 0.9;
    private final double TURN_SPEED = 0.2;
    private final int SHIP_SIZE = 20;
    private final int RESPAWN_PROTECTION_TIME = 3000;
    private final int START_HEALTH = 3;
    
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;
    
    private Sound sound = new Sound();
    
    private double vx;
    private double vy;
    public double rotation;
    private int health;
    private int score;
    private boolean invincible;
    private long respawnTime;
    
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
    	health = START_HEALTH;
    	score = 0;
        vx = 0;
        vy = 0;
        rotation = 0;
        turningL = false;
        turningR = false;
        accelerating = false;
        invincible = false;
    }
    
    /**
     * Creates the three corners of the player ship.
     *
     * @param size Length between points
     */
    private void createPlayerPoints(double size) {
    	corners = new Point2D.Float[4];
    	
    	corners[0] = new Point2D.Float(0,0);
    	corners[1] = new Point2D.Float((float)(-2.5*size), (float)(size));
    	corners[2] = new Point2D.Float((float)(-2*size), 0);
    	corners[3] = new Point2D.Float((float)(-2.5*size), (float)(-size));
    	
    	Point2D.Float sum = Matrix.Point2DSum(this.corners);
        Point2D.Float center = new Point2D.Float((float)sum.getX()/this.corners.length,(float)sum.getY()/this.corners.length);
        //System.out.println(center);
        for (Point2D.Float corner : this.corners) {
            corner.x -= center.x;
            corner.y -= center.y;
        }
    }
    
    /**
     * Updates the position of the player
     */
    public void updatePos() { 
  
        double totalVelocity = Math.abs(vx) + Math.abs(vy);
        
    	if(accelerating && totalVelocity < MAX_SPEED) {
    	    vx += Math.cos(rotation) * ACC;
    	    vy += Math.sin(rotation) * ACC;
    	}    		
    	
    	    vy *= friction;
    	    vx *= friction;
    	    		
    	
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
    	position.setLocation((float)position.getX()+vx, (float)position.getY()+vy);
    	
    	
    	       
    	
    	
    	//If the player moves out of bounds
        if(position.getX()<0) {
            position.setLocation(SCREEN_WIDTH, position.getY());
        }
        
        else if(position.getX()>SCREEN_WIDTH) {
            position.setLocation(0, position.getY());
        }
        
        if(position.getY() < 0) {
            position.setLocation(position.getX() , SCREEN_HEIGHT);
        }
        
        else if(position.getY() > SCREEN_HEIGHT) {
            position.setLocation(position.getX() , 0);
        }
        
        //Checks the respawnTime
        if (System.currentTimeMillis() - respawnTime > RESPAWN_PROTECTION_TIME) {
            respawnTime = 0;
            this.invincible=false;
        }
    }
    
    /**
     * @author Albin Karlquist
     * @return Returns a projectile at the ships front
     */
    public PlayerProjectile shoot() {
    	sound.startSound(2);
    	double x = position.getX() + Math.cos(rotation)*20;
    	double y = position.getY() + Math.sin(rotation)*20;
        return new PlayerProjectile((float)x, (float) y,rotation);
    }
    
    
    
    /**
     * Returns a polygon based on the ships corners for drawing.
     * @return Polygon based on the ships corners.
     */
    public Polygon getPolygon() {    	
    	int[] x = new int[corners.length];
    	int[] y = new int[corners.length];
    	
    	for (int i = 0; i < corners.length; i++) {
            x[i] = (int)(position.getX() + corners[i].getX());
            y[i] = (int)(position.getY() + corners[i].getY());
        }
    	
    	return new Polygon(x, y, x.length);
    }
    
    public Point2D.Float getPosition() {
    	return this.position;
    }
    
    public void setPosition(int x, int y) {
    	this.position = new Point2D.Float(x,y);
    }
    
    /**
     * @author Albin Karlquist
     * player takes damage and respawns
     * @throws NoHPLeftException if HP is 0
     */
    public void takeDamage() throws NoHPLeftException{
        if(!invincible) {
            this.health--;
            respawn();
            if (health == 0) {
            	throw new NoHPLeftException();
            }
        }
        
    }
    
    /**
     * @author Albin Karlquist
     * respawns the player, returning it to the starting position. Grants invincibility for 3 seconds
     */
    private void respawn() {    	
        this.invincible = true;
    	this.position.setLocation(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
    	this.vx = 0;
    	this.vy = 0;
    	this.respawnTime = System.currentTimeMillis();
    	    	   	
    }
    
    /**
     * @author Albin Karlquist
     * @return Returns if the player is inincible or not
     */
    public boolean getInincibility() {
        return this.invincible;
    }
    
    /**
     * @author Albin Karlquist
     * @return Returns the remaing health
     */
    public int getHP() {
        return this.health;
    }
    
    /**
     * @author Albin Karlquist
     * @return Returns the current score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * @author Albin Karlquist
     * Increament The player's score
     * @param score Amount of score to add
     */
    public void increaseScore(int score) {
        this.score += score;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }
}
