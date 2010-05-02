package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.PersonalInfo;
import pl.radical.open.gg.PublicDirSearchReply;
import pl.radical.open.gg.packet.in.GGPubdirReply;
import pl.radical.open.gg.utils.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2004-12-15
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGPubdirReplyPacketHandler implements PacketHandler {
	private static final Logger log = LoggerFactory.getLogger(GGPubdirReplyPacketHandler.class);

	/**
	 * @see pl.radical.open.gg.packet.handlers.PacketHandler#handle(pl.radical.open.gg.packet.handlers.Context)
	 */
	public void handle(final PacketContext context) throws GGException {
		if (log.isDebugEnabled()) {
			log.debug("Received GGPubdirReply packet.");
			log.debug("PacketHeader: " + context.getHeader());
			log.debug("PacketBody: " + GGUtils.prettyBytesToString(context.getPackageContent()));
		}

		final GGPubdirReply pubdirReply = new GGPubdirReply(context.getPackageContent());
		final int querySeq = pubdirReply.getQuerySeq();

		if (pubdirReply.isPubdirReadReply()) {
			final PersonalInfo publicDirInfo = pubdirReply.getPubdirReadReply();
			context.getSessionAccessor().notifyPubdirRead(querySeq, publicDirInfo);
		} else if (pubdirReply.isPubdirWriteReply()) {
			context.getSessionAccessor().notifyPubdirUpdated(querySeq);
		} else if (pubdirReply.isPubdirSearchReply()) {
			final PublicDirSearchReply pubDirSearchReply = pubdirReply.getPubdirSearchReply();
			context.getSessionAccessor().notifyPubdirGotSearchResults(querySeq, pubDirSearchReply);
		}
	}

}
