package com.ecsoft.asteroids.controller;

import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Sound {

	String url[] = {
			"src/audio/bom.wav",
			"src/audio/pang.wav",
			"src/audio/pew.wav",
			"src/audio/vroom.wav"
	};
	
	public void startSound(int i) {
		AudioStream as = null;
		try {
			InputStream in = new FileInputStream(url[i]);
			as = new AudioStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AudioPlayer.player.start(as);
	}
	
}
