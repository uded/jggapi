package pl.mn.communicator.gui.config;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
public class ConfigSoundPage extends PreferencePage {
	/**
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(
			"W przysz³oœci bêdzie mo¿na tutaj \nustawiæ dŸwiêki\nnp. przychodz¹cej wiadomoœci.");
		return label;
	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#getTitle()
	 */
	public String getTitle() {
		return "DŸwiêki";
	}

}
