package com.ecsoft.asteroids.controller;

import com.ecsoft.asteroids.view.View;

/**
 * Name: Asteroids
 * Description: Controller
 *
 * @author: Victor Häggqvist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.controller
 */
public class Controller {
    private final View view;
    private Game game;

    public Controller(View view) {

        this.view = view;
    }

    public void start() {
        view.createWindow();
        game = new Game();
        game.start();
    }
}
