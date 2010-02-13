package pl.radical.open.gg.packet;

/**
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface GGUser {

	/** Every user that is added to our contact list */
	int GG_USER_BUDDY = 0x01;

	/** User to whom we are seen in friends only mode */
	int GG_USER_FRIEND = 0x02;

	/** Ordinary user that is added to contact list */
	int GG_USER_NORMAL = 0x03;

	/** The user from whom we do not want to receive messages */
	int GG_USER_BLOCKED = 0x04;

	int GG_USER_UNKNOWN = -0x01;

}
