import java.io.IOException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;

import pl.mn.communicator.GGException;
import pl.mn.communicator.ILocalStatus;
import pl.mn.communicator.IRemoteStatus;
import pl.mn.communicator.IServer;
import pl.mn.communicator.ISession;
import pl.mn.communicator.ISingleChat;
import pl.mn.communicator.IUser;
import pl.mn.communicator.IncomingMessage;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.PersonalInfo;
import pl.mn.communicator.PublicDirSearchReply;
import pl.mn.communicator.SessionFactory;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.StatusType;
import pl.mn.communicator.User;
import pl.mn.communicator.User.UserMode;
import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.ContactListListener;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.event.MessageListener;
import pl.mn.communicator.event.PublicDirListener;
import pl.mn.communicator.event.SessionStateListener;
import pl.mn.communicator.event.UserListener;

/*
 * Created on 2004-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Main2 {

	public static void main(String args[]) throws IOException, GGException {

		final ISession session = SessionFactory.createSession();

		final LoginContext loginContext = new LoginContext(1336843, "dupadupa");

		session.addSessionStateListener(new SessionStateListener(){

			public void sessionStateChanged(SessionState oldSessionState, SessionState newSessionState) {
				System.out.println("session state changed, oldState: "+oldSessionState+", newState: "+newSessionState);
			}
			
		});
		
		session.getConnectionService().addConnectionListener(new ConnectionListener.Stub() {

			public void connectionEstablished() throws GGException {
				System.out.println("Connection established.");
				session.getLoginService().login(loginContext);
			}

			public void connectionClosed() {
				System.out.println("Connection closed.");
			}

			public void connectionError(Exception ex) throws GGException {
				System.out.println("Connection Error: "+ex.getMessage());
				session.getConnectionService().disconnect();
				IServer server = session.getConnectionService().lookupServer(loginContext.getUin());
				session.getConnectionService().connect(server);
			}

		});
		session.getLoginService().addLoginListener(new LoginListener.Stub() {

			public void loginOK() throws GGException {
				System.out.println("Login OK.");
				
				ILocalStatus status = session.getPresenceService().getStatus();
				//status.setFriendsOnly(true);
				status.setDescription("desc124253");
				status.setStatusType(StatusType.BUSY_WITH_DESCRIPTION);
				session.getPresenceService().setStatus(status);

				OutgoingMessage.createNewMessage(376798, "body");
				session.getMessageService().sendMessage(OutgoingMessage.createNewMessage(376798, String.valueOf(System.currentTimeMillis())));

				ISingleChat matiChat = session.getMessageService().createSingleChat(376798);
				matiChat.sendMessage("body");
				matiChat.sendMessage("dupka");
			}

			public void loginFailed() throws GGException {
				System.out.println("Login Failed.");
			}
			
			/**
			 * @see pl.mn.communicator.event.LoginListener.Stub#loggedOut()
			 */
			public void loggedOut() throws GGException {
			    System.out.println("Logged out...");
				session.getConnectionService().disconnect();
			}
			
		});
		session.getPresenceService().addUserListener(new UserListener() {

			public void userStatusChanged(IUser user, IRemoteStatus newStatus) {
				System.out.println("User changed status: "+user.getUin());
				System.out.println("Status: "+newStatus.getStatusType());
				System.out.println("Description: "+newStatus.getDescription());
				System.out.println("ReturnDate: "+newStatus.getReturnDate());
			}
			
		});
		session.getMessageService().addMessageListener(new MessageListener.Stub() {

			public void messageArrived(IncomingMessage incommingMessage) {
				System.out.println("MessageArrived, fromUser: "+incommingMessage.getRecipientUin());
				System.out.println("MessageBody: "+incommingMessage.getMessageBody());
				System.out.println("MessageID: "+incommingMessage.getMessageID());
				System.out.println("MessageStatus: "+incommingMessage.getMessageClass());
				System.out.println("MessageTime: "+incommingMessage.getMessageDate());
			}

			public void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus) {
				System.out.println("MessageDelivered, toUser: "+String.valueOf(uin));
				System.out.println("MessageDelivered, messageID: "+String.valueOf(messageID));
				System.out.println("MessageDelivered, messageStatus: "+deliveryStatus);
			}
			
		});
		
		session.getContactListService().addContactListListener(new ContactListListener() {

			public void contactListExported() {
				System.out.println("ContactList successfuly exported.");
			}
			
			/**
			 * @see pl.mn.communicator.event.ContactListListener#contactListReceived(java.util.Collection)
			 */
			public void contactListReceived(Collection users) {
				System.out.println("ContactList received.");
			}
			
		});

		session.getPublicDirectoryService().addPublicDirListener(new PublicDirListener() {

			public void onPublicDirectoryUpdated(int queryID) {
				System.out.println("Updated pubDir");
			}

			public void onPublicDirectorySearchReply(int queryID, PublicDirSearchReply publicDirSearchReply) {
				System.out.println("Got pubdir search results.");
				
			}

			public void onPublicDirectoryRead(int queryID, PersonalInfo pubDirReply) {
				System.out.println("Got pubDir read reply");
				System.out.println("FirstName: "+pubDirReply.getFirstName());
				System.out.println("Surname: "+pubDirReply.getLastName());
			}
			
		});

		IUser acze = new User(1136132, UserMode.BUDDY);
		IUser jaffa = new User(1542863, UserMode.BUDDY);
		IUser mati = new User(376798, UserMode.BUDDY);
		
		loginContext.getMonitoredUsers().add(acze);
		loginContext.getMonitoredUsers().add(jaffa);
		loginContext.getMonitoredUsers().add(mati);

		IServer server = session.getConnectionService().lookupServer(loginContext.getUin());
 		session.getConnectionService().connect(server);

//		session.getPublicDirectoryService().readFromPublicDirectory();
//		PublicDirInfo publicDirInfo = new PublicDirInfo();
//		publicDirInfo.setFirstName("Piotr");
//		publicDirInfo.setLastName("Kowalczyk");
//		publicDirInfo.setCity("Poznań");
//		publicDirInfo.setGender(Gender.MALE);
//		publicDirInfo.setNickName("Kowal");
//		publicDirInfo.setBirthDate("1967");
//		publicDirInfo.setFamilyCity("Kraków");
//		publicDirInfo.setFamilyName("Kowalskis Family");
//		session.getPublicDirectoryService().writeToPublicDirectory(publicDirInfo);
//		session.getPublicDirectoryService().readFromPublicDirectory();
//
//		PublicDirSearchQuery publicDirQuery = new PublicDirSearchQuery();
//		publicDirQuery.setCity("Szczecin");
//		publicDirQuery.setGender(Gender.FEMALE);
//		publicDirQuery.setActiveOnly(new Boolean(true));
//		
//		session.getPublicDirectoryService().search(publicDirQuery);
		
//		loginContext.setPassword("dupadupa");
//		session.getLoginService().login();
		
//		ISingleChat matiChat = session.getMessageService().createSingleChat(376798);
//		matiChat.sendMessage("body");
//		matiChat.sendMessage("dupka");

//		OutgoingMessage.createNewMessage(376798, "body");
//		session.getMessageService().sendMessage(OutgoingMessage.createNewMessage(376798, String.valueOf(System.currentTimeMillis())));
//		session.getMessageService().sendMessage(OutgoingMessage.createMessage(376798, String.valueOf(System.currentTimeMillis())));
//		session.getMessageService().sendMessage(OutgoingMessage.createMessage(376798, String.valueOf(System.currentTimeMillis())));
//		session.getMessageService().sendMessage(OutgoingMessage.createMessageWithoutConfirmation(376798, String.valueOf(System.currentTimeMillis())));

//		LocalUser localUser1 = new LocalUser();
//		localUser1.setDisplayName("ziom");
//		localUser1.setUin(376712);
//
//		LocalUser localUser2 = new LocalUser();
//		localUser2.setDisplayName("jan");
//		localUser2.setUin(326712);

//		ArrayList localUsers = new ArrayList();
//		localUsers.add(localUser1);
//		localUsers.add(localUser2);
		
//		session.getContactListService().clearUserListRequest();
//		session.getContactListService().exportContacts(localUsers);
//		session.getContactListService().importContacts();
		
//		GGUser user3 = new GGUser(2944956, GGUserMode.BUDDY);
//		session.getPresenceService().addMonitoredUser(user3);
//
//		session.getPresenceService().removeMonitoredUser(user1);
		
//		IUser user2 = new GGUser(2040781, GGUserMode.BUDDY);
//		session.getPresenceService().addMonitoredUser(user2);

//		user1.setUserMode(GGUserMode.FRIEND);
//		session.getPresenceService().changeMonitoredUserStatus(user1);

		System.out.println("JGGApi simple console MENU");
		System.out.println("[1] - Zakonczenie programu");
		System.out.println("[2] - Wylogowanie uzytkownika");
		System.out.println("[3] - Wyslij wiadomosc do uzytkownika 376798 z aktualna data.");
		System.out.println("[4] - Zamiana statusu na dostepny.");
		System.out.println("[5] - Zamiana statusu na niewidoczny z opisem.");
		System.out.println("[6] - Zamiana statusu na zajety");
		
 		boolean active = true;
 		while (active) {
 		    if (System.in.available() > 0) {
 		        int i = System.in.read();
 		        if (i == 49) {
 		            active = false;
 		        } else if (i == 50) {
 		    		session.getLoginService().logout();
 		        } else  if(i == 51) {
 		            OutgoingMessage outgoingMessage = OutgoingMessage.createNewMessage(376798, DateFormat.getDateTimeInstance().format(new Date()));
 		            session.getMessageService().sendMessage(outgoingMessage);
 		        } else if (i == 10 || i == 13) {
 		            //ignore
 		        } else if (i == 52) {
 		           ILocalStatus status = session.getPresenceService().getStatus();
 		            if (status.getStatusType() != StatusType.ONLINE) {
 		                status.setStatusType(StatusType.ONLINE);
 		                session.getPresenceService().setStatus(status);
 		            }
 		        } else if (i == 53) {
 		            ILocalStatus status = session.getPresenceService().getStatus();
 		            if (status.getStatusType() != StatusType.INVISIBLE_WITH_DESCRIPTION) {
 		                status.setStatusType(StatusType.INVISIBLE_WITH_DESCRIPTION);
 		                status.setDescription("Invisible desc");
 		                session.getPresenceService().setStatus(status);
 		            }
		        } else if (i == 54) {
		            ILocalStatus status = session.getPresenceService().getStatus();
 		            if (status.getStatusType() != StatusType.BUSY) {
 		                status.setStatusType(StatusType.BUSY_WITH_DESCRIPTION);
 		                status.setDescription("busy desc");
 		                session.getPresenceService().setStatus(status);
 		            }
		        } else {
		    		System.out.println("JGGApi simple console MENU");
		    		System.out.println("[1] - Zakonczenie programu");
		    		System.out.println("[2] - Wylogowanie uzytkownika");
		    		System.out.println("[3] - Wyslij wiadomosc do uzytkownika 376798 z aktualna data.");
		    		System.out.println("[4] - Zamiana statusu na dostepny.");
		    		System.out.println("[5] - Zamiana statusu na niewidoczny z opisem.");
		    		System.out.println("[6] - Zamiana statusu na zajety");
		        }
 		    }
 		    try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
         		session.getLoginService().logout();
            }
 		}

		System.out.println("Abandon ship...");
		//session.getConnectionService().disconnect();
	}
	
}
