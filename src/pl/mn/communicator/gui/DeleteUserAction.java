package pl.mn.communicator.gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pl.mn.communicator.gui.util.ResourceManager;


/**
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
class DeleteUserAction extends Action {
	private MainForm mainForm;
	
	DeleteUserAction(MainForm mainForm){
		this.mainForm = mainForm;
	}

	/**
	 * @see org.eclipse.jface.action.IAction#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return ResourceManager.getImageDescriptor(EditUserAction.class,"icons/error_tsk.gif");
	}

	/**
	 * @see org.eclipse.jface.action.IAction#getText()
	 */
	public String getText() {
		return "Usuñ...";
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		mainForm.deleteSelectedUser();
	}

}
