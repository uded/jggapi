package pl.radical.open.gg.packet.in;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface IncomingPacket {
	int type();

	Class handler();
}
