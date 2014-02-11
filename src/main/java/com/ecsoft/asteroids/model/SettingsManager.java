package com.ecsoft.asteroids.model;

import java.util.prefs.Preferences;

/**
 * Name: Asteroids
 * Description: SettingsManager
 *
 * @author: Victor Häggqvist
 * @since: 2/11/14
 * Package: com.ecsoft.asteroids.model
 */
public class SettingsManager {
    private SettingsManager settingsManager;
    private Preferences preferences = Preferences.userNodeForPackage(com.ecsoft.asteroids.model.SettingsManager.class);;

    private SettingsManager() {

    }

    public SettingsManager getSettingsManager(){
        return new SettingsManager();
    }


}
