package com.ecsoft.asteroids.controller;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.prefs.Preferences;

import com.ecsoft.asteroids.mathematics.Collision;
import com.ecsoft.asteroids.model.Asteroid;
import com.ecsoft.asteroids.model.BulletExpired;
import com.ecsoft.asteroids.model.Player;
import com.ecsoft.asteroids.model.Projectile;
import com.ecsoft.asteroids.model.Saucer;
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
	public ArrayList<Saucer> saucers = new ArrayList<Saucer>();
	public Player player;
	private final int TICK_DELAY = 33;
	
    public Controller() {
    	player = new Player(350, 350);
    }
    
    /**
     * @author Albin Karlquist
     * moves the player forward
     */
    public void moveForward() {
        player.accelerating = true;
    }
    
    /**
     * @author Albin Karlquist
     * stops thrusting
     */
    public void stopMove() {
        player.accelerating = false;
    }

    /**
     * @author Albin Karlquist
     * Begin rotating left
     */
    public void rotateLeft() {
        player.turningR = false;
        player.turningL = true;        
    }
     
    /**
     * @author Albin Karlquist
     * Begin rotating right
     */
    public void rotateRight() {
        player.turningL = false;
        player.turningR = true;
    }
    
    /**
     * @author Albin Karlquist
     * Stop rotating
     */
    public void stopRotate() {
        player.turningL = false;
        player.turningR = false;
    }
    
    /**
     * @author Albin Karlquist
     * Shoots a projectile
     */
    public void shoot() {
        projectiles.add(player.shoot());        
    }
    
	@Override
	public void run() {
        // [todo] - remove sample
        Preferences preferences = SettingsManager.getPreferences();
        final String SAMPLE_KEY = "sampel_key";
        preferences.put(SAMPLE_KEY, "saved stuff");
        System.out.println("Sample setting: "+preferences.get(SAMPLE_KEY,""));
        
        while(asteroids.size() < 10)
            asteroids.add(new Asteroid(1000, 600));
        while(true) {
			long time = System.currentTimeMillis();
			
			
			
//			if(projectiles.size() < 10)
//			    projectiles.add(new Projectile((float)(1000*Math.random()), (float)(800*Math.random()), Math.random()*2*Math.PI));
			
			if(saucers.size() < 2)
                saucers.add(new Saucer());
			
			for(Asteroid a : asteroids)
				a.updatePos();
			
			for(Saucer a : saucers)
                a.updatePos();
			
			player.updatePos();			
			
			for (int i = 0; i < projectiles.size(); i++) {
                try {
                    projectiles.get(i).updatePos();
                } catch (BulletExpired e) {
                    projectiles.remove(i);
                }
			}
			
			//Check for collision between player and asteroids
			for (int i = 0; i < asteroids.size(); i++) {
			    		    
                if (player.getPolygon().intersects(asteroids.get(i).getPolygon().getBounds2D())) {                 
                    if(Collision.collide(player.getPolygon(), asteroids.get(i).getPolygon())) {
                        //Catch noHPLeft exception
                        player = new Player(350, 350);
                    }
                        
                    
                    
                }
            }
			
			//Checks for collision between projectiles and asteroids
			for (int i = 0; i < asteroids.size(); i++) {
                for (int j = 0; j < projectiles.size(); j++) {
                    if(asteroids.get(i).getPolygon().contains(projectiles.get(j).getPos())) {
                        projectiles.remove(j);
                        int size = asteroids.get(i).getSize();
                        Point2D.Float pos = asteroids.get(i).getPosition();
                        asteroids.remove(i);
                        if(size > 1) {
                            asteroids.add(new Asteroid(pos, size-1));
                            asteroids.add(new Asteroid(pos, size-1));      
                        }
                    }                        
                }
            }
			
			
			super.setChanged();
			super.notifyObservers();
			
			try {
				Thread.sleep(TICK_DELAY -(System.currentTimeMillis()-time));
			} catch (Exception e){}
		}
	}
}
