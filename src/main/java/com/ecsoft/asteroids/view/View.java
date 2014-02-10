package com.ecsoft.asteroids.view;

import com.ecsoft.asteroids.controller.Controller;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Name: Asteroids
 * Description: View
 *
 * @author: Victor Häggqvist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.view
 */
public class View {

    public View() {

    }

    public void createWindow() {

        JFrame frame = new JFrame( "Asteroids" );
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible( true );

    }
}
