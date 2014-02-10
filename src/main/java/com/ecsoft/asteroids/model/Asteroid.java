package com.ecsoft.asteroids.model;

import java.awt.geom.Point2D;

/**
 * Name: Asteroids
 * Description: Aseteroid
 *
 * @author: Victor Häggqvist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.controller
 */
public class Asteroid {

    private Point2D position;
    private Point2D velocity;
    private int size;



    /**
     * Creates a new asteroid at a random location with a random velocity
     * @param x Width of the game screen
     * @param y Heght of the game screen
     */
	public Asteroid(int x, int y) {
        this.size = 4;
        this.position = new Point2D.Float((float)(Math.random()*x),(float)(Math.random()*y));
        this.velocity = new Point2D.Float((float)(Math.random()),(float)(Math.random()));
	}

    /**
     * Creates a new asteroid at the specified location and size
     * @param x X-position of the asteroid
     * @param y Y-position of the asteroid
     * @param size Size of the asteroid
     */
    public Asteroid(int x, int y, int size) {
        this.size = size;
        this.position = new Point2D.Float(x,y);
        this.velocity = new Point2D.Float((float)(Math.random()),(float)(Math.random()));
    }

    /**
     * Updates the position of the asteroid
     */
    public void updatePos () {
        position.setLocation(position.getX() + velocity.getX(), position.getY() + velocity.getY());
    }





}
