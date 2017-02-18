package javatrix.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Matt Stone
 * @version 2-9-17
 */
@RunWith(Suite.class)
@SuiteClasses({ MatrixBasicTests.class, TestConstructor.class })
public class AllTests
{
    /**
     * A method so checkstyle doesn't think this is a blank class.
     */
    public static void print()
    {
        System.out.println("This is to make checkstyle"
                + " happy about having 1 line.");
    }
}
