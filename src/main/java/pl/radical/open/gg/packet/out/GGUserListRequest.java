package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.LocalUser;
import pl.radical.open.gg.packet.GGOutgoingPackage;
import pl.radical.open.gg.packet.OutgoingPacket;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@OutgoingPacket(type = 0x0016, label = "GG_USERLIST_REQUEST")
public final class GGUserListRequest implements GGOutgoingPackage {

	public static final int GG_USERLIST_REQUEST = 0x0016;

	public static final byte GG_USER_LIST_PUT = 0x00;
	public static final byte GG_USERLIST_PUT_MORE = 0x01;
	public static final int GG_USERLIST_GET = 0x02;

	private byte type;

	private String request;

	private GGUserListRequest() {
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_USERLIST_REQUEST;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 1 + request.length();
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final byte[] toSend = new byte[getLength()];

		toSend[0] = type;

		final byte[] bytes = request.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			toSend[1 + i] = bytes[i];
		}

		return toSend;
	}

	public static GGUserListRequest createClearUserListRequest() {
		final GGUserListRequest listRequest = new GGUserListRequest();
		listRequest.request = "";
		listRequest.type = GG_USER_LIST_PUT;

		return listRequest;
	}

	public static void changeRequestType(final GGUserListRequest userListRequest, final byte newType) {
		userListRequest.type = newType;
	}

	public static GGUserListRequest createPutUserListRequest(final List<String> lines) {
		if (lines == null) {
			throw new IllegalArgumentException("lines collection cannot be null");
		}
		final GGUserListRequest listRequest = new GGUserListRequest();
		listRequest.type = GG_USER_LIST_PUT;
		listRequest.request = createRequestFromList(lines);

		return listRequest;
	}

	public static GGUserListRequest createPutMoreUserListRequest(final List<String> lines) {
		if (lines == null) {
			throw new IllegalArgumentException("lines collection cannot be null");
		}
		final GGUserListRequest listRequest = new GGUserListRequest();
		listRequest.type = GG_USERLIST_PUT_MORE;
		listRequest.request = createRequestFromList(lines);

		return listRequest;
	}

	public static GGUserListRequest createGetUserListRequest() {
		final GGUserListRequest listRequest = new GGUserListRequest();
		listRequest.type = GG_USERLIST_GET;
		listRequest.request = "";

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
