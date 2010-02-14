package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGNullPointerException;
import pl.radical.open.gg.Session;
import pl.radical.open.gg.Session.SessionAccessor;
import pl.radical.open.gg.packet.GGHeader;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class PacketContext {

	private Session m_session = null;
	private GGHeader m_header = null;
	private byte[] m_packageContent = null;

	public PacketContext(final Session session, final GGHeader header, final byte[] packageContent) {
		if (session == null) {
			throw new GGNullPointerException("session cannot be null");
		}
		if (header == null) {
			throw new GGNullPointerException("header cannot be null");
		}
		if (packageContent == null) {
			throw new GGNullPointerException("packageContent cannot be null");
		}
		m_session = session;
		m_header = header;
		m_packageContent = packageContent;
	}

	public SessionAccessor getSessionAccessor() {
		return m_session.getSessionAccessor();
	}

	/**
	 * Returns the Gadu-Gadu packet header.
	 * 
	 * @return Gadu-Gadu packet header.
	 */
	public GGHeader getHeader() {
		return m_header;
	}

	/**
	 * Returns the content of the Gadu-Gadu packet.
	 * 
	 * @return Gadu-Gadu packet content
	 */
	public byte[] getPackageContent() {
		return m_packageContent;
	}

}
