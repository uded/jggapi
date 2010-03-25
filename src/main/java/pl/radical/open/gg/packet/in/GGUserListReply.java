package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.LocalUser;
import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.handlers.GGUserListReplyHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x0010, label = "GG_USERLIST_REPLY", handler = GGUserListReplyHandler.class)
@Deprecated
// FIXME Wyczyścić - coś mi się nie zgadza typ pakietów
public class GGUserListReply extends GGBaseIncomingPacket implements GGIncomingPackage {

	public final static int GG_USERLIST_REPLY = 0x0010;

	public final static int GG_USERLIST_PUT_REPLY = 0x00; /* początek eksportu listy */
	public final static int GG_USERLIST_PUT_MORE_REPLY = 0x02; /* kontynuacja */

	public final static int GG_USERLIST_GET_MORE_REPLY = 0x04; /* początek importu listy */
	public final static int GG_USERLIST_GET_REPLY = 0x06; /* ostatnia część importu */

	private byte m_type = -1;

	private Collection<LocalUser> m_users = null;

	public GGUserListReply(final byte[] data) throws IOException {
		m_type = data[0];
		if (isGetMoreReply() || isGetReply()) {
			m_users = createUsersCollection(data);
		}
	}

	private Collection<LocalUser> createUsersCollection(final byte[] data) throws IOException {
		final ArrayList<LocalUser> localUsers = new ArrayList<LocalUser>();

		final List<String> lines = GGUserListReply.getLinesStringList(data);

		for (final String subline : lines) {
			final String[] contactListStrings = subline.split(";");
			final List<String> contactList = Arrays.asList(contactListStrings);
			final LocalUser localUser = createLocalUser(contactList);
			localUsers.add(localUser);
		}

		return localUsers;
	}

	// imie;nazwisko;pseudo;wyswietlane;telefon;grupa;uin;adres@email;0;;0; //stara wersja
	// imi�;nazwisko;pseudonim;wy�wietlane;telefon_kom�rkowy;grupa;uin;adres_email;dost�pny;�cie�ka_dost�pny;wiadomo��;�cie�ka_wiadomo��;ukrywanie;telefon_domowy
	private LocalUser createLocalUser(final List<String> entries) {
		String firstName = null;
		String lastName = null;
		String nickName = null;
		String displayName = null;
		String telephone = null;
		String group = null;
		String uin = null;
		String email = null;

		final Iterator<String> it = entries.iterator();

		if (it.hasNext()) {
			firstName = it.next();
		}
		if (it.hasNext()) {
			lastName = it.next();
		}
		if (it.hasNext()) {
			nickName = it.next();
		}
		if (it.hasNext()) {
			displayName = it.next();
		}
		if (it.hasNext()) {
			telephone = it.next();
		}
		if (it.hasNext()) {
			group = it.next();
		}
		if (it.hasNext()) {
			uin = it.next();
		}
		if (it.hasNext()) {
			email = it.next();
		}

		final LocalUser localUser = new LocalUser();
		if (!isEmpty(firstName)) {
			localUser.setFirstName(firstName);
		}
		if (!isEmpty(lastName)) {
			localUser.setLastName(lastName);
		}
		if (!isEmpty(nickName)) {
			localUser.setNickName(nickName);
		}
		if (!isEmpty(displayName)) {
			localUser.setDisplayName(displayName);
		}
		if (!isEmpty(telephone)) {
			localUser.setTelephone(telephone);
		}
		if (!isEmpty(group)) {
			localUser.setGroup(group);
		}
		int uinInt = -1;
		try {
			uinInt = Integer.valueOf(uin).intValue();
			if (uinInt != -1 && !isEmpty(uin)) {
				localUser.setUin(uinInt);
			}
		} catch (final NumberFormatException ex) {
			// ignore
		}
		if (!isEmpty(email)) {
			localUser.setEmailAddress(email);
		}

		if (localUser.getDisplayName() == null && localUser.getFirstName() != null && localUser.getFirstName().equals("i")) {
			localUser.setBlocked(true);
		}

		return localUser;
	}

	public Collection<LocalUser> getContactList() {
		return m_users;
	}

	public boolean isPutReply() {
		return m_type == GG_USERLIST_PUT_REPLY;
	}

	public boolean isPutMoreReply() {
		return m_type == GG_USERLIST_PUT_MORE_REPLY;
	}

	public boolean isGetReply() {
		return m_type == GG_USERLIST_GET_REPLY;
	}

	public boolean isGetMoreReply() {
		return m_type == GG_USERLIST_GET_MORE_REPLY;
	}

	public static List<String> getLinesStringList(final byte[] data) throws IOException {
		final String contactListString = new String(data, 1, data.length - 1, "windows-1250");
		final BufferedReader bufReader = new BufferedReader(new StringReader(contactListString));

		final ArrayList<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufReader.readLine()) != null) {
			lines.add(line);
		}

		return lines;
	}

	private boolean isEmpty(final String text) {
		return text == null || text.trim().equals("");
	}

}
