/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator;

/**
 * Created on 2004-11-30 Exception that is thrown when user tries to move from certain state to state that is not
 * allowed at that moment.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGSessionException.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public class GGSessionException extends GGException {

	/**
     * 
     */
	private static final long serialVersionUID = 4199162248270553752L;
	private final SessionState m_sessionState;

	public GGSessionException(final SessionState actualSessionState) {
		super("Incorrect session state: " + actualSessionState);
		m_sessionState = actualSessionState;
	}

	public SessionState getSessionState() {
		return m_sessionState;
	}

}
