package com.ecsoft.asteroids.model;

import java.util.ArrayList;

/**
 * Name: Asteroids
 * Description: HighScoreList
 *
 * @author: Victor Häggqvist
 * @since: 2/19/14
 * Package: com.ecsoft.asteroids.model
 */
public class HighScoreList {
    private ArrayList<HighScore> list;

    public ArrayList<HighScore> getList() {
        return list;
    }

    public void setList(ArrayList<HighScore> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HighScoreList{" +
                "list=" + list +
                '}';
    }
}
