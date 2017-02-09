package javatrix.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import javatrix.Matrix;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Matt Stone
 * @version 2-9-17
 * 
 */
public class TestConstructor
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
     * Basic test of the double array param constructor.
     */
    @Test
    public void testConstructorDoubleArray1()
    {
        double[][] a = new double[5][6];
        a[3][4] = 66.0;
        a[1][2] = 54;
        Matrix matrix = new Matrix(a);

        try
        {
            // use reflection to test private field
            Field matrixField = Matrix.class.getDeclaredField("matrix");
            matrixField.setAccessible(true);

            // test if copy was shallow
            assertTrue(
                    "The private matrix field is the same as passed argument",
                    matrixField.get(matrix) != a);

            // test that elements are the same
            double[][] privMatrix = (double[][]) matrixField.get(matrix);
            int rowLength = privMatrix[0].length;
            for (int i = 0; i < a.length; ++i)
            {
                assertTrue("non-square matrix",
                        rowLength == privMatrix[i].length);
                for (int j = 0; j < a[i].length; ++j)
                {
                    assertTrue("incorrect element at:" + i + " " + j,
                            a[i][j] == privMatrix[i][j]);
                }
            }
        }
        // having to catch these instead of throwing them because checkstyle
        // doesn't work well with @test annotation
        catch (Exception all)
        {
            // automatically fail test and give the exception message
            assertTrue(all.getMessage(), false);
        }
    }

    /**
     * Tests the constant constructor.
     */
    @Test
    public void testConstructorConstant()
    {
        int m = 5;
        int n = 6;
        double s = 100;
        double[][] a = makeDoubleArrayFrom(m, n, s);

        Matrix matrix = new Matrix(m, n, s);

        try
        {
            // use reflection to test private field
            Field matrixField = Matrix.class.getDeclaredField("matrix");
            matrixField.setAccessible(true);

            // test if copy was shallow
            assertTrue(
                    "The private matrix field is the same as passed argument",
                    matrixField.get(matrix) != a);

            // test that elements are the same
            double[][] privMatrix = (double[][]) matrixField.get(matrix);
            int rowLength = privMatrix[0].length;
            for (int i = 0; i < a.length; ++i)
            {
                assertTrue("non-square matrix",
                        rowLength == privMatrix[i].length);
                for (int j = 0; j < a[i].length; ++j)
                {
                    assertTrue("incorrect element at:" + i + " " + j,
                            a[i][j] == privMatrix[i][j]);
                }
            }
        }
        // having to catch these instead of throwing them because checkstyle
        // doesn't work well with @test annotation
        catch (Exception all)
        {
            // automatically fail test and give the exception message
            assertTrue(all.getMessage(), false);
        }
    }

    /**
     * Creates a 2d double array.
     * 
     * @param m number of rows
     * @param n number of columns
     * @param s the number to fill
     * @return a 2d double array filled with the number s;
     */
    private double[][] makeDoubleArrayFrom(int m, int n, double s)
    {
        double[][] ret = new double[m][n];
        for (int i = 0; i < m; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                ret[i][j] = s;
            }
        }
        return ret;
    }

    /**
     * Creates a 1d double array.
     * 
     * @param n number of elements
     * @param s the number to fill
     * @return a 1d double array filled with the number s;
     */
    private double[] make1DDoubleArrayFrom(int n, double s)
    {
        double[] ret = new double[n];

        for (int j = 0; j < n; ++j)
        {
            ret[j] = s;
        }

        return ret;
    }

    /**
     * Tests The zeroed constructor.
     */
    @Test
    public void testConstructorZeros()
    {
        int m = 5;
        int n = 6;
        double[][] a = makeDoubleArrayFrom(m, n, 0);

        Matrix matrix = new Matrix(m, n);

        try
        {
            // use reflection to test private field
            Field matrixField = Matrix.class.getDeclaredField("matrix");
            matrixField.setAccessible(true);

            // test if copy was shallow
            assertTrue(
                    "The private matrix field is the same as passed argument",
                    matrixField.get(matrix) != a);

            // test that elements are the same
            double[][] privMatrix = (double[][]) matrixField.get(matrix);
            int rowLength = privMatrix[0].length;
            for (int i = 0; i < a.length; ++i)
            {
                assertTrue("non-square matrix",
                        rowLength == privMatrix[i].length);
                for (int j = 0; j < a[i].length; ++j)
                {
                    assertTrue("incorrect element at:" + i + " " + j,
                            a[i][j] == privMatrix[i][j]);
                }
            }
        }
        // having to catch these instead of throwing them because checkstyle
        // doesn't work well with @test annotation
        catch (Exception all)
        {
            // automatically fail test and give the exception message
            assertTrue(all.getMessage(), false);
        }
    }

    /**
     * Tests The quick constructor.
     */
    @Test
    public void testConstructorQuickWithoutChecks()
    {
        int m = 5;
        int n = 6;
        double[][] a = makeDoubleArrayFrom(m, n, 0);

        Matrix matrix = new Matrix(a, m, n);

        try
        {
            // use reflection to test private field
            Field matrixField = Matrix.class.getDeclaredField("matrix");
            matrixField.setAccessible(true);

            // test if copy was shallow
            assertTrue(
                    "The private matrix field is the same as passed argument",
                    matrixField.get(matrix) != a);

            // test that elements are the same
            double[][] privMatrix = (double[][]) matrixField.get(matrix);
            int rowLength = privMatrix[0].length;
            for (int i = 0; i < m; ++i)
            {
                assertTrue("non-square matrix",
                        rowLength == privMatrix[i].length);
                for (int j = 0; j < n; ++j)
                {
                    assertTrue("incorrect element at:" + i + " " + j,
                            a[i][j] == privMatrix[i][j]);
                }
            }
        }
        // having to catch these instead of throwing them because checkstyle
        // doesn't work well with @test annotation
        catch (Exception all)
        {
            // automatically fail test and give the exception message
            assertTrue(all.getMessage(), false);
        }
    }

    /**
     * Tests The clone constructor.
     */
    @Test
    public void testOneDimConstructor()
    {
        int m = 5;
        int n = 6 * m;
        int s = 101;
        double[] a = make1DDoubleArrayFrom(n, s);

        Matrix matrix = new Matrix(a, m);

        try
        {
            // use reflection to test private field
            Field matrixField = Matrix.class.getDeclaredField("matrix");
            matrixField.setAccessible(true);

            // test if copy was shallow
            assertTrue(
                    "The private matrix field is the same as passed argument",
                    matrixField.get(matrix) != a);

            // test that elements are the same
            double[][] privMatrix = (double[][]) matrixField.get(matrix);
            int iter = 0;
            int rowLength = privMatrix[0].length;
            for (int i = 0; i < m; ++i)
            {
                assertTrue("non-square matrix",
                        rowLength == privMatrix[i].length);
                for (int j = 0; j < privMatrix[0].length; ++j)
                {
                    assertTrue("incorrect element at:" + i + " " + j,
                            a[iter] == privMatrix[i][j]);
                    iter++;
                }
            }
        }
        // having to catch these instead of throwing them because checkstyle
        // doesn't work well with @test annotation
        catch (Exception all)
        {
            // automatically fail test and give the exception message
            assertTrue(all.toString(), false);
        }
    }
}
