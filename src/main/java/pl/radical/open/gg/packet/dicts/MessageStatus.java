package pl.radical.open.gg.packet.dicts;

import lombok.Getter;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public enum MessageStatus {
	/** Message has not been blocked. */
	BLOCKED("message_status_blocked"),

	/** Message has been successfuly delivered. */
	DELIVERED("message_status_delivered"),

	/** Message has been queued for later delivery. */
	QUEUED("message_status_queued"),

	/** Message has not been delivered because remote queue is full (max. 20 messages). */
	BLOCKED_MBOX_FULL("message_status_mbox_full"),

	/** Message has not been delivered. This status is only in case of GG_CLASS_CTCP */
	NOT_DELIVERED("message_status_not_delivered"),

	UNKNOWN("message_status_unknown");

	@Getter
	private String messageStatus = null;

	private MessageStatus(final String messageStatus) {
		this.messageStatus = messageStatus;
	}

}