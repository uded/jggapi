/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.gadu;

import pl.mn.communicator.IMessage;

/**
 * @version $Revision: 1.7 $
 * @author mnaglik
 */
class GGSendMsg implements GGOutgoingPackage {
	private int user;
	private String text;

	private static int seqNo;
	private int seq;
	private int msgClass = 0x0004;

	public GGSendMsg(IMessage message) {
		this.user = message.getUser();
		this.text = message.getText();
		seq = seqNo++;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getHeader()
	 */
	public int getHeader() {
		return 0x0b;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 12+text.length()+1;
	}

	/**
	 * @see pl.mn.communicator.gadu.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		byte [] toSend = new byte[12+text.length()+1];

		toSend[3] = (byte) (user >> 24 & 0xFF);
		toSend[2] = (byte) (user >> 16 & 0xFF);
		toSend[1] = (byte) (user >> 8 & 0xFF);
		toSend[0] = (byte) (user & 0xFF);

		toSend[7] = (byte) (seq >> 24 & 0xFF);
		toSend[6] = (byte) (seq >> 16 & 0xFF);
		toSend[5] = (byte) (seq >> 8 & 0xFF);
		toSend[4] = (byte) (seq & 0xFF);

		toSend[11]= (byte) (msgClass >> 24 & 0xFF);
		toSend[10]= (byte) (msgClass >> 16 & 0xFF);
		toSend[9] = (byte) (msgClass >> 8 & 0xFF);
		toSend[8] = (byte) (msgClass & 0xFF);
		
		byte [] textBytes = text.getBytes(); 
		for (int i=0; i<text.length(); i++)
			toSend[12+i] = textBytes[i];

		return toSend;
	}
}
