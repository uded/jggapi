/*
 * Created on 2004-12-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface GGNotifiable {

	/** Every user that is added to contact list */
    public static final int GG_USER_BUDDY = 0x01;

    /** User to whom we are seen in friends only mode */
    public static final int GG_USER_FRIEND = 0x02;

//    /** Ordinary user that is added to contact list */
//    public static final int GG_USER_NORMAL = 0x03;

    /** The user from whom we do not want to receive messages */
    public static final int GG_USER_BLOCKED = 0x04;

}
