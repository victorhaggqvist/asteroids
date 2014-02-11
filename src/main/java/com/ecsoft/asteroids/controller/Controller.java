package com.ecsoft.asteroids.controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Date;

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
    	
    	long time = System.currentTimeMillis();
    	while(true) {
    		if(System.currentTimeMillis()-time < 1000)
    			continue;
    		else {
    			notifyObservers();
    			System.out.println("Tick.");
    			time = System.currentTimeMillis();
    		}
    	}
    }
}
