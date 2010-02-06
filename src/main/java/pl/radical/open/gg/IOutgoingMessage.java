/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg;

public interface IOutgoingMessage extends IMessage {

	/**
	 * Use this method if you want to set new message body on this message.
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
