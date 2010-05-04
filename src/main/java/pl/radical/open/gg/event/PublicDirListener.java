package pl.radical.open.gg.event;

import pl.radical.open.gg.PersonalInfo;
import pl.radical.open.gg.PublicDirSearchReply;

import java.util.EventListener;

/**
 * The listener interface that is related to Gadu-Gadu's public directory service.
 * <p>
 * The class that implement this interface, aka PublicDirHandler is notified whether the search request was received or
 * the Gadu-Gadu user's personal information has been successfully read from the public directory or written to public
 * directory.
 * <p>
 * Created on 2004-12-15
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface PublicDirListener extends EventListener {

	/**
	 * Messaged when search results arrived from Gadu-Gadu server after sending query.
	 * 
	 * @param queryID
	 *            the id of the query that has been previously created.
	 * @param publicDirSearchReply
	 *            the object that represents Gadu-Gadu's server reply.
	 */
	void onPublicDirectorySearchReply(int queryID, PublicDirSearchReply dirSearchReply);

	/**
	 * Messaged when we have successfully retrieved our personal information from the Gadu-Gadu's public directory.
	 * 
	 * @param queryID
	 *            the id of the query that has been previously created.
	 * @param personalInfo
	 *            the user's that is currently logged in personal information.
	 */
	void onPublicDirectoryRead(int queryID, PersonalInfo personalInfo);

	/**
	 * Messaged when user's personal information has been successfully written to the Gadu-Gadu's public directory.
	 * 
	 * @param queryID
	 *            the id of the query that has been previously created.
	 */
	void onPublicDirectoryUpdated(int queryID);

	class Stub implements PublicDirListener {

		/**
		 * @see pl.radical.open.gg.event.PublicDirListener#onPublicDirectoryUpdated(int)
		 */
		public void onPublicDirectoryUpdated(final int queryID) {
		}

		/**
		 * @see pl.radical.open.gg.event.PublicDirListener#onPublicDirectorySearchReply(int, PublicDirSearchReply)
		 */
		public void onPublicDirectorySearchReply(final int queryID, final PublicDirSearchReply dirSearchReply) {
		}

		/**
		 * @see pl.radical.open.gg.event.PublicDirListener#onPublicDirectoryRead(int, PersonalInfo)
		 */
		public void onPublicDirectoryRead(final int queryID, final PersonalInfo pubDirReply) {
		}

	}

}
