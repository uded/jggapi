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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import pl.mn.communicator.gui.util.Config;

/**
 * @version $Revision: 1.5 $
 * @author mnaglik
 */
public class ConfigServerPage extends PreferencePage {
	private String serverAddress;
	private String serverPort;
	private boolean serverStandard;
	private Text serverAddressText;
	private Text serverPortText;
	public ConfigServerPage() throws IOException {
		super();
		setPreferenceStore(Config.getPreferenceStore());
		PreferenceStore store = (PreferenceStore) getPreferenceStore();

		serverAddress = store.getString("host");
		serverPort = store.getString("port");
		serverStandard = store.getBoolean("standard");
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
		label1.setText("Serwer:");

		gd = new GridData(GridData.FILL_HORIZONTAL);
		serverAddressText = new Text(comp, SWT.BORDER);
		serverAddressText.setLayoutData(gd);
		serverAddressText.setText(serverAddress);
		serverAddressText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				ConfigServerPage.this.serverAddress =
					ConfigServerPage.this.serverAddressText.getText();
			}
		});

		Label label2 = new Label(comp, SWT.NORMAL);
		label2.setText("Port:");

		serverPortText = new Text(comp, SWT.BORDER);
		serverPortText.setText(serverPort);
		serverPortText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				ConfigServerPage.this.serverPort =
					ConfigServerPage.this.serverPortText.getText();
			}
		});

		Button button = new Button(comp, SWT.CHECK);
		button.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent arg0) {
				serverStandard = !serverStandard;				
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}});
		button.setSelection(serverStandard);
		Label label3 = new Label(comp, SWT.NORMAL);
		label3.setText("U¿ywaj standardowego serwera");

		return comp;
	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#getTitle()
	 */
	public String getTitle() {
		return "Serwer";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	public boolean performOk() {
		try {
			PreferenceStore store = (PreferenceStore) getPreferenceStore();
			store.setValue("host", serverAddress);
			store.setValue("port", serverPort);
			store.setValue("standard",serverStandard);
			store.save();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
