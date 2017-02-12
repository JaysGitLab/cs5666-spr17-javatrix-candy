package javatrix.tests;

import static org.junit.Assert.*;

import javatrix.Matrix;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Matt Stone
 * @author Chad Halvorsen
 * @author Mikeal Anderson
 * 
 * @version 2-7-17
 * 
 */
public class MatrixBasicTests
{

    /**
     * Basic set up before any test is run.
     */
    @Before
    public void setup()
    {

    }

    /**
     * Basic teardown after a test is run.
     */
    @After
    public void teardown()
    {

    }

    /**
     * Demo example test2.
     */
    @Test
    public void exampletest2()
    {
        // System.out.println("You passed test2");
    }
    
    /**
     * Test get
     */
    @Test
    public void getTest()
    {
        double[][] a = {{1,2,3},{4,5,6},{7,8,9}};
        Matrix matrix = new Matrix(a);
        double x = a[1][1];
        System.out.println(matrix.get(1,1));
        assertEquals(matrix.get(1,1), x, 0.02);            
    }

}
