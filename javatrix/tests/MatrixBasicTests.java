package javatrix.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
     * Test get method.
     */
    @Test
    public void getTest()
    {
        //@formatter:off
        double[][] a = {{1, 2, 3 }, {4, 5, 6 }, {7, 8, 9 } };
        //@formatter:on
        Matrix matrix = new Matrix(a);
        double x = a[1][1];
        // System.out.println(matrix.get(1, 1));
        assertEquals(matrix.get(1, 1), x, 0.02);
    }

    /**
     * Tests the getColumnDimension method.
     */
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

    /**
     * Tests the 3 paramter set method.
     */
    @Test
    public void test3paramSet()
    {
        double[][] a = new double[4][5];
        Matrix matrix = new Matrix(a);

        try
        {
            matrix.set(2, 3, 2.0);
            double l = matrix.get(2, 3);
            assertEquals(2.0, l, 0.001);
        }
        catch (Exception all)
        {
            assertTrue(all.getMessage(), false);
        }
    }

    /**
     * Test's the getRowDimension method.
     */
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
        catch (Exception all)
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

    /**
     * Tests the printing of the matrix. Not sure on the fortran stuff Dr.
     * Fenwick wanted.
     */
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

    /**
     * Tests the testArrayLeftDivide.
     */
    @Test
    public void testArrayLeftDivide()
    {
        // create two matrices to multiply
        Matrix one = new Matrix(4, 4, 10);
        Matrix two = new Matrix(4, 4, 2);
        String methodName = "arrayLeftDivide";

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method arrayLeftDividePointer = Matrix.class.getMethod(methodName,
                    Matrix.class);
            // set the accessibility to public
            arrayLeftDividePointer.setAccessible(true);

            // invoke method and check results (ie: one.arrayLeftDivide(two);)
            Matrix three = (Matrix) arrayLeftDividePointer.invoke(one, two);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] threePvArr = (double[][]) internalMatrixField.get(three);
            double[][] onePvArr = (double[][]) internalMatrixField.get(one);

            for (int i = 0; i < threePvArr.length; ++i)
            {
                for (int j = 0; j < threePvArr[i].length; ++j)
                {
                    assertEquals("invalid value in patrix: " + threePvArr[i][j]
                            + "should be" + 5.0 + ".", threePvArr[i][j], 5.0,
                            0.001);
                    // Make sure original array wasn't corrupted
                    assertEquals("array should contain 10.0, it "
                            + "contains something other", onePvArr[i][j], 10.0,
                            0.001);
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

    /**
     * Tests the testArrayLeftDivide.
     */
    @Test
    public void testArrayLeftDivideEquals()
    {
        // create two matrices to multiply
        Matrix one = new Matrix(4, 4, 10);
        Matrix two = new Matrix(4, 4, 2);
        String methodName = "arrayLeftDivideEquals";

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method arrayLeftDivideEqualsPointer = Matrix.class.getMethod(
                    methodName, Matrix.class);
            // set the accessibility to public
            arrayLeftDivideEqualsPointer.setAccessible(true);

            // invoke method and check results
            // (ie: one.arrayLeftDivideEquals(two);)
            Matrix three = (Matrix) arrayLeftDivideEqualsPointer.invoke(one,
                    two);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] threePvArr = (double[][]) internalMatrixField.get(three);
            double[][] onePvArr = (double[][]) internalMatrixField.get(one);

            // assertTrue("internal array is same as returned array",
            // threePvArr.hashCode() != onePvArr.hashCode());

            for (int i = 0; i < threePvArr.length; ++i)
            {
                for (int j = 0; j < threePvArr[i].length; ++j)
                {
                    assertEquals("invalid value in patrix: " + threePvArr[i][j]
                            + "should be" + 5.0 + ".", threePvArr[i][j], 5.0,
                            0.001);
                    // Make sure original array wasn't corrupted
                    assertEquals("array should contain 5.0, it "
                            + "contains something other", onePvArr[i][j], 5.0,
                            0.001);
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

    /**
     * Test the random method.
     */
    @Test
    public void testRandom()
    {
        // create two matrices to multiply
        String methodName = "random";
        int m = 5;
        int n = 4;

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method randFuncPtr = Matrix.class.getMethod(methodName,
                    int.class, int.class);
            // set the accessibility to public (in case changed to priv later)
            randFuncPtr.setAccessible(true);

            // TODO set random see, then invoke, then double check that it
            // produces
            // same output based on seed.

            // invoke method and check results (ie: one.arrayTimes(two);)
            // null is for static methods; it's optional, but helps readability.
            Matrix three = (Matrix) randFuncPtr.invoke(null, m, n);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] privMatrix = (double[][]) internalMatrixField.get(three);

            assertTrue(privMatrix != null);

            // test to make sure the user actually initialized to something
            // other than zeros
            double sum = 0;
            for (int i = 0; i < privMatrix.length; ++i)
            {
                for (int j = 0; j < privMatrix[i].length; ++j)
                {
                    sum += privMatrix[i][j];
                }
            }
            assertTrue("The matrix was all zeros - which is a very"
                    + " low probability. Did random work?", sum != 0.0);

        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            fail("Cannot find method: " + methodName);
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
            fail("failed to invoke the " + methodName + ".");
        }
        catch (Exception all)
        {
            all.printStackTrace();
            fail("Exception occured - stack trace printed");
        }

    }

    /**
     * Test the plus method.
     */
    @Test
    public void testPlus()
    {
        // create two matrices to multiply
        Matrix one = new Matrix(4, 4, 10);
        Matrix two = new Matrix(4, 4, 2);
        String methodName = "plus";

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method plusFuncPtr = Matrix.class.getMethod(methodName,
                    Matrix.class);
            // set the accessibility to public
            plusFuncPtr.setAccessible(true);

            // invoke method and check results
            // (ie: one.arrayLeftDivideEquals(two);)
            Matrix three = (Matrix) plusFuncPtr.invoke(one, two);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] threePvArr = (double[][]) internalMatrixField.get(three);
            double[][] onePvArr = (double[][]) internalMatrixField.get(one);

            // assertTrue("internal array is same as returned array",
            // threePvArr.hashCode() != onePvArr.hashCode());
            double value = 12;
            for (int i = 0; i < threePvArr.length; ++i)
            {
                for (int j = 0; j < threePvArr[i].length; ++j)
                {
                    assertEquals("invalid value in patrix: " + threePvArr[i][j]
                            + "should be" + value + ".", threePvArr[i][j],
                            value, 0.001);
                    // Make sure original array is updated!
                    assertEquals("original matrix should contain 10s, it "
                            + "contains" + onePvArr[i][j] + ".",
                            onePvArr[i][j], 10.0, 0.001);
                }
            }

            assertTrue(
                    "The returned matrix is the same instance as the matrix "
                            + "instance that invoked function", three != one);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            fail("Cannot find method: " + methodName);
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
            fail("failed to invoke the " + methodName + ".");
        }
        catch (Exception all)
        {
            all.printStackTrace();
            fail("Exception occured - stack trace printed");
        }
    }

    /**
     * Test the plus equals method.
     */
    @Test
    public void testPlusEquals()
    {
        // create two matrices to multiply
        Matrix one = new Matrix(4, 4, 10);
        Matrix two = new Matrix(4, 4, 2);
        String methodName = "plusEquals";

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method plusEqualsFuncPtr = Matrix.class.getMethod(methodName,
                    Matrix.class);
            // set the accessibility to public
            plusEqualsFuncPtr.setAccessible(true);

            // invoke method and check results
            // (ie: one.arrayLeftDivideEquals(two);)
            Matrix three = (Matrix) plusEqualsFuncPtr.invoke(one, two);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] threePvArr = (double[][]) internalMatrixField.get(three);
            double[][] onePvArr = (double[][]) internalMatrixField.get(one);

            // assertTrue("internal array is same as returned array",
            // threePvArr.hashCode() != onePvArr.hashCode());

            for (int i = 0; i < threePvArr.length; ++i)
            {
                for (int j = 0; j < threePvArr[i].length; ++j)
                {
                    assertEquals("invalid value in patrix: " + threePvArr[i][j]
                            + "should be" + 12.0 + ".", threePvArr[i][j], 12.0,
                            0.001);
                    // Make sure original array is updated!
                    assertEquals("original matrix should contain 12s, it "
                            + "contains something other", onePvArr[i][j], 12.0,
                            0.001);
                }
            }

            assertTrue("The returned matrix isn't the matrix "
                    + "instance that invoked function", three == one);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            fail("Cannot find method: " + methodName);
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
            fail("failed to invoke the " + methodName + ".");
        }
        catch (Exception all)
        {
            all.printStackTrace();
            fail("Exception occured - stack trace printed");
        }
    }

    /**
     * Test the minus method.
     */
    @Test
    public void testMinus()
    {
        // create two matrices to multiply
        Matrix one = new Matrix(4, 4, 10);
        Matrix two = new Matrix(4, 4, 2);
        String methodName = "minus";

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method minusFuncPtr = Matrix.class.getMethod(methodName,
                    Matrix.class);
            // set the accessibility to public
            minusFuncPtr.setAccessible(true);

            // invoke method and check results
            // (ie: one.arrayLeftDivideEquals(two);)
            Matrix three = (Matrix) minusFuncPtr.invoke(one, two);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] threePvArr = (double[][]) internalMatrixField.get(three);
            double[][] onePvArr = (double[][]) internalMatrixField.get(one);

            // assertTrue("internal array is same as returned array",
            // threePvArr.hashCode() != onePvArr.hashCode());
            double value = 8;
            for (int i = 0; i < threePvArr.length; ++i)
            {
                for (int j = 0; j < threePvArr[i].length; ++j)
                {
                    assertEquals("invalid value in patrix: " + threePvArr[i][j]
                            + "should be" + value + ".", threePvArr[i][j],
                            value, 0.001);
                    // Make sure original array is updated!
                    assertEquals("original matrix should contain 10s, it "
                            + "contains" + onePvArr[i][j] + ".",
                            onePvArr[i][j], 10.0, 0.001);
                }
            }

            assertTrue(
                    "The returned matrix is the same instance as the matrix "
                            + "instance that invoked function", three != one);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            fail("Cannot find method: " + methodName);
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
            fail("failed to invoke the " + methodName + ".");
        }
        catch (Exception all)
        {
            all.printStackTrace();
            fail("Exception occured - stack trace printed");
        }
    }

    /**
     * Test the minus equals method.
     */
    @Test
    public void testMinusEquals()
    {
        // create two matrices to multiply
        Matrix one = new Matrix(4, 4, 10);
        Matrix two = new Matrix(4, 4, 2);
        String methodName = "minusEquals";

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method minusEqualsFuncPtr = Matrix.class.getMethod(methodName,
                    Matrix.class);
            // set the accessibility to public
            minusEqualsFuncPtr.setAccessible(true);

            // invoke method and check results
            // (ie: one.arrayLeftDivideEquals(two);)
            Matrix three = (Matrix) minusEqualsFuncPtr.invoke(one, two);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] threePvArr = (double[][]) internalMatrixField.get(three);
            double[][] onePvArr = (double[][]) internalMatrixField.get(one);

            // assertTrue("internal array is same as returned array",
            // threePvArr.hashCode() != onePvArr.hashCode());

            for (int i = 0; i < threePvArr.length; ++i)
            {
                for (int j = 0; j < threePvArr[i].length; ++j)
                {
                    assertEquals("invalid value in matrix: " + threePvArr[i][j]
                            + "should be" + 8.0 + ".", threePvArr[i][j], 8.0,
                            0.001);
                    // Make sure original array is updated!
                    assertEquals("original matrix should contain 8s, it "
                            + "contains something other", onePvArr[i][j], 8.0,
                            0.001);
                }
            }

            assertTrue("The returned matrix isn't the matrix "
                    + "instance that invoked function", three == one);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            fail("Cannot find method: " + methodName);
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
            fail("failed to invoke the " + methodName + ".");
        }
        catch (Exception all)
        {
            all.printStackTrace();
            fail("Exception occured - stack trace printed");
        }
    }

    /**
     * Test the arrayTimesEquals method.
     */
    @Test
    public void arrayTimesEquals()
    {
        // create two matrices to multiply
        Matrix one = new Matrix(4, 4, 10);
        Matrix two = new Matrix(4, 4, 2);
        String methodName = "arrayTimesEquals";

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

            // make sure the returned array is the original referenced array
            // values should have already been checked by this point
            assertTrue(three == one);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            fail("Cannot find method: " + methodName);
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

}
