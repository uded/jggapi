package pl.radical.open.gg.packet;

/**
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
public interface GGOutgoingPackage extends GGPacket {

	@Deprecated
	int getLength();

	byte[] getContents();

}
