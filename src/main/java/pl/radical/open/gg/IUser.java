package pl.radical.open.gg;

/**
 * Interface that represents Gadu-Gadu user.
 * <p>
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface IUser {

	IUser[] EMPTY_ARRAY = new IUser[0];

	/**
	 * Gets uin of the Gadu-Gadu user.
	 * 
	 * @return the uin of Gadu-Gadu user.
	 */
	int getUin();

	/**
	 * Gets userMode of this Gadu-Gadu user.
	 * 
	 * @return the <code>User.UserMode</code> of this Gadu-Gadu user.
	 */
	User.UserMode getUserMode();

}
