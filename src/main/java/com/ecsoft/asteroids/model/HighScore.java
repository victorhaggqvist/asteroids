package com.ecsoft.asteroids.model;

import com.google.gson.Gson;

/**
 * A high score object
 */
public class HighScore {

	private String name;
	private int score;
	
	public HighScore(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

    @Override
    public String toString() {
        return "HighScore{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    public String getAsJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
