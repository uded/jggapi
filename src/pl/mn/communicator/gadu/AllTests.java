/*
 * Created on 2003-10-04
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package pl.mn.communicator.gadu;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Marcin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
