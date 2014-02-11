package com.ecsoft.asteroids.view;

import com.ecsoft.asteroids.controller.Controller;
import com.ecsoft.asteroids.model.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
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

	JPanel panel;
	Controller contr;
	
    public View(Controller contr) {
    	this.contr = contr;
    	contr.addObserver(this);
    	createWindow();
    }

    public void createWindow() {

        JFrame frame = new JFrame( "Asteroids" );
        panel = new JPanel(){
        	
			private static final long serialVersionUID = 1L;

			@Override
        	protected void paintComponent(Graphics g) {
				g.setColor(Color.black);
        		g.fillOval(50, 50, 50, 50);
        		
        		for(Asteroid a : contr.asteroid)
        			g.drawPolygon(a.getPolygon());
        	}
        	
        	
        };
        frame.add(panel);
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible( true );
        panel.repaint();
        

    }

	@Override
	public void update(Observable o, Object obj) {
		panel.repaint();
	}
}
