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
    private static SettingsManager instance;
    private Preferences preferences;

    // settings keys
    public static final String KEY_DIFFICULTY = "difficulty";

    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_MEDIUM = 2;
    public static final int DIFFICULTY_HARD = 3;

    private static final String FILE_NAME = "settings.ini";
     
    private int difficulty;
    private String color;
    
    private SettingsManager() {
          preferences = Preferences.userNodeForPackage(com.ecsoft.asteroids.model.SettingsManager.class);
    }

    public static SettingsManager getInstance(){
        if (instance == null){
            instance = new SettingsManager();
        }
        return instance;
    }

    /**
     * Get difficulty
     * @return
     */
    public int getDifficulty() {
        return preferences.getInt(KEY_DIFFICULTY,DIFFICULTY_MEDIUM);
    }

    /**
     * Set difficulty
     * @param difficulty
     */
    public void setDifficulty(int difficulty) {
        preferences.putInt(KEY_DIFFICULTY,difficulty);
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
}
