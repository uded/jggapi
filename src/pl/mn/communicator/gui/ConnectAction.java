package pl.mn.communicator.gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import pl.mn.communicator.gui.util.ResourceManager;


/**
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
class ConnectAction extends Action {
	private MainForm mainForm;
	
	ConnectAction(MainForm mainForm){
		this.mainForm = mainForm;
	}
	
	/**
	 * @see org.eclipse.jface.action.IAction#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return ResourceManager.getImageDescriptor(EditUserAction.class,"icons/run_exc.gif");
	}

	/**
	 * @see org.eclipse.jface.action.IAction#getText()
	 */
	public String getText() {
		return "Po³¹cz";
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		mainForm.connect();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IAction#getToolTipText()
	 */
	public String getToolTipText() {
		return "Po³¹cz";
	}

}
