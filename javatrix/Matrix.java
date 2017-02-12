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
    private int rows;
    private int cols;

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
        int rowLength = a[0].length;

        // Check to make sure all column lengths match the number of rows
        for (int i = 0; i < a.length; ++i)
        {
            if (a[i].length != rowLength)
            {
                throw new IllegalArgumentException();
            }
        }

        // clone using the built in array clone.
        matrix = a.clone();
    }

    /**
     * Construct an m-by-n constant matrix.
     * 
     * @param m Number of rows.
     * @param n Number of colums.
     * @param s Fill the matrix with this scalar value.
     */
    public Matrix(int m, int n, double s)
    {
        matrix = new double[m][n];
        for (int i = 0; i < m; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                matrix[i][j] = s;
            }
        }
        rows = m;
        cols = n;
    }

    /**
     * Construct an m-by-n matrix of zeros. Parameters:
     * 
     * @param m Number of rows.
     * @param n Number of columns.
     */
    public Matrix(int m, int n)
    {
        rows = m;
        cols = n;
        matrix = new double[m][n];
    }

    /**
     * Construct a matrix quickly without checking arguments.
     * 
     * @param a - Two-dimensional array of doubles.
     * @param m - Number of rows.
     * @param n - Number of columns
     */
    public Matrix(double[][] a, int m, int n)
    {
        rows = m;
        cols = n;
        matrix = new double[m][n];
        for (int i = 0; i < m; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                matrix[i][j] = a[i][j];
            }
        }
    }

    /**
     * Construct a matrix from a one-dimensional packed array.
     * 
     * @param vals One-dimensional array of doubles, packed by columns (ala
     *            Fortran).
     * @param m the number of rows
     * 
     * @throws java.lang.IllegalArgumentException - Array length must be a
     *             multiple of m.
     */
    public Matrix(double[] vals, int m) throws IllegalArgumentException
    {
        if (vals.length % m != 0)
        {
            throw new IllegalArgumentException();
        }
        int iter = 0;
        rows = m;
        cols = vals.length / m;
        matrix = new double[m][cols];
        for (int i = 0; i < matrix.length; ++i)
        {
            for (int j = 0; j < matrix[0].length; ++j)
            {
                matrix[i][j] = vals[iter];
                iter++;
            }
        }
    }
    
    /**
     * Left division for a matrix denotes a sequence in which x = A\b is
     * a solution to A*x = b. It will divide every entry in the matrix it 
     * is called on one at a time by all the entires in the parameter 
     * matrix. *Note* the matricies to be used must have the same dimensions
     *
     * @param B some dimensional matrix.
     *
     * @return C a matrix that has is the result of single elements from A
     *              being divided by single elements from B
     *
     * @throws java.lang.IllegalArgumentException - The argument must be a matrix
     *
     */
    public Matrix arrayLeftDivide(Matrix b)
    {
        Matrix C = new Matrix(B.rows, B.cols);
        for (int i = 0; i < B.rows; i++)
        {
            for (int j = 0; j < B.cols; j++)
            {
                C[i][j] = (double)matrix[i][j] / (double)B[i][j];
            }
        }
        return C;
    }

    /**
     * Same as the left division above but instead of instantiating a new matrix
     * this will be done in place on the A matrix.
     *
     * @param B some dimensional matrix.
     *
     *
     * @return matrix the resulting matrix of the inplace division
     *
     */
    public Matrix arrayLeftDivideEquals(Matrix B)
    {
        for (int i = 0; i < B.rows; i++)
        {
            for (int j = 0; j < B.cols; j++)
            {
                matrix[i][j] = (double)matrix[i][j] / (double)B[i][j];
            }
        }
        return matrix;
    }

}
