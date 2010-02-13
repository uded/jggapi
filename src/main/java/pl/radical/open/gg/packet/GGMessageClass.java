package pl.radical.open.gg.packet;

/**
 * Constants specific for Gadu-Gadu messages.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface GGMessageClass {

	int GG_CLASS_QUEUED = 0x0001;

	int GG_CLASS_MSG = 0x0004;

	int GG_CLASS_CHAT = 0x0008;

	int GG_CLASS_CTCP = 0x0010;

	int GG_CLASS_ACK = 0x0020;

	int GG_CLASS_QUEUED2 = 0x0009;

	int GG_CLASS_UNKNOWN = -0x0001;

}
