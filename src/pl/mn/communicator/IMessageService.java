/*
 * Created on 2004-11-27
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
public interface IMessageService {

	/** 
	 * Sends the message to Gadu-Gadu server.
	 * @param outgoingMessage
	 */
	void sendMessage(OutgoingMessage outgoingMessage) throws GGException;

	/** 
	 * Adds <code>MessageListener</code> to listen for message events.
	 * @param messageListener
	 */
	void addMessageListener(MessageListener messageListener);
	
	/**
	 * Remove <code>MessageListener</code> that listened for message events.
	 * @param messageListener
	 */
	void removeMessageListener(MessageListener messageListener);
	
}
