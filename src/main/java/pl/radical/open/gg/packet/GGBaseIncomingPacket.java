package pl.radical.open.gg.packet;

public abstract class GGBaseIncomingPacket implements GGIncomingPackage {
	public int getPacketType() {
		return this.getClass().getAnnotation(IncomingPacket.class).type();
	}
}
