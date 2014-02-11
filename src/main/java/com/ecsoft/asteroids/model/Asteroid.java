package com.ecsoft.asteroids.model;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import com.ecsoft.asteroids.mathematics.*;

/**
 * Name: Asteroids
 * Description: Asteroid
 *
 * @author: Albin Karlquist
 * @since: 2/11/14
 * Package: com.ecsoft.asteroids.controller
 */
public class Asteroid {
    
    private static int scale = 20;

    private Point2D position;
    private Point2D velocity;
    private int size;    
    private Point2D.Float polygon[] = new Point2D.Float[8];
    private float rotateSpeed;
    
    private static final int screenWidth = 1000;
    private static final int screenHeight = 600;



    /**
     * Creates a new asteroid at a random location with a random velocity
     * @param x Width of the game screen
     * @param y Height of the game screen
     */
    public Asteroid(int x, int y) {
        this.size = 4;
        this.position = new Point2D.Float((float)(Math.random()*x),(float)(Math.random()*y));
        this.velocity = new Point2D.Float((float)((Math.random()*2)-1),(float)((Math.random()*2)-1));
        this.rotateSpeed = (float)(Math.random()/10)-0.05f;
        
        randomPolygon();
    }

    /**
     * Creates a new asteroid at the specified location and size
     * @param x X-position of the asteroid
     * @param y Y-position of the asteroid
     * @param size Size of the asteroid
     */
    public Asteroid(int x, int y, int size) {
        this.size = size;
        this.position = new Point2D.Float(x,y);
        this.velocity = new Point2D.Float((float)(Math.random()),(float)(Math.random()));
        randomPolygon();
    }

    /**
     * Creates a randomized polygon
     */
    private void randomPolygon() {        
        
        Point2D.Float polygon [][] = new Point2D.Float[3][3];
        
        int sectorSize = (this.size*20)/3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                polygon[i][j] = new Point2D.Float((float)(Math.random()*sectorSize+(j*sectorSize)), (float)(Math.random()*sectorSize + (i*sectorSize)));
                
            }
        }

        //Reformats the polygon to a single dimensional array and removes the middle point
        for (int i = 0; i < 3; i++) {
           this.polygon[i] = polygon[0][i]; 
        }
        
        this.polygon[3] = polygon[1][0];
        this.polygon[4] = polygon[1][2];
        for (int i = 0; i < 3; i++) {
            this.polygon[i+5] = polygon[2][i];
        }
        
        Point2D.Float sum = Matrix.Point2DSum(this.polygon);
        Point2D.Float center = new Point2D.Float((float)sum.getX()/this.polygon.length,(float)sum.getY()/this.polygon.length);
        for (int i = 0; i < this.polygon.length; i++) {
            this.polygon[i].x -= center.x;
            this.polygon[i].y -= center.y;
        }
        
        
    }

    /**
     * Updates the position of the asteroid
     * 
     * @editor: Peter Lundberg
     * @since: 2/11/14
     */
    public void updatePos () {        
        position.setLocation(position.getX() + velocity.getX(), position.getY() + velocity.getY());
        float transformationMatrix[][] = {{(float)Math.cos(rotateSpeed), (float)-Math.sin(rotateSpeed)}, {(float)Math.sin(rotateSpeed), (float)Math.cos(rotateSpeed)}};
        polygon = Matrix.convert2DMatrixToPoint2DArray(Matrix.Transform(transformationMatrix, Matrix.convertPoint2DArrayTo2DMatrix(polygon)));
        
        //If the asteroid moves out of bounds
        if(position.getX()<0-size*scale/2) {
            position.setLocation(screenWidth+size*scale/2, position.getY());
        }
        
        else if(position.getX()>screenWidth+size*scale/2) {
            position.setLocation(0-size*scale/2, position.getY());
        }
        
        else if(position.getY() < 0-size*scale/2) {
            position.setLocation(position.getX() , screenHeight+size*scale/2);
        }
        
        else if(position.getY() > screenHeight+size*scale/2) {
            position.setLocation(position.getX() , 0-size*scale/2);
        }
        
    }
    
    /**
     * 
     * @return Returns a drawable polygon
     */
    public Polygon getPolygon() {
        
        int [] x = new int[8];
        int [] y = new int[8];        
        //p.bounds = new Rectangle((int)position.getX(), (int)position.getY(), size*scale, size*scale);        
        
        for (int i = 0; i < 3; i++) {
            x[i] = (int)(position.getX() + polygon[i].getX());
            y[i] =  (int)(position.getY() + polygon[i].getY());
        }
        x[3] = (int)(position.getX() + polygon[4].getX());
        y[3] = (int)(position.getY() + polygon[4].getY());
        x[4] = (int)(position.getX() + polygon[7].getX());
        y[4] = (int)(position.getY() + polygon[7].getY());
        x[5] = (int)(position.getX() + polygon[6].getX());
        y[5] = (int)(position.getY() + polygon[6].getY());
        x[6] = (int)(position.getX() + polygon[5].getX());
        y[6] = (int)(position.getY() + polygon[5].getY());
        x[7] = (int)(position.getX() + polygon[3].getX());
        y[7] = (int)(position.getY() + polygon[3].getY());
        
        Polygon p = new Polygon(x,y,8);
        
        
        return p;
    }





}
