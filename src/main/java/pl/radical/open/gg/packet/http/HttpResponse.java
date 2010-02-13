package pl.radical.open.gg.packet.http;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public abstract class HttpResponse {

	public abstract boolean isOKResponse();

	public abstract String getResponseMessage();

}
