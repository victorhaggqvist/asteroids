package com.ecsoft.asteroids.mathematics;

import java.awt.geom.Point2D;

public class Matrix {
    
    //Print matrix
    public static void print(double [][] x){
        
        for(int i = 0; i < x.length; i++){
            for(int j = 0; j < x[0].length; j++){
            
                System.out.print(x[i][j] + " ");
                
            }
            System.out.println();
        }
        
    }
    
    
    public static void print(float [][] x){
        
        for(int i = 0; i < x.length; i++){
            for(int j = 0; j < x[0].length; j++){
            
                System.out.print(x[i][j] + " ");
                
            }
            System.out.println();
        }
        
    }
    
    //
    public static int[][] ConvertdoubleToint(double [][] x){
        
        //Creating size for returning matrix
        int rows = x.length;
        int columns = x[0].length;
        
        int[][] matrix = new int[rows][columns];
        
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
            
                matrix[i][j] = (int)x[i][j];
                
            }
        }
        
        return matrix;
        
    }
    
    public static float[][] convertPoint2DArrayTo2DMatrix(Point2D.Float[] x) {
    	
    	int rows = x.length;
    	
    	float[][] matrix = new float[rows][2];
    	
    	for(int i = 0; i < rows; i++) {
    			matrix[i][0] = x[i].x;
    			matrix[i][1] = x[i].y;
    	}
    	
    	return matrix;
    }
    
    
    //M . x = b     Transformation
    public static float[][] Transform(float[][] m, float[][] x){
        
        //Creating size for returning matrix
        int rows = x.length;
        int columns = x[0].length;
        
        float[][] matrix = new float[rows][columns];
        
        try{
    
            //Transforming transposed "x" matrix with transformation matrix "m"
            //Also Transposing in the same process
            for(int i = 0; i < m.length; i++){
                for(int k = 0; k < x.length; k++){
                    
                    float t = 0;
                    
                    for(int j = 0; j < m[0].length; j++){
                        
                        //Flipt k and j to skip transposing
                        t = t + m[i][j]*x[k][j];
                    
                    }
                    
                    //Flipt k and i to skip transposing
                    matrix[k][i] = t;
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            //If matrix dimension does not match it will throw a ArrayIndexOutOfBoundsException
            throw new ArrayIndexOutOfBoundsException("Matrix dimension does not match");
            
        }
        return matrix;
        
    }
    
    
    //M . x = b     Transformation
    public static double[][] Transform(double[][] m, double[][] x){
        
        //Creating size for returning matrix
        int rows = x.length;
        int columns = x[0].length;
        
        double[][] matrix = new double[rows][columns];
        
        try{
    
            //Transforming transposed "x" matrix with transformation matrix "m"
            //Also Transposing in the same process
            for(int i = 0; i < m.length; i++){
                for(int k = 0; k < x.length; k++){
                    
                    double t = 0;
                    
                    for(int j = 0; j < m[0].length; j++){
                        
                        //Flipt k and j to skip transposing
                        t = t + m[i][j]*x[k][j];
                    
                    }
                    
                    //Flipt k and i to skip transposing
                    matrix[k][i] = t;
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            //If matrix dimension does not match it will throw a ArrayIndexOutOfBoundsException
            throw new ArrayIndexOutOfBoundsException("Matrix dimension does not match");
            
        }
        return matrix;
        
    }
    
    //M^T Transposing
    public static double[][] Transpose(double[][] x){
        
        //Creating size for returning matrix
        int rows = x.length;
        int columns = x[0].length;
        
        double[][] matrix = new double[columns][rows];
        
        //Transposing "x" matrix        
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                
                matrix[j][i] = x[i][j];
                
            }
        }
        
        return matrix;
        
    }
    
    //a*M Scalar Multiplication
    public static double[][] ScalarMultiplication(double a, double[][] x){
    
        //Creating size for returning matrix
        int rows = x.length;
        int columns = x[0].length;
        
        double[][] matrix = new double[rows][columns];
        
        // Scalar Multiplication
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
            
                matrix[i][j] = x[i][j]*a;
                
            }
        }
        
        return matrix;
        
    }
    
    //R^a -> R^(a+1) Add homogeneous coordinates to a figure
    public static double[][] AddHomogeneousCoordinates(double[][] x){
        
        //Creating size for returning matrix
        int rows = x.length;
        int columns = x[0].length;
        
        double[][] matrix = new double[rows][columns+1];
        
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
            
                matrix[i][j] = x[i][j];
                
            }
        }
        
        //Adds homogeneous coordinates
        for(int i = 0; i < rows; i++){
            
            matrix[i][columns] = 1;
            
        }
        
        return matrix;
        
    }
    
    //R^(a+1) -> R^a Remove homogeneous coordinates from a figure
    public static double[][] RemoveHomogeneousCoordinates(double[][] x){
        
        //Creating size for returning matrix
        int rows = x.length;
        int columns = x[0].length;
        
        double[][] matrix = new double[rows][columns-1];
        
        //Removes homogeneous coordinates
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns-1; j++){
            
                matrix[i][j] = x[i][j];
                
            }
        }
        
        return matrix;
        
    }
    
}
