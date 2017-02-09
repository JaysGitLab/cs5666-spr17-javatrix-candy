package javatrix;

/**
 * A class that provides the functionality of a basic matrx.
 * 
 * @author Matt Stone
 * @author Chad Halvorsen
 * @author Mikeal Anderson
 * 
 * @version 2-7-17
 * 
 */
public class Matrix
{
    private double[][] matrix;

    /**
     * Construct a matrix from a copy of a 2-D array.
     * 
     * @param a Two-dimensional array of doubles.
     * 
     * @throws java.lang.IllegalArgumentException - All rows must have the same
     *             length
     */
    public Matrix(double[][] a)
    {
        // Check to make sure all column lengths match the number of rows
        for (int i = 0; i < a.length; ++i)
        {
            if (a[i].length != a.length)
            {
                throw new IllegalArgumentException();
            }
        }

        // clone using the built in array clone.
        matrix = a.clone();
    }
}
