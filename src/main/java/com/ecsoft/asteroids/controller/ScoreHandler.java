package com.ecsoft.asteroids.controller;

import com.ecsoft.asteroids.model.HighScore;
import com.ecsoft.asteroids.model.HighScoreList;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public final class ScoreHandler {

	private static final String FILE_NAME = "hiscore.json";
    private static HighScoreList highScoreList;

    /**
     * Get highscorelist
     * @return
     * @throws IOException
     */
	public static ArrayList<HighScore> getHiscores(){
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(FILE_NAME));
        } catch (FileNotFoundException e) {
            try {
                PrintWriter writer = new PrintWriter(FILE_NAME, "UTF-8");
                writer.println();
                writer.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        String line = null;
        try {
            line = in.readLine();
            while (line!=null){
                sb.append(line);
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

		Gson gson = new Gson();
        highScoreList = gson.fromJson(sb.toString(),HighScoreList.class);
		return highScoreList.getList();
	}

    /**
     * Add score to list
     * @param highScore
     * @throws IOException
     */
	public static void addScore(HighScore highScore) throws IOException {
		getHiscores();

        //Update highScores
        for (int i = 0; i < highScoreList.getList().size(); i++) {
            // if new score is better than score at i insert it at i
            if (highScore.getScore()>highScoreList.getList().get(i).getScore()){
                highScoreList.getList().add(i,highScore);
                break;
            }
        }
        highScoreList.getList().add(highScore);
		
		//Writes to file
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME)));
        Gson gson = new Gson();
        pw.println(gson.toJson(highScoreList));
		pw.close();
	}
}
