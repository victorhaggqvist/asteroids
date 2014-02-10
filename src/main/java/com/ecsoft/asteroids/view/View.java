package com.ecsoft.asteroids.view;

import com.ecsoft.asteroids.controller.Controller;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
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
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.setSize( 1000, 500 );

        JFrame frame = new JFrame( "Asteroids" );
        frame.getContentPane().add( glcanvas);

        // shutdown the program on windows close event
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });

        frame.setSize( frame.getContentPane().getPreferredSize() );
        frame.setVisible( true );
    }
}
