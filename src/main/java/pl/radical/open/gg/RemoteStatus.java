package pl.radical.open.gg;

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

	private boolean blocked = false;

	private byte[] remoteIP = null;
	private int remotePort = -1;

	private int imageSize = -1;
	private int version = -1;
	private int descriptionSize = -1;

	private boolean supportsVoiceCommunication = false;
	private boolean supportsDirectCommunication = false;
	private boolean areWeInRemoteUserBuddyList = false;
	private boolean isUserBehindFirewall = false;

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

	public boolean supportsVoiceCommunication() {
		return supportsVoiceCommunication;
	}

	public void setSupportsVoiceCommunication(final boolean supportsVoiceCommunication) {
		this.supportsVoiceCommunication = supportsVoiceCommunication;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#supportsDirectCommunication()
	 */
	public boolean supportsDirectCommunication() {
		return supportsDirectCommunication;
	}

	public void setSupportsDirectCommunication(final boolean supportsDirectCommunication) {
		this.supportsDirectCommunication = supportsDirectCommunication;
	}

	/**
	 * @see pl.radical.open.gg.IRemoteStatus#areWeInRemoteUserBuddyList()
	 */
	public boolean areWeInRemoteUserBuddyList() {
		return areWeInRemoteUserBuddyList;
	}

	public void setAreWeInRemoteUserBuddyList(final boolean areWeInRemoteUserBuddyList) {
		this.areWeInRemoteUserBuddyList = areWeInRemoteUserBuddyList;
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
		return isUserBehindFirewall;
	}

	public void setUserBehindFirewall(final boolean userBehingFirewall) {
		isUserBehindFirewall = true;
	}

}
