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
package pl.mn.communicator;

import pl.mn.communicator.event.PublicDirListener;

/**
 * The service that allows clients to search Gadu-Gadu's public directory service.
 * <p>
 * Also through this interface it is possible to read user's
 * personal information and update it if necessary.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IPublicDirectoryService.java,v 1.11 2004-12-19 21:14:06 winnetou25 Exp $
 */
public interface IPublicDirectoryService {

	/**
	 * Looks up for Gadu-Gadu users that are matching criteria
	 * specified in <code>PublicDirSearchQuery</code> object.
	 * 
	 * @param searchQuery the query that will be used in searching process.
	 * @throws GGException if there is an error while searching.
	 * @throws GGException if we are not currently logged in.
	 * @throws NullPointerException if searchQuery is null.
	 */
	void search(PublicDirSearchQuery searchQuery) throws GGException;
	
	/**
	 * Reads personal information from the Gadu-Gadu's
	 * public directory service.
	 * <p>
	 * The invocation of this method sends only request to obtain
	 * the personal information. The client can receive the personal information
	 * only if it previously rehistered as <code>PublicDirListener</code> in this service.
	 * 
	 * @throws GGException if there is an error while sending request to read personal information from directory.
	 * @throws GGSessionException if we are currently not logged in.
	 */
	void readFromPublicDirectory() throws GGException;
	
	/**
	 * Write or update personal information to the Gadu-Gadu's
	 * public directory service.
	 * 
	 * @param personalInfo the object that holds information to be written or updated.
	 * @throws GGException if an error occurs while writing to the public directory.
	 * @throws GGSessionException if we are currently not logged in.
	 * @throws NullPointerException if personalInfo is null.
	 */
	void writeToPublicDirectory(PersonalInfo personalInfo) throws GGException;
	
	/**
	 * Adds <code>PublicDirListener</code> object to the list of listeners
	 * that will be notified of public directory related events.
	 * 
	 * @param publicDirListener the <code>PublicDirListener</code> object that will be notified.
	 */
	void addPublicDirListener(PublicDirListener publicDirListener);
	
	/**
	 * Remove <code>PublicDirListener</code> object from the list of listeners
	 * that will be notified of public directory related events.
	 * 
	 * @param publicDirListener the <code>PublicDirListener</code> object that will not longer be notified.
	 */
	void removePublicDirListener(PublicDirListener publicDirListener);
	
}
