package pl.radical.open.gg;

import pl.radical.open.gg.event.PublicDirListener;
import pl.radical.open.gg.packet.dicts.SessionState;
import pl.radical.open.gg.packet.out.GGPubdirRequest;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * The default implementation of <code>IPublicDirectoryService</code>.
 * <p>
 * Created on 2004-12-14
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class DefaultPublicDirectoryService implements IPublicDirectoryService {

	private final Set<PublicDirListener> m_directoryListeners = new HashSet<PublicDirListener>();

	private Session m_session = null;

	// friendly
	DefaultPublicDirectoryService(final Session session) {
		Preconditions.checkNotNull(session, new IllegalArgumentException("session cannot be null"));
		m_session = session;
	}

	/**
	 * @see pl.radical.open.gg.IPublicDirectoryService#search(pl.radical.open.gg.PublicDirSearchQuery)
	 */
	@Override
	public void search(final PublicDirSearchQuery publicDirQuery) throws GGException {
		Preconditions.checkNotNull(publicDirQuery, new IllegalArgumentException("publicDirQuery cannot be null"));
		checkSessionState();
		try {
			final GGPubdirRequest pubdirRequest = GGPubdirRequest.createSearchPubdirRequest(publicDirQuery);
			m_session.getSessionAccessor().sendPackage(pubdirRequest);
		} catch (final IOException ex) {
			throw new GGException("Unable to perform search.", ex);
		}
	}

	/**
	 * * @see pl.radical.open.gg.IPublicDirectoryService#readFromPublicDirectory()
	 */
	@Override
	public void readFromPublicDirectory() throws GGException {
		checkSessionState();
		try {
			final GGPubdirRequest pubdirRequest = GGPubdirRequest.createReadPubdirRequest();
			m_session.getSessionAccessor().sendPackage(pubdirRequest);
		} catch (final IOException ex) {
			throw new GGException("Unable to read information from public directory.", ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPublicDirectoryService#writeToPublicDirectory(pl.radical.open.gg.PersonalInfo)
	 */
	@Override
	public void writeToPublicDirectory(final PersonalInfo publicDirInfo) throws GGException {
		Preconditions.checkNotNull(publicDirInfo, new IllegalArgumentException("publicDirInfo cannot be null"));
		checkSessionState();
		try {
			final GGPubdirRequest pubdirRequest = GGPubdirRequest.createWritePubdirRequest(publicDirInfo);
			m_session.getSessionAccessor().sendPackage(pubdirRequest);
		} catch (final IOException ex) {
			throw new GGException("Unable to write or update information in public directory.", ex);
		}
	}

	/**
	 * @see pl.radical.open.gg.IPublicDirectoryService#addPublicDirListener(pl.radical.open.gg.event.PublicDirListener)
	 */
	@Override
	public void addPublicDirListener(final PublicDirListener publicDirListener) {
		Preconditions.checkNotNull(publicDirListener, new IllegalArgumentException("publicDirListener cannot be null"));
		m_directoryListeners.add(publicDirListener);
	}

	/**
	 * @see pl.radical.open.gg.IPublicDirectoryService#removePublicDirListener(pl.radical.open.gg.event.PublicDirListener)
	 */
	@Override
	public void removePublicDirListener(final PublicDirListener publicDirListener) {
		Preconditions.checkNotNull(publicDirListener, new IllegalArgumentException("pubDirListener cannot be null"));
		m_directoryListeners.remove(publicDirListener);
	}

	protected void notifyPubdirRead(final int queryID, final PersonalInfo publicDirInfo) {
		Preconditions.checkNotNull(publicDirInfo, new IllegalArgumentException("publicDirInfo cannot be null"));
		for (final Object element : m_directoryListeners) {
			final PublicDirListener publicDirListener = (PublicDirListener) element;
			publicDirListener.onPublicDirectoryRead(queryID, publicDirInfo);
		}
	}

	protected void notifyPubdirUpdated(final int queryID) {
		for (final Object element : m_directoryListeners) {
			final PublicDirListener publicDirListener = (PublicDirListener) element;
			publicDirListener.onPublicDirectoryUpdated(queryID);
		}
	}

	protected void notifyPubdirGotSearchResults(final int queryID, final PublicDirSearchReply publicDirSearchReply) {
		Preconditions.checkNotNull(publicDirSearchReply, new IllegalArgumentException("publicDirSearchReply cannot be null"));
		for (final Object element : m_directoryListeners) {
			final PublicDirListener publicDirListener = (PublicDirListener) element;
			publicDirListener.onPublicDirectorySearchReply(queryID, publicDirSearchReply);
		}
	}

	private void checkSessionState() throws GGSessionException {
		if (m_session.getSessionState() != SessionState.LOGGED_IN) {
			throw new GGSessionException(m_session.getSessionState());
		}
	}

}
