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
    private Game game;

    public Controller() {

    }

    public void start() {
        game = new Game();
        game.start();
    }
}
