/*
 * Created on 2004-12-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IStatus60 extends IStatus {

	byte[] getRemoteIP();
	int getRemotePort();
	byte getGGVersion();
	byte getImageSize();
	boolean supportsVoiceCommunication();
	boolean supportsDirectCommunication();
	boolean areWeInRemoteUserBuddyList();
	int getDescriptionSize();
	
}
