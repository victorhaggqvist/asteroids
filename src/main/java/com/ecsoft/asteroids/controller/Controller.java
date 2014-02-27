package com.ecsoft.asteroids.controller;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import com.ecsoft.asteroids.mathematics.Collision;
import com.ecsoft.asteroids.mathematics.Trigonometry;
import com.ecsoft.asteroids.model.*;

/**
 * Name: Asteroids Description: Controller
 * 
 * @author: Victor Häggqvist
 * @since: 2/10/14 Package: com.ecsoft.asteroids.controller
 */
public class Controller extends Observable implements Runnable {

	private static final int SCREEN_WIDTH = 1000;
	private static final int SCREEN_HEIGHT = 600;
	
	private static final int NMBR_OF_ASTEROIDS = 10;
	private static final int EXPLOSION_SIZE = 10;
	private static final int ASTEROID_HEALTH = 2;
	private static final double ASTEROID_BASE_VELOCITY = 1.5;
	private static final int ASTEROID_SCORE = 100;
	private static final int SAUCER_SCORE = 250;
	private static final int SAUCER_SPAWN_RATE = 20000;
	private static final int PLAYER_TRAIL_INTENSITY = 10;
	private static final int PLAYER_SHOOTING_DELAY = 200;

	public ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	public ArrayList<PlayerProjectile> playerProjectiles = new ArrayList<PlayerProjectile>();
	public ArrayList<SaucerProjectile> saucerProjectiles = new ArrayList<SaucerProjectile>();
	public ArrayList<Saucer> saucers = new ArrayList<Saucer>();
	public long saucerTimer;
	public ArrayList<Particle> particles = new ArrayList<Particle>();
	private Sound sound = new Sound();
	
	public Player player;	
	private long trailTimer;
	private long shootTimer;

	private SettingsManager settings;
	private int level;

	public boolean gameStarted;
	private boolean gameOver;
	
	boolean shooting = false;

	private final int TICK_DELAY = 33;

	public Controller() {
	    settings = SettingsManager.getInstance();
		// Spawns a player at the center of the screen
		gameStarted = false;
		trailTimer = 0;
		shootTimer = 0;
		player = new Player(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		initiateGame(0);
	}

    /**
     * Perform an action related to the player
     * @param playerMovement Action to perform
     */
    public void playerAction(PlayerMovement playerMovement){
        switch (playerMovement){
            case UP:
            	if(!player.accelerating)
            		sound.startSound(3);
            	 
            	
                player.accelerating = true;
                break;
            case LEFT:
                player.turningL = true;
                player.turningR = false;
                break;
            case RIGHT:
                player.turningL = false;
                player.turningR = true;
                break;
            case NO_UP:
                player.accelerating = false;
                break;
            case NO_TURN:
                player.turningL = false;
                player.turningR = false;
                break;
            case SHOOT:
                startShoot();
                break;
            case NO_SHOOT:
                stopShoot();
        }
    }

	/**
	 * Start firing projectiles.
	 * Cannot start firing if already firing (prevents auto spamming caused by the OS)
	 * @author Albin Karlquist 
	 */
	public void startShoot() {
		if (!shooting) {
			playerProjectiles.add(player.shoot());
			shootTimer = System.currentTimeMillis();	
			shooting = true;
		}
	}
	
	/**
	 * Stop firing projectiles
	 * @author Albin Karlquist 
	 */
	public void stopShoot() {
		shootTimer = 0;
		shooting = false;
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
	 * @return True if game is over, otherwise false
	 */
	public boolean getGameOver() {
		return this.gameOver;
	}

	/**
	 * @author Albin Karlquist
	 * @return Returns the current level
	 */
	public int getLevel() {
		return this.level;
	}
	
	
	/**
	 * @author Albin Karlquist
	 * Destroys all current asteroids
	 */
	public void destroyAsteroids() {
		for (int i = 0; i < asteroids.size(); i++) {
			createExplosion((int)asteroids.get(i).getPosition().getX(), (int)asteroids.get(i).getPosition().getY());
			destroyAsteroid(i);			
		}
	}
	
	
	private void gameOver() {
		if(!gameOver) {
			System.out.println("game over");
			gameOver = true;			
		}
		
	}

	/**
	 * @author Albin Karlquist Iniatate game. Spawns asteroids at random
	 *         positions and a player in the center
	 * @param level
	 *            The current level. 0 for menu.
	 */
	public void initiateGame(int level) {
		asteroids.clear();
		playerProjectiles.clear();
		saucerProjectiles.clear();
		saucers.clear();
		saucerTimer = System.currentTimeMillis();
		particles.clear();
		player.setPosition(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		if (level == 1)
		    player = new Player(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		gameOver = false;
		this.level = level;

		// startmenu background
		if (level == 0) {
			for(int i = 0; i < 30; i++) {
				int x = (int) (Math.random() * SCREEN_WIDTH);
				int y = (int) (Math.random() * SCREEN_HEIGHT);
				asteroids.add(new Asteroid(new Point2D.Float(x, y),
						ASTEROID_HEALTH, ASTEROID_BASE_VELOCITY));
			}
		}

		// Actual level
		else {

			// Creates asteroids at random positions, but not too close to the
			// player's starting position (center)
			while (asteroids.size() < NMBR_OF_ASTEROIDS+level) {
				int x = SCREEN_WIDTH / 2;
				int y = SCREEN_HEIGHT / 2;

				// Randomize until value isn't too close to the center
				while (x > (SCREEN_WIDTH / 2) - 100
						&& x < (SCREEN_WIDTH / 2) + 100) {
					x = (int) (Math.random() * SCREEN_WIDTH);
				}

				while (y > (SCREEN_HEIGHT / 2) - 100
						&& y < (SCREEN_HEIGHT / 2) + 100) {
					y = (int) (Math.random() * SCREEN_HEIGHT);
				}
				asteroids.add(new Asteroid(new Point2D.Float(x, y),
						ASTEROID_HEALTH + settings.getDifficulty(), ASTEROID_BASE_VELOCITY+(level*0.2)));
			}
		}
	}

	@Override
	public void run() {

		while (true) {
			
			long time = System.currentTimeMillis();

			// Check if level is completed
			if (asteroids.size() == 0) {
				level++;
				initiateGame(level);
			}

            synchronized (asteroids){
                // Update the position of all asteroids
                for (Asteroid a : asteroids)
                    a.updatePos();
            }

			// Spawn a saucer every x second where x = SAUCER_SPAWN_RATE
			if (System.currentTimeMillis() - saucerTimer > SAUCER_SPAWN_RATE) {
				saucerTimer = System.currentTimeMillis();
				// Spawning a saucer far outside the map, which instantly moves
				// it to a random corner ("out of bounds"-mechanic)
				saucers.add(new Saucer(10000, 10000));
			}

			// Update the position of all saucers
			for (Saucer a : saucers) {
				try {
					a.updatePos();
				} catch (SaucerShootException e) {
					// Add a projectile at the location of the saucer going
					// towards the player
					double angle = Trigonometry.angle(a.getPosition(),
							player.getPosition());
					saucerProjectiles.add(new SaucerProjectile((float) e.getSaucerPos()
							.getX(), (float) e.getSaucerPos().getY(), angle));
				}
			}

			// Updates position of player
			player.updatePos();
			
			//Shoot if space is pressed down
			if (shootTimer != 0) {
				if (System.currentTimeMillis()-shootTimer > PLAYER_SHOOTING_DELAY) {
					shootTimer = System.currentTimeMillis();
					playerProjectiles.add(player.shoot());
					
				}				
			}
			

			// Updates player projectiles
			for (int i = 0; i < playerProjectiles.size(); i++) {
				try {
					playerProjectiles.get(i).updatePos();
					// Removes the projectile if it catches Exception
				} catch (ObjectExpiredException e) {
					playerProjectiles.remove(i);
				}
			}
			
			// Updates saucer projectiles
			for (int i = 0; i < saucerProjectiles.size(); i++) {
				try {
					saucerProjectiles.get(i).updatePos();
					// Removes the projectile if it catches Exception
				} catch (ObjectExpiredException e) {
					saucerProjectiles.remove(i);
				}
			}
			
			

			// Updates particles
			for (int i = 0; i < particles.size(); i++) {
				try {
					particles.get(i).updatePos();
					// Removes the particle if it catches Exception
				} catch (ObjectExpiredException e) {
					particles.remove(i);
				}
			}

			// Check for collision between player and asteroids
			for (int i = 0; i < asteroids.size(); i++) {

				if (player.getPolygon().intersects(
						asteroids.get(i).getPolygon().getBounds2D())) {
					if (Collision.collide(player.getPolygon(), asteroids.get(i)
							.getPolygon())) {
						try {
							player.takeDamage();

							// Game Over if NoHPLeftException is catched
						} catch (NoHPLeftException e) {
							gameOver();
						}
					}
				}
			}

			// Check for collision between player and saucer
			for (int i = 0; i < saucers.size(); i++) {

				if (player.getPolygon().intersects(
						saucers.get(i).getPolygon().getBounds2D())) {
					if (Collision.collide(player.getPolygon(), saucers.get(i)
							.getPolygon())) {
						try {
							player.takeDamage();

							// Game Over if NoHPLeftException is catched
						} catch (NoHPLeftException e) {
							gameOver();
						}
					}
				}
			}
			
			//Check for collision between player and projectiles
			for (int j = 0; j < saucerProjectiles.size(); j++) {
			    if (player.getPolygon().contains(saucerProjectiles.get(j).getPos())) {
					try {
						player.takeDamage();
						// Game Over if NoHPLeftException is catched
					} catch (NoHPLeftException e) {
						gameOver();
					}
				}
			}
			
			
			//Checks for collision between asteroids
			for (int i = 0; i < asteroids.size()-1; i++) {
				for (int j = i+1; j < asteroids.size(); j++) {
					if (asteroids.get(i).getPolygon().intersects(
							asteroids.get(j).getPolygon().getBounds2D())) {
						if (Collision.collide(asteroids.get(i).getPolygon(), asteroids.get(j)
								.getPolygon())) {
							
							//Change the direction of the asteroids so that they bounce off each other
							Point2D.Float velocity = Trigonometry.getVector(asteroids.get(i).getPosition(), asteroids.get(j).getPosition());
							asteroids.get(i).setVelocity(velocity);
							velocity = Trigonometry.getVector(asteroids.get(j).getPosition(), asteroids.get(i).getPosition());
							asteroids.get(j).setVelocity(velocity);
						}
						
					}
				}				
			}
			
			

			// Checks for collision between player projectiles and asteroids
			for (int i = 0; i < asteroids.size(); i++) {
				for (int j = 0; j < playerProjectiles.size(); j++) {

					// Checks if a bullet has collided with an asteroid
				    //if (Collision.projectileCollision(playerProjectiles.get(j), player.getPolygon())) {
				    if (asteroids.get(i).getPolygon().contains(playerProjectiles.get(j).getPos())) {
						
						createExplosion((int)playerProjectiles.get(j).getPos().getX(), (int)playerProjectiles.get(j).getPos().getY());
						
						
						playerProjectiles.remove(j);
						// Removes the bullet and asteroid. Then creates two new
						// smaller asteroids next to each other.
						
						int size = asteroids.get(i).getSize();

						destroyAsteroid(i);
						player.increaseScore(ASTEROID_SCORE);

						// Break to prevent IndexOutOfBoundsException
						break;
					}
				}
			}
			
			// Checks for collision between saucer projectiles and asteroids
			for (int i = 0; i < asteroids.size(); i++) {
				for (int j = 0; j < saucerProjectiles.size(); j++) {

					// Checks if a bullet has collided with an asteroid
					if (asteroids.get(i).getPolygon()
							.contains(saucerProjectiles.get(j).getPos())) {

						createExplosion((int) saucerProjectiles.get(j).getPos()
								.getX(), (int) saucerProjectiles.get(j)
								.getPos().getY());

						saucerProjectiles.remove(j);
						// Removes the bullet and asteroid. Then creates two new
						// smaller asteroids next to each other.

						int size = asteroids.get(i).getSize();

						destroyAsteroid(i);
						player.increaseScore(ASTEROID_SCORE);

						// Break to prevent IndexOutOfBoundsException
						break;
					}
				}
			}

			// Checks for collisions between projectiles and saucers
			for (int i = 0; i < saucers.size(); i++) {
				for (int j = 0; j < playerProjectiles.size(); j++) {

					// Checks if a bullet has collided with an asteroid
					// [BUG] : Causes IndexOutOfBoundsException in some cases
					if (saucers.get(i).getPolygon()
							.contains(playerProjectiles.get(j).getPos())) {

						// Creates particles for explosion effect
						for (int k = 0; k < EXPLOSION_SIZE; k++) {
							particles.add(new Particle(new Point2D.Float(
									((int) playerProjectiles.get(j).getPos().getX()),
									(int) playerProjectiles.get(j).getPos().getY())));
						}
						// Removes the saucer
						saucers.remove(i);
						playerProjectiles.remove(j);

						player.increaseScore(SAUCER_SCORE);

						// Break to prevent IndexOutOfBoundsException
						break;
					}
				}
			}
			
			//Creates a trail of particles if the player is accelerating
			if (player.accelerating == true) {
				if (System.currentTimeMillis() - trailTimer > PLAYER_TRAIL_INTENSITY) {
					trailTimer = System.currentTimeMillis();
					double x = player.getPosition().getX() - Math.cos(player.rotation)*20;
			    	double y = player.getPosition().getY() - Math.sin(player.rotation)*20;
					
					Point2D.Float pos = new Point2D.Float((float)x, (float)y);
					particles.add(new Particle(pos));
				}
				
			}

			super.setChanged();
			super.notifyObservers();

			try {
				Thread.sleep(TICK_DELAY - (System.currentTimeMillis() - time));				
			} catch (Exception e) {
			}
		}
	}
	
	private void destroyAsteroid(int i) {

		int size = asteroids.get(i).getSize();

		if (size > 1) {
			asteroids.add(new Asteroid(new Point2D.Float((int) asteroids.get(i)
					.getPosition().getX()
					+ 10 * size, (int) asteroids.get(i).getPosition().getY()),
					size - 1, ASTEROID_BASE_VELOCITY + (level * 0.2)));
			asteroids.add(new Asteroid(new Point2D.Float((int) asteroids.get(i)
					.getPosition().getX()
					- 10 * size, (int) asteroids.get(i).getPosition().getY()),
					size - 1, ASTEROID_BASE_VELOCITY + (level * 0.2)));
		}
		asteroids.remove(i);
	}
	
	public void createExplosion (int x, int y) {
		if(Math.random() < 0.5) {
			sound.startSound(1);
		}else {
			sound.startSound(0);
		}
		
		for (int k = 0; k < EXPLOSION_SIZE; k++) {

			particles.add(new Particle(new Point2D.Float(x,y)));
		}
	}
}
