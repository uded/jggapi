package pl.radical.open.gg;

/**
 * Class that represents local user status.
 * <p>
 * Created on 2004-12-21
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface ILocalStatus extends IStatus {

	/**
	 * Flag indicating whether or not we want to be visible only for friends
	 * 
	 * @return whether or not we want to be visible only for friends.
	 */
	boolean isFriendsOnly();

	void setFriendsOnly(boolean isFriendsOnly);

}
