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
