package pl.radical.open.gg;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import de.huxhorn.lilith.slf4j.Logger;
import de.huxhorn.lilith.slf4j.LoggerFactory;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ConnectionTest.class,
	CommunicationTest.class
})
public final class AlljGGapiTest {
	private static Logger LOG = LoggerFactory.getLogger(AlljGGapiTest.class);

	public static final int TEST_UIN_1 = 20239471;
	public static final int TEST_UIN_2 = 20241237;

	protected static final LoginContext loginContext1 = new LoginContext(TEST_UIN_1, "RadicalEntropy");
	protected static final LoginContext loginContext2 = new LoginContext(TEST_UIN_2, "RadicalTest");

	protected static ISession session1;
	protected static ISession session2;

	@BeforeClass
	public static void setUpContexts() {
		LOG.info("TEST STARTING UP...");
	}

	@AfterClass
	public static void tearDown() {
		LOG.info("ALL TESTING FINISHED!");
	}
}
