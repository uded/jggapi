package pl.mn.communicator.gui;

import org.apache.log4j.Logger;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pl.mn.communicator.AbstractUser;
import pl.mn.communicator.IMessage;
import pl.mn.communicator.gadu.Message;

/**
 * @version $Revision: 1.3 $
 * @author mnaglik
 */
public class ChatForm extends Window{
	private static Logger logger = Logger.getLogger(ChatForm.class);
	private MainForm mainForm;
	private AbstractUser user;

	private Text text1;
	//private TextViewer text1;
	private Text text2;

	

	public ChatForm(Shell shell, MainForm mainForm,AbstractUser user) {
		super(shell);
		this.mainForm = mainForm;
		this.user = user;
		setShellStyle(SWT.CLOSE ^ SWT.RESIZE);
		//setBlockOnOpen(true);
	}
	
	public void messageArrived(IMessage message) {
		text1.append("<"+UsersData.getInstance().getUserName(message.getUser())+"> "+message.getText()+"\n");
	}
	
	/**
	 * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);

		Composite textPanel = createTextPanel(parent);
		setGridData(textPanel, GridData.FILL, true, GridData.FILL, true);

		Composite buttonPanel = createButtonPanel(parent);
		setGridData(buttonPanel, GridData.FILL, true, GridData.FILL, false);

		return parent;
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Rozmowa z " + UsersData.getInstance().getUserName(user.getNumber()));
		shell.setSize(400, 400);
	}

	/*
	 * GUI METHODS
	 */
	private Composite createTextPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NULL);

		panel.setLayout(new FillLayout());
		SashForm sash = new SashForm(panel, SWT.VERTICAL | SWT.NULL);
		sash.SASH_WIDTH = 5;

		//TextViewer text1 = new TextViewer(sash,SWT.NORMAL);
		//text1.addTextListener(this);
		text1 = new Text(sash, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		text2 = new Text(sash, SWT.BORDER^SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);

		text1.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0) {
				text2.forceFocus();
			}
		});


		return panel;
	}

	private Composite createButtonPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		panel.setLayout(layout);

		Button sendButton = new Button(panel, SWT.NULL);
		sendButton.setText("Wyœlij");
		sendButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				String toSend = text2.getText();
				ChatForm.this.mainForm.sendMessage(new Message(user.getNumber(),toSend));
				text1.append("<JA> "+toSend+System.getProperty("line.separator"));
				text2.setText("");
				text2.forceFocus();
			}
		});

		Button closeButton = new Button(panel, SWT.NULL);
		closeButton.setText("Zamknij");
		closeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				close();
			}
		});

		return panel;
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
	 * @see org.eclipse.jface.window.Window#close()
	 */
	public boolean close() {
		mainForm.chatFormClosed(user);
		return super.close();
	}

}
