package pl.radical.open.gg.packet.handlers;

import pl.radical.open.gg.GGException;

/**
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface PacketHandler {

	/**
	 * Handles an incoming Gadu-Gadu packet.
	 * 
	 * @param context
	 */
	void handle(PacketContext context) throws GGException;

}
