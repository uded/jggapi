package pl.mn.communicator.gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import pl.mn.communicator.gui.util.ResourceManager;

/**
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
class AboutAction extends Action {
	private MainForm mainForm;
	AboutAction(MainForm mainForm){
		this.mainForm = mainForm;
	}
	/**
	 * @see org.eclipse.jface.action.IAction#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return ResourceManager.getImageDescriptor(EditUserAction.class,"icons/home_nav.gif");
	}

	/**
	 * @see org.eclipse.jface.action.IAction#getText()
	 */
	public String getText() {
		return "O programie";
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		String[] tab = { IDialogConstants.OK_LABEL };
		MessageDialog dialog =
			new MessageDialog(
				mainForm.getShell(),
				"Informacje o programie",
				null,
				"JavaGG (c) Marcin Naglik 2003",
				MessageDialog.INFORMATION,
				tab,
				0);
		dialog.open();
	}

}
