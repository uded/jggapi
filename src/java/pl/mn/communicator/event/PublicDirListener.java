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
package pl.mn.communicator.event;

import java.util.EventListener;

import pl.mn.communicator.PersonalInfo;
import pl.mn.communicator.PublicDirSearchReply;

/**
 * The listener interface that is related to Gadu-Gadu's
 * public directory service.
 * <p>
 * The class that implement this interface, aka PublicDirHandler
 * is notified whether the search request was received or the 
 * Gadu-Gadu user's personal information has been successfully
 * read from the public directory or written to public directory.
 * <p> 
 * Created on 2004-12-15
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: PublicDirListener.java,v 1.12 2004-12-19 17:14:42 winnetou25 Exp $
 */
public interface PublicDirListener extends EventListener {

	/**
	 * Messaged when search results arrived from Gadu-Gadu
	 * server after sending query.
	 * 
	 * @param queryID the id of the query that has been previously created.
	 * @param publicDirSearchReply the object that represents Gadu-Gadu's server reply.
	 */
	void onPublicDirectorySearchReply(int queryID, PublicDirSearchReply publicDirSearchReply);

	/**
	 * Messaged when we have successfully retrieved our personal information
	 * from the Gadu-Gadu's public directory.
	 * 
	 * @param queryID the id of the query that has been previously created.
	 * @param personalInfo the user's that is currently logged in personal information.
	 */
	void onPublicDirectoryRead(int queryID, PersonalInfo personalInfo);

	/**
	 * Messaged when user's personal information has been
	 * successfully written to the Gadu-Gadu's public directory.
	 * 
	 * @param queryID the id of the query that has been previously created.
	 */
	void onPublicDirectoryUpdated(int queryID);

	public static class Stub implements PublicDirListener {

		/**
		 * @see pl.mn.communicator.event.PublicDirListener#onPublicDirectoryUpdated()
		 */
		public void onPublicDirectoryUpdated(int queryID) { }

		/**
		 * @see pl.mn.communicator.event.PublicDirListener#gotSearchResults(java.util.Collection)
		 */
		public void onPublicDirectorySearchReply(int queryID, PublicDirSearchReply publicDirSearchReply) { }

		/**
		 * @see pl.mn.communicator.event.PublicDirListener#onPublicDirectoryRead(pl.mn.communicator.PublicDirQuery)
		 */
		public void onPublicDirectoryRead(int queryID, PersonalInfo pubDirReply) { }
		
	}
	
}
