package com.ecsoft.asteroids.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.ecsoft.asteroids.controller.Controller;
import com.ecsoft.asteroids.model.Asteroid;
import com.ecsoft.asteroids.model.Heart;
import com.ecsoft.asteroids.model.Particle;
import com.ecsoft.asteroids.model.Projectile;
import com.ecsoft.asteroids.model.Saucer;
import com.ecsoft.asteroids.model.SettingBox;
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
	private String [] menuItems = {"START GAME", "OPTIONS" , "ABOUT", "QUIT"};
	private String [] optionItems = {"DIFFICULTY", "SHIP COLOR" ,"BACK"};
	private int menuSelector = 0;
	private boolean optionScreen;
	
	private boolean gameStarted;
	
    public View(Controller contr) {
        settings = new SettingsManager();
        frame = new JFrame( "Asteroids" );
        frame.addKeyListener(new KeyListener());
        frame.addKeyListener(new menuListener());
        gameStarted = false;
        optionScreen = false;
    	this.contr = contr;
    	contr.addObserver(this);
    	createStartPanel();
    	//createGamePanel();
    	
    }
    
    private void createStartPanel() {
    	
    	contr.initiateGame(0);
    	
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
                
                //Draws start screen
                int width = 0;
                if (!optionScreen) { 
                    for (int i = 0; i < menuItems.length; i++) {
                        width = fm.stringWidth(menuItems[i]);
                        g.drawString(menuItems[i], (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*i);
                    }
                }
                
                //Draws option screen
                else {
                    width = fm.stringWidth(optionItems[0]);
                    g.drawString(optionItems[0] + " = " + settings.getDifficulty(), (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*0);
                    
                    width = fm.stringWidth(optionItems[1]);
                    g.drawString(optionItems[1] + " = " + settings.getColorString(), (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*1);
                    
                    width = fm.stringWidth(optionItems[2]);
                    g.drawString(optionItems[2], (SCREEN_WIDTH/2)-width/2 , ((SCREEN_HEIGHT/2)-60)+40*2);

                }
                
                
                //Draws the marker
                    int [] xPoints = new int[3];
                    int [] yPoints = new int[3];
                    
                    xPoints[0] = (SCREEN_WIDTH/2)-fm.stringWidth(menuItems[menuSelector])/2 - 20;
                    yPoints[0] = ((SCREEN_HEIGHT/2)-70)+40*menuSelector;
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
                
              	fps = 1000/(System.currentTimeMillis()-time); 		        
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
                    }
                    else if(menuSelector == optionItems.length-1) {
                        optionScreen = false;
                    }
                    
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
                    }
                    
                    else if(menuSelector == 3) {
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
                case KeyEvent.VK_LEFT: contr.rotateLeft();
                break;
                case KeyEvent.VK_RIGHT: contr.rotateRight();
                break;
                case KeyEvent.VK_UP: contr.moveForward();
                break;
                case KeyEvent.VK_SPACE: contr.startShoot();
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
                case KeyEvent.VK_LEFT: contr.stopRotate();
                break;
                case KeyEvent.VK_RIGHT: contr.stopRotate();
                break;
                case KeyEvent.VK_UP: contr.stopMove();
                break;
                case KeyEvent.VK_SPACE: contr.stopShoot();
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
