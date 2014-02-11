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
    // [todo] - create test for preferences
    private void SettingsManager() { }

    /**
     * Get a Preferences object
     * @return a Preferences object
     */
    public static Preferences getPreferences() {
        return Preferences.userNodeForPackage(com.ecsoft.asteroids.model.SettingsManager.class);
    }
}
