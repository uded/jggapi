package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;
import pl.radical.open.gg.RemoteStatus;
import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.dicts.GGStatuses;
import pl.radical.open.gg.packet.handlers.GGStatusPacketHandler;
import pl.radical.open.gg.utils.GGConversion;
import pl.radical.open.gg.utils.GGUtils;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x0002, handler = GGStatusPacketHandler.class)
@Deprecated
public class GGStatus extends GGBaseIncomingPacket implements GGStatuses, GGIncomingPackage {

	private IUser m_user = null;
	private RemoteStatus m_status = null;

	public GGStatus(final byte[] data) {
		handleUser(data);
		handleStatus(data);
	}

	public IUser getUser() {
		return m_user;
	}

	public IRemoteStatus getStatus() {
		return m_status;
	}

	private void handleUser(final byte[] data) {
		final int uin = GGUtils.byteToInt(data);
		final int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
		final User.UserMode userMode = GGConversion.getUserMode(protocolStatus);
		m_user = new User(uin, userMode);
	}

	private void handleStatus(final byte[] data) {
		final int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
		String description = null;
		long timeInMillis = -1;
		if (protocolStatus == GGStatuses.GG_STATUS_AVAIL_DESCR || protocolStatus == GGStatuses.GG_STATUS_BUSY_DESCR || protocolStatus == GGStatuses.GG_STATUS_INVISIBLE_DESCR || protocolStatus == GGStatuses.GG_STATUS_NOT_AVAIL_DESCR) {
			description = GGUtils.byteToString(data, 8);
			if (data.length > 8 + description.length()) {
				final int timeInSeconds = GGUtils.byteToInt(data, data.length - 4);
				timeInMillis = GGUtils.secondsToMillis(timeInSeconds);
			}
		}
		m_status = GGConversion.getClientRemoteStatus(protocolStatus, description, timeInMillis);
	}

}
