package com.ecsoft.asteroids.controller;

import java.util.ArrayList;
import java.util.Observable;

import com.ecsoft.asteroids.model.Asteroid;
import com.ecsoft.asteroids.view.View;

/**
 * Name: Asteroids
 * Description: Controller
 *
 * @author: Victor Häggqvist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.controller
 */
public class Controller extends Observable {
	
	public ArrayList<Asteroid> asteroid;
	
    public Controller() {
    	//Test att rita ut asteroid
    	asteroid.add(new Asteroid(50, 50));
    	notifyObservers();
    }
}
