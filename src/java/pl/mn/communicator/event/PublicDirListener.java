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
package pl.mn.communicator.event;

import java.util.EventListener;

import pl.mn.communicator.PublicDirInfo;
import pl.mn.communicator.PublicDirSearchReply;

/**
 * Created on 2004-12-15
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: PublicDirListener.java,v 1.8 2004-12-18 15:52:15 winnetou25 Exp $
 */
public interface PublicDirListener extends EventListener {

	/**
	 * <p>
	 * Messaged when search results arrived from Gadu-Gadu
	 * server after sending query.
	 * <p>
	 * The parameter contains collection of matched results.
	 */
	void onPublicDirectorySearchReply(PublicDirSearchReply publicDirSearchReply);

	/**
	 * Messaged when we have successfuly retrieved information about our
	 * uin from the public directory.
	 * 
	 * @param pubDirReply
	 */
	void onPublicDirectoryRead(PublicDirInfo pubDirReply);

	/**
	 * Messaged when information has been successfuly written
	 * to the public directory.
	 */
	void onPublicDirectoryUpdated();

	public static class Stub implements PublicDirListener {

		/**
		 * @see pl.mn.communicator.event.PublicDirListener#onPublicDirectoryUpdated()
		 */
		public void onPublicDirectoryUpdated() { }

		/**
		 * @see pl.mn.communicator.event.PublicDirListener#gotSearchResults(java.util.Collection)
		 */
		public void onPublicDirectorySearchReply(PublicDirSearchReply publicDirSearchReply) { }

		/**
		 * @see pl.mn.communicator.event.PublicDirListener#onPublicDirectoryRead(pl.mn.communicator.PublicDirQuery)
		 */
		public void onPublicDirectoryRead(PublicDirInfo pubDirReply) { }
		
	}
	
}
