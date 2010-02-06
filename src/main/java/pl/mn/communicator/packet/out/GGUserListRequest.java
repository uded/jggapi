/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.packet.out;

import pl.mn.communicator.LocalUser;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGUserListRequest.java,v 1.3 2005/11/20 16:09:03 winnetou25 Exp $
 */
public class GGUserListRequest implements GGOutgoingPackage {

	public static final int GG_USERLIST_REQUEST = 0x0016;

	public static final byte GG_USER_LIST_PUT = 0x00;
	public static final byte GG_USERLIST_PUT_MORE = 0x01;
	public static final int GG_USERLIST_GET = 0x02;

	private byte m_type;

	private String m_request;

	private GGUserListRequest() {
	}

	/**
	 * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_USERLIST_REQUEST;
	}

	/**
	 * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 1 + m_request.length();
	}

	/**
	 * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final byte[] toSend = new byte[getLength()];

		toSend[0] = m_type;

		final byte[] bytes = m_request.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			toSend[1 + i] = bytes[i];
		}

		return toSend;
	}

	public static GGUserListRequest createClearUserListRequest() {
		final GGUserListRequest listRequest = new GGUserListRequest();
		listRequest.m_request = "";
		listRequest.m_type = GG_USER_LIST_PUT;

		return listRequest;
	}

	public static void changeRequestType(final GGUserListRequest userListRequest, final byte newType) {
		userListRequest.m_type = newType;
	}

	public static GGUserListRequest createPutUserListRequest(final List<String> lines) {
		if (lines == null) {
			throw new NullPointerException("lines collection cannot be null");
		}
		final GGUserListRequest listRequest = new GGUserListRequest();
		listRequest.m_type = GG_USER_LIST_PUT;
		listRequest.m_request = createRequestFromList(lines);

		return listRequest;
	}

	public static GGUserListRequest createPutMoreUserListRequest(final List<String> lines) {
		if (lines == null) {
			throw new NullPointerException("lines collection cannot be null");
		}
		final GGUserListRequest listRequest = new GGUserListRequest();
		listRequest.m_type = GG_USERLIST_PUT_MORE;
		listRequest.m_request = createRequestFromList(lines);

		return listRequest;
	}

	public static GGUserListRequest createGetUserListRequest() {
		final GGUserListRequest listRequest = new GGUserListRequest();
		listRequest.m_type = GG_USERLIST_GET;
		listRequest.m_request = "";

		return listRequest;
	}

	private static String createRequestFromList(final List<String> lines) {

		final StringBuffer buffer = new StringBuffer();

		for (final String line : lines) {
			buffer.append(line);
			buffer.append("\n");
		}

		return buffer.toString();
	}

	public static String prepareRequest(final Collection<LocalUser> usersToExport) {
		final StringBuffer buffer = new StringBuffer();
		for (final Object element : usersToExport) {
			final LocalUser localUser = (LocalUser) element;
			if (localUser.isBlocked()) {
				buffer.append("i;;;;;;" + String.valueOf(localUser.getUin()));
				buffer.append("\n");
				continue;
			}
			if (localUser.getFirstName() != null) {
				buffer.append(localUser.getFirstName());
			}
			buffer.append(';');
			if (localUser.getLastName() != null) {
				buffer.append(localUser.getLastName());
			}
			buffer.append(';');
			if (localUser.getNickName() != null) {
				buffer.append(localUser.getNickName());
			}
			buffer.append(';');
			if (localUser.getDisplayName() != null) {
				buffer.append(localUser.getDisplayName());
			}
			buffer.append(';');
			if (localUser.getTelephone() != null) {
				buffer.append(localUser.getTelephone());
			}
			buffer.append(';');
			if (localUser.getGroup() != null) {
				buffer.append(localUser.getGroup());
			}
			buffer.append(';');
			if (localUser.getUin() != -1) {
				buffer.append(localUser.getUin());
			}
			buffer.append(';');
			if (localUser.getEmailAddress() != null) {
				buffer.append(localUser.getEmailAddress());
			}
			buffer.append(';');
			// buffer.append(0);
			// buffer.append(';');
			// buffer.append(';');
			// buffer.append(0);
			// buffer.append(';');
			buffer.append("\n");
		}
		return buffer.toString();
	}

}
