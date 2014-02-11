package com.ecsoft.asteroids.model;

import javax.swing.*;
import java.awt.*;

/**
 * Name: Asteroids
 * Description: startOverlay
 *
 * @author: Victor Häggqvist
 * @since: 2/11/14
 * Package: com.ecsoft.asteroids.model
 */
public class startOverlay extends JPanel {
    public startOverlay() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        JLabel levelLable = new JLabel("Lavel");
        panel.add(levelLable);
        JComboBox<String> level = new JComboBox<String>(new String[]{"Easy","Normal","Hardcore"});
        panel.add(level);

        add(panel,BorderLayout.CENTER);
    }
}
