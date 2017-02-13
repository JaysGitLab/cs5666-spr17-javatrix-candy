package javatrix.tests;

import static org.junit.Assert.*;

import javatrix.Matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    @Test
    public void testGetRow()
    {
        double[][] a = new double[4][5];
        Matrix matrix = new Matrix(a);

        try
        {
            int i = matrix.getRowDimension();
            assertEquals(4, i);
        }
        catch ( Exception all)
        {
            assertTrue(all.getMessage(), false);
        }
    }

    /**
     * Tests to make sure array times results in a matrix with the correct
     * fields.
     */
    @Test
    public void testArrayTimes()
    {
        // create two matrices to multiply
        Matrix one = new Matrix(4, 4, 10);
        Matrix two = new Matrix(4, 4, 2);
        String methodName = "arrayTimes";

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method arrayTimesPointer = Matrix.class.getMethod(methodName,
                    Matrix.class);
            // set the accessibility to public
            arrayTimesPointer.setAccessible(true);

            // invoke method and check results (ie: one.arrayTimes(two);)
            Matrix three = (Matrix) arrayTimesPointer.invoke(one, two);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] privMatrix = (double[][]) internalMatrixField.get(three);
            for (int i = 0; i < privMatrix.length; ++i)
            {
                for (int j = 0; j < privMatrix[i].length; ++j)
                {
                    assertTrue("invalid value in patrix: " + privMatrix[i][j]
                            + "should be" + 20.0 + ".",
                            privMatrix[i][j] == 20.0);
                }
            }
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            assertTrue("Cannot find method: " + methodName, false);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
            assertTrue("security exception ", false);
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
            assertTrue("failed to invoke the " + methodName + ".", false);
        }
        catch (Exception all)
        {
            all.printStackTrace();
            assertTrue("Exception occured - stack trace printed", false);
        }

    }

    @Test
    public void testPrint()
    {
        Matrix testMatrix = new Matrix(3, 3, 8);
        String output = "8.0, 8.0, 8.0\n8.0, 8.0, 8.0\n8.0, 8.0, 8.0\n";

        // save original in
        PrintStream originalOut = System.out;

        // create something to capture
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // actual test
        String methodName = "print";
        try
        {
            // Actual Test
            Method printFuncPtr = Matrix.class.getDeclaredMethod(methodName,
                    int.class, int.class);

            // A
            printFuncPtr.invoke(testMatrix, 3, 1);
            assertEquals(baos.toString().trim(), output.trim());
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            assertTrue("Cannot find method: " + methodName, false);
        }
        catch (Exception all)
        {
            all.printStackTrace();
            assertTrue("Exception occured - stack trace printed", false);
        }
        finally
        {
            // restore system original
            System.setOut(originalOut);
        }

    }

}
