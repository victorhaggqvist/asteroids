import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import org.junit.Test;

import com.ecsoft.asteroids.mathematics.*;

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

}
