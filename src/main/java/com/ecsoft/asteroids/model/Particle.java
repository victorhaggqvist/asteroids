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
    private static final float MAX_VELOCITY = 2;
    private static final int PARTICLE_SIZE = 4;
    
    private Point2D.Float position, velocity; 
    private float timeToLive;
    private long time;
    private int size;
    
    /**
     * Creates a new particle at the specified location with a random velocity
     * @param pos Position of particle
     */
    public Particle(Point2D.Float pos) {
       this.position = pos;
       this.velocity = new Point2D.Float((float)(MAX_VELOCITY*Math.random()-MAX_VELOCITY/2), ((float)(MAX_VELOCITY*Math.random()-MAX_VELOCITY/2)));
       timeToLive = (float)Math.random()*MAX_TIME_TO_LIVE;
       time = System.currentTimeMillis();
       this.size = 0;
    }
    
   /**
    * @author Albin Karlquist
    * Updates the position of the particle
    * @throws ObjectExpiredException if the particle has expired
    */
    public void updatePos() throws ObjectExpiredException {
        
        position.setLocation(this.position.getX() + this.velocity.getX(), this.position.getY() + this.velocity.getY());
        
        //Increase the sie of the particle as its lifetime increases for effect
        long lifeTime = System.currentTimeMillis()-time;
        if(lifeTime<timeToLive*0.8) {
            this.size = PARTICLE_SIZE;
            if(lifeTime<timeToLive*0.6) {
                this.size = PARTICLE_SIZE-1;
                if(lifeTime<timeToLive*0.4) {
                    this.size=PARTICLE_SIZE-2;
                    if(lifeTime<timeToLive*0.2) {
                        this.size=PARTICLE_SIZE-3;
                        if(lifeTime<timeToLive*0.1) {
                            this.size=PARTICLE_SIZE-4;
                        }
                    }
                }
            }
        }
        
        
        //If the particle has expired
        if(System.currentTimeMillis()-time > timeToLive)
            throw new ObjectExpiredException();
    }
    
    public Point2D.Float getPos() {
        return position;
    }
    
    /**
     * @author Albin Karlquist
     * @return Returns the size of the particle
     */
    public int getSize() {
        return this.size;
    }
    
}
