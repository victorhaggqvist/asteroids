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
    public static final String KEY_COLOR = "color";

    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;
    
    public static final String [] DIFFICULTIES = {"EASY", "MEDIUM", "HARD"};
    
    public static final int COLOR_WHITE = 0;
    public static final int COLOR_YELLOW = 1;
    public static final int COLOR_PINK = 2;
    
    public static final String [] COLOR_NAMES = {"WHITE", "YELLOW", "PINK"};
    public static final Color [] COLORS = {Color.WHITE, Color.YELLOW, Color.PINK};

    private static final String FILE_NAME = "settings.ini";
     
    private int difficulty;
    private int color;
    
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

    
    public int getColor() {
        return preferences.getInt(KEY_COLOR ,COLOR_WHITE);
    }



    public void setColor(int color) {
        preferences.putInt(KEY_COLOR, color);
    }
}
