package pl.mn.communicator.gui.config;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Shell;

/**
 * @author mnaglik
 */
public class ConfigForm extends PreferenceDialog {
	private static Logger logger = Logger.getLogger(ConfigForm.class);
	public ConfigForm(Shell shell, PreferenceManager preference) {
		super(shell, preference);
		setBlockOnOpen(true);
		addPreferenceNodes(preference);
	}

	/**
	 * Method addPreferenceNodes.
	 * @param preference
	 */
	private void addPreferenceNodes(PreferenceManager preference) {
		try {
			preference.addToRoot(
				new PreferenceNode("id0", new ConfigUserPage()));

			
			preference.addToRoot(
				new PreferenceNode("id1", new ConfigServerPage()));
			
			/*
			preference.addToRoot(
				new PreferenceNode("id2", new ConfigSoundPage()));
			*/
		} catch (IOException e) {
			logger.error(e);
		}
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Ustawienia");
		shell.setSize(500, 350);
	}

}
