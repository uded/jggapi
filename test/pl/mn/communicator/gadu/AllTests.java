package pl.mn.communicator.gadu;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * @author mnaglik
 */
public class AllTests {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for pl.mn.communicator.gadu");

        //$JUnit-BEGIN$
        suite.addTestSuite(GGConversionTest.class);

        //$JUnit-END$
        return suite;
    }
}
