package pl.mn.communicator.gadu;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import pl.mn.communicator.util.Util;


/**
 * @author mnaglik
 */
public class GGConversionTest extends TestCase {
    private static Logger logger = Logger.getLogger(GGConversionTest.class);

    /*
     * Class to test for int byteToInt(byte[])
     */
    public void testByteToIntbyteArray() {
        byte[] bajty = new byte[] { 41, -54, 26, 0 };
        int out = GGConversion.byteToInt(bajty);
        logger.info("Bajty wejsciowe: " + Util.bytesToString(bajty));
        logger.info("Wyjscie: " + out);
        assertEquals(out, 1755689);
    }

    /*
     * Class to test for int byteToInt(byte[], int)
     */
    public void testByteToIntbyteArrayint() {
    }

    public void testIntToByte() {
    }

    public void testUnsignedIntToLong() {
    }
}
