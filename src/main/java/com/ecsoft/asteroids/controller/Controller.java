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
public class Controller extends Observable implements Runnable{
	
	public ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	private final int tickDelay = 1000;
	
    public Controller() {
<<<<<<< HEAD
    	//Test att rita ut asteroid
    	asteroid.add(new Asteroid(500, 500));
    	
    	long time = System.currentTimeMillis();
//    	while(true) {
//    		if(System.currentTimeMillis()-time < 1000)
//    			continue;
//    		else {
//    			notifyObservers();
//    			System.out.println("Tick.");
//    			time = System.currentTimeMillis();
//    		}
//    	}
=======
>>>>>>> f88d337682265e4b364c825c3d27c3c4ecc51ff7
    }

	@Override
	public void run() {
		while(true) {
			long time = System.currentTimeMillis();
			
			asteroids.add(new Asteroid((int)(1000*Math.random()), (int)(800*Math.random())));
			
			super.setChanged();
			super.notifyObservers();
			
			try {
				Thread.sleep(tickDelay-(System.currentTimeMillis()-time));
			} catch (Exception e){}
		}
	}
}
