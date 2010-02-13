package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.Gender;
import pl.radical.open.gg.PersonalInfo;
import pl.radical.open.gg.PublicDirSearchQuery;
import pl.radical.open.gg.packet.GGPubdirConsts;

import java.util.Random;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGPubdirRequest implements GGOutgoingPackage, GGPubdirConsts {

	public static final int GG_PUBDIR50_REQUEST = 0x0014;

	private final static Random SEQUENCER = new Random();

	private byte m_requestType = -1;
	private int m_seq = -1;
	private String m_request = "";

	private GGPubdirRequest() {
		m_seq = SEQUENCER.nextInt(99999);
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_PUBDIR50_REQUEST;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 5 + m_request.getBytes().length;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final byte[] toSend = new byte[getLength()];

		toSend[0] = m_requestType;
		toSend[1] = (byte) (m_seq & 0xFF);
		toSend[2] = (byte) (m_seq >> 8 & 0xFF);
		toSend[3] = (byte) (m_seq >> 16 & 0xFF);
		toSend[4] = (byte) (m_seq >> 24 & 0xFF);

		final byte[] requestBytes = m_request.getBytes();
		for (int i = 0; i < requestBytes.length; i++) {
			toSend[5 + i] = requestBytes[i];
		}

		return toSend;
	}

	public static GGPubdirRequest createSearchPubdirRequest(final PublicDirSearchQuery publicDirQuery) {
		final GGPubdirRequest pubdirRequest = new GGPubdirRequest();
		pubdirRequest.m_requestType = GG_PUBDIR50_SEARCH;

		final StringBuffer buffer = new StringBuffer();
		if (publicDirQuery.getUin() != null) {
			final Integer uin = publicDirQuery.getUin();
			final String uinEntry = getEntry(UIN, String.valueOf(uin.intValue()));
			buffer.append(uinEntry);
		}
		if (publicDirQuery.getFirstName() != null) {
			final String firstNameEntry = getEntry(FIRST_NAME, publicDirQuery.getFirstName());
			buffer.append(firstNameEntry);
		}
		if (publicDirQuery.getLastName() != null) {
			final String lastNameEntry = getEntry(LAST_NAME, publicDirQuery.getLastName());
			buffer.append(lastNameEntry);
		}
		if (publicDirQuery.getCity() != null) {
			final String cityEntry = getEntry(CITY, publicDirQuery.getCity());
			buffer.append(cityEntry);
		}
		if (publicDirQuery.getNickName() != null) {
			final String nickNameEntry = getEntry(NICK_NAME, publicDirQuery.getNickName());
			buffer.append(nickNameEntry);
		}
		if (publicDirQuery.getBirthYear() != null) {
			final String birthEntry = getEntry(BIRTH_YEAR, publicDirQuery.getBirthYear());
			buffer.append(birthEntry);
		}
		if (publicDirQuery.getGender() != null) {
			final Gender gender = publicDirQuery.getGender();
			final String genderEntry = getEntry(GENDER, gender == Gender.MALE ? "2" : "1");
			buffer.append(genderEntry);
		}
		if (publicDirQuery.getFamilyName() != null) {
			final String familyNameEntry = getEntry(FAMILY_NAME, publicDirQuery.getFamilyName());
			buffer.append(familyNameEntry);
		}
		if (publicDirQuery.getFamilyCity() != null) {
			final String familyCityEntry = getEntry(FAMILY_CITY, publicDirQuery.getFamilyCity());
			buffer.append(familyCityEntry);
		}
		if (publicDirQuery.getStart() != null) {
			final Integer startInteger = publicDirQuery.getStart();
			final String startEntry = getEntry(START, String.valueOf(startInteger.intValue()));
			buffer.append(startEntry);
		}
		pubdirRequest.m_request = buffer.toString();
		return pubdirRequest;
	}

	public static GGPubdirRequest createReadPubdirRequest() {
		final GGPubdirRequest pubdirRequest = new GGPubdirRequest();
		pubdirRequest.m_requestType = GG_PUBDIR50_READ;
		pubdirRequest.m_request = "";
		return pubdirRequest;
	}

	public static GGPubdirRequest createWritePubdirRequest(final PersonalInfo publicDirInfo) {
		if (publicDirInfo == null) {
			throw new NullPointerException("publicDirInfo cannot be null");
		}
		final GGPubdirRequest pubdirRequest = new GGPubdirRequest();
		pubdirRequest.m_requestType = GG_PUBDIR50_WRITE;
		pubdirRequest.m_request = prepareWriteRequest(publicDirInfo);
		return pubdirRequest;
	}

	private static String prepareWriteRequest(final PersonalInfo publicDirInfo) {
		final StringBuffer buffer = new StringBuffer();
		if (publicDirInfo.getFirstName() != null) {
			final String firstNameEntry = getEntry(FIRST_NAME, publicDirInfo.getFirstName());
			buffer.append(firstNameEntry);
		}
		if (publicDirInfo.getLastName() != null) {
			final String lastNameEntry = getEntry(LAST_NAME, publicDirInfo.getLastName());
			buffer.append(lastNameEntry);
		}
		if (publicDirInfo.getCity() != null) {
			final String cityEntry = getEntry(CITY, publicDirInfo.getCity());
			buffer.append(cityEntry);
		}
		if (publicDirInfo.getNickName() != null) {
			final String nickNameEntry = getEntry(NICK_NAME, publicDirInfo.getNickName());
			buffer.append(nickNameEntry);
		}
		if (publicDirInfo.getGender() != null) {
			final Gender gender = publicDirInfo.getGender();
			final String genderEntry = getEntry(GENDER, gender == Gender.MALE ? "1" : "2");
			buffer.append(genderEntry);
		}
		if (publicDirInfo.getFamilyName() != null) {
			final String familyNameEntry = getEntry(FAMILY_NAME, publicDirInfo.getFamilyName());
			buffer.append(familyNameEntry);
		}
		if (publicDirInfo.getFamilyCity() != null) {
			final String familyCityEntry = getEntry(FAMILY_CITY, publicDirInfo.getFamilyCity());
			buffer.append(familyCityEntry);
		}
		if (publicDirInfo.getBirthDate() != null) {
			final String birthDateEntry = getEntry(BIRTH_YEAR, publicDirInfo.getBirthDate());
			buffer.append(birthDateEntry);
		}
		return buffer.toString();
	}

	private static String getEntry(final String key, final String value) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(key);
		buffer.append('\0');
		buffer.append(value);
		buffer.append('\0');
		return buffer.toString();
	}

}
