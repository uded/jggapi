import java.io.IOException;

import pl.mn.communicator.ConnectionListener;
import pl.mn.communicator.GGException;
import pl.mn.communicator.IServer;
import pl.mn.communicator.ISession;
import pl.mn.communicator.IStatus;
import pl.mn.communicator.IUser;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.LoginListener;
import pl.mn.communicator.MessageArrivedEvent;
import pl.mn.communicator.MessageDeliveredEvent;
import pl.mn.communicator.MessageListener;
import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.Server;
import pl.mn.communicator.Status;
import pl.mn.communicator.StatusConst;
import pl.mn.communicator.UserListener;
import pl.mn.communicator.gadu.handlers.Session;

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
		final LoginContext loginContext = new LoginContext(1336843, "dupadupa");
		IServer server = Server.getDefaultServer(loginContext);
		final ISession session = new Session(server);

		session.getConnectionService().addConnectionListener(new ConnectionListener.Stub() {

			public void connectionEstablished() {
				System.out.println("Connection established.");
				try {
					session.getLoginService().login(loginContext);
				} catch (GGException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				IStatus status = new Status(StatusConst.ONLINE);
				try {
					session.getPresenceService().setStatus(status);
//					status.setStatus(StatusConst.BUSY_WITH_DESCRIPTION);
//					status.setDescription("busy with desc");
//					Calendar cal = Calendar.getInstance();
//					cal.set(2004, 2, 3);
//					status.setReturnDate(cal.getTime());
//					System.out.println("Time: "+status.getReturnDate());
//					session.getPresenceService().setStatus(status);
				} catch (GGException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			public void loginFailed() {
				System.out.println("Login Failed.");
			}
			
		});
		session.getPresenceService().addUserListener(new UserListener() {

			public void userStatusChanged(IUser user, IStatus newStatus) {
				System.out.println("User changed status: "+user.getUin());
				System.out.println("Status: "+newStatus.getStatus());
				System.out.println("Description: "+newStatus.getDescription());
				System.out.println("ReturnDate: "+newStatus.getReturnDate());
			}
			
		});
		session.getMessageService().addMessageListener(new MessageListener() {

			public void messageArrived(MessageArrivedEvent messageArrivedEvent) {
				System.out.println("MessageArrived, from user: "+messageArrivedEvent.getMessage().getUin());
				System.out.println("MessageBody: "+messageArrivedEvent.getMessage().getText());
				System.out.println("MessageID: "+messageArrivedEvent.getMessage().getMessageID());
				System.out.println("MessageStatus: "+messageArrivedEvent.getMessage().getMessageClass());
				System.out.println("MessageTime: "+messageArrivedEvent.getMessage().getMessageDate());
			}

			public void messageDelivered(MessageDeliveredEvent messageDeliveredEvent) {
				System.out.println("MessageDelivered, messageID: "+messageDeliveredEvent.getMessageID());
				System.out.println("MessageDelivered, fromUser: "+messageDeliveredEvent.getRecipient().getUin());
				System.out.println("MessageDelivered, messageStatus: "+messageDeliveredEvent.getDeliveryStatus());
			}
			
		});

		session.getConnectionService().connect();
		session.getMessageService().sendMessage(OutgoingMessage.createMessage(376798, String.valueOf(System.currentTimeMillis())));
		session.getMessageService().sendMessage(OutgoingMessage.createMessage(376798, String.valueOf(System.currentTimeMillis())));
		session.getMessageService().sendMessage(OutgoingMessage.createMessage(376798, String.valueOf(System.currentTimeMillis())));
		session.getMessageService().sendMessage(OutgoingMessage.createMessageWithoutConfirmation(376798, String.valueOf(System.currentTimeMillis())));
		session.getLoginService().logout();
		session.getConnectionService().disconnect();
	}
	
}
