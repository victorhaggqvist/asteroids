package com.ecsoft.asteroids.controller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Name: Asteroids
 * Description: Loop
 *
 * @author: Victor Häggqvist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.controller
 */
public class Loop {
    private Timer timer;

    public Loop() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
                draw();
            }
        },100);
    }

    private void draw() {

    }

    public void update(){

    }
}
