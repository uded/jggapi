package pl.mn.communicator.gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pl.mn.communicator.gui.util.ResourceManager;

/**
 * @author mnaglik
 */
class AddUserAction extends Action {
	private MainForm mainForm;
	AddUserAction(MainForm mainForm) {
		this.mainForm = mainForm;
	}
	
	/**
	 * @see org.eclipse.jface.action.IAction#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return ResourceManager.getImageDescriptor(EditUserAction.class,"icons/new_bookmark_wiz.gif");
	}

	/**
	 * @see org.eclipse.jface.action.IAction#getText()
	 */
	public String getText() {
		return "Dodaj...";
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		mainForm.addUser();
	}

}
