package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.packet.GGPacket;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface GGOutgoingPackage extends GGPacket {

	@Deprecated
	int getLength();

	byte[] getContents();

}
