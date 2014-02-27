package com.ecsoft.asteroids.mathematics;

import java.awt.Polygon;
import java.awt.geom.Line2D;

import com.ecsoft.asteroids.model.Projectile;

/**
 * @author Albin Karlquist
 * This class contains static methods handling collisions
 */

public class Collision {
    
    /**
     * @author Albin Karlquist
     * Checks if two polygons intersect
     * @param p1 First polygon
     * @param p2 Second polygon
     * @return Returns true if they intersect, false if they don't
     */
    public static boolean collide(Polygon p1, Polygon p2) {
        
        Line2D.Float p1lines [] = new Line2D.Float[p1.xpoints.length];
        Line2D.Float p2lines [] = new Line2D.Float[p2.xpoints.length];
        
        for (int i = 0; i < p1.xpoints.length; i++) {
            p1lines[i] = new Line2D.Float(p1.xpoints[i],p1.ypoints[i],p1.xpoints[(i+1)%p1.xpoints.length],p1.ypoints[(i+1)%p1.xpoints.length]);
        }
        
        for (int i = 0; i < p2.xpoints.length; i++) {
            p2lines[i] = new Line2D.Float(p2.xpoints[i],p2.ypoints[i],p2.xpoints[(i+1)%p2.xpoints.length],p2.ypoints[(i+1)%p2.xpoints.length]);
        }
        
        for (int i = 0; i < p1lines.length; i++) {
            for (int j = 0; j < p2lines.length; j++) {
                if(p1lines[i].intersectsLine(p2lines[j]))
                    return true;
            }
        }
        if(!p1.contains(p2.xpoints[0], p2.ypoints[0]))
        	return false;
        else
        	return true;        
    }
    
    /**
     * @author Albin Karlquist
     * Accurately checks for collisions between a projectile and a polygon.
     * @param proj 
     * @param pol
     * @return True if colliding
     */
    public static boolean projectileCollision(Projectile proj, Polygon pol) {
        Line2D.Float line = new Line2D.Float(proj.getPos().x, proj.getPos().y, proj.getPos().x + proj.getPos().x*(float)Math.cos(proj.getDirection())*500, proj.getPos().y + proj.getPos().y*(float)Math.sin(proj.getDirection())*500);

        Line2D.Float p1lines [] = new Line2D.Float[pol.xpoints.length];
        
        for (int i = 0; i < pol.xpoints.length; i++) {
            p1lines[i] = new Line2D.Float(pol.xpoints[i],pol.ypoints[i], pol.xpoints[(i+1)%pol.xpoints.length],pol.ypoints[(i+1)%pol.ypoints.length]);
        }
        
        for (int i = 0; i < p1lines.length; i++) {
            if (p1lines[i].intersectsLine(line))
                return true;
        }
        return false;
    }

}
