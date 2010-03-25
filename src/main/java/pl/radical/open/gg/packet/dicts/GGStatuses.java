package pl.radical.open.gg.packet.dicts;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
// TODO Dodać opisy słowno-muzyczne wersji
// TODO Change that to enum - interface should not define constants
public interface GGStatuses {

	/**
	 * Status not available
	 */
	int GG_STATUS_NOT_AVAIL = 0x00000001;

	/**
	 * Status not available with description
	 */
	int GG_STATUS_NOT_AVAIL_DESCR = 0x00000015;

	/**
	 * Available to talk
	 */
	int GG_STATUS_FFC = 0x00000017;

	/**
	 * Available to talk with description
	 */
	int GG_STATUS_FFC_DESCR = 0x00000018;

	/**
	 * Status available
	 */
	int GG_STATUS_AVAIL = 0x00002;

	/**
	 * Status available with description
	 */
	int GG_STATUS_AVAIL_DESCR = 0x00000004;

	/**
	 * Status busy
	 */
	int GG_STATUS_BUSY = 0x00000003;

	/**
	 * Status busy with description
	 */
	int GG_STATUS_BUSY_DESCR = 0x00000005;

	/**
	 * Do not disturb
	 */
	int GG_STATUS_DND = 0x00000021;

	/**
	 * Do not disturb with description
	 */
	int GG_STATUS_DND_DESCR = 0x00000022;

	/**
	 * Status invisible
	 */
	int GG_STATUS_INVISIBLE = 0x00000014;

	/**
	 * Status invisible with description
	 */
	int GG_STATUS_INVISIBLE_DESCR = 0x00000016;

	/**
	 * Bitmask for status blocked
	 */
	int GG_STATUS_BLOCKED = 0x00000006;

	/**
	 * Bitmask for image status
	 */
	int GG_STATUS_IMAGE_MASK = 0x00000100;

	/**
	 * Bitmask that suggest there is a description
	 */
	int GG_STATUS_DESCR_MASK = 0x00004000;

	/**
	 * Bitmask for status for friends only
	 */
	int GG_STATUS_FRIENDS_MASK = 0x00008000; // 1|000|000|000|000|000 = 32768 = (2^16)/2

	int GG_STATUS_UNKNOWN = -0x00000001; // 1|000|000|000|000|000 = 32768 = (2^16)/2

}
