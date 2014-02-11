package com.ecsoft.asteroids.view;

import com.ecsoft.asteroids.controller.Controller;
import com.ecsoft.asteroids.model.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Name: Asteroids
 * Description: View
 *
 * @author: Victor Häggqvist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.view
 */
public class View implements Observer{

	JPanel panel, startOverlay;
	Controller contr;
	
    public View(Controller contr) {
    	this.contr = contr;
    	contr.addObserver(this);
    	createWindow();
    }

    public void createWindow() {

        JFrame frame = new JFrame( "Asteroids" );
        frame.setJMenuBar(createMenu());

        startOverlay = new startOverlay();
        panel = new JPanel(){
        	
			private static final long serialVersionUID = 1L;

			@Override
        	protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				this.setBackground(Color.black);
				g.setColor(Color.white);	
  	
        		for(Asteroid a : contr.asteroids)
        			g.drawPolygon(a.getPolygon());
        		
        		for(Projectile a : contr.projectiles)
                    g.fillOval((int)a.getPos().getX(), (int)a.getPos().getY(), 5, 5);
        	}
        	
        	
        };
        frame.add(panel);
        frame.add(startOverlay);
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible( true );
        panel.repaint();
        

    }

    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu game = new JMenu("Game");
        JMenuItem ne = new JMenuItem("New");
        game.add(ne);
        JMenuItem settings = new JMenuItem("Settings");
        game.add(settings);
        settings.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingBox().setVisible(true);
            }
        });
        JMenuItem quit = new JMenu("Quit");
        game.add(quit);
        menuBar.add(game);

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        help.add(about);
        menuBar.add(help);

        return menuBar;
    }

    @Override
	public void update(Observable o, Object obj) {
		panel.repaint();
	}
}
