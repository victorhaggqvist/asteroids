import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.awt.Polygon;
import java.awt.geom.Point2D;

import org.junit.Test;

import com.ecsoft.asteroids.mathematics.Collision;
import com.ecsoft.asteroids.mathematics.Matrix;
import com.ecsoft.asteroids.mathematics.Trigonometry;

public class FootestTest {

	@Test
	public void testConverter() {
		
		float matrix[][] = {{6,7}, {3,4}};
		Point2D.Float array[] = {new Point2D.Float((float)6,(float)7),new Point2D.Float((float)3,(float)4)};
		
		assertArrayEquals("The matrix should look like this:{{6,7}, {3,4}}", matrix, Matrix.convertPoint2DArrayTo2DMatrix(array));
		assertArrayEquals("The array should look like this:{Point(6,7), Point(3,4)}", array, Matrix.convert2DMatrixToPoint2DArray(matrix));
	}
	
	@Test
	public void testSum() {
		Point2D.Float array[] = {new Point2D.Float((float)6,(float)7),new Point2D.Float((float)3,(float)4)};
		Point2D.Float point = new Point2D.Float(9, 11);
		assertEquals(point, Matrix.Point2DSum(array));
	}
	
	@Test
	public void testCollision() {
    	int [] xpointsp = {0, 3, 3};
    	int [] ypointsp = {0, 3, -3};
    	Polygon player = new Polygon(xpointsp, ypointsp, 3);
    	
    	int [] xpointsa = {-1, 0, 0};
    	int [] ypointsa = {0, 0, 1};
    	Polygon astroid = new Polygon(xpointsa, ypointsa, 3);
    	
    	for(int i = 0; i < 5; i++) {
    		assertEquals("Collision:", true, Collision.collide(player, astroid));
    		astroid.translate(1, 0);
    	}
    	
    	assertEquals("Collision:", false, Collision.collide(player, astroid));
    	
		
	}
	
	
	@Test
	public void testTrigonometry() {
		assertEquals("Angle should be " + 0 + ":", 0,  Math.toDegrees(Trigonometry.angle( new Point2D.Double(1, 0), new Point2D.Double(1, 0))), 0.1);
		assertEquals("Angle should be " + 135 + ":", 135, Math.toDegrees(Trigonometry.angle(new Point2D.Double(1, 0), new Point2D.Double(0, 1))), 0.1);
		assertEquals("Angle should be " + 180 + ":", 180,  Math.toDegrees(Trigonometry.angle(new Point2D.Double(1, 0), new Point2D.Double(-1, 0))), 0.1);
		assertEquals("Angle should be " + 225 + ":", 225,  Math.toDegrees(Trigonometry.angle(new Point2D.Double(1, 0), new Point2D.Double(0, -1))), 0.1);
		
		assertEquals("Angle should be " + 0 + ":", 0,  Math.toDegrees(Trigonometry.angle( new Point2D.Double(1, 0), new Point2D.Double(1, 0))), 0.1);
		assertEquals("Angle should be " + -45 + ":", -45, Math.toDegrees(Trigonometry.angle(new Point2D.Double(0, 1), new Point2D.Double(1, 0))), 0.1);
		assertEquals("Angle should be " + 0 + ":", 0,  Math.toDegrees(Trigonometry.angle(new Point2D.Double(-1, 0), new Point2D.Double(1, 0))), 0.1);
		assertEquals("Angle should be " + 45 + ":", 45,  Math.toDegrees(Trigonometry.angle(new Point2D.Double(0, -1), new Point2D.Double(1, 0))), 0.1);
	}

}
