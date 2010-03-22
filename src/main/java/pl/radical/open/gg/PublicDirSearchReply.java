package pl.radical.open.gg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class represents an object that is contructed as a result of a reply from Gadu-Gadu's public directory.
 * <p>
 * It holds the collection of entries, each entry represents a single matched Gadu-Gadu user. These entries can be
 * viewed through a public iterator.
 * <p>
 * Created on 2004-11-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class PublicDirSearchReply {

	private Integer nextStart = null;

	private final Collection<PublicDirSearchReply.Entry> m_publicDirSearchEntries = new ArrayList<Entry>();

	/**
	 * Method that creates a new entry and adds it to entries collection.
	 * 
	 * @return <code>PublicDirSearchReply.Entry</code> object
	 */
	public PublicDirSearchReply.Entry createSearchEntry() {
		final Entry entry = new Entry();
		m_publicDirSearchEntries.add(entry);
		return entry;
	}

	/**
	 * Returns an unmodifiable iterator over search results entries.
	 * 
	 * @return <code>Iterator</code> over search results entries.
	 */
	public Iterator<PublicDirSearchReply.Entry> listResults() {
		return new UnmodifiableIterator(m_publicDirSearchEntries.iterator());
	}

	/**
	 * Sets nextStart.
	 * 
	 * @param nextStart
	 */
	public void setNextStart(final Integer nextStart) {
		this.nextStart = nextStart;
	}

	/**
	 * Returns nextStart.
	 * 
	 * @return <code>Integer</code> value that holds nextStart.
	 */
	public Integer getNextStart() {
		return nextStart;
	}

	public static class Entry extends PublicDirSearch {

		private IStatus m_status = null;

		private Entry() {
			// private contructor
		}

		public void setStatus(final IStatus status) {
			m_status = status;
		}

		public IStatus getStatus() {
			return m_status;
		}

	}

	private static class UnmodifiableIterator implements Iterator<PublicDirSearchReply.Entry> {

		private final Iterator<PublicDirSearchReply.Entry> iterator;

		private UnmodifiableIterator(final Iterator<PublicDirSearchReply.Entry> iterator) {
			this.iterator = iterator;
		}

		/**
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return iterator.hasNext();
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		public PublicDirSearchReply.Entry next() {
			return iterator.next();
		}

		/**
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			throw new UnsupportedOperationException("this is read-only iterator");
		}

	}

}
