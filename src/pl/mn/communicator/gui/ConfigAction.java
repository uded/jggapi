package pl.mn.communicator.gui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.resource.ImageDescriptor;

import pl.mn.communicator.gui.config.ConfigForm;
import pl.mn.communicator.gui.util.ResourceManager;

/**
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
class ConfigAction extends Action {
	private MainForm mainForm;
	
	ConfigAction(MainForm mainForm){
		this.mainForm = mainForm;
	}
	/**
	 * @see org.eclipse.jface.action.IAction#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return ResourceManager.getImageDescriptor(EditUserAction.class,"icons/thread_obj.gif");
	}

	/**
	 * @see org.eclipse.jface.action.IAction#getText()
	 */
	public String getText() {
		return "Ustawienia";
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		ConfigForm config =
			new ConfigForm(mainForm.getShell(), new PreferenceManager());
		config.open();
	}

}
