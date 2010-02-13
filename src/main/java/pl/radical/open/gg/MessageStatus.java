package pl.radical.open.gg;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class MessageStatus {

	private String m_messageStatus = null;

	private MessageStatus(final String messageStatus) {
		m_messageStatus = messageStatus;
	}

	/** Message has not been blocked. */
	public final static MessageStatus BLOCKED = new MessageStatus("message_status_blocked");

	/** Message has been successfuly delivered. */
	public final static MessageStatus DELIVERED = new MessageStatus("message_status_delivered");

	/** Message has been queued for later delivery. */
	public final static MessageStatus QUEUED = new MessageStatus("message_status_queued");

	/** Message has not been delivered because remote queue is full (max. 20 messages). */
	public final static MessageStatus BLOCKED_MBOX_FULL = new MessageStatus("message_status_mbox_full");

	/** Message has not been delivered. This status is only in case of GG_CLASS_CTCP */
	public final static MessageStatus NOT_DELIVERED = new MessageStatus("message_status_not_delivered");

	public final static MessageStatus UNKNOWN = new MessageStatus("message_status_unknown");

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return m_messageStatus;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return m_messageStatus.hashCode();
	}

}
