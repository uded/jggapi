package pl.mn.communicator.gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pl.mn.communicator.gui.util.ResourceManager;


/**
 * @author mnaglik
 */
class EditUserAction extends Action {
	private MainForm mainForm;
	EditUserAction(MainForm mainForm) {
		this.mainForm = mainForm;
	}

	/**
	 * @see org.eclipse.jface.action.IAction#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return ResourceManager.getImageDescriptor(EditUserAction.class,"icons/category_obj.gif");
	}

	/**
	 * @see org.eclipse.jface.action.IAction#getText()
	 */
	public String getText() {
		return "Edytuj...";
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		mainForm.editSelectedUser();
	}

}
