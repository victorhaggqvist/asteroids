package com.ecsoft.asteroids.controller;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.prefs.Preferences;

import com.ecsoft.asteroids.mathematics.Collision;
import com.ecsoft.asteroids.mathematics.Trigonometry;
import com.ecsoft.asteroids.model.Asteroid;
import com.ecsoft.asteroids.model.NoHPLeftException;
import com.ecsoft.asteroids.model.ObjectExpiredException;
import com.ecsoft.asteroids.model.Particle;
import com.ecsoft.asteroids.model.Player;
import com.ecsoft.asteroids.model.Projectile;
import com.ecsoft.asteroids.model.Saucer;
import com.ecsoft.asteroids.model.SaucerShootException;
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

	private static final int SCREEN_WIDTH = 1000;
	private static final int SCREEN_HEIGHT = 600;
	private static final int NMBR_OF_ASTEROIDS = 10;
	private static final int EXPLOSION_SIZE = 10;
	private static final int ASTEROID_HEALTH = 3;
	private static final int ASTEROID_SCORE = 100;
	private static final int SAUCER_SCORE = 250;
	private static final int SAUCER_SPAWN_RATE = 10000;
	
	public ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public ArrayList<Saucer> saucers = new ArrayList<Saucer>();
	public long saucerTimer;
	public ArrayList<Particle> particles = new ArrayList<Particle>();
	public Player player;
	public boolean gameStarted;
	
	private final int TICK_DELAY = 33;
	
    public Controller() {
    	//Spawns a player at the center of the screen
        gameStarted=false;
        saucerTimer = 0;
        initiateGame(0);
    	
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
    
    /**
     * @author Albin Karlquist
     * @return Returns the remaing health
     */
    public int getHP() {
        return player.getHP();
    }
    
    /**
     * @author Albin Karlquist
     * @return Returns the player's score
     */
    public int getScore() {
        return player.getScore();
    }

    
    /**
     * @author Albin Karlquist
     * Iniatate game. Spawns asteroids at random positions and a player in the center
     * @param level The current level. 0 for menu.
     */
    public void initiateGame(int level) {
      asteroids.clear();
      projectiles.clear();
      saucers.clear();
      saucerTimer = 0;
      particles.clear();
      player = new Player(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
      //Creates asteroids at random positions, but not too close to the player's starting position (center)
        while(asteroids.size() < NMBR_OF_ASTEROIDS) {
            int x = SCREEN_WIDTH/2;
            int y = SCREEN_HEIGHT/2;
            
            //Randomize until value isn't too close to the center
            while(x > (SCREEN_WIDTH/2)-100 && x < (SCREEN_WIDTH/2)+100) {
                x = (int)(Math.random()*SCREEN_WIDTH);
            }
            
            while(y > (SCREEN_HEIGHT/2)-100 && y < (SCREEN_HEIGHT/2)+100) {
                y = (int)(Math.random()*SCREEN_HEIGHT);
            }
            
            asteroids.add(new Asteroid(new Point2D.Float(x,y) , ASTEROID_HEALTH));
        }
    }
    
	@Override
	public void run() {
        // [todo] - remove sample
        Preferences preferences = SettingsManager.getPreferences();
        final String SAMPLE_KEY = "sampel_key";
        preferences.put(SAMPLE_KEY, "saved stuff");
        System.out.println("Sample setting: "+preferences.get(SAMPLE_KEY,""));
             
        
        while(true) {
			long time = System.currentTimeMillis();			
			
			
			for(Asteroid a : asteroids)
				a.updatePos();
			
			//Spawn a saucer every x second where x = SAUCER_SPAWN_RATE
			if (System.currentTimeMillis()-saucerTimer > SAUCER_SPAWN_RATE) {
				saucerTimer = System.currentTimeMillis();
				//Spawning a saucer far outside the map, which instantly moves it to a random corner ("out of bounds"-mechanic)
				saucers.add(new Saucer(10000,10000));
			}
                
			
			
			for(Saucer a : saucers) {
                try {
					a.updatePos();
				} catch (SaucerShootException e) {
					//Add a projectile at the location of the saucer going towards the player
					double angle = Trigonometry.angle(a.getPosition(), player.getPosition());
					projectiles.add(new Projectile((float)e.getSaucerPos().getX(), (float)e.getSaucerPos().getY(), angle));
				}
			}
			
			player.updatePos();			
			
			//Updates projectiles
			for (int i = 0; i < projectiles.size(); i++) {
                try {
                    projectiles.get(i).updatePos();
                    //Removes the projectile if it catches Exception
                } catch (ObjectExpiredException e) {
                    projectiles.remove(i);
                }
			}
			
			//Updates particles
			for (int i = 0; i < particles.size(); i++) {
                try {
                    particles.get(i).updatePos();
                    //Removes the particle if it catches Exception
                } catch (ObjectExpiredException e) {
                    particles.remove(i);
                }
            }
			
			//Check for collision between player and asteroids
			for (int i = 0; i < asteroids.size(); i++) {
			    		    
                if (player.getPolygon().intersects(asteroids.get(i).getPolygon().getBounds2D())) {                 
                    if(Collision.collide(player.getPolygon(), asteroids.get(i).getPolygon())) {
                        try {
							player.takeDamage();							
							
							//Game Over if NoHPLeftException is catched
						} catch (NoHPLeftException e) {
							System.out.println("Game Over");
						}
                    } 
                }
            }
			
			//Check for collision between player and saucer
			for (int i = 0; i < saucers.size(); i++) {
			    		    
                if (player.getPolygon().intersects(saucers.get(i).getPolygon().getBounds2D())) {                 
                    if(Collision.collide(player.getPolygon(), saucers.get(i).getPolygon())) {
                        try {
                            player.takeDamage();                            
                            
                            //Game Over if NoHPLeftException is catched
                        } catch (NoHPLeftException e) {
                            System.out.println("Game Over");
                        }
                    } 
                }
            }
			
			//Checks for collision between projectiles and asteroids
			for (int i = 0; i < asteroids.size(); i++) {
                for (int j = 0; j < projectiles.size(); j++) {
                	
                	//Checks if a bullet has collided with an asteroid
                    //[BUG] : Causes IndexOutOfBoundsException in some cases
                    if(asteroids.get(i).getPolygon().contains(projectiles.get(j).getPos())) {
                    	
                      //Creates particles for explosion effect
                        for (int k = 0; k < EXPLOSION_SIZE; k++) {
                            particles.add(new Particle(new Point2D.Float(((int)projectiles.get(j).getPos().getX()),(int)projectiles.get(j).getPos().getY())));
                        }
                        
                    	//Removes the bullet and asteroid. Then creates two new smaller asteroids.
                        projectiles.remove(j);
                        int size = asteroids.get(i).getSize();
                    
                        if(size > 1) {
                            asteroids.add(new Asteroid(new Point2D.Float((int)asteroids.get(i).getPosition().getX(), (int)asteroids.get(i).getPosition().getY()), size-1));
                            asteroids.add(new Asteroid(new Point2D.Float((int)asteroids.get(i).getPosition().getX(), (int)asteroids.get(i).getPosition().getY()), size-1));      
                        }
                        asteroids.remove(i);
                        
                        player.increaseScore(ASTEROID_SCORE);
                        
                        //Break to prevent IndexOutOfBoundsException
                        break;
                    }                        
                }
            }
			
			//Checks for collisions between projectiles and saucers
			for (int i = 0; i < saucers.size(); i++) {
                for (int j = 0; j < projectiles.size(); j++) {
                    
                    //Checks if a bullet has collided with an asteroid
                    //[BUG] : Causes IndexOutOfBoundsException in some cases
                    if(saucers.get(i).getPolygon().contains(projectiles.get(j).getPos())) {
                        
                      //Creates particles for explosion effect
                        for (int k = 0; k < EXPLOSION_SIZE; k++) {
                            particles.add(new Particle(new Point2D.Float(((int)projectiles.get(j).getPos().getX()),(int)projectiles.get(j).getPos().getY())));
                        }                        
                        //Removes the saucer
                        saucers.remove(i);
                        projectiles.remove(j);
                        
                        player.increaseScore(SAUCER_SCORE);
                        
                        //Break to prevent IndexOutOfBoundsException
                        break;
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
