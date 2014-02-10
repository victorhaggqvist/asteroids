package com.ecsoft.asteroids.controller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Name: Asteroids
 * Description: GameLoop
 *
 * @author: Victor Häggqvist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.controller
 */
public class GameLoop {
    private Timer timer;
    private long timeout;

    public GameLoop(long timeout) {

        this.timeout = timeout;
    }

    /**
     * Update stuff
     */
    public void update(){

    }

    /**
     * Drawing stuff
     */
    private void draw() {

    }

    public void kill(){
        timer.cancel();
    }

    public void start(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
                draw();
            }
        },timeout);

    }
}
