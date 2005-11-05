/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet.handlers;

import pl.mn.communicator.Session;
import pl.mn.communicator.Session.SessionAccessor;
import pl.mn.communicator.packet.GGHeader;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: PacketContext.java,v 1.1 2005-11-05 23:34:53 winnetou25 Exp $
 */
public class PacketContext {

	private Session m_session = null;
	private GGHeader m_header = null;
	private byte[] m_packageContent = null;
	
	public PacketContext(Session session, GGHeader header, byte[] packageContent) {
		if (session == null) throw new NullPointerException("session cannot be null");
		if (header == null) throw new NullPointerException("header cannot be null");
		if (packageContent == null) throw new NullPointerException("packageContent cannot be null");
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
