/*
 * Created on 2004-10-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.IOException;

import pl.mn.communicator.ConnectionFactory;
import pl.mn.communicator.ConnectionListener;
import pl.mn.communicator.ISession;
import pl.mn.communicator.IMessage;
import pl.mn.communicator.IStatus;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.MessageArrivedEvent;
import pl.mn.communicator.MessageDeliveredEvent;
import pl.mn.communicator.MessageListener;
import pl.mn.communicator.MessageSentEvent;
import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.Status;

/**
 * @author mateusz
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Main {
	
	public static void main(String args[]) throws IOException {
        
		LoginContext loginContext = new LoginContext(1336843, "dupadupa");
		IStatus initialStatus = new Status(IStatus.BUSY);
		loginContext.setStatus(initialStatus);
		loginContext.setImageSize(32);
		
		ISession con = ConnectionFactory.createConnection(loginContext);
		
		con.addConnectionListener(new ConnectionListener() {
			
			public void connectionEstablished() {
				System.out.println("Connected.");
			}
			
			public void connectionClosed() {
				System.out.println("Disconnected.");
			}
			
			public void connectionError(Exception ex) {
				System.out.println("Unable to connect: " + ex.getMessage());
			}
		});

		con.addMessageListener(new MessageListener() {

			public void messageArrived(MessageArrivedEvent arrivedEvent) {
				System.out.println("Message: "+arrivedEvent.getMessage().getText());
				System.out.println("From user: "+arrivedEvent.getMessage().getUser());
				System.out.println("MessageID: "+arrivedEvent.getMessage().getMessageID());
				System.out.println("MessageClass: "+arrivedEvent.getMessage().getMessageClass());
				System.out.println("MessageTime: "+arrivedEvent.getMessage().getMessageDate());
			}
			
			public void messageSent(MessageSentEvent sentEvent) {
				System.out.println("Message was sent to: "+sentEvent.getMessage().getUser());
			}

			public void messageDelivered(MessageDeliveredEvent messageSentEvent) {
				System.out.println("Message was delivered to: "+messageSentEvent.getRecipient().getNumber());
				System.out.println("Message status: "+messageSentEvent.getMessageDeliveryStatus());
				System.out.println("MessageID: "+messageSentEvent.getMessageID());
			}

		});
		con.connect();
		IMessage message1 = new OutgoingMessage(376798, "test");
		con.sendMessage(message1);
		
		IMessage message2 = OutgoingMessage.createMessageWithoutConfirmation(376798, "test2");
		con.sendMessage(message2);

		IMessage message3 = OutgoingMessage.createPingMessage(376798);
		con.sendMessage(message3);

		IStatus currentStatus = con.getStatus();

//		currentStatus.setStatus(IStatus.ONLINE);
//		con.setStatus(currentStatus);

		currentStatus.setStatus(IStatus.BUSY);
		currentStatus.setFriendsOnly(true);
		con.setStatus(currentStatus);
		
		currentStatus.setStatus(IStatus.ONLINE_WITH_DESCRIPTION);
		currentStatus.setDescription("Online.");
		currentStatus.setFriendsOnly(false);
		con.setStatus(currentStatus);

		currentStatus.setStatus(IStatus.BUSY_WITH_DESCRIPTION);
		currentStatus.setDescription("jestem zajety");
		con.setStatus(currentStatus);
		
		currentStatus.setStatus(IStatus.INVISIBLE_WITH_DESCRIPTION);
		currentStatus.setDescription("jestem niewidoczny");
		con.setStatus(currentStatus);

		currentStatus.setStatus(IStatus.OFFLINE_WITH_DESCRIPTION);
		currentStatus.setDescription("Offline with description");
		con.setStatus(currentStatus);

		con.disconnect();
	}
	
}