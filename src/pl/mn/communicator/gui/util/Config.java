package pl.mn.communicator.gui.util;

import java.io.IOException;

import org.eclipse.jface.preference.PreferenceStore;

/**
 * @version $Revision: 1.2 $
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
