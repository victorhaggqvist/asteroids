package com.ecsoft.asteroids.integration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public final class ScoreHandler {

	private static final String FILE_NAME = "hiscore.txt";
	public static ArrayList<User> users = new ArrayList<User>();

	public static ArrayList<User> getHiscores() throws IOException {
		users.clear();		
		BufferedReader in;
		in = new BufferedReader(new FileReader(FILE_NAME));

		for (int i = 0; i < 10; i++) {			
			String line = in.readLine();
			
			String name = line.substring(0 , line.indexOf(' '));
			int score = Integer.parseInt(line.substring(line.indexOf(' ')+1));		
			users.add(new User(name , score));	
		}
		
		return users;
		
		
	}
	
	public static void addScore(User u) throws IOException {
		//Update users
		getHiscores();
		for (int i = 0; i < users.size(); i++) {
			//If score is higher than user i, insert the score and remove the very last entry
			if (users.get(i).getScore() < u.getScore()) {
				users.add(i, u);
				users.remove(users.size()-1);
				break;
			}
		} 
		
		//Writes top-10 to the text file
		PrintWriter pw;
		pw = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME)));
		for (int i = 0; i < users.size(); i++) {
			pw.println(users.get(i).getName() + " " + users.get(i).getScore());
		}		
		pw.close();
		
	}

}
