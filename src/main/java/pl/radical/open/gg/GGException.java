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
 * Created on 2004-11-30
 * <P>
 * An exception that provides information on access to Gadu-Gadu server. It contains the exception chain.
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGException.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public class GGException extends Exception {

	/**
     * 
     */
	private static final long serialVersionUID = -2956708463640990150L;

	public GGException(final String message) {
		super(message);
	}

	public GGException(final String message, final Exception ex) {
		super(message, ex);
	}

}
