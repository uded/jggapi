package pl.radical.open.gg;

import lombok.Getter;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public enum MessageClass {
	/**
	 * the message has been queued because the user it not available
	 */
	QUEUED("message_class_queued"),

	/**
	 * The message will popup ip new window
	 */
	MESSAGE("message_class_message"),

	/**
	 * the message is a part of conversation
	 */
	CHAT("message_class_chat"),

	/**
	 * this means that we do not want to receive confirmation from Gadu-Gadu server of delivery of this message
	 */
	DO_NOT_CONFIRM("message_class_do_not_confirm"),

	/**
	 * this means that message is ping only and the user will not see anyhow whether or not we are sending this message
	 */
	PING("message_class_ping"),

	UNKNOWN("message_class_unknown");

	@Getter
	private String messageClass = null;

	private MessageClass(final String messageClass) {
		this.messageClass = messageClass;
	}

}
