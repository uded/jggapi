/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.gui.config;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * @version $Revision: 1.5 $
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
