/*
 * Created on 2003-03-25
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package pl.mn.communicator.gui;

import java.io.IOException;

import org.eclipse.jface.preference.PreferenceStore;

/**
 * @author mnaglik
 */
public class Config {
	/*
	 * Ladowanie preferenceStore uzywanego
	 * w zakladkach do pobierania
	 * danych konfiguracyjnych
	 */
	private static PreferenceStore configStore =
		new PreferenceStore("config.cfg");
	static {
		try {
			configStore.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static PreferenceStore getPreferenceStore() {
		return configStore;
	}
}
