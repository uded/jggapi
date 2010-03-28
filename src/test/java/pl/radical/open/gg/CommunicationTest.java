package pl.radical.open.gg;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.radical.open.gg.event.MessageListener;

public class CommunicationTest {
	
	static boolean asyncOp = false;

	private ISession session1;
	private ISession session2;
	
	private int user1 = 20239471;
	private int user2 = 20241237;

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	@Before
	public void setUpSession() {
		this.session1 = ConnectionTest.session1;
		this.session2 = ConnectionTest.session2;
			
	}
	
	@Test(timeout = 1000 * 30)
	public void sendRecvMsgTest() throws GGException, InterruptedException {
	
		final String hello = "hello hello hello";
		asyncOp = false;
		
		IMessageService messageService1 = session1.getMessageService();
		IMessageService messageService2 = session2.getMessageService();
		
		OutgoingMessage outMessage = OutgoingMessage.createNewMessage(user2, hello);
		log.debug("Sending message ["+hello+"] to "+user2);
		messageService1.sendMessage(outMessage);
		
		messageService2.addMessageListener(new MessageListener(){

			@Override
            public void messageArrived(IIncommingMessage incommingMessage) {
				log.info("Message ["+incommingMessage.getMessageBody()+"] received from "+incommingMessage.getRecipientUin());
				assertEquals(user1, incommingMessage.getRecipientUin());
				assertEquals(hello,incommingMessage.getMessageBody());
				CommunicationTest.asyncOp = true;
            }

			@Override
            public void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus) {
	            // TODO Auto-generated method stub
	            
            }

			@Override
            public void messageSent(IOutgoingMessage outgoingMessage) {
	            // TODO Auto-generated method stub
	            
            }});

		while (!asyncOp) {
			Thread.sleep(100);
		}
		asyncOp = false;

		
	}
}
