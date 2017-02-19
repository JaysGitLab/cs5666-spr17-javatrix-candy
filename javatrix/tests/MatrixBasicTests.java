package javatrix.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertArrayEquals;

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
            Method randFuncPtr = Matrix.class.getMethod(methodName, int.class,
                    int.class);
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

    /**
     * Tests the times method where the parameter is another matrix. Dot product
     * style.
     */
    @Test
    public void testTimesParamMatrix()
    {
        String methodName = "times";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    Matrix.class);
            functionPointer.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            //@formatter:off
            double[][] first = new double[][] {{3, 1, 2 }, {-2, 0, 5 } };
            double[][] sec = new double[][] {{-1, 3 }, {0, 5 }, {2, 5 } };
            //@formatter:on

            Matrix caller = new Matrix(first);
            Matrix argument = new Matrix(sec);
            Matrix result = (Matrix) functionPointer.invoke(caller, argument);

            // Instance check
            assertTrue("the returned object is the same "
                    + "instance as calling object", caller != result);

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);
            double[][] callerField = (double[][]) internalMatrixField
                    .get(caller);
            double[][] argField = (double[][]) internalMatrixField
                    .get(argument);

            assertEquals("result cols should match "
                    + "callee rows and argument cols", 2, callerField.length);
            assertEquals("result rows should match"
                    + "callee rows and argument cols", 2, argField[0].length);

            // test values
            // https://www.youtube.com/watch?v=OAh573i_qn8#t=441.673302
            assertEquals(1.0, resultField[0][0], 0.001);
            assertEquals(24.0, resultField[0][1], 0.001);
            assertEquals(12.0, resultField[1][0], 0.001);
            assertEquals(19.0, resultField[1][1], 0.001);

            // make sure an exception is thrown
            try
            {
                Matrix incompatible = new Matrix(5, 5);

                // below should throw exception
                Matrix result2 = (Matrix) functionPointer.invoke(caller,
                        incompatible);

                fail("did not throw exception on incompatible dimensions");
                System.out.println(result2.toString());
            }
            catch (InvocationTargetException a)
            {
                // An illegal argument exception gets converted to Invocation
                // Exception
                // because the "invoker" - for lack of a better word - is the
                // thread
                // encountering the exception.

                // below line is to make checkstyle happy.
                first = null;
            }
            // check that two fields are not shallow copied
            assertTrue("caller and result have same internal matrix",
                    resultField != callerField);

        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Tests the times method where a parameter is a double scalar value.
     */
    @Test
    public void testTimesParamDouble()
    {
        String methodName = "times";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    double.class);
            functionPointer.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            Matrix caller = new Matrix(3, 4, 5.0);
            double argument = 2.0;
            Matrix result = (Matrix) functionPointer.invoke(caller, argument);

            // Instance check
            assertTrue("the returned object is the same "
                    + "instance as calling object", caller != result);

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);
            double[][] callerField = (double[][]) internalMatrixField
                    .get(caller);

            for (int i = 0; i < resultField.length; ++i)
            {
                for (int j = 0; j < resultField[0].length; ++j)
                {
                    assertEquals("results invalid", callerField[i][j]
                            * argument, resultField[i][j], 0.001);
                }
            }

            // check values
            assertTrue("caller and result have same internal matrix",
                    resultField != callerField);

        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Tests the timesEquals method that uses a double parameter.
     */
    @Test
    public void testTimesEquals()
    {
        String methodName = "timesEquals";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    double.class);
            functionPointer.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            Matrix caller = new Matrix(3, 4, 5.0);
            double argument = 2.0;
            Matrix result = (Matrix) functionPointer.invoke(caller, argument);

            // Instance check
            assertTrue("the returned object is the same "
                    + "instance as calling object", caller == result);

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);
            double[][] callerField = (double[][]) internalMatrixField
                    .get(caller);

            for (int i = 0; i < resultField.length; ++i)
            {
                for (int j = 0; j < resultField[0].length; ++j)
                {
                    assertEquals("results invalid", 10.0, resultField[i][j],
                            0.001);
                }
            }

            // check values
            assertTrue("caller and result do not have same internal matrix",
                    resultField == callerField);

        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Tests the get matrix function that provides for integers as parameters.
     */
    @Test
    public void testGetMatrix4ints()
    {
        String methodName = "getMatrix";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    int.class, int.class, int.class, int.class);
            functionPointer.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            int i0 = 3;
            int i1 = 6;
            int j0 = 3;
            int j1 = 6;
            double value = 8.0;

            double[][] src = new double[7][7];
            for (int i = i0; i <= i1; ++i)
            {
                for (int j = j0; j <= j1; ++j)
                {
                    src[i][j] = value;
                }
            }
            Matrix caller = new Matrix(src);

            Matrix result = (Matrix) functionPointer.invoke(caller, i0, i1, j0,
                    j1);

            // Instance check
            assertTrue("the returned object is the same "
                    + "instance as calling object", caller != result);

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);
            double[][] callerField = (double[][]) internalMatrixField
                    .get(caller);

            assertTrue("inccorect number of rows: " + resultField.length,
                    resultField.length == i1 - i0 + 1);

            for (int i = 0; i < resultField.length; ++i)
            {
                assertTrue("row " + i + " has incorrect column number",
                        resultField[i].length == j1 - j0 + 1);
                for (int j = 0; j < resultField[0].length; ++j)
                {
                    assertEquals("results invalid", value, resultField[i][j],
                            0.001);
                }
            }

            // check values
            assertTrue("caller and result have same internal matrix",
                    resultField != callerField);

        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Tests the getMatrix function that provides an array of as first
     * parameter, and two integers as the second parameter.
     */
    @Test
    public void testGetMatrixIntArrayRIntj0Intj1()
    {
        String methodName = "getMatrix";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    int[].class, int.class, int.class);
            functionPointer.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);
            //@formatter:off
            int[] rows = {1, 2, 4 };
            //@formatter:on

            int j0 = 3;
            int j1 = 6;
            double value = 8.0;

            double[][] src = new double[7][7];
            for (int i : rows)
            {
                for (int j = j0; j <= j1; ++j)
                {
                    src[i][j] = value;
                }
            }
            Matrix caller = new Matrix(src);

            Matrix result = (Matrix) functionPointer.invoke(caller, rows, j0,
                    j1);

            // Instance check
            assertTrue("the returned object is the same "
                    + "instance as calling object", caller != result);

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);
            double[][] callerField = (double[][]) internalMatrixField
                    .get(caller);

            assertTrue("inccorect number of rows: " + resultField.length,
                    resultField.length == rows.length);

            for (int i = 0; i < resultField.length; ++i)
            {
                assertTrue("row " + i + " has incorrect column number",
                        resultField[i].length == j1 - j0 + 1);
                for (int j = 0; j < resultField[0].length; ++j)
                {
                    assertTrue("results invalid", resultField[i][j] == value);
                }
            }

            // check values
            assertTrue("caller and result have same internal matrix",
                    resultField != callerField);

        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Tests the getMatrix() function that provides two arrays as parameters.
     */
    @Test
    public void testGetMatrixIntArrayjIntArrayc()
    {
        String methodName = "getMatrix";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    int[].class, int[].class);
            functionPointer.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            //@formatter:off
            int[] rows = {1, 2, 4 };
            int[] cols = {1, 2, 4 };
            double value = 8.0;
            //@formatter:on

            double[][] src = new double[7][7];
            for (int i : rows)
            {
                for (int j : cols)
                {
                    src[i][j] = value;
                }
            }
            Matrix caller = new Matrix(src);

            Matrix result = (Matrix) functionPointer.invoke(caller, rows, cols);

            // Instance check
            assertTrue("the returned object is the same "
                    + "instance as calling object", caller != result);

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);
            double[][] callerField = (double[][]) internalMatrixField
                    .get(caller);

            assertTrue("inccorect number of rows: " + resultField.length,
                    resultField.length == rows.length);

            for (int i = 0; i < resultField.length; ++i)
            {
                assertTrue("row " + i + " has incorrect column number",
                        resultField[i].length == cols.length);
                for (int j = 0; j < resultField[0].length; ++j)
                {
                    assertTrue("results invalid", resultField[i][j] == value);
                }
            }

            // check values
            assertTrue("caller and result have same internal matrix",
                    resultField != callerField);

        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Tests the getMatrix() function that provides two integer parameters
     * followed by an array.
     */
    @Test
    public void testGetMatrixInti0Inti1IntArrayc()
    {
        String methodName = "getMatrix";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    int.class, int.class, int[].class);
            functionPointer.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);
            //@formatter:off
            int[] cols = {1, 2, 4 };
            //@formatter:on
            int i0 = 3;
            int i1 = 6;
            double value = 8.0;

            double[][] src = new double[7][7];
            for (int i = i0; i <= i1; ++i)
            {
                for (int j : cols)
                {
                    src[i][j] = value;
                }
            }
            Matrix caller = new Matrix(src);

            Matrix result = (Matrix) functionPointer.invoke(caller, i0, i1,
                    cols);

            // Instance check
            assertTrue("the returned object is the same "
                    + "instance as calling object", caller != result);

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);
            double[][] callerField = (double[][]) internalMatrixField
                    .get(caller);

            assertTrue("inccorect number of rows: " + resultField.length,
                    resultField.length == i1 - i0 + 1);

            for (int i = 0; i < resultField.length; ++i)
            {
                assertTrue("row " + i + " has incorrect column number",
                        resultField[i].length == cols.length);
                for (int j = 0; j < resultField[0].length; ++j)
                {
                    assertTrue("results invalid", resultField[i][j] == value);
                }
            }

            // check values
            assertTrue("caller and result have same internal matrix",
                    resultField != callerField);

        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Test the setMatrix that uses 4 int parameters.
     */
    @Test
    public void testSetMatrix4Ints()
    {
        String methodName = "setMatrix";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    int.class, int.class, int.class, int.class, Matrix.class);
            functionPointer.setAccessible(true);
            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            int i0 = 3;
            int i1 = 6;
            int j0 = 3;
            int j1 = 6;
            double value = 8.0;

            double[][] src = new double[7][7];
            double[][] argMatSrc = new double[i1 - i0 + 1][j1 - j0 + 1];

            // init the array
            for (int i = 0; i < argMatSrc.length; ++i)
            {
                for (int j = 0; j < argMatSrc[i].length; ++j)
                {
                    argMatSrc[i][j] = value;
                }
            }

            for (int i = i0; i <= i1; ++i)
            {
                for (int j = j0; j <= j1; ++j)
                {
                    src[i][j] = value - 1;
                }
            }
            Matrix caller = new Matrix(src);
            Matrix argument = new Matrix(argMatSrc);

            functionPointer.invoke(caller, i0, i1, j0, j1, argument);
            Matrix result = caller;

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);

            for (int i = i0; i <= i1; ++i)
            {
                for (int j = j0; j < j1; ++j)
                {
                    assertEquals("results invalid", value, resultField[i][j],
                            0.001);
                }
            }

        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method"
                    + " or method caused exception");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Test the setMatrix that uses an int[] parameter followed by 2 int
     * parameters.
     */
    @Test
    public void testSetMatrixArray2Ints()
    {
        String methodName = "setMatrix";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    int[].class, int.class, int.class, Matrix.class);
            functionPointer.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);
            //@formatter:off
            int[] rows = {1, 2, 4 };
            //@formatter:on

            int j0 = 3;
            int j1 = 6;
            double value = 8.0;

            double[][] argMatSrc = new double[rows.length][j1 - j0 + 1];

            // init the array
            for (int i = 0; i < argMatSrc.length; ++i)
            {
                for (int j = 0; j < argMatSrc[i].length; ++j)
                {
                    argMatSrc[i][j] = value;
                }
            }

            double[][] src = new double[7][7];
            for (int i : rows)
            {
                for (int j = j0; j <= j1; ++j)
                {
                    // change the value so that it doesn't pass test.
                    // value is what will be used to determine correct
                    // positions in array were tested.
                    src[i][j] = value - 5.0;
                }
            }
            Matrix caller = new Matrix(src);
            Matrix argument = new Matrix(argMatSrc);

            functionPointer.invoke(caller, rows, j0, j1, argument);
            Matrix result = caller;

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);
            double[][] callerField = (double[][]) internalMatrixField
                    .get(caller);

            for (int i : rows)
            {
                for (int j = j0; j < j1; ++j)
                {
                    assertEquals("results invalid", value, resultField[i][j],
                            0.001);
                }
            }

            // check values
            assertTrue("caller and result should have same internal matrix",
                    resultField == callerField);

        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Test the setMatrix that uses two int parameters followed by an int array.
     */
    @Test
    public void testSetMatrix2IntsArray()
    {
        String methodName = "setMatrix";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    int.class, int.class, int[].class, Matrix.class);
            functionPointer.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);
            //@formatter:off
            int[] cols = {1, 2, 4 };
            //@formatter:on
            int i0 = 3;
            int i1 = 6;
            double value = 8.0;

            double[][] argMatSrc = new double[1 + i1 - i0][cols.length];
            // init the array
            for (int i = 0; i < argMatSrc.length; ++i)
            {
                for (int j = 0; j < argMatSrc[i].length; ++j)
                {
                    argMatSrc[i][j] = value;
                }
            }

            double[][] src = new double[7][7];
            for (int i = i0; i <= i1; ++i)
            {
                for (int j : cols)
                {
                    src[i][j] = value - 5.0;
                }
            }
            Matrix caller = new Matrix(src);
            Matrix argument = new Matrix(argMatSrc);

            functionPointer.invoke(caller, i0, i1, cols, argument);
            Matrix result = caller;

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);

            for (int i = i0; i <= i1; ++i)
            {
                for (int j : cols)
                {
                    assertEquals("results invalid", value, resultField[i][j],
                            0.001);
                }
            }
        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Test the setMatrix that has two int[] parameters.
     */
    @Test
    public void testSetMatrixArrayArray()
    {
        String methodName = "setMatrix";
        try
        {
            // Get a handle on the method
            Method functionPointer = Matrix.class.getDeclaredMethod(methodName,
                    int[].class, int[].class, Matrix.class);
            functionPointer.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            //@formatter:off
            int[] rows = {1, 2, 4 };
            int[] cols = {1, 2, 4 };
            double value = 8.0;
            //@formatter:on

            double[][] argMatSrc = new double[rows.length][cols.length];

            // init the array
            for (int i = 0; i < argMatSrc.length; ++i)
            {
                for (int j = 0; j < argMatSrc[i].length; ++j)
                {
                    argMatSrc[i][j] = value;
                }
            }

            double[][] src = new double[7][7];
            for (int i : rows)
            {
                for (int j : cols)
                {
                    src[i][j] = value - 5.0;
                }
            }
            Matrix caller = new Matrix(src);
            Matrix argument = new Matrix(argMatSrc);

            functionPointer.invoke(caller, rows, cols, argument);
            Matrix result = caller;

            // value checks
            double[][] resultField = (double[][]) internalMatrixField
                    .get(result);

            for (int i : rows)
            {
                for (int j : cols)
                {
                    assertEquals("results invalid", value, resultField[i][j],
                            0.001);
                }
            }

        }
        catch (NoSuchMethodException e)
        {
            fail("Method " + methodName + "could not be found");
        }
        catch (IllegalAccessException e)
        {
            fail("Method could not be accessed");
        }
        catch (IllegalArgumentException e)
        {
            fail("Test gave bad argument to method\n test provided");
        }
        catch (InvocationTargetException e)
        {
            fail("Method found, but could not invoke the method");
        }
        catch (NoSuchFieldException e)
        {
            fail("Could not load the matrix private array field");
        }
        catch (Exception allOthers)
        {
            allOthers.printStackTrace();
            fail("exception occured" + allOthers.toString());
        }
    }

    /**
     * Test the "get Array" function.
     */
    @Test
    public void testGetArray()
    {
        // create two matrices to multiply
        String methodName = "getArray";
        Matrix one = new Matrix(4, 4, 10);

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method funcPtr = Matrix.class.getMethod(methodName);
            // set the accessibility to public
            funcPtr.setAccessible(true);

            // invoke method and check results
            double[][] result = (double[][]) funcPtr.invoke(one);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] onePvArr = (double[][]) internalMatrixField.get(one);

            assertTrue("should return internal array, not copy",
                    onePvArr == result);

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
     * Tests the arrayCopy method.
     */
    @Test
    public void getArrayCopy()
    {
        // create two matrices to multiply
        String methodName = "getArrayCopy";
        Matrix one = new Matrix(4, 4, 10);

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method funcPtr = Matrix.class.getMethod(methodName);
            // set the accessibility to public
            funcPtr.setAccessible(true);

            // invoke method and check results
            double[][] result = (double[][]) funcPtr.invoke(one);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] onePvArr = (double[][]) internalMatrixField.get(one);

            assertTrue("internal matrix and returned should not be not same",
                    onePvArr != result);

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
     * Tests the clone method.
     */
    @Test
    public void testCopy()
    {
        // create two matrices to multiply
        String methodName = "copy";
        Matrix one = new Matrix(4, 4, 10);

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method funcPtr = Matrix.class.getMethod(methodName);
            // set the accessibility to public
            funcPtr.setAccessible(true);

            // invoke method and check results
            Matrix result = (Matrix) funcPtr.invoke(one);

            assertTrue("clone should not return caller", result != one);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] onePvArr = (double[][]) internalMatrixField.get(one);
            double[][] resultPvArr = (double[][]) internalMatrixField
                    .get(result);

            Field rowsField = Matrix.class.getDeclaredField("rows");
            rowsField.setAccessible(true);
            Field colsField = Matrix.class.getDeclaredField("cols");
            colsField.setAccessible(true);
            assertTrue("clone should have same number of rows",
                    onePvArr.length == resultPvArr.length);
            assertTrue("clone should have same number of cols",
                    onePvArr[0].length == resultPvArr[0].length);
            assertEquals("cols field should match",
                    (Integer) colsField.get(one),
                    (Integer) colsField.get(result));
            assertEquals("rows field should match",
                    (Integer) rowsField.get(one),
                    (Integer) rowsField.get(result));

            for (int i = 0; i < onePvArr.length; ++i)
            {
                for (int j = 0; j < resultPvArr.length; ++j)
                {
                    assertEquals("element mismatch on clone in intenral array",
                            onePvArr[i][j], resultPvArr[i][j], 0.001);
                }
            }

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
     * Tests the clone method.
     */
    @Test
    public void testClone()
    {
        // create two matrices to multiply
        String methodName = "clone";
        Matrix one = new Matrix(4, 4, 10);

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method funcPtr = Matrix.class.getMethod(methodName);
            // set the accessibility to public
            funcPtr.setAccessible(true);

            // invoke method and check results
            Matrix result = (Matrix) funcPtr.invoke(one);

            assertTrue("clone should not return caller", result != one);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] onePvArr = (double[][]) internalMatrixField.get(one);
            double[][] resultPvArr = (double[][]) internalMatrixField
                    .get(result);

            Field rowsField = Matrix.class.getDeclaredField("rows");
            rowsField.setAccessible(true);
            Field colsField = Matrix.class.getDeclaredField("cols");
            colsField.setAccessible(true);
            assertTrue("clone should have same number of rows",
                    onePvArr.length == resultPvArr.length);
            assertTrue("clone should have same number of cols",
                    onePvArr[0].length == resultPvArr[0].length);
            assertEquals("cols field should match",
                    (Integer) colsField.get(one),
                    (Integer) colsField.get(result));
            assertEquals("rows field should match",
                    (Integer) rowsField.get(one),
                    (Integer) rowsField.get(result));

            for (int i = 0; i < onePvArr.length; ++i)
            {
                for (int j = 0; j < resultPvArr.length; ++j)
                {
                    assertEquals("element mismatch on clone in intenral array",
                            onePvArr[i][j], resultPvArr[i][j], 0.001);
                }
            }

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
     * Tests the trace method.
     */
    @Test
    public void testTrace()
    {
        // create two matrices to multiply
        String methodName = "trace";
        Matrix one = new Matrix(4, 4, 10);
        Double sum = 50.0;

        // get method
        try
        {
            // get a reflection to method (like a function pointer)
            Method funcPtr = Matrix.class.getMethod(methodName);
            // set the accessibility to public
            funcPtr.setAccessible(true);

            // get the private matrix field to test
            Field internalMatrixField = Matrix.class.getDeclaredField("matrix");
            internalMatrixField.setAccessible(true);

            double[][] onePvArr = (double[][]) internalMatrixField.get(one);
            onePvArr[1][1] = 20.0;
            // assertTrue("internal matrix and returned should not be not same",
            // onePvArr != result);

            // invoke method and check results
            Double result = (Double) funcPtr.invoke(one);

            assertEquals(
                    "the method should have sumed the left to right diagonal",
                    sum, result);

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
     * Test norm1 function.
     */

    @Test
    public void testNorm1()
    {
        double[][] a = {{1, 2, 3},
                        {1, 2, 3},
                        {1, 2, 3}};
        Matrix mat = new Matrix(a);
        //norm1 is greatest column sum
        double norm1 = a[0][2] + a[1][2] + a[2][2];
        double result = mat.norm1();
        assertEquals("norm1 should return the greatest col sum",
                    norm1, result, 0.02);        
    }

    /**
     * Test normInf function.
     */

    @Test
    public void testNormInf()
    {
        double[][] a = {{1, 1, 1},
                        {2, 2, 2},
                        {3, 3, 3}};
        Matrix mat = new Matrix(a);
        //norm1 is greatest column sum
        double normInf = a[2][0] + a[2][1] + a[2][2];
        double result = mat.normInf();
        assertEquals("normInf should return the greatest row sum",
                    normInf, result, 0.02);
    }

    /**
     * Test getColumnPackedCopy function.
     */
    @Test
    public void testGCPC()
    {
        double[][] a = {{1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}};
        Matrix mat = new Matrix(a);
        double[] aCP = {1, 4, 7, 2, 5, 8, 3, 6, 9};
        double[] matCP = mat.getColumnPackedCopy();
        assertArrayEquals("Column Packed Array incorrect", aCP, matCP, 0.002);
    }
    
    /**
     * Test getRowPackedCopy function.
     */
    @Test
    public void testGRPC()
    {
        double[][] a = {{1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}};
        Matrix mat = new Matrix(a);
        double[] aCP = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[] matCP = mat.getRowPackedCopy();
        assertArrayEquals("Row Packed Array incorrect", aCP, matCP, 0.002);
    }
    
    /**
     * Test arrayRightDivide function.
     */
    @Test 
    public void testARD()
    {
        double[][] x = {{1, 2}, {2, 2}};
        double[][] y = {{3, 2}, {1, 1}};
        Matrix a = new Matrix(x);
        Matrix b = new Matrix(y);
        double[] correct = {x[0][0] / y[0][0], x[0][1] / y[0][1], 
                            x[1][0] / y[1][0], x[1][1] / y[1][1]};
        double[] result = a.arrayRightDivide(b).getRowPackedCopy();
        assertArrayEquals(correct, result, 0.002);
    }


}
