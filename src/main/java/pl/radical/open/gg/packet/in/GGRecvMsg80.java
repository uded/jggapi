package pl.radical.open.gg.packet.in;

import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ByteList;

import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.dicts.GGMessageClass;
import pl.radical.open.gg.packet.handlers.GGRecvMsg80PacketHandler;
import pl.radical.open.gg.utils.GGUtils;

/**
 * Created on 2010-03-18
 * 
 * @author <a href="mailto:klacia.85@gmail.com">Kamil Klatkowski</a>
 * @since 1.7.0
 */

@IncomingPacket(type = 0x002E, label = "GG_RECV_MSG80", handler = GGRecvMsg80PacketHandler.class)
public class GGRecvMsg80 extends GGBaseIncomingPacket implements GGIncomingPackage, GGMessageClass {

    private int sender = -1;
    private int seq = -1;
    private long time = -1;
    private int msgClass = -1;
    private int offsetPlain = -1;
    private int offsetAttributes = -1 ;
    private String htmlMessage = null;				/* message in HTML format */
    private String plainMessage = null;				/* message in plain text */
    private byte[] attributes = null;				/* messages's attributes */

    public GGRecvMsg80(byte[] data) {
        sender = GGUtils.byteToInt(data);
        seq = GGUtils.byteToInt(data, 4);
        time = GGUtils.secondsToMillis(GGUtils.byteToInt(data, 8));
        msgClass = GGUtils.byteToInt(data, 12);
        offsetPlain = GGUtils.byteToInt(data,16);
        offsetAttributes = GGUtils.byteToInt(data,20);
        
        if(getOffsetAttributes()==data.length){
        	offsetAttributes = 0;
        }else{
        	final ByteList byteList = new ArrayByteList(data.length-offsetAttributes);
        	for(int i = offsetAttributes; i < data.length ; i++){
        		byteList.add(data[i]);
        	}
        	attributes = byteList.toArray();
        }
                
        if(data.length > offsetPlain)
        	plainMessage = GGUtils.byteToString(data,offsetPlain);
        
        //FIXME no htmlMessage
    }
        
    /**
     * Returns the msgClass.
     * @return int msgClass
     */
    public int getMsgClass() {
        return msgClass;
    }

    /**
     * Returns the sender uin number.
     * @return int the sender uin.
     */
    public int getSenderUin() {
        return sender;
    }

    /**
     * Returns the unique message sequence number.
     * @return int message sequence number.
     */
    public int getMessageSeq() {
        return seq;
    }

    /**
     * Time in seconds.
     * @return int the time in seconds.
     */
    public long getTime() {
        return time;
    }

	public int getOffsetPlain() {
		return offsetPlain;
	}

	public void setOffsetPlain(int mOffsetPlain) {
		offsetPlain = mOffsetPlain;
	}

	public int getOffsetAttributes() {
		return offsetAttributes;
	}

	public void setOffsetAttributes(int mOffsetAttributes) {
		offsetAttributes = mOffsetAttributes;
	}

	public String getHtmlMessage() {
		return htmlMessage;
	}

	public void setHtmlMessage(String mHtmlMessage) {
		htmlMessage = mHtmlMessage;
	}

	public String getPlainMessage() {
		return plainMessage;
	}

	public void setPlainMessage(String mPlainMessage) {
		plainMessage = mPlainMessage;
	}

	public byte[] getAttributes() {
		return attributes;
	}

	public void setAttributes(byte[] mAttributes) {
		attributes = mAttributes;
	}
    

}
