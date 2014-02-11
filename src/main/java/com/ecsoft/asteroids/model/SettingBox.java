package com.ecsoft.asteroids.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;

/**
 * Name: Asteroids
 * Description: SettingBox
 *
 * @author: Victor Häggqvist
 * @since: 2/11/14
 * Package: com.ecsoft.asteroids.model
 */
public class SettingBox extends JFrame {

    public SettingBox(){
        super("Settings");
        setSize(300,300);
        setLayout(new BorderLayout());

        JPanel middle = new JPanel();
        JPanel row1 = new JPanel();
        JLabel levelLable = new JLabel("Lavel");
        row1.add(levelLable);
        JComboBox<String> level = new JComboBox<String>(new String[]{"Easy","Normal","Hardcore"});
        row1.add(level);
        add(row1);
        middle.add(row1);

        JButton save = new JButton("Save");
        add(middle, BorderLayout.CENTER);
        add(save,BorderLayout.SOUTH);

        save.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Preferences preferences = SettingsManager.getPreferences();
            }
        });
    }
}
