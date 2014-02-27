package com.ecsoft.asteroids.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ecsoft.asteroids.model.SettingsManager;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Sound {

	String url[] = {
			"src/audio/bom.wav",
			"src/audio/pang.wav",
			"src/audio/pew.wav",
			"src/audio/vroom.wav",
			"src/audio/metroid.wav"
	};
	
	private static AudioStream as;
	
	public void startSound(final int i) {
	    SettingsManager settings = SettingsManager.getInstance();
	    if(settings.getSound()) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AePlayWave apw = new AePlayWave(url[i]);
                apw.start();

                try{
                    boolean alive = apw.isAlive();
                    while(alive){
                        //check periodically if the thread is alive
                        alive = apw.isAlive();
                        Thread.currentThread().sleep(500);
                    }
                }catch(InterruptedException e){
                    System.out.println("Interrupted");
                    e.printStackTrace();
                }
            }
        }).start();
    }
	}
	
	public void startMusic() {	
		SettingsManager settings = SettingsManager.getInstance();
	    if(settings.getMusic()) {	 
	    	AudioPlayer.player.stop(as);
			try {
				InputStream in = new FileInputStream(url[4]);
				as = new AudioStream(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			AudioPlayer.player.start(as);

	    }
	}
	
	public void stopMusic() {
		AudioPlayer.player.stop(as);
	}
	
}
