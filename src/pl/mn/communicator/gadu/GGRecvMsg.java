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


/**
 * Wiadomosc otrzymana z serwera gg (tekstowa)
 * @version $Revision: 1.9 $
 * @author mnaglik
 */
class GGRecvMsg implements GGIncomingPackage {
    private long sender;
    private int seq;
    private int time;
    private int msgClass;
    private String message = "";

    public GGRecvMsg(byte[] data) {
        this.sender = GGConversion.unsignedIntToLong(GGConversion.byteToInt(
                    data));
        this.seq = GGConversion.byteToInt(data, 4);
        this.time = GGConversion.byteToInt(data, 8);
        this.msgClass = GGConversion.byteToInt(data, 12);

        message = GGConversion.byteToString(data,16);;
    }

    /**
     * Returns the message.
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the msgClass.
     * @return int
     */
    public int getMsgClass() {
        return msgClass;
    }

    /**
     * Returns the sender.
     * @return int
     */
    public int getSender() {
        return (int) sender;
    }

    /**
     * Returns the seq.
     * @return int
     */
    public int getSeq() {
        return seq;
    }

    /**
     * Returns the time.
     * @return int
     */
    public int getTime() {
        return time;
    }
}
