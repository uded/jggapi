package pl.mn.communicator.gadu;

import pl.mn.communicator.IConnectionService;
import pl.mn.communicator.IContactListService;
import pl.mn.communicator.IIncommingMessage;
import pl.mn.communicator.ILoginService;
import pl.mn.communicator.IMessageService;
import pl.mn.communicator.IOutgoingMessage;
import pl.mn.communicator.IPublicDirectoryService;
import pl.mn.communicator.ISession;
import pl.mn.communicator.LocalUser;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.PersonalInfo;
import pl.mn.communicator.PublicDirSearchQuery;
import pl.mn.communicator.PublicDirSearchReply;
import pl.mn.communicator.Server;
import pl.mn.communicator.SessionFactory;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.ContactListListener;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.event.MessageListener;
import pl.mn.communicator.event.PublicDirListener;
import pl.mn.communicator.event.SessionStateListener;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author mehul
 */
public class Connect {

	ILoginService loginService;
	IConnectionService connectionService;
	ISession session;
	IContactListService contactListService;
	LoginContext loginContext;
	public IPublicDirectoryService publicdirService;
	public PublicDirSearchQuery searchQuery;
	public Collection<LocalUser> ulist;

	public void E() {
		try {

			loginContext = new LoginContext(1038285, "test");

			session = SessionFactory.createSession();

			session.addSessionStateListener(new SessionStateListener() {
				public void sessionStateChanged(final SessionState oldSessionState, final SessionState newSessionState) {

					System.out.println("" + newSessionState.toString());

					if (newSessionState.toString().equals("connected")) {

						// F();

					}

					if (newSessionState.toString().equals("authentication_awaiting")) {

						F();
					}

					if (newSessionState.toString().equals("logged_in")) {

						try {
							// connectionService.disconnect();
							System.out.println("IN HERE 1");
							contactListService = session.getContactListService();
							System.out.println("IN HERE 2");
							contactListService.importContactList();
							System.out.println("IN HERE 3");
							contactListService.addContactListListener(new ContactListListener() {

								public void contactListExported() {

									System.out.println("Contact list exported: ");
								}

								public void contactListReceived(final Collection<LocalUser> users) {
									ulist = users;

									System.out.println("contact list recieved successfully: " + users + " length is:" + users.size());

									final Iterator<LocalUser> it = users.iterator();

									Object obj;
									while (it.hasNext()) {
										obj = it.next();
										System.out.println("Object is =====" + obj);
										final LocalUser lusr = (pl.mn.communicator.LocalUser) obj;
										System.out
										.println("The details are: ===========\nUIN: " + lusr.getUin() + "\nDisplay Name: " + lusr
												.getDisplayName() + "\n=======================");

									}

									X();
								}

							});
							// X();

						} catch (final Exception e) {
							System.out.println("Exception while getting USER_LIST: " + e);
						}

						try {
							final IMessageService messageService = session.getMessageService();

							messageService.addMessageListener(new MessageListener() {

								public void messageArrived(final IIncommingMessage incommingMessage) {

									System.out
									.println("================\nMessage Body: " + incommingMessage.getMessageBody() + "\nMessage ID: " + incommingMessage
											.getMessageID() + "\nFrom ID: " + incommingMessage.getRecipientUin() + "\n========================");

								}

								public void messageDelivered(final int uin, final int messageID, final MessageStatus deliveryStatus) {
								}

								public void messageSent(final IOutgoingMessage outgoingmessage) {
								}

							});

							final OutgoingMessage outMessage = OutgoingMessage.createNewMessage(5914398, "helo hello hello");
							messageService.sendMessage(outMessage);

						} catch (final Exception e) {

							System.out.println("Exception while sending the message: " + e);
						}

					}

				}
			});

			// ///

			connectionService = session.getConnectionService();

			connectionService.addConnectionListener(new ConnectionListener.Stub() {
				@Override
				public void connectionEstablished() {
					System.out.println("Connection established.");

				}

				@Override
				public void connectionClosed() {
					System.out.println("Connection closed.");
				}

				@Override
				public void connectionError(final Exception ex) {
					System.out.println("Connection Error: " + ex.getMessage());
				}
			});
			// connectionService.connect();

			connectionService.connect(new Server("217.17.41.93", 8074));

		} catch (final Exception e) {
			System.out.println("Exception in Connect: " + e);
		}

	}

	public void F() {

		try {
			loginService = session.getLoginService();
			// loginService.login();
			loginService.login(loginContext);
			loginService.addLoginListener(new LoginListener.Stub() {
				@Override
				public void loginOK() {
					System.out.println("Login OK.");

				}

				@SuppressWarnings("unused")
				public void loginFailed() {
					System.out.println("Login Failed.");
				}
			});

		} catch (final Exception e) {
			System.out.println("Exception in F(): " + e);
		}

	}

	// =====================================ADDED NOW FOR LIST EXPORT TEST
	public void X() {

		System.out.println("Inside X()");
		publicdirService = session.getPublicDirectoryService();

		publicdirService.addPublicDirListener(new PublicDirListener() {

			public void onPublicDirectoryRead(final int queryID, final PersonalInfo pubDirReply) {
			}

			public void onPublicDirectoryUpdated(final int queryID) {

				System.out.println("List Updated to the server");
			}

			public void onPublicDirectorySearchReply(final int queryID, final PublicDirSearchReply publicDirSearchReply) {
			}

		});

		LocalUser luser = null;

		final String[] fname = {
				"january", "february", "march", "april", "may", "june", "july", "August", "sebptember", "octomber", "november", "december",
				"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "ripley", "jonathanMores", "RickyMartin",
				"SubramaniamSwamy", "recardo", "Christiano", "senthilchengalvarayan", "rathisMacGuire", "ChristopherReeves",
				"Imissyouverymuchdoyou", "santasinghbantasingh", "requirethisornot", "thisshouldexceed2400mark", "theprogramshouldfail",
				"newyorknewyork", "chicagorensays", "dreaming4u", "someoneNowhere", "OughttobeOneAmongYOu"
		};
		try {

			final int j = 10101;
			for (int i = 0; i < 37; i++) {
				luser = new LocalUser();
				luser.setUin(j + i);
				luser.setFirstName(fname[i]);
				luser.setDisplayName(fname[i]);
				luser.setNickName(fname[i]);
				luser.setEmailAddress(fname[i] + "@month.com");
				luser.setTelephone(fname[i]);
				System.out.println("Before adding USER to ULIST COllection" + i);
				ulist.add(luser);

			}

			contactListService.exportContactList(ulist);

		} catch (final Exception e) {
			System.out.println("Exception while adding user to the list:" + e);

		}

	}

	// =====================================

	public static void main(final String args[]) {
		final Connect a = new Connect();
		a.E();

	}

}
