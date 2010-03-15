package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.Gender;
import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.PersonalInfo;
import pl.radical.open.gg.PublicDirSearchReply;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.dicts.GGPubdirConsts;
import pl.radical.open.gg.packet.handlers.GGPubdirReplyPacketHandler;
import pl.radical.open.gg.utils.GGConversion;
import pl.radical.open.gg.utils.GGUtils;

import java.util.StringTokenizer;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x000e, handler = GGPubdirReplyPacketHandler.class)
public class GGPubdirReply extends GGBaseIncomingPacket implements GGPubdirConsts, GGIncomingPackage {

	private byte m_replyType = -1;
	private int m_querySeq = -1;

	private PersonalInfo m_pubDirInfo = null;
	private PublicDirSearchReply m_publicDirSearchReply = null;

	public GGPubdirReply(final byte[] data) {
		m_replyType = data[0];
		m_querySeq = GGUtils.byteToInt(data, 1);
		if (isPubdirReadReply()) {
			handlePubdirReadReply(data);
		} else if (isPubdirSearchReply()) {
			handlePubdirSearchReply(data);
		}
	}

	public int getQuerySeq() {
		return m_querySeq;
	}

	public PersonalInfo getPubdirReadReply() {
		return m_pubDirInfo;
	}

	public PublicDirSearchReply getPubdirSearchReply() {
		return m_publicDirSearchReply;
	}

	public boolean isPubdirSearchReply() {
		return m_replyType == GG_PUBDIR50_SEARCH_REPLY;
	}

	public boolean isPubdirReadReply() {
		return m_replyType == GG_PUBDIR50_SEARCH;
	}

	public boolean isPubdirWriteReply() {
		return m_replyType == GG_PUBDIR50_WRITE;
	}

	private String byteToString(final byte[] data, final int startIndex) {
		int counter = 0;

		while (counter + startIndex < data.length) {
			counter++;
		}

		final byte[] desc = new byte[counter];
		System.arraycopy(data, startIndex, desc, 0, counter);

		return new String(desc);
	}

	private void handlePubdirReadReply(final byte[] data) {
		final String string = byteToString(data, 5);
		final StringTokenizer tokenizer = new StringTokenizer(string, "\0");
		m_pubDirInfo = new PersonalInfo();
		while (tokenizer.hasMoreTokens()) {
			final String token = tokenizer.nextToken();
			if (token.equals(FIRST_NAME)) {
				final String firstName = tokenizer.nextToken();
				m_pubDirInfo.setFirstName(firstName);
			} else if (token.equals(LAST_NAME)) {
				final String lastName = tokenizer.nextToken();
				m_pubDirInfo.setLastName(lastName);
			} else if (token.equals(BIRTH_YEAR)) {
				final String birthDate = tokenizer.nextToken();
				m_pubDirInfo.setBirthDate(birthDate);
			} else if (token.equals(CITY)) {
				final String city = tokenizer.nextToken();
				m_pubDirInfo.setCity(city);
			} else if (token.equals(NICK_NAME)) {
				final String nickName = tokenizer.nextToken();
				m_pubDirInfo.setNickName(nickName);
			} else if (token.equals(GENDER)) {
				final String genderString = tokenizer.nextToken();
				Gender gender = null;
				if (genderString.equals("1")) {
					gender = Gender.MALE;
				} else if (genderString.equals("2")) {
					gender = Gender.FEMALE;
				}
				m_pubDirInfo.setGender(gender);
			} else if (token.equals(FAMILY_NAME)) {
				final String familyName = tokenizer.nextToken();
				m_pubDirInfo.setFamilyName(familyName);
			} else if (token.equals(FAMILY_CITY)) {
				final String familyCity = tokenizer.nextToken();
				m_pubDirInfo.setFamilyCity(familyCity);
			}
		}
	}

	private void handlePubdirSearchReply(final byte[] data) {
		final String string = byteToString(data, 5);
		m_publicDirSearchReply = new PublicDirSearchReply();
		final StringTokenizer tokenizer = new StringTokenizer(string, "\0");
		PublicDirSearchReply.Entry entry = m_publicDirSearchReply.createSearchEntry();

		boolean processedUin = false;
		while (tokenizer.hasMoreTokens()) {
			final String token = tokenizer.nextToken();
			if (processedUin && token.equals(UIN)) {
				processedUin = false;
				entry = m_publicDirSearchReply.createSearchEntry();
				final String uin = tokenizer.nextToken();
				entry.setUin(Integer.valueOf(uin));
				processedUin = true;
			} else if (token.equals(FIRST_NAME)) {
				final String firstName = tokenizer.nextToken();
				entry.setFirstName(firstName);
			} else if (token.equals(UIN)) {
				final String uin = tokenizer.nextToken();
				entry.setUin(Integer.valueOf(uin));
				processedUin = true;
			} else if (token.equals(STATUS)) {
				final String status = tokenizer.nextToken();
				final int protocolStatus = Integer.valueOf(status).intValue();
				final IRemoteStatus statusBiz = GGConversion.getClientRemoteStatus(protocolStatus, null, -1);
				entry.setStatus(statusBiz);
			} else if (token.equals(BIRTH_YEAR)) {
				final String birthYear = tokenizer.nextToken();
				entry.setBirthYear(birthYear);
			} else if (token.equals(CITY)) {
				final String city = tokenizer.nextToken();
				entry.setCity(city);
			} else if (token.equals(NICK_NAME)) {
				final String nickName = tokenizer.nextToken();
				entry.setNickName(nickName);
			} else if (token.equals(NEXT_START)) {
				final String nextNumber = tokenizer.nextToken();
				m_publicDirSearchReply.setNextStart(Integer.valueOf(nextNumber));
				break;
			}
		}
	}

}
