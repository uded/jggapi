package pl.mn.communicator.gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pl.mn.communicator.gui.util.ResourceManager;


/**
 * @author mnaglik
 */
class ExitAction extends Action {
	private MainForm mainForm;
	
	ExitAction(MainForm mainForm) {
		this.mainForm = mainForm;	
	}
	/**
	 * @see org.eclipse.jface.action.IAction#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return ResourceManager.getImageDescriptor(EditUserAction.class,"icons/redo_edit.gif");
	}

	/**
	 * @see org.eclipse.jface.action.IAction#getText()
	 */
	public String getText() {
		return "Wyjœcie";
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		mainForm.close();
	}

}
