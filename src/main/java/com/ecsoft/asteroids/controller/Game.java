package com.ecsoft.asteroids.controller;

/**
 * Name: Asteroids
 * Description: Game
 *
 * @author: Victor Häggqvist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.controller
 */
public class Game {
    private GameLoop gameLoop;

    public Game() {
        gameLoop = new GameLoop(50);
    }

    public void start() {

    }
}