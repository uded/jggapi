package pl.mn.communicator.gui;

import java.io.IOException;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Administrator
 */
public class ConfigForm extends PreferenceDialog {
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
			System.err.println("e: " + e);
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
