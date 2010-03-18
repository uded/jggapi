package pl.radical.open.gg.packet.in;

import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ByteList;

import pl.radical.open.gg.packet.GGBaseIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.dicts.GGMessageClass;
import pl.radical.open.gg.packet.handlers.GGRecvMsg80PacketHandler;
import pl.radical.open.gg.utils.GGUtils;

@IncomingPacket(type = 0x002E, label = "GG_RECV_MSG80", handler = GGRecvMsg80PacketHandler.class)
public class GGRecvMsg80 extends GGBaseIncomingPacket implements GGIncomingPackage, GGMessageClass {

    private int m_sender = -1;
    private int m_seq = -1;
    private long m_time = -1;
    private int m_msgClass = -1;
    private int m_offsetPlain = -1;
    private int m_offsetAttributes = -1 ;
    private String m_htmlMessage = null;				/* message in HTML format */
    private String m_plainMessage = null;				/* message in plain text */
    private byte[] m_attributes = null;				/* messages's attributes */

    public GGRecvMsg80(byte[] data) {
        m_sender = GGUtils.byteToInt(data);
        m_seq = GGUtils.byteToInt(data, 4);
        m_time = GGUtils.secondsToMillis(GGUtils.byteToInt(data, 8));
        m_msgClass = GGUtils.byteToInt(data, 12);
        m_offsetPlain = GGUtils.byteToInt(data,16);
        m_offsetAttributes = GGUtils.byteToInt(data,20);
        
        if(getOffsetAttributes()==data.length){
        	m_offsetAttributes = 0;
        }else{
        	final ByteList byteList = new ArrayByteList(data.length-m_offsetAttributes);
        	for(int i = m_offsetAttributes; i < data.length ; i++){
        		byteList.add(data[i]);
        	}
        }
        
        if(data.length > getOffsetPlain())
        	m_plainMessage = GGUtils.byteToString(data,m_offsetPlain);
        
        //FIXME no htmlMessage
    }
        
    /**
     * Returns the msgClass.
     * @return int msgClass
     */
    public int getMsgClass() {
        return m_msgClass;
    }

    /**
     * Returns the sender uin number.
     * @return int the sender uin.
     */
    public int getSenderUin() {
        return m_sender;
    }

    /**
     * Returns the unique message sequence number.
     * @return int message sequence number.
     */
    public int getMessageSeq() {
        return m_seq;
    }

    /**
     * Time in seconds.
     * @return int the time in seconds.
     */
    public long getTime() {
        return m_time;
    }

	public int getOffsetPlain() {
		return m_offsetPlain;
	}

	public void setOffsetPlain(int mOffsetPlain) {
		m_offsetPlain = mOffsetPlain;
	}

	public int getOffsetAttributes() {
		return m_offsetAttributes;
	}

	public void setOffsetAttributes(int mOffsetAttributes) {
		m_offsetAttributes = mOffsetAttributes;
	}

	public String getHtmlMessage() {
		return m_htmlMessage;
	}

	public void setHtmlMessage(String mHtmlMessage) {
		m_htmlMessage = mHtmlMessage;
	}

	public String getPlainMessage() {
		return m_plainMessage;
	}

	public void setPlainMessage(String mPlainMessage) {
		m_plainMessage = mPlainMessage;
	}

	public byte[] getAttributes() {
		return m_attributes;
	}

	public void setAttributes(byte[] mAttributes) {
		m_attributes = mAttributes;
	}
    

}
