package pl.radical.open.gg;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ConnectionTest.class,
	CommunicationTest.class
})
public final class AlljGGapiTest {
	public static final int TEST_UIN_1 = 20239471;
	public static final int TEST_UIN_2 = 20241237;

	public static final String TEST_PASS_1 = "RadicalEntropy";
	public static final String TEST_PASS_2 = "RadicalTest";
}
