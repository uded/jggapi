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
public class MessageClass {

	private String m_messageClass = null;
	
	private MessageClass(String messageClass) {
		m_messageClass = messageClass;
	}
	
	public final static MessageClass QUEUED = new MessageClass("message_class_queued");
	public final static MessageClass IN_NEW_WINDOW = new MessageClass("message_class_in_new_window");
	public final static MessageClass CHAT  = new MessageClass("message_class_chat");
	public final static MessageClass DO_NOT_CONFIRM = new MessageClass("message_class_do_not_confirm");
	public final static MessageClass PING = new MessageClass("message_class_ping");
	
	
    public String toString() {
        return m_messageClass;
    }

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
	
}
