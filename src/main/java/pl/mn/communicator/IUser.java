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

/**
 * Interface that represents Gadu-Gadu user.
 * <p>
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: IUser.java,v 1.1 2005-11-05 23:34:52 winnetou25 Exp $
 */
public interface IUser {
	
    IUser[] EMPTY_ARRAY = new IUser[0];
    
	/**
	 * Gets uin of the Gadu-Gadu user.
	 * 
	 * @return the uin of Gadu-Gadu user.
	 */
	int getUin();
	
	/**
	 * Gets userMode of this Gadu-Gadu user.
	 * 
	 * @return the <code>User.UserMode</code> of this Gadu-Gadu user.
	 */
	User.UserMode getUserMode();
	
}
