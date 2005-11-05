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
package pl.mn.communicator.packet;

/**
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGUtils.java,v 1.1 2005-11-05 23:34:52 winnetou25 Exp $
 */
public class GGUtils {

	public static String prettyBytesToString(byte[] bytes) {
	    StringBuffer received = new StringBuffer();
	    received.append("{");
	
	    String dump = HexDump.hexDump(bytes);
	    received.append(dump);
        
//	    for (int i=0; i<bytes.length; i++) {
//	    	received.append("'" + bytes[i] + "',");
//	    }
	
	    received.append("}");
	
	    return received.toString();
	}

	public static int byteToInt(byte[] buf) {
	    return byteToInt(buf, 0);
	}

	public static int byteToInt(byte[] buf, int start) {
	    int i = 0;
	    int pos = start;
	
	    int tmp;
	    tmp = unsignedByteToInt(buf[pos++]) << 0;
	    i += tmp;
	    tmp = unsignedByteToInt(buf[pos++]) << 8;
	    i += tmp;
	    tmp = unsignedByteToInt(buf[pos++]) << 16;
	    i += tmp;
	    tmp = unsignedByteToInt(buf[pos++]) << 24;
	    i += tmp;
	
	    return i;
	}
	
	public static int byteToShort(byte[] buf, int start) {
	    int i = 0;
	    int pos = start;
	
	    int tmp;
	    tmp = unsignedByteToInt(buf[pos++]) << 0;
	    i += tmp;
	    tmp = unsignedByteToInt(buf[pos++]) << 8;
	    i += tmp;
	
	    return i;
	}
	
	public static long secondsToMillis(int seconds) {
		return seconds*1000L;
	}
	
	public static int millisToSeconds(long millis) {
		return (int) (millis/1000L);
	}

	public static int unsignedByteToInt(byte i) {
	    if (i < 0) {
	        return (i & 0x7F) + 0x80;
	    } else {
	        return i;
	    }
	}

	public static byte[] intToByte(int buf) {
	    byte[] toSend = new byte[4];
	
	    toSend[3] = (byte) ((buf >> 24) & 0xFF);
	    toSend[2] = (byte) ((buf >> 16) & 0xFF);
	    toSend[1] = (byte) ((buf >> 8) & 0xFF);
	    toSend[0] = (byte) (buf & 0xFF);
	
	    return toSend;
	}

	public static long unsignedIntToLong(int i) {
	    long plus = 0;
	    plus -= (i & 0x80000000);
	    i &= 0x7FFFFFFF;
	
	    return (long) (i + plus);
	}

	public static String byteToString(byte[] data, int startIndex) {
	    int counter = 0;
	
	    while (((counter + startIndex) < data.length) &&
	            (data[counter + startIndex] != 0)) {
	        counter++;
	    }
	
	    byte[] desc = new byte[counter];
	    System.arraycopy(data, startIndex, desc, 0, counter);
	
	    return new String(desc);
	}
	
	public static byte[] convertIntToByteArray(int i) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) ((i & 0xFF));
		bytes[1] = (byte) ((i >> 8) & (0xFF));
		bytes[2] = (byte) ((i >> 16) & (0xFF));
		bytes[3] = (byte) ((i >>24 )& (0xFF));
		
		return bytes;
	}
	
    public static int getLoginHash(char[] password, int seed) {
        long x;
        long y;
        long z;

        y = seed;

        int i;

        for (x = 0, i = 0; i < password.length; i++) {
            x = (x & 0xffffff00) | password[i];
            y ^= x;

            int k = (int) y;
            k += x;
            y = GGUtils.unsignedIntToLong(k);

            k = (int) x;
            k <<= 8;
            x = GGUtils.unsignedIntToLong(k);

            y ^= x;

            k = (int) x;
            k <<= 8;
            x = GGUtils.unsignedIntToLong(k);

            k = (int) y;
            k -= x;
            y = GGUtils.unsignedIntToLong(k);

            k = (int) x;
            k <<= 8;
            x = GGUtils.unsignedIntToLong(k);

            y ^= x;

            z = y & 0x1f;
            y = GGUtils.unsignedIntToLong((int) ((y << z)
                    | (y >> (32 - z))));
        }

        return (int) y;
    }
	
}
