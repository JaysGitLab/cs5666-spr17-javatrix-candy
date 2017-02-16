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
    public Matrix(double[][] a) throws IllegalArgumentException
    {
        rows = a.length;
        cols = a[0].length;
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
     * @param n Number of columns.
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
        for (int i = 0; i < matrix[0].length; ++i)
        {
            for (int j = 0; j < matrix.length; ++j)
            {
                matrix[j][i] = vals[iter];
                iter++;
            }
        }
    }

    /**
     * Get a single element.
     * 
     * @param i Row index
     * 
     * @param j Column index
     * 
     * @throws java.lang.ArrayIndexOutOfBoundsException Element index must be
     *             inbounds
     * @return the value at index i j
     */
    public double get(int i, int j) throws ArrayIndexOutOfBoundsException
    {
        if (i >= rows || i < 0 || j >= cols || j < 0)
        {
            throw new ArrayIndexOutOfBoundsException();
        }

        return matrix[i][j];

    }

    /**
     * Left division for a matrix denotes a sequence in which x = A\b is a
     * solution to A*x = b. It will divide every entry in the matrix it is
     * called on one at a time by all the entires in the parameter matrix.
     * *Note* the matricies to be used must have the same dimensions
     * 
     * @param b some dimensional matrix.
     * 
     * @return C a matrix that has is the result of single elements from A being
     *         divided by single elements from B
     * 
     * @throws java.lang.IllegalArgumentException - The argument must be a
     *             matrix
     * 
     */
    public Matrix arrayLeftDivide(Matrix b) throws IllegalArgumentException
    {
        Matrix c = new Matrix(b.rows, b.cols);
        for (int i = 0; i < b.rows; i++)
        {
            for (int j = 0; j < b.cols; j++)
            {
                c.matrix[i][j] = matrix[i][j] / b.matrix[i][j];
            }
        }
        return c;
    }

    /**
     * Same as the left division above but instead of instantiating a new matrix
     * this will be done in place on the A matrix.
     * 
     * @param b some dimensional matrix.
     * 
     * 
     * @return matrix the resulting matrix of the in place division
     * 
     */
    public Matrix arrayLeftDivideEquals(Matrix b)
    {
        for (int i = 0; i < b.rows; i++)
        {
            for (int j = 0; j < b.cols; j++)
            {
                matrix[i][j] = matrix[i][j] / b.matrix[i][j];
            }
        }
        return this;
    }

    /**
     * Element-by-element multiplication, C = A.*B.
     * 
     * 
     * @param b another matrix
     * 
     * 
     * @return A*B
     * 
     * @throws java.lang.IllegalArgumentException - Matrix dimensions must be
     *             comptible
     */

    public Matrix arrayTimes(Matrix b) throws IllegalArgumentException
    {
        if (rows != b.rows || cols != b.cols)
        {
            throw new IllegalArgumentException();
        }
        Matrix c = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < cols; ++j)
            {
                c.matrix[i][j] = matrix[i][j] * b.matrix[i][j];
            }
        }
        return c;
    }

    //@formatter:off
    /**
     * sets a specific index of the matrix with a double value.
     * 
     * @param i the row index for the matrix
     * 
     * @param j the column index for the matrix
     * 
     * @param s the double value to be inserted to the matrix
     * 
     * @throws java.lang.ArrayIndexOutOfBoundsException - matrix[i][j] doesnt
     *             exist
     */
    public void set(int i, int j, double s)
        throws ArrayIndexOutOfBoundsException
    {
        if (i >= rows || j >= cols || i <= -1 || j <= -1)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.matrix[i][j] = s;
    }
    //@formatter:on

    /**
     * returns the column value for this matrix object.
     * 
     * @return col the column value of the matrix object
     */
    public int getColumnDimension()
    {
        return this.cols;
    }

    /**
     * Get row dimension.
     * 
     * @return the number of rows in the matrix
     */
    public int getRowDimension()
    {
        return this.rows;
    }

    /**
     * Print the matrix to stdout. Line the elements up in columns with a
     * Fortran-like 'Fw.d' style format.
     * 
     * @param w - Column width.
     * @param d - Number of digits after the decimal.
     */
    public void print(int w, int d)
    {
        String formatedStr = "%." + d + "f";
        for (int i = 0; i < matrix.length; ++i)
        {
            for (int j = 0; j < w; ++j)
            {
                if (j + 1 != w)
                {
                    System.out.printf(formatedStr + ", ", matrix[i][j]);
                }
                else
                {
                    System.out.printf(formatedStr + "\n", matrix[i][j]);
                }

            }
        }
    }

}
