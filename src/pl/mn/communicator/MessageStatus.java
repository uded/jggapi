/*
 * Created on 2004-12-11
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
public class MessageStatus {

	private String m_messageStatus;
	
	private MessageStatus(String messageStatus) {
		m_messageStatus = messageStatus;
	}
	
	/** Message has not been delivered. */
	public final static MessageStatus BLOCKED = new MessageStatus("message_status_blocked");

	/** Message has been successfuly delivered. */
	public final static MessageStatus DELIVERED = new MessageStatus("message_status_delivered");

	/** Message has been queued for later delivery. */
	public final static MessageStatus QUEUED = new MessageStatus("message_status_queued");

	/** Message has not been delivered because remote queue is full (max. 20 messages). */
	public final static MessageStatus BLOCKED_MBOX_FULL = new MessageStatus("message_status_mbox_full");

	/** Message has not been delivered. This status is only in case of GG_CLASS_CTCP */
	public final static MessageStatus NOT_DELIVERED = new MessageStatus("message_status_not_delivered");

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return m_messageStatus;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return m_messageStatus.hashCode();
	}
	
}
