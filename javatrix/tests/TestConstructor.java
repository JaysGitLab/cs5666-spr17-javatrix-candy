package javatrix.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import javatrix.Matrix;

import org.junit.Test;

/**
 * @author Matt Stone
 * @version 2-9-17
 * 
 */
public class TestConstructor
{

    /**
     * Basic test of the double array param constructor.
     */
    @Test
    public void testConstructorDoubleArray1()
    {
        double[][] a = new double[5][5];
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
            for (int i = 0; i < a.length; ++i)
            {
                assertTrue("non-square matrix",
                        privMatrix.length == privMatrix[i].length);
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
}
