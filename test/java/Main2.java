import java.io.IOException;
import java.util.Collection;

import pl.mn.communicator.GGException;
import pl.mn.communicator.Gender;
import pl.mn.communicator.ISession;
import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.IncomingMessage;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.PersonalInfo;
import pl.mn.communicator.PublicDirSearchQuery;
import pl.mn.communicator.PublicDirSearchReply;
import pl.mn.communicator.SessionFactory;
import pl.mn.communicator.User;
import pl.mn.communicator.User.UserMode;
import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.ContactListListener;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.event.MessageListener;
import pl.mn.communicator.event.PublicDirListener;
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

		IUser acze = new User(1136132, UserMode.BUDDY);
		IUser jaffa = new User(1542863, UserMode.BUDDY);
		IUser mati = new User(376798, UserMode.BUDDY);

		final LoginContext loginContext = new LoginContext(1336843, "dupadupa");
		loginContext.getStatus().setFriendsOnly(true);
		
		loginContext.getMonitoredUsers().add(acze);
		loginContext.getMonitoredUsers().add(jaffa);
		loginContext.getMonitoredUsers().add(mati);
		
		final ISession session = SessionFactory.createSession(loginContext);
		
		session.getConnectionService().addConnectionListener(new ConnectionListener.Stub() {

			public void connectionEstablished() {
				System.out.println("Connection established.");
			}

			public void connectionClosed() {
				System.out.println("Connection closed.");
			}

			public void connectionError(Exception ex) {
				System.out.println("Connection Error: "+ex.getMessage());
			}

		});
		session.getLoginService().addLoginListener(new LoginListener() {

			public void loginOK() {
				System.out.println("Login OK.");
			}

			public void loginFailed() {
				System.out.println("Login Failed.");
			}
			
		});
		session.getPresenceService().addUserListener(new UserListener() {

			public void userStatusChanged(IUser user, IStatus newStatus) {
				System.out.println("User changed status: "+user.getUin());
				System.out.println("Status: "+newStatus.getStatusType());
				System.out.println("Description: "+newStatus.getDescription());
				System.out.println("ReturnDate: "+newStatus.getReturnDate());
			}
			
		});
		session.getMessageService().addMessageListener(new MessageListener() {

			public void messageArrived(IncomingMessage incommingMessage) {
				System.out.println("MessageArrived, from user: "+incommingMessage.getUin());
				System.out.println("MessageBody: "+incommingMessage.getMessageBody());
				System.out.println("MessageID: "+incommingMessage.getMessageID());
				System.out.println("MessageStatus: "+incommingMessage.getMessageClass());
				System.out.println("MessageTime: "+incommingMessage.getMessageDate());
			}

			public void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus) {
				System.out.println("MessageDelivered, fromUser: "+String.valueOf(uin));
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
		
 		session.getConnectionService().connect();
		session.getLoginService().login();
		
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
		PublicDirSearchQuery publicDirQuery = new PublicDirSearchQuery();
		publicDirQuery.setCity("Szczecin");
		publicDirQuery.setGender(Gender.FEMALE);
		publicDirQuery.setActiveOnly(new Boolean(true));
		
		session.getPublicDirectoryService().search(publicDirQuery);
		
//		loginContext.setPassword("dupadupa");
//		session.getLoginService().login();
		
//		IStatus status = session.getPresenceService().getStatus();
//		status.setFriendsOnly(true);
//		session.getPresenceService().setStatus(status);
//		
		session.getMessageService().sendMessage(OutgoingMessage.createMessage(376798, String.valueOf(System.currentTimeMillis())));
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

		session.getLoginService().logout();
		session.getConnectionService().disconnect();
	}
	
}
