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
package pl.mn.communicator.packet.handlers;

import java.io.IOException;

import pl.mn.communicator.GGException;
import pl.mn.communicator.GGSessionException;
import pl.mn.communicator.IPublicDirectoryService;
import pl.mn.communicator.PublicDirQuery;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.packet.out.GGPubdirRequest;

/**
 * Created on 2004-12-14
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultPublicDirectoryService.java,v 1.1 2004-12-14 22:52:04 winnetou25 Exp $
 */
public class DefaultPublicDirectoryService implements IPublicDirectoryService {

	private Session m_session;
	
	public DefaultPublicDirectoryService(Session session) {
		m_session = session;
	}
	
	/**
	 * @see pl.mn.communicator.IPublicDirectoryService#search(pl.mn.communicator.PublicDirQuery)
	 */
	public void search(PublicDirQuery publicDirQuery) throws GGException {
		if (publicDirQuery == null) throw new NullPointerException("publicDirQuery cannot be null");
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(m_session.getSessionState());
		}
		try {
			GGPubdirRequest pubdirRequest = GGPubdirRequest.createSearchPubdirRequest(publicDirQuery);
			m_session.getSessionAccessor().sendPackage(pubdirRequest);
		} catch (IOException ex) {
			throw new GGException("Unable to perform search.", ex);
		}
	}

}
