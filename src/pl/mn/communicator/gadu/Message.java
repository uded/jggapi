package pl.mn.communicator.gadu;

import org.apache.log4j.Logger;

import pl.mn.communicator.AbstractMessage;

/**
 * Wiadomoœæ gg.
 * 
 * @author mnaglik
 */
public final class Message extends AbstractMessage {
	private static Logger logger = Logger.getLogger(Message.class);
	public Message(int toUser, String text) {
		super(toUser,text);
	}
}
