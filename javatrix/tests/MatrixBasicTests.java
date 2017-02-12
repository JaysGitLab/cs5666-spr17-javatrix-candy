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
    @Test
    public void testGetCol()
    {
        double[][] a = new double[4][5];
        Matrix matrix = new Matrix(a);

        try
        {
            int i = matrix.getColumnDimension();
            assertEquals(5, i);
        }
        catch (Exception all)
        {
            assertTrue(all.getMessage(), false);
        }
    }
    @Test
    public void test3paramSet()
    {
        double [][] a = new double[4][5];
        Matrix matrix = new Matrix(a);

        try
        {
            matrix.set(2,3,2.0);
            double l = matrix.get(2,3);
            assertEquals(2.0, l);
        }
        catch ( Exception all)
        {
            assertTrue(all.getMessage(), false);
        }
    }

}
