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

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import pl.mn.communicator.gui.util.Config;

/**
 * @version $Revision: 1.5 $
 * @author mnaglik
 */
public class ConfigUserPage extends PreferencePage {
	private String userNumber;
	private String userPassword;

	private Text userNumberText;
	private Text userPasswordText;
	public ConfigUserPage() throws IOException {
		super();
		setPreferenceStore(Config.getPreferenceStore());
		PreferenceStore store = (PreferenceStore) getPreferenceStore();

		userNumber = store.getString("user");
		userPassword = store.getString("password");
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {

		Composite comp = new Composite(parent, SWT.NORMAL);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;

		comp.setLayout(gridLayout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalAlignment = GridData.HORIZONTAL_ALIGN_CENTER;
		Label label1 = new Label(comp, SWT.NORMAL);
		label1.setLayoutData(gd);
		label1.setText("Nr u¿ytkownika:");

		gd = new GridData(GridData.FILL_HORIZONTAL);
		userNumberText = new Text(comp, SWT.BORDER);
		userNumberText.setLayoutData(gd);
		userNumberText.setText(userNumber);
		userNumberText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				ConfigUserPage.this.userNumber =
					ConfigUserPage.this.userNumberText.getText();
			}
		});

		Label label2 = new Label(comp, SWT.NORMAL);
		label2.setText("Has³o:");

		gd = new GridData(GridData.FILL_HORIZONTAL);
		userPasswordText = new Text(comp, SWT.BORDER);
		userPasswordText.setLayoutData(gd);
		userPasswordText.setEchoChar('*');
		userPasswordText.setText(userPassword);
		userPasswordText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				ConfigUserPage.this.userPassword =
					ConfigUserPage.this.userPasswordText.getText();
			}
		});

		return comp;

	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#getTitle()
	 */
	public String getTitle() {
		return "U¿ytkownik";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		try {
			PreferenceStore store = (PreferenceStore) getPreferenceStore();
			store.setValue("user", userNumber);
			store.setValue("password", userPassword);

			store.save();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
