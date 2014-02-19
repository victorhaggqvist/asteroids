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

import com.ecsoft.asteroids.controller.*;
import com.ecsoft.asteroids.model.*;

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
	private String [] optionItems = {"DIFFICULTY", "SHIP COLOR" ,"BACK"};
	private String [] hiscoreItems = {"BACK"};
	
	private boolean gameStarted;
	
	private int menuSelector = 0;	
	private boolean optionScreen;	
	private boolean hiscoreScreen;
	public static ArrayList<HighScore> highScores = new ArrayList<HighScore>();
	
	
    public View(Controller contr) {
        settings = SettingsManager.getInstance();
        frame = new JFrame( "Asteroids" );
        frame.addKeyListener(new KeyListener());
        frame.addKeyListener(new menuListener());
        gameStarted = false;
        optionScreen = false;
        hiscoreScreen = false;
    	this.contr = contr;
    	contr.addObserver(this);
    	createStartPanel();
    	//createGamePanel();
    	
    }
    
    private void createStartPanel() {
    	
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
                
                //Draw projectiles
                g.setColor(Color.green);  
                for(Projectile a : contr.projectiles)
                    g.fillOval((int)a.getPos().getX(), (int)a.getPos().getY(), 5, 5);
                
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
                	width = fm.stringWidth(optionItems[0]);
                    g.drawString(optionItems[0] + " = " + settings.getDifficulty(), (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*0);
                    
                    width = fm.stringWidth(optionItems[1]);
                    g.drawString(optionItems[1] + " = " + settings.getColorString(), (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*1);
                    
                    width = fm.stringWidth(optionItems[2]);
                    g.drawString(optionItems[2], (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*2);
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
	                    xPoints[0] = (SCREEN_WIDTH/2)-fm.stringWidth(optionItems[menuSelector])/2 - 20;
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

              	fps = 1000/(((System.currentTimeMillis()-time)==0)?(1L):(System.currentTimeMillis()-time));
		        time = System.currentTimeMillis();
		        
			    			    
				           
			    //Draw FPS
			    g.setFont(new Font("Arial", Font.PLAIN, 10));
                g.drawString("FPS: " + fps, SCREEN_WIDTH/2, SCREEN_HEIGHT-50);
			    
                //Draws game over text if nescessary
			    g.setFont(new Font("Arial", Font.BOLD, 20));
                FontMetrics fm = getFontMetrics( g.getFont() );
                g.setColor(Color.WHITE); 
                int width = 0; 			    
                
                if (contr.getGameOver()) {
                	width = fm.stringWidth("GAME OVER");
                    g.drawString("GAME OVER", (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-20));    
                    frame.repaint();
                    try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                    gameStarted = false;
                    createStartPanel();        
                    
                                        
                }
                
                //Draw current level
                g.setFont(new Font("Arial", Font.BOLD, 15));
                fm = getFontMetrics( g.getFont() );
                width = fm.stringWidth("LEVEL " + contr.getLevel());
                g.drawString("LEVEL " + contr.getLevel(), (SCREEN_WIDTH/2)-width/2 , 30);
                
  	
        		for(Asteroid a : contr.asteroids) {
        			g.drawPolygon(a.getPolygon());
        			//g.drawRect((int)a.getPolygon().getBounds2D().getX(),(int)a.getPolygon().getBounds2D().getY(),(int)a.getPolygon().getBounds2D().getWidth(),(int)a.getPolygon().getBounds2D().getHeight());
        		}       		
        		
        		
        		//Draw player
        		g.drawPolygon(contr.player.getPolygon());
        		//If player is ininvcible, draw a fabulous circle around it
        		if(contr.player.getInincibility()) {
        		    g.setColor(Color.blue);
        		    g.drawOval((int)contr.player.getPosition().getX()-50, (int)contr.player.getPosition().getY()-50, 100, 100);
        		}
        		
        		//Draw saucers
        		g.setColor(Color.red);  
        		for(Saucer a : contr.saucers)
                    g.drawPolygon(a.getPolygon());
        		
        		//Draw projectiles
        		g.setColor(Color.green);  
        		for(Projectile a : contr.projectiles)
                    g.fillOval((int)a.getPos().getX(), (int)a.getPos().getY(), 5, 5);
        		
        		//Draw particles
        		g.setColor(Color.white); 
        		for (int i = 0; i < contr.particles.size(); i++) {
        		    Particle a = contr.particles.get(i);
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
                case KeyEvent.VK_ENTER:
                    if (menuSelector == 0) {
                        createGamePanel();
                        gameStarted = true;
                    }
                    else if(menuSelector == 1) {
                        optionScreen = true;
                        menuSelector = 0;
                    }
                    else if(menuSelector == optionItems.length-1) {
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
                    	hiscoreScreen = false;
                        createGamePanel();
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
                case KeyEvent.VK_ESCAPE:
                    //createStartPanel();
                    createStartPanel();
                    gameStarted=false;
                    break;
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
