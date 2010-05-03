package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.IRemoteStatus;
import pl.radical.open.gg.PersonalInfo;
import pl.radical.open.gg.PublicDirSearchReply;
import pl.radical.open.gg.dicts.Gender;
import pl.radical.open.gg.packet.AbstractGGIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
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
@IncomingPacket(type = 0x000e, label = "GG_PUBDIR50_REPLY", handler = GGPubdirReplyPacketHandler.class)
public class GGPubdirReply extends AbstractGGIncomingPacket implements GGPubdirConsts, GGIncomingPackage {

	private byte replyType = -1;
	private int querySeq = -1;

	private PersonalInfo pubDirInfo = null;
	private PublicDirSearchReply publicDirSearchReply = null;

	public GGPubdirReply(final byte[] data) {
		replyType = data[0];
		querySeq = GGUtils.byteToInt(data, 1);
		if (isPubdirReadReply()) {
			handlePubdirReadReply(data);
		} else if (isPubdirSearchReply()) {
			handlePubdirSearchReply(data);
		}
	}

	public int getQuerySeq() {
		return querySeq;
	}

	public PersonalInfo getPubdirReadReply() {
		return pubDirInfo;
	}

	public PublicDirSearchReply getPubdirSearchReply() {
		return publicDirSearchReply;
	}

	public boolean isPubdirSearchReply() {
		return replyType == GG_PUBDIR50_SEARCH_REPLY;
	}

	public boolean isPubdirReadReply() {
		return replyType == GG_PUBDIR50_SEARCH;
	}

	public boolean isPubdirWriteReply() {
		return replyType == GG_PUBDIR50_WRITE;
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
		pubDirInfo = new PersonalInfo();
		while (tokenizer.hasMoreTokens()) {
			final String token = tokenizer.nextToken();
			if (token.equals(FIRST_NAME)) {
				final String firstName = tokenizer.nextToken();
				pubDirInfo.setFirstName(firstName);
			} else if (token.equals(LAST_NAME)) {
				final String lastName = tokenizer.nextToken();
				pubDirInfo.setLastName(lastName);
			} else if (token.equals(BIRTH_YEAR)) {
				final String birthDate = tokenizer.nextToken();
				pubDirInfo.setBirthDate(birthDate);
			} else if (token.equals(CITY)) {
				final String city = tokenizer.nextToken();
				pubDirInfo.setCity(city);
			} else if (token.equals(NICK_NAME)) {
				final String nickName = tokenizer.nextToken();
				pubDirInfo.setNickName(nickName);
			} else if (token.equals(GENDER)) {
				// FIXME Review this part
				final String genderString = tokenizer.nextToken();
				Gender gender = null;
				if (genderString.equals("1")) {
					gender = Gender.MALE;
				} else if (genderString.equals("2")) {
					gender = Gender.FEMALE;
				}
				pubDirInfo.setGender(gender);
			} else if (token.equals(FAMILY_NAME)) {
				final String familyName = tokenizer.nextToken();
				pubDirInfo.setFamilyName(familyName);
			} else if (token.equals(FAMILY_CITY)) {
				final String familyCity = tokenizer.nextToken();
				pubDirInfo.setFamilyCity(familyCity);
			}
		}
	}

	private void handlePubdirSearchReply(final byte[] data) {
		final String string = byteToString(data, 5);
		publicDirSearchReply = new PublicDirSearchReply();
		final StringTokenizer tokenizer = new StringTokenizer(string, "\0");
		PublicDirSearchReply.Entry entry = publicDirSearchReply.createSearchEntry();

		boolean processedUin = false;
		while (tokenizer.hasMoreTokens()) {
			final String token = tokenizer.nextToken();
			if (processedUin && token.equals(UIN)) {
				processedUin = false;
				entry = publicDirSearchReply.createSearchEntry();
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
				publicDirSearchReply.setNextStart(Integer.valueOf(nextNumber));
				break;
			}
		}
	}

}
