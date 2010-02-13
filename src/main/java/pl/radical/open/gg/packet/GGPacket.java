package pl.radical.open.gg.packet;

/**
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface GGPacket {

	/**
	 * Returns Gadu-Gadu packet type.
	 * 
	 * @return constant specyfing Gadu-Gadu packet type.
	 */
	int getPacketType();

}
