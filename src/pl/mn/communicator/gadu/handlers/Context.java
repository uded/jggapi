/*
 * Created on 2004-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator.gadu.handlers;

import pl.mn.communicator.gadu.GGHeader;
import pl.mn.communicator.gadu.handlers.Session.SessionAccessor;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Context {

	private Session m_session;
	private GGHeader m_header;
	private byte[] m_packageContent;
	
	public Context(Session session, GGHeader header, byte[] packageContent) {
		if (packageContent == null) throw new NullPointerException("packageContent cannot be null");
		m_session = session;
		m_header = header;
		m_packageContent = packageContent;
	}

	public SessionAccessor getSessionAccessor() {
		return m_session.getSessionAccessor();
	}
	
	/**
	 * @return Returns the gg header.
	 */
	public GGHeader getHeader() {
		return m_header;
	}

	/**
	 * @return Returns the content of the package.
	 */
	public byte[] getPackageContent() {
		return m_packageContent;
	}

}
