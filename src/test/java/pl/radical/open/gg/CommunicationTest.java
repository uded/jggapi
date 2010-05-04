package pl.radical.open.gg;

import static org.junit.Assert.assertEquals;
import static pl.radical.open.gg.AlljGGapiTest.TEST_UIN_1;
import static pl.radical.open.gg.AlljGGapiTest.TEST_UIN_2;

import pl.radical.open.gg.event.MessageListener;
import pl.radical.open.gg.packet.dicts.MessageStatus;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommunicationTest {

	static boolean asyncOp = false;

	private ISession session1;
	private ISession session2;

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Before
	public void setUpSession() {
		session1 = ConnectionTest.session1;
		session2 = ConnectionTest.session2;

	}

	@Test(timeout = 1000 * 60)
	public void sendRecvMsgTest() throws GGException, InterruptedException {

		ConnectionTest.COMMUNICATION_LATCH.await();

		final String hello = "hello hello hello";
		asyncOp = false;

		final IMessageService messageService1 = session1.getMessageService();
		final IMessageService messageService2 = session2.getMessageService();

		final OutgoingMessage outMessage = OutgoingMessage.createNewMessage(TEST_UIN_2, hello);
		log.debug("Sending message [{}] to ", "hello", TEST_UIN_2);
		if (session1.getLoginService().isLoggedIn()) {
			messageService1.sendMessage(outMessage);

			messageService2.addMessageListener(new MessageListener(){

				@Override
				public void messageArrived(final IIncommingMessage incommingMessage) {
					log.info("Message ["+incommingMessage.getMessageBody()+"] received from "+incommingMessage.getRecipientUin());
					assertEquals(TEST_UIN_1, incommingMessage.getRecipientUin());
					assertEquals(hello,incommingMessage.getMessageBody());
					CommunicationTest.asyncOp = true;
				}

				@Override
				public void messageDelivered(final int uin, final int messageID, final MessageStatus deliveryStatus) {
					// TODO Auto-generated method stub

				}

				@Override
				public void messageSent(final IOutgoingMessage outgoingMessage) {
					// TODO Auto-generated method stub

				}});
		}

		while (!asyncOp) {
			Thread.sleep(100);
		}
		asyncOp = false;


	}
}
