/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.gadu;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mn.communicator.packet.GGUtils;

/**
 * @author mnaglik
 */
public class GGConversionTest extends TestCase {

	private static Log logger = LogFactory.getLog(GGConversionTest.class);

    /*
     * Class to test for int byteToInt(byte[])
     */
    public void testByteToIntbyteArray() {
        byte[] bajty = new byte[] {41, -54, 26, 0};
        int out = GGUtils.byteToInt(bajty);
        logger.info("Bajty wejsciowe: " + GGUtils.bytesToString(bajty));
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
