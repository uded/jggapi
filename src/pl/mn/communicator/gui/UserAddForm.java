package pl.mn.communicator.gui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.mn.communicator.IUser;
import pl.mn.communicator.gadu.User;

/**
 * Form to add new user
 * or edit existing user
 * 
 * @version $Revision: 1.4 $
 * @author mnaglik
 */
public class UserAddForm extends Dialog {
	private IUser user = null;
	private Text userName;
	private Text userNumber;

	private boolean newUser = false;
	private Button button1;
	
	public UserAddForm(Shell shell) {
		super(shell);
		this.user = new User(0,"",false);
		this.newUser = true;
		setShellStyle(SWT.CLOSE);
		setBlockOnOpen(true);
	}

	public UserAddForm(Shell shell, IUser user) {
		super(shell);
		this.user = user;
		setShellStyle(SWT.CLOSE);
		setBlockOnOpen(true);
	}

	public IUser getUser() {
		return this.user;
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#cancelPressed()
	 */
	protected void cancelPressed() {
		super.cancelPressed();
	}

	/**
	 * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite panel = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);

		Composite inputPanel = createInputPanel(panel);
		setGridData(inputPanel, GridData.FILL, true, GridData.CENTER, false);

		Composite buttonPanelB = createButtonSection(panel);
		setGridData(buttonPanelB, GridData.FILL, true, GridData.CENTER, false);

		if (!newUser) {
			userNumber.setText(""+user.getNumber());
			userName.setText(user.getName());
		}
		
		userNumber.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				updateButtons();
			}});
		userName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				updateButtons();
			}});

		updateButtons();

		return panel;
	}

	/**
	 * @param panel
	 * @return Composite
	 */
	private Composite createButtonSection(Composite parent) {
		Composite panel = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		panel.setLayout(layout);

		String labelAdd = newUser ? "Dodaj" : "Zapisz";
		button1 = createButton(panel, 102, labelAdd, false);
		setGridData(button1, GridData.FILL, true, GridData.FILL, false);

		Button button0 = createButton(panel, 101, "Anuluj", false);
		setGridData(button0, GridData.FILL, true, GridData.FILL, false);

		return panel;
	}

	/**
	 * Create the panel where the user specifies the text to search
	 * for and the optional replacement text
	 *
	 * @param parent the parent composite
	 * @return the input panel
	 */
	private Composite createInputPanel(Composite parent) {

		Composite panel = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		panel.setLayout(layout);

		Label label0 = new Label(panel, SWT.LEFT);
		label0.setText("Nazwa:");
		setGridData(label0, GridData.BEGINNING, false, GridData.CENTER, false);

		userName = new Text(panel, SWT.LEFT ^ SWT.BORDER);
		setGridData(userName, GridData.FILL, true, GridData.CENTER, false);

		Label label1 = new Label(panel, SWT.LEFT);
		label1.setText("Numer:");
		setGridData(label1, GridData.BEGINNING, false, GridData.CENTER, false);

		userNumber = new Text(panel, SWT.LEFT ^ SWT.BORDER);
		userNumber.addVerifyListener(new VerifyListener() {

			public void verifyText(VerifyEvent event) {
				if (event.text.length() > 0) {
					char charEntered = event.text.charAt(0);
					if (!Character.isDigit(charEntered))
						event.doit = false;
				}
			}

		});
		setGridData(userNumber, GridData.FILL, true, GridData.CENTER, false);
		return panel;
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("U¿ytkownik");
		shell.setSize(200, 120);
	}

	/**
	 * Attaches the given layout specification to the <code>component</code>
	 * 
	 * @param component the component
	 * @param horizontalAlignment horizontal alignment
	 * @param grabExcessHorizontalSpace grab excess horizontal space
	 * @param verticalAlignment vertical alignment
	 * @param grabExcessVerticalSpace grab excess vertical space
	 */
	private void setGridData(
		Control component,
		int horizontalAlignment,
		boolean grabExcessHorizontalSpace,
		int verticalAlignment,
		boolean grabExcessVerticalSpace) {
		GridData gd = new GridData();
		gd.horizontalAlignment = horizontalAlignment;
		gd.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
		gd.verticalAlignment = verticalAlignment;
		gd.grabExcessVerticalSpace = grabExcessVerticalSpace;
		component.setLayoutData(gd);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	protected void buttonPressed(int key) {
		if (key == 101) {
			cancelPressed();
		} else {
			user.setName(userName.getText());
			try{
				user.setNumber(Integer.parseInt(userNumber.getText()));
			}catch(NumberFormatException e){
				user.setNumber(0);					
			}
			close();
		}
	}
	
	/**
	 * Sets OK button to disabled if
	 * date is incorrect
	 */
	private void updateButtons(){
		if (userName.getText().length() == 0 
		|| userNumber.getText().length() == 0){
			button1.setEnabled(false);
		}else{
			button1.setEnabled(true);
		}
	}

}
