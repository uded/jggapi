/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg;

/**
 * Class that represents status of the user that we are monitoring.
 * <p>
 * Created on 2004-12-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IRemoteStatus.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public interface IRemoteStatus extends IStatus {

	/**
	 * Flag indicating the Gadu-Gadu user blocked us.
	 * 
	 * @return boolean indicating that user blocked us.
	 */
	boolean isBlocked();

	/**
	 * Gets remoteIP of the user. It might return -1 if there is no information about remote ip of the user.
	 * 
	 * @return the IP address of the user or -1 if there is no information.
	 */
	byte[] getRemoteIP();

	/**
	 * Gets remotePort of the user. It might return -1 if there is not information about remote port of the user.
	 * 
	 * @return the remote port of the user or -1 if there is no information.
	 */
	int getRemotePort();

	/**
	 * Gets Gadu-Gadu version of the client that the user uses. It might return -1 if there is not information about
	 * client's version.
	 * 
	 * @return the Gadu-Gadu client version or -1 if there is no information.
	 */
	int getGGVersion();

	/**
	 * Gets max image size that the user accepts. It might return -1 if the user does not accept images at all.
	 * 
	 * @return max image size that user accepts.
	 */
	int getImageSize();

	/**
	 * Flag indicating whether or not user supports voice communication.
	 * 
	 * @return whether or not the user supports voice communication.
	 */
	boolean supportsVoiceCommunication();

	/**
	 * Flag indicating whether the user supports direct communication.
	 * 
	 * @return whether or not the user support direct communication.
	 */
	boolean supportsDirectCommunication();

	/**
	 * Flag indicating whether or not we are in the remote user's buddy list.
	 * 
	 * @return whether or not we are in remote user's buddy list.
	 */
	boolean areWeInRemoteUserBuddyList();

	/**
	 * Flag indicating that remote user is behing firewall.
	 * 
	 * @return whether or not the remote user is behind firewall.
	 */
	boolean isUserBehindFirewall();

	/**
	 * The size of the user's descrption. it might return -1 if there is no information about the description or
	 * description is not set.
	 * 
	 * @return the size of the user's description or -1 if there is no information.
	 */
	int getDescriptionSize();

}
