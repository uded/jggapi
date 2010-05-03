package pl.radical.open.gg;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public final class MessageClass {

	private String messageClass = null;

	private MessageClass(final String messageClass) {
		this.messageClass = messageClass;
	}

	/** the message has been queued because the user it not available */
	public final static MessageClass QUEUED = new MessageClass("message_class_queued");

	/** The message will popup ip new window */
	public final static MessageClass MESSAGE = new MessageClass("message_class_message");

	/** the message is a part of conversation */
	public final static MessageClass CHAT = new MessageClass("message_class_chat");

	/** this means that we do not want to receive confirmation from Gadu-Gadu server of delivery of this message */
	public final static MessageClass DO_NOT_CONFIRM = new MessageClass("message_class_do_not_confirm");

	/** this means that message is ping only and the user will not see anyhow whether or not we are sending this message */
	public final static MessageClass PING = new MessageClass("message_class_ping");

	public final static MessageClass UNKNOWN = new MessageClass("message_class_unknown");

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return messageClass;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return messageClass.hashCode();
	}

}
