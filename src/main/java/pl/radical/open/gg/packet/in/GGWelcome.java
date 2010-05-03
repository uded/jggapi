package pl.radical.open.gg.packet.in;

import pl.radical.open.gg.packet.AbstractGGIncomingPacket;
import pl.radical.open.gg.packet.GGIncomingPackage;
import pl.radical.open.gg.packet.IncomingPacket;
import pl.radical.open.gg.packet.handlers.GGWelcomePacketHandler;
import pl.radical.open.gg.utils.GGUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The packet is retrieved from the Gadu-Gadu server just after we connect to it. The class parses package and gets seed
 * from server.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
@IncomingPacket(type = 0x0001, label = "GG_WELCOME", handler = GGWelcomePacketHandler.class)
public class GGWelcome extends AbstractGGIncomingPacket implements GGIncomingPackage {
	private static final Logger LOGGER = LoggerFactory.getLogger(GGWelcome.class);

	private int seed = -1;

	public GGWelcome(final byte[] data) {
		if (data == null) {
			LOGGER.error("Was expecting a data in constructor, got null");
			throw new IllegalArgumentException("data cannot be null");
		}

		seed = GGUtils.byteToInt(data);
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Seed to be used in this connection: {}", seed);
		}
	}

	public int getSeed() {
		return seed;
	}

}
