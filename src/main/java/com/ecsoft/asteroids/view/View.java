package com.ecsoft.asteroids.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.ecsoft.asteroids.controller.Controller;
import com.ecsoft.asteroids.controller.ScoreHandler;
import com.ecsoft.asteroids.controller.Sound;
import com.ecsoft.asteroids.model.Asteroid;
import com.ecsoft.asteroids.model.Heart;
import com.ecsoft.asteroids.model.HighScore;
import com.ecsoft.asteroids.model.Particle;
import com.ecsoft.asteroids.model.PlayerMovement;
import com.ecsoft.asteroids.model.Projectile;
import com.ecsoft.asteroids.model.Saucer;
import com.ecsoft.asteroids.model.SettingsManager;

/**
 * Name: Asteroids
 * Description: View
 *
 * @author: Victor Häggqvist
 * @editor Albin Karlquist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.view
 */
public class View implements Observer{
    
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 600;

	private JPanel gamePanel, startPanel;
	private Controller contr;
	private boolean startOverlayOntop = true;
	private JFrame frame;
	
	private SettingsManager settings;
	private String [] menuItems = {"START GAME", "OPTIONS" , "HISCORE" , "ABOUT", "QUIT"};
	private String [] optionItems = {"DIFFICULTY", "SHIP COLOR", "SOUND", "MUSIC" ,"BACK"};
	private String [] hiscoreItems = {"BACK"};
	
	private boolean gameStarted;
	
	private int menuSelector = 0;	
	private boolean optionScreen;	
	private boolean hiscoreScreen;
	public static ArrayList<HighScore> highScores = new ArrayList<HighScore>();
	
	private String hiscoreName = "";
	
	private KeyAdapter keyListener, menuListener;
	
	private Sound sound = new Sound();
	
    public View(Controller contr) {
        settings = SettingsManager.getInstance();
        frame = new JFrame( "Asteroids" );
        frame.setResizable(false);
        frame.setUndecorated(true);
        
        gameStarted = false;
        optionScreen = false;
        hiscoreScreen = false;
    	this.contr = contr;
    	contr.addObserver(this);
    	createStartPanel();
    	//createGamePanel();
    	
    }
    
    private void createStartPanel() {
    	//Register key listener
    	frame.removeKeyListener(keyListener);
    	menuListener = new menuListener();
    	frame.addKeyListener(menuListener);
    	
    	contr.initiateGame(0);
    	menuSelector = 0;
        startPanel = new JPanel(){            
            private static final long serialVersionUID = 1L;
            

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                this.setBackground(Color.black);
                g.setColor(Color.white);    
    
                for(Asteroid a : contr.asteroids) {
                    g.drawPolygon(a.getPolygon());
                    //g.drawRect((int)a.getPolygon().getBounds2D().getX(),(int)a.getPolygon().getBounds2D().getY(),(int)a.getPolygon().getBounds2D().getWidth(),(int)a.getPolygon().getBounds2D().getHeight());
                }
                
                //Draw saucers
                g.setColor(Color.red);  
                for(Saucer a : contr.saucers)
                    g.drawPolygon(a.getPolygon());
                
                
                
                //Draw particles
                g.setColor(Color.white); 
                for (int i = 0; i < contr.particles.size(); i++) {
                    Particle a = contr.particles.get(i);
                    g.fillOval((int)(a.getPos().getX()+Math.random()), (int)(a.getPos().getY()+Math.random()), 4, 4);
                }
                
                //
                //MENU
                //
                
                g.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics fm = getFontMetrics( g.getFont() );
                g.setColor(Color.WHITE);
                
                //Draws option screen
                int width = 0;
                if (optionScreen) { 
                	width = fm.stringWidth(optionItems[0] + " = " + settings.DIFFICULTIES[settings.getDifficulty()]);
                    g.drawString(optionItems[0] + " = " + settings.DIFFICULTIES[settings.getDifficulty()], (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*0);
                    
                    width = fm.stringWidth(optionItems[1] + " = " + settings.COLOR_NAMES[settings.getColor()]);
                    g.drawString(optionItems[1] + " = " + settings.COLOR_NAMES[settings.getColor()], (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*1);
                    
                    width = fm.stringWidth(optionItems[2] + " = " + settings.getSound());
                    g.drawString(optionItems[2] + " = " + settings.getSound(), (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*2);
                    
                    width = fm.stringWidth(optionItems[3] + " = " + settings.getMusic());
                    g.drawString(optionItems[3] + " = " + settings.getMusic(), (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*3);
                    
                    width = fm.stringWidth(optionItems[4]);
                    g.drawString(optionItems[4], (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*4);
                }
                
                //Draw hiscore screen
                else if (hiscoreScreen) { 
                	for (int i = 0; i < highScores.size(); i++) {
                		String name = highScores.get(i).getName();
                		int score = highScores.get(i).getScore();
                        g.drawString(name , (SCREEN_WIDTH/2)-200 , 50+45*i);
                        g.drawString("" + score , (SCREEN_WIDTH/2)+200 , 50+45*i);
                        g.drawLine((SCREEN_WIDTH/2)-200, 52+45*i , (SCREEN_WIDTH/2)+250, 52+45*i);
                        
                        width = fm.stringWidth(hiscoreItems[0]);
                        g.drawString(hiscoreItems[0], (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT)-60));
					}
                	
                }
                
                //Draws start screen
                else {
                	 for (int i = 0; i < menuItems.length; i++) {
                         width = fm.stringWidth(menuItems[i]);
                         g.drawString(menuItems[i], (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*i);
                     }
                	
                    

                }
                
                //Draws the marker                
                    int [] xPoints = new int[3];
                    int [] yPoints = new int[3];
                    
                  //Marker for optionsScreen
                    if(optionScreen == true && hiscoreScreen == false) {
	                    xPoints[0] = (SCREEN_WIDTH/2)-fm.stringWidth(optionItems[menuSelector]) - 5;
	                    yPoints[0] = ((SCREEN_HEIGHT/2)-70)+40*menuSelector;
                    }
                    
                    //Marker for hiscore
                    else if(optionScreen == false && hiscoreScreen == true) {
	                    xPoints[0] = (SCREEN_WIDTH/2)-fm.stringWidth(hiscoreItems[menuSelector])/2 - 20;
	                    yPoints[0] = ((SCREEN_HEIGHT)-70)+40*menuSelector;
                    }
                    
                    else {
                    	xPoints[0] = (SCREEN_WIDTH/2)-fm.stringWidth(menuItems[menuSelector])/2 - 20;
	                    yPoints[0] = ((SCREEN_HEIGHT/2)-70)+40*menuSelector;
                    }
                    
                    xPoints[1] = xPoints[0]-20;
                    yPoints[1] = yPoints[0]-10;
                    xPoints[2] = xPoints[0]-20;
                    yPoints[2] = yPoints[0]+10;
                    
                    g.fillPolygon(xPoints, yPoints, xPoints.length); 
            }            
            
            
        };
        frame.add(startPanel);
        frame.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        startPanel.repaint();        
    }
    
    
    private void createGamePanel() {   
    	sound.startMusic();
    	
    	//Register key listener
    	frame.removeKeyListener(menuListener);
    	keyListener = new KeyListener();
    	frame.addKeyListener(keyListener);
    	hiscoreName = "";
        
        gamePanel = new JPanel(){
        	
			private static final long serialVersionUID = 1L;
			
			//used for calculating FPS
			long time = System.currentTimeMillis();
			long fps = 0;
			
			@Override
        	protected void paintComponent(Graphics g) {
			    
			    super.paintComponent(g);
                this.setBackground(Color.black);
                g.setColor(Color.white);	        
                FontMetrics fm;                
			    
                g.setColor(Color.WHITE); 
                int width = 0; 			    
                
                //Draws game over text if nescessary
                if (contr.getGameOver()) {
                	g.setFont(new Font("Courier", Font.BOLD, 20));
                    fm = getFontMetrics( g.getFont() );
                	width = fm.stringWidth("GAME OVER");
                    g.drawString("GAME OVER", (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-50));
                    
                  //Draw name prompt
                    width = fm.stringWidth(hiscoreName);
                    g.drawString(hiscoreName, (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)+20)); 

                    g.setFont(new Font("Courier", Font.BOLD, 15));
                    fm = getFontMetrics( g.getFont() );
                    width = fm.stringWidth("PLEASE ENTER YOUR NAME");
                    g.drawString("PLEASE ENTER YOUR NAME", (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-20));                   
                    
                    
                    
                    g.drawRect(SCREEN_WIDTH/2-100, (SCREEN_HEIGHT/2), 200, 25);
                    
                    //gameStarted = false;
                    //createStartPanel(); 
                }
                else {
            		//Draw player
                    g.setColor(settings.COLORS[settings.getColor()]);
            		g.drawPolygon(contr.player.getPolygon());
            		//If player is ininvcible, draw a circle around it
            		if(contr.player.getInincibility()) {
            		    g.setColor(Color.blue);
            		    g.drawOval((int)contr.player.getPosition().getX()-50, (int)contr.player.getPosition().getY()-50, 100, 100);
            		}
                }
                
                g.setColor(Color.white);
                //Draw current level
                g.setFont(new Font("Arial", Font.BOLD, 15));
                fm = getFontMetrics( g.getFont() );
                width = fm.stringWidth("LEVEL " + contr.getLevel());
                g.drawString("LEVEL " + contr.getLevel(), (SCREEN_WIDTH/2)-width/2 , 30);
                
  	
        		for(Asteroid a : contr.asteroids) {
        			g.drawPolygon(a.getPolygon());
        			//g.drawRect((int)a.getPolygon().getBounds2D().getX(),(int)a.getPolygon().getBounds2D().getY(),(int)a.getPolygon().getBounds2D().getWidth(),(int)a.getPolygon().getBounds2D().getHeight());
        		}       		
        		
        		
        		
        		
        		//Draw saucers
        		g.setColor(Color.red);  
        		for(Saucer a : contr.saucers)
                    g.drawPolygon(a.getPolygon());
        		
        		//Draw player projectiles
                g.setColor(Color.green);  
                for(Projectile a : contr.playerProjectiles) {
                    g.fillOval((int)a.getPos().getX(), (int)a.getPos().getY(), 5, 5);
                    //g.drawLine((int)a.getPos().getX(), (int)a.getPos().getY() ,(int)( a.getPos().getX() * Math.cos(a.getDirection())),(int) (a.getPos().getY()  * Math.sin(a.getDirection())));
                }
                
              //Draw saucer projectiles
                g.setColor(Color.red);  
                for(Projectile a : contr.saucerProjectiles)
                    g.fillOval((int)a.getPos().getX(), (int)a.getPos().getY(), 5, 5);
                
        		
        		//Draw particles
        		g.setColor(Color.white); 
        		for (int i = 0; i < contr.particles.size(); i++) {
        		    Particle a = contr.particles.get(i);
        		    //g.setColor(new Color((int)(Math.random()*255), (int)(Math.random()*255) ,(int)(Math.random()*255))); 
                    g.fillOval((int)(a.getPos().getX()+Math.random()), (int)(a.getPos().getY()+Math.random()), a.getSize(), a.getSize());
                }
        		
        		//Draw HP left
        		g.setColor(Color.red);
        		for (int i = 0; i < contr.getHP(); i++) {
                    //g.fillOval(50+50*i, 25, 25, 25);
        		    g.fillPolygon(new Heart(25+25*i, 25, 1));
                }
        		
        		//Draw score
        		g.setColor(Color.white);
        		g.setFont(new Font("Arial", Font.BOLD, 15));
        		g.drawString("SCORE: " + contr.getScore() , SCREEN_WIDTH-150, 25);
        		
        		
        	}
        	
        	
        };
        frame.add(gamePanel);
        //frame.add(startOverlay);
        frame.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        gamePanel.repaint();
        
    }
    
    
    private class menuListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {            
            int key = e.getKeyCode();
            
            //Option menu
            if (optionScreen) {
                switch (key) {
                case KeyEvent.VK_UP:
                    if(menuSelector != 0)
                        menuSelector--;
                    else
                        menuSelector = optionItems.length-1;
                    break;
                case KeyEvent.VK_DOWN:
                    if(menuSelector < optionItems.length-1)
                        menuSelector++;
                    else
                        menuSelector = 0;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (menuSelector == 0 && settings.getDifficulty() < 2) {
                        settings.setDifficulty(settings.getDifficulty() + 1);
                    }
                    if (menuSelector == 1 && settings.getColor() < 2) {
                        settings.setColor(settings.getColor() + 1);
                    }                    
                    if (menuSelector == 2) {
                        settings.setSound(true);
                    }
                    
                    if (menuSelector == 3) {
                        settings.setMusic(true);
                    }
                    break;
                
                case KeyEvent.VK_LEFT:
                    if (menuSelector == 0 && settings.getDifficulty() > 0) {
                        settings.setDifficulty(settings.getDifficulty() - 1);
                    }
                    if (menuSelector == 1 && settings.getColor() > 0) {
                        settings.setColor(settings.getColor() - 1);
                    }
                    
                    if (menuSelector == 2) {
                        settings.setSound(false);
                    }
                    
                    if (menuSelector == 3) {
                    	sound.stopMusic();
                        settings.setMusic(false);
                    }
                    
                    break;
                    
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    if(menuSelector == optionItems.length-1) {
                        optionScreen = false;
                        menuSelector = 0;
                    }
                    
                break;
                }
            }
            
          //hiscore menu
            else if (hiscoreScreen) {
                switch (key) {                
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    	hiscoreScreen = false;                        
                break;
                }
            }
            
            //Main menu
            else {
            switch(key){
                case KeyEvent.VK_UP:
                    if(menuSelector != 0)
                        menuSelector--;
                    else
                        menuSelector = menuItems.length-1;
                    break;
                case KeyEvent.VK_DOWN:
                    if(menuSelector < menuItems.length-1)
                        menuSelector++;
                    else
                        menuSelector = 0;
                    break;
                    
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    if (menuSelector == 0) {
                    	contr.initiateGame(1);
                    	//Remove all panels and create a game panel
                    	frame.getContentPane().removeAll();
                        createGamePanel();
                        gameStarted = true;                        
                    }
                    
                    else if(menuSelector == 1) {
                        optionScreen = true;
                        menuSelector = 0;
                    }
                    
                    else if(menuSelector == 2) {
                        highScores = ScoreHandler.getHiscores();
                        hiscoreScreen = true;
                        menuSelector = 0;
                    }
                    
                    else if(menuSelector == 4) {
                        System.exit(0);
                    }
                    
                break;
            }
        }
        }
    }
    
    private class KeyListener extends KeyAdapter {
        
        public void keyPressed(KeyEvent e) {            
            int key = e.getKeyCode();
            switch(key){
                case KeyEvent.VK_LEFT: contr.playerAction(PlayerMovement.LEFT);
                break;
                case KeyEvent.VK_RIGHT: contr.playerAction(PlayerMovement.RIGHT);
                break;
                case KeyEvent.VK_UP: contr.playerAction(PlayerMovement.UP);
                break;
                case KeyEvent.VK_SPACE: contr.playerAction(PlayerMovement.SHOOT);
                break;
                case KeyEvent.VK_F12: contr.destroyAsteroids();
                break; 
                case KeyEvent.VK_ESCAPE:
                    createStartPanel();
                    gameStarted=false;
                    break;
            }            
            //At the game over screen
            if(contr.getGameOver()) {
            	//If a letter is typed
	            if (hiscoreName.length() < 15) {
	            	if (e.getKeyChar() >= 'A' && e.getKeyChar() <= 'z')
	            		hiscoreName += e.getKeyChar();
	            }
	            
	            //Erase a character if backspace is pressed
	            if (hiscoreName.length() > 0) {
	            	if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
	            		hiscoreName = hiscoreName.substring(0,hiscoreName.length()-1);
	            	}
	            }
	            
	            //Submit hiscore
	            if (e.getKeyCode() == KeyEvent.VK_ENTER && !hiscoreName.equals("")) {
	            	try {
        				ScoreHandler.addScore(new HighScore(hiscoreName , contr.getScore()));
        			} catch (IOException ex) {
        				ex.printStackTrace();
        			}                		
            		createStartPanel();
            		gameStarted=false;
            		highScores = ScoreHandler.getHiscores();
                    hiscoreScreen = true;
                    menuSelector = 0;
	            }
            }
        }
        
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch(key){
                case KeyEvent.VK_LEFT: contr.playerAction(PlayerMovement.NO_TURN);
                break;
                case KeyEvent.VK_RIGHT: contr.playerAction(PlayerMovement.NO_TURN);
                break;
                case KeyEvent.VK_UP: contr.playerAction(PlayerMovement.NO_UP);
                break;
                case KeyEvent.VK_SPACE: contr.playerAction(PlayerMovement.NO_SHOOT);
                break;
            }
        }
    }

    @Override
	public void update(Observable o, Object obj) {
        if(gameStarted)
            gamePanel.repaint();
        else
            startPanel.repaint();
	}
}
