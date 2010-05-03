package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;
import pl.radical.open.gg.RemoteStatus;
import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.AbstractGGIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
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
@IncomingPacket(type = 0x0002, label = "GG_STATUS", handler = GGStatusPacketHandler.class)
@Deprecated
public class GGStatus extends AbstractGGIncomingPacket implements GGStatuses, GGIncomingPackage {

	private IUser user = null;
	private RemoteStatus status = null;

	public GGStatus(final byte[] data) {
		handleUser(data);
		handleStatus(data);
	}

	public IUser getUser() {
		return user;
	}

	public IRemoteStatus getStatus() {
		return status;
	}

	private void handleUser(final byte[] data) {
		final int uin = GGUtils.byteToInt(data);
		final int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
		final User.UserMode userMode = GGConversion.getUserMode(protocolStatus);
		user = new User(uin, userMode);
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
		status = GGConversion.getClientRemoteStatus(protocolStatus, description, timeInMillis);
	}

}
