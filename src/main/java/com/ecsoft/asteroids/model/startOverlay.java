package com.ecsoft.asteroids.model;

import javax.swing.*;
import javax.swing.border.Border;
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
        setSize(400,400);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        //panel.setBackground(Color.BLACK);

        JLabel levelLabel = new JLabel("Lavel");
        //levelLabel.setForeground(Color.WHITE);
        panel.add(levelLabel);
        JComboBox<String> level = new JComboBox<String>(new String[]{"Easy","Normal","Hardcore"});
        //level.setBackground(Color.BLACK);
        //level.setForeground(Color.WHITE);
        panel.add(level);

        add(panel,BorderLayout.CENTER);
    }
}
