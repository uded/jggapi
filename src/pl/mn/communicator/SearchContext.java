/*
 * Created on 2004-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pl.mn.communicator;

/**
 * @author mateusz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchContext {

	private String m_uin = null;
	private String m_firstName = null;
	private String m_surName = null;
	private String m_nickName = null;
	private String m_birthYear = null;
	private Integer m_gener = null;  //1-female, 0-male
	private Boolean m_activeOnly = null;
	private String m_familyName = null;
	private String m_familyCity = null;
	
	/**
	 * @param only The m_activeOnly to set.
	 */
	public void setM_activeOnly(Boolean only) {
		m_activeOnly = only;
	}
	
	/**
	 * @return Returns the m_activeOnly.
	 */
	public boolean isActiveOnly() {
		return m_activeOnly;
	}
	
}
