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
package pl.mn.communicator;

import pl.mn.communicator.event.PublicDirListener;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IPublicDirectoryService.java,v 1.6 2004-12-18 15:13:06 winnetou25 Exp $
 */
public interface IPublicDirectoryService {

	/**
	 * Looks up for users matching certain criteria.
	 * 
	 * @param publicDirQuery the information provided to look for users.
	 * @return the collection of users that matched the query.
	 */
	void search(PublicDirSearchQuery publicDirQuery) throws GGException;
	
	void readFromPublicDirectory() throws GGException;
	
	/**
	 * Write information to the public directory.
	 * 
	 * @param publicDirInfo the object that holds information to be written.
	 * @throws GGException if an error occurs while writing to the public directory.
	 */
	void writeToPublicDirectory(PublicDirInfo publicDirInfo) throws GGException;
	
	/**
	 * Adds <code>PublicDirListener</code> to the list of listeners.
	 * 
	 * @param publicDirListener to be added.
	 */
	void addPublicDirListener(PublicDirListener publicDirListener);
	
	/**
	 * Remove <code>PublicDirListener</code> from the list of listeners.
	 * 
	 * @param publicDirListener to be removed.
	 */
	void removePublicDirListener(PublicDirListener publicDirListener);
	
}
