/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
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
package pl.mn.communicator.gadu.handlers;

import pl.mn.communicator.gadu.GGHeader;
import pl.mn.communicator.gadu.handlers.Session.SessionAccessor;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: Context.java,v 1.3 2004-12-13 23:44:02 winnetou25 Exp $
 */
public class Context {

	private Session m_session = null;
	private GGHeader m_header = null;
	private byte[] m_packageContent = null;
	
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
