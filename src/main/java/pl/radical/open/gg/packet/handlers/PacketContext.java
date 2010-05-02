package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.Session;
import pl.radical.open.gg.Session.SessionAccessor;
import pl.radical.open.gg.packet.GGHeader;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class PacketContext {
	private Session session = null;
	private GGHeader header = null;
	private byte[] packageContent = null;

	public PacketContext(final Session session, final GGHeader header, final byte[] packageContent) {
		if (session == null) {
			throw new IllegalArgumentException("session cannot be null");
		}
		if (header == null) {
			throw new IllegalArgumentException("header cannot be null");
		}
		if (packageContent == null) {
			throw new IllegalArgumentException("packageContent cannot be null");
		}
		this.session = session;
		this.header = header;
		this.packageContent = packageContent;
	}

	public SessionAccessor getSessionAccessor() {
		return session.getSessionAccessor();
	}

	/**
	 * Returns the Gadu-Gadu packet header.
	 * 
	 * @return Gadu-Gadu packet header.
	 */
	public GGHeader getHeader() {
		return header;
	}

	/**
	 * Returns the content of the Gadu-Gadu packet.
	 * 
	 * @return Gadu-Gadu packet content
	 */
	public byte[] getPackageContent() {
		return packageContent;
	}

}
