package pl.radical.open.gg.packet;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface GGStatuses {

	/** Status available */
	int GG_STATUS_AVAIL = 0x00000002;

	/** Status available with description */
	int GG_STATUS_AVAIL_DESCR = 0x00000004;

	/** Status not available */
	int GG_STATUS_NOT_AVAIL = 0x00000001;

	/** Status not available with description */
	int GG_STATUS_NOT_AVAIL_DESCR = 0x00000015;

	/** Status busy */
	int GG_STATUS_BUSY = 0x00000003;

	/** Status busy with description */
	int GG_STATUS_BUSY_DESCR = 0x00000005;

	/** Status invisible */
	int GG_STATUS_INVISIBLE = 0x00000014;

	/** Status invisible with description */
	int GG_STATUS_INVISIBLE_DESCR = 0x00000016;

	/** Bitmask for status blocked */
	int GG_STATUS_BLOCKED = 0x00000006;

	/** Bitmask for status for friends only */
	int GG_STATUS_FRIENDS_MASK = 0x00008000; // 1|000|000|000|000|000 = 32768 = (2^16)/2

	int GG_STATUS_UNKNOWN = -0x00000001; // 1|000|000|000|000|000 = 32768 = (2^16)/2

}
