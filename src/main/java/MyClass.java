

import java.awt.geom.Point2D;

import com.ecsoft.asteroids.mathematics.Matrix;


public class MyClass {
	
	public int multiply(int a, int b) {
		return a+b;
	}

	public static void main(String[]args) {
		
		float matrix[][] = {{6,7}, {3,4}};
		Point2D.Float array[] = {new Point2D.Float((float)6,(float)7),new Point2D.Float((float)3,(float)4)};
		
		Matrix.print(Matrix.convertPoint2DArrayTo2DMatrix(array));
		Matrix.print(matrix);
		
	}
	
}
