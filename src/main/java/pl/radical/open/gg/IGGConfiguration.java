package pl.radical.open.gg;

/**
 * Created on 2005-05-08
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface IGGConfiguration {

	int getPingIntervalInMiliseconds();

	int getSocketTimeoutInMiliseconds();

	int getConnectionThreadSleepTimeInMiliseconds();

	String getServerLookupURL();

	String getRegistrationURL();

	String getTokenRequestURL();

	String getSendPasswordURL();

}
