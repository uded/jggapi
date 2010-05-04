package pl.radical.open.gg;

import pl.radical.open.gg.packet.dicts.StatusType;

import java.util.Arrays;
import java.util.Date;

/**
 * The default implementation of <code>IRemoteStatus</code>
 * <p>
 * Created on 2004-12-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class RemoteStatus extends AbstractStatus implements IRemoteStatus {
	private static final long serialVersionUID = 6536864191847671371L;

	private boolean blocked = false;

	private byte[] remoteIP = null;
	private int remotePort = -1;

	private int imageSize = -1;
	private int version = -1;
	private int descriptionSize = -1;

	private boolean voiceCommunicationSupported = false;
	private boolean directCommunicationSupported = false;
	private boolean inRemoteUserBuddyList = false;
	private boolean userBehindFirewall = false;

	public RemoteStatus(final StatusType statusType) {
		super(statusType);
	}

	public RemoteStatus(final StatusType statusType, final String description) {
		super(statusType, description);
	}

	public RemoteStatus(final StatusType statusType, final String description, final Date returnDate) {
		super(statusType, description, returnDate);
	}

	public void setBlocked(final boolean blocked) {
		this.blocked = blocked;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#isBlocked()
	 */
	public boolean isBlocked() {
		return blocked;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#getRemoteIP()
	 */
	public byte[] getRemoteIP() {
		return remoteIP;
	}

	public void setRemoteIP(final byte[] remoteIP) {
		if (remoteIP == null) {
			throw new IllegalArgumentException("remoteIP cannot be null");
		}
		if (remoteIP.length != 4) {
			throw new IllegalArgumentException("remoteIP must contain 4 entries");
		}
		this.remoteIP = Arrays.copyOf(remoteIP, remoteIP.length);
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#getRemotePort()
	 */
	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(final int remotePort) {
		if (remotePort < 0 || remotePort > 65535) {
			throw new IllegalArgumentException("Incorrect remotePort number");
		}
		this.remotePort = remotePort;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#getGGVersion()
	 */
	public int getGGVersion() {
		return version;
	}

	public void setGGVersion(final int version) {
		this.version = version;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#getImageSize()
	 */
	public int getImageSize() {
		return imageSize;
	}

	public void setImageSize(final int imageSize) {
		if (imageSize < 0) {
			throw new IllegalArgumentException("Illegal imageSize");
		}
		this.imageSize = imageSize;
	}

	public boolean isVoiceCommunicationSupported() {
		return voiceCommunicationSupported;
	}

	public void setSupportsVoiceCommunication(final boolean voiceCommunicationSupported) {
		this.voiceCommunicationSupported = voiceCommunicationSupported;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#isDirectCommunicationSupported()
	 */
	public boolean isDirectCommunicationSupported() {
		return directCommunicationSupported;
	}

	public void setSupportsDirectCommunication(final boolean directCommunicationSupported) {
		this.directCommunicationSupported = directCommunicationSupported;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#isinRemoteUserBuddyList()
	 */
	public boolean isinRemoteUserBuddyList() {
		return inRemoteUserBuddyList;
	}

	public void setInRemoteUserBuddyList(final boolean inRemoteUserBuddyList) {
		this.inRemoteUserBuddyList = inRemoteUserBuddyList;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#getDescription()
	 */
	public int getDescriptionSize() {
		return descriptionSize;
	}

	public void setDescriptionSize(final int descriptionSize) {
		if (descriptionSize < 0) {
			throw new IllegalArgumentException("descriptionSize cannot be less than 0");
		}
		this.descriptionSize = descriptionSize;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#isUserBehindFirewall()
	 */
	public boolean isUserBehindFirewall() {
		return userBehindFirewall;
	}

	public void setUserBehindFirewall(final boolean userBehingFirewall) {
		userBehindFirewall = true;
	}

}
