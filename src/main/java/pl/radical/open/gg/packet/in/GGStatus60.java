package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.IUser;
import pl.radical.open.gg.RemoteStatus;
import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.dicts.GGStatuses;
import pl.radical.open.gg.packet.handlers.GGStatus60PacketHandler;
import pl.radical.open.gg.utils.GGConversion;
import pl.radical.open.gg.utils.GGUtils;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x000f, handler = GGStatus60PacketHandler.class)
@Deprecated
public class GGStatus60 extends GGBaseIncomingPacket implements GGStatuses, GGIncomingPackage {

	private IUser m_user = null;

	private RemoteStatus m_status60 = null;

	public GGStatus60(final byte[] data) {
		handleUser(data);
		handleStatus60(data);
	}

	public IUser getUser() {
		return m_user;
	}

	public IRemoteStatus getStatus60() {
		return m_status60;
	}

	private void handleUser(final byte[] data) {
		final byte flag = data[3]; // cache flag
		data[3] = GGUtils.intToByte(0)[0]; // remove flag
		final int uin = GGUtils.byteToInt(data);
		data[3] = flag;
		final int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
		final User.UserMode userMode = GGConversion.getUserMode(protocolStatus);
		m_user = new User(uin, userMode);
	}

	private void handleStatus60(final byte[] data) {
		final byte flag = data[3]; // cache flag

		final int protocolStatus = GGUtils.unsignedByteToInt(data[4]);
		final int remoteIP = GGUtils.byteToInt(data, 5);
		final byte[] remoteIPArray = GGUtils.convertIntToByteArray(remoteIP);
		final int remotePort = GGUtils.byteToShort(data, 9);
		final int version = GGUtils.unsignedByteToInt(data[11]);
		final int imageSize = GGUtils.unsignedByteToInt(data[12]);

		String description = null;
		long timeInMillis = -1;
		if (protocolStatus == GGStatuses.GG_STATUS_AVAIL_DESCR || protocolStatus == GGStatuses.GG_STATUS_BUSY_DESCR || protocolStatus == GGStatuses.GG_STATUS_INVISIBLE_DESCR || protocolStatus == GGStatuses.GG_STATUS_NOT_AVAIL_DESCR) {
			description = GGUtils.byteToString(data, 14);
			if (data.length > 14 + description.length()) {
				final int timeInSeconds = GGUtils.byteToInt(data, data.length - 4);
				timeInMillis = GGUtils.secondsToMillis(timeInSeconds);
			}
		}

		m_status60 = GGConversion.getClientRemoteStatus(protocolStatus, description, timeInMillis);
		m_status60.setRemoteIP(remoteIPArray);
		m_status60.setImageSize(imageSize);
		m_status60.setGGVersion(version);

		if (remotePort == 0) {
			m_status60.setSupportsDirectCommunication(false);
		} else if (remotePort == 1) {
			m_status60.setUserBehindFirewall(true);
		} else if (remotePort == 2) {
			m_status60.setAreWeInRemoteUserBuddyList(false);
		} else {
			m_status60.setRemotePort(remotePort);
		}

		if (flag == 0x40) {
			m_status60.setSupportsVoiceCommunication(true);
		}
	}

}
