package com.ecsoft.asteroids.startup;

import com.ecsoft.asteroids.controller.Controller;
import com.ecsoft.asteroids.view.View;

/**
 * Name: Asteroids
 * Description: Startup
 *
 * @author: Victor Häggqvist
 * @since: 2/10/14
 * Package: com.ecsoft.asteroids.startup
 */
public class Startup {
    public static void main(String[] args) {
        System.out.println("Asteroids");
        View view = new View();
        Controller controller = new Controller(view);
        controller.start();
    }
}
