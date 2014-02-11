package com.ecsoft.asteroids.model;

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
    private static double friction = 0.99;
    
    private Point2D position;
    private double velocity;
    private double degree;
    
   /**
    * Creates a player-controlled ship at the center position
    * @param x The width of the gamescreen
    * @param y The height of the gamescreen
    */
    
    public Player(int x, int y) {
        position = new Point2D.Float(x/2,y/2);
        velocity = 0;
        degree = 0;
    }
    
    /**
     * Updates the position of the player
     */
    public void updatePos() {
        
    }
    
    
    
    
    
    
}
