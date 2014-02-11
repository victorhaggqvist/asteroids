package com.ecsoft.asteroids.controller;

import java.util.ArrayList;
import java.util.Observable;

import com.ecsoft.asteroids.model.Asteroid;

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
	private final int TICK_DELAY = 33;
	
    public Controller() {

    }

	@Override
	public void run() {
		while(true) {
			long time = System.currentTimeMillis();
			
			if(asteroids.size() < 10)
				asteroids.add(new Asteroid((int)(1000*Math.random()), (int)(800*Math.random())));
			
			for(Asteroid a : asteroids)
				a.updatePos();
			
			super.setChanged();
			super.notifyObservers();
			
			try {
				Thread.sleep(TICK_DELAY -(System.currentTimeMillis()-time));
			} catch (Exception e){}
		}
	}
}
