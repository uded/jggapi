package pl.mn.communicator.gadu;

import org.apache.log4j.Logger;

import pl.mn.communicator.ILocalUser;

/**
 * Wiadomosc wysylana w czasie logowania
 * 
 * @version $Revision: 1.6 $
 * @author mnaglik
 */
class GGLogin implements GGOutgoingPackage {
	private static Logger logger = Logger.getLogger(GGLogin.class);
	private int uin;
	private int hash;
	private int status = GGStatus.GG_STATUS_AVAIL;
	private int version = 0x40000019; //wersja 5.01
	private int local_ip;
	private short local_port;

	public GGLogin(
		byte[] ip,
		int port,
		ILocalUser user,
		GGWelcome welcome) {
		this.local_ip = 0;
		this.local_port = (short) port;
		this.uin = user.getUserNo();
		this.hash = gg_login_hash(user.getPassword(), welcome.getSeed());
		
		logger.debug("user: " + user.getUserNo() + " pass: " + user.getPassword());
		logger.debug("seed: " + welcome.getSeed() + " hash: " + hash);
	}

	private int gg_login_hash(String password, int seed) {
		long x, y, z;

		y = seed;
		int i;

		for (x = 0, i = 0; i < password.length(); i++) {

			// Konrad Rodziewski implementation
//			x = (x & 0xffffff00) | password.charAt(i);
//			y ^= x;
//			y += x;
//			x <<= 8;
//			y ^= x;
//			x <<= 8;
//			y -= x;
//			x <<= 8;
//			y ^= x;
//
//			z = y & 0x1f;
//			y = (y << z) | (y >>> (32 - z));
			// end

			// old implementation
						x = (x & 0xffffff00) | password.charAt(i);
						y ^= x;
			
						int k = (int) y;
						k += x;
						y = GGConversion.unsignedIntToLong(k);
			
						k = (int) x;
						k <<= 8;
						x = GGConversion.unsignedIntToLong(k);
			
						y ^= x;
			
						k = (int) x;
						k <<= 8;
						x = GGConversion.unsignedIntToLong(k);
			
						k = (int) y;
						k -= x;
						y = GGConversion.unsignedIntToLong(k);
			
						k = (int) x;
						k <<= 8;
						x = GGConversion.unsignedIntToLong(k);
			
						y ^= x;
			
						z = y & 0x1f;
						y =
							GGConversion.unsignedIntToLong(
								(int) ((y << z) | (y >> (32 - z))));
			
			// end
		}

		return (int) y;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
	 */
	public int getHeader() {
		return 0x0c;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 0x16;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		byte[] toSend = new byte[22];

		toSend[3] = (byte) (uin >> 24 & 0xFF);
		toSend[2] = (byte) (uin >> 16 & 0xFF);
		toSend[1] = (byte) (uin >> 8 & 0xFF);
		toSend[0] = (byte) (uin & 0xFF);

		toSend[7] = (byte) (hash >> 24 & 0xFF);
		toSend[6] = (byte) (hash >> 16 & 0xFF);
		toSend[5] = (byte) (hash >> 8 & 0xFF);
		toSend[4] = (byte) (hash & 0xFF);

		toSend[11] = (byte) (status >> 24 & 0xFF);
		toSend[10] = (byte) (status >> 16 & 0xFF);
		toSend[9] = (byte) (status >> 8 & 0xFF);
		toSend[8] = (byte) (status & 0xFF);

		toSend[15] = (byte) (version >> 24 & 0xFF);
		toSend[14] = (byte) (version >> 16 & 0xFF);
		toSend[13] = (byte) (version >> 8 & 0xFF);
		toSend[12] = (byte) (version & 0xFF);

		toSend[19] = (byte) 233;
		toSend[18] = (byte) 0;
		toSend[17] = (byte) 168;
		toSend[16] = (byte) 192;

		toSend[21] = (byte) (local_port >> 8 & 0xFF);
		toSend[20] = (byte) (local_port & 0xFF);

		return toSend;
	}
}