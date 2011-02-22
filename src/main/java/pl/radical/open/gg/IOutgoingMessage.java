package pl.radical.open.gg;

public interface IOutgoingMessage extends IMessage {

	/**
	 * <p>Use this method if you want to set new message body on this message.</p>
	 * <p>The message length should not exceed 1989 characters.</p>
	 * 
	 * @param messageBody
	 *            the new message body.
	 * @throws NullPointerException
	 *             if the messageBody object is null.
	 */
	void setMessageBody(String messageBody);

	/**
	 * Use this method if you want to set new uin on this message.
	 * 
	 * @param uin
	 *            the new Gadu-Gadu number to whom this message will be addressed.
	 * @throws IllegalArgumentException
	 *             if the uin is a negative value.
	 */
	void setRecipientUin(int recipientUin);

	void addAdditionalRecipient(int recipientUin);

	void removeAdditionalRecipient(int recipientUin);

	int[] getAdditionalRecipients();

	int[] getAllRecipients();

}
