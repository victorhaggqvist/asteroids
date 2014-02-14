package com.ecsoft.asteroids.model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Name: Asteroids
 * Description: SettingsManager
 *
 * @author: Victor Häggqvist
 * @editor: Albin Karlquist
 * @since: 2/11/14
 * Package: com.ecsoft.asteroids.model
 */
public class SettingsManager {
    
    private static final String FILE_NAME = "settings.ini";
     
    private int difficulty;
    private String color;
    
    public SettingsManager() {       
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(FILE_NAME));
            for (int i = 0; i < 2; i++) {
                String line = in.readLine();
                
                if (line.substring(0 , line.indexOf('=')).equals("difficulty")) {
                    this.difficulty = Integer.parseInt(line.substring(line.indexOf('=')+1));
                }
                
                else if (line.substring(0 , line.indexOf('=')).equals("color")) {
                    this.color = line.substring(line.indexOf('=')+1);
                    
                }                
            }
            
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
//        PrintWriter pw;
//        try {
//            pw = new PrintWriter(new BufferedWriter(new FileWriter("settings.ini")));
//            pw.write("asd\nasdasd");
//            pw.close(); 
//        } catch (IOException e) {
//            e.printStackTrace();
//        }   
    }



    public int getDifficulty() {
        return difficulty;
    }



    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }



    public String getColorString() {
        return color;
    }
    
    public Color getColor() {
        if (color.equals("WHITE")) 
            return Color.white;
        else if (color.equals("GREEN")) 
            return Color.green;
        else if (color.equals("BLUE")) 
            return Color.blue;
        return Color.white;
    }



    public void setColor(String color) {
        this.color = color;
    }



    /**
     * Get a Preferences object
     * @return a Preferences object
     */
    public static Preferences getPreferences() {
        return Preferences.userNodeForPackage(com.ecsoft.asteroids.model.SettingsManager.class);
    }
}
