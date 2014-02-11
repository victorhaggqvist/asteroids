package com.ecsoft.asteroids.controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.prefs.Preferences;

import com.ecsoft.asteroids.model.Asteroid;
import com.ecsoft.asteroids.model.Projectile;

import com.ecsoft.asteroids.model.SettingsManager;
import org.omg.DynamicAny._DynAnyFactoryStub;


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
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private final int TICK_DELAY = 33;
	
    public Controller() {

    }

	@Override
	public void run() {
        Preferences preferences = SettingsManager.getPreferences();
        final String SAMPLE_KEY = "sampel_key";
        preferences.put(SAMPLE_KEY, "saved stuff");
        System.out.println("Sample setting: "+preferences.get(SAMPLE_KEY,""));
        while(true) {
			long time = System.currentTimeMillis();
			
			if(asteroids.size() < 10)
				asteroids.add(new Asteroid((int)(1000*Math.random()), (int)(800*Math.random())));
			
			if(projectiles.size() < 10)
			    projectiles.add(new Projectile((float)(1000*Math.random()), (float)(800*Math.random()), Math.PI-Math.PI/2));
			
			for(Asteroid a : asteroids)
				a.updatePos();
			
			for(Projectile a : projectiles)
                a.updatePos();
			
			super.setChanged();
			super.notifyObservers();
			
			try {
				Thread.sleep(TICK_DELAY -(System.currentTimeMillis()-time));
			} catch (Exception e){}
		}
	}
}
