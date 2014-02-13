/**
 * 
 */
package com.ecsoft.asteroids.model;

import java.awt.geom.Point2D;

/**
 * @author Albin Karlquist
 * This class represents a particle.
 */
public class Particle {
    
    private static final float MAX_TIME_TO_LIVE = 3000;
    private static final float MAX_VELOCITY = 1;
    
    private Point2D.Float position, velocity; 
    private float timeToLive;
    private long time;
    
    /**
     * Creates a new particle at the specified location with a random velocity
     * @param pos Position of particle
     */
    public Particle(Point2D.Float pos) {
       this.position = pos;
       this.velocity = new Point2D.Float((float)(MAX_VELOCITY*Math.random()-MAX_VELOCITY/2), ((float)(MAX_VELOCITY*Math.random()-MAX_VELOCITY/2)));
       timeToLive = (float)Math.random()*MAX_TIME_TO_LIVE;
       time = System.currentTimeMillis();
    }
    
   /**
    * @author Albin Karlquist
    * Updates the position of the particle
    * @throws ObjectExpiredException if the particle has expired
    */
    public void updatePos() throws ObjectExpiredException {
        
        position.setLocation(this.position.getX() + this.velocity.getX(), this.position.getY() + this.velocity.getY());        
        //If the particle has expired
        if(System.currentTimeMillis()-time > timeToLive){
            System.out.println("Expired");
            throw new ObjectExpiredException();
            
        }
    }
    
    public Point2D.Float getPos() {
        return position;
    }
}
