/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.gui;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import pl.mn.communicator.AbstractConnection;
import pl.mn.communicator.AbstractLocalUser;
import pl.mn.communicator.AbstractServer;
import pl.mn.communicator.ConnectionListener;
import pl.mn.communicator.IMessage;
import pl.mn.communicator.IUser;
import pl.mn.communicator.MessageListener;
import pl.mn.communicator.UserListener;
import pl.mn.communicator.gadu.Connection;
import pl.mn.communicator.gadu.LocalUser;
import pl.mn.communicator.gadu.Server;
import pl.mn.communicator.gadu.Status;
import pl.mn.communicator.gadu.User;
import pl.mn.communicator.gui.util.Config;
import pl.mn.communicator.gui.util.ResourceManager;

/**
 * @version $Revision: 1.14 $
 * @author mnaglik
 */
public class MainForm
	extends ApplicationWindow
	implements ConnectionListener, UserListener, MessageListener {

	private class UsersContentProvider implements IStructuredContentProvider {

		/**
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {

		}

		/**
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object o) {
			UsersData u = (UsersData) o;

			IUser[] users = new User[u.getUserCount()];

			Iterator i = u.getIterator();
			int j = 0;
			while (i.hasNext()) {
				users[j++] = (IUser) i.next();
			}
			return users;
		}

		/**
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {

		}

	}

	private class UsersLabelProvider extends LabelProvider {
		private Image on, off;

		public UsersLabelProvider() {
			on = ResourceManager.getImage(MainForm.class,"icons/on.bmp");
			off = ResourceManager.getImage(MainForm.class,"icons/off.bmp");
		}
		/**
		 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
		 */
		public Image getImage(Object element) {
			if (((IUser) element).isOnLine())
				return on;
			else
				return off;
		}

		public String getText(Object element) {
			return ((IUser) element).getName();
		}

	}

	private class UsersSorter extends ViewerSorter {
		// TODO duze i male litery sa zle sortowane
		/**
		 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public int compare(Viewer arg0, Object o1, Object o2) {
			IUser u1 = (IUser) o1;
			User u2 = (User) o2;

			if (u1.isOnLine() && !u2.isOnLine())
				return -1;
			else if (u2.isOnLine() && !u1.isOnLine())
				return 1;
			else
				return u1.getName().compareTo(u2.getName());
		}

	}

	/**
	 * Method main.
	 * @param args
	 */
	public static void main(String[] args) {
		MainForm w = new MainForm();
		w.setShellStyle(SWT.CLOSE | SWT.RESIZE);
		w.setBlockOnOpen(true);
		w.addToolBar(SWT.NONE);
		w.addMenuBar();
		w.open();
		Display.getCurrent().dispose();
	}

	private static Logger logger = Logger.getLogger(MainForm.class);
	private AbstractConnection connection;
	private AbstractLocalUser localUser;
	private AbstractServer server;
	private UsersData usersData;
	private TableViewer usersViewer;

	private HashMap usersChatForms = new HashMap();

	/*
	 * ACTIONS
	 */
	private Action chatStarted;

	/**
	 * @see java.lang.Object#Object()
	 */
	public MainForm() {
		super(null);
		usersData = UsersData.getInstance();
		logger.debug("Started");
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("JavaGG");
		shell.setImage(ResourceManager.getImage(MainForm.class,"icons/debug_exec.gif"));
		shell.setSize(160, 600);
	}

	/**
	 * @see org.eclipse.jface.window.ApplicationWindow#createContents()
	 */
	protected Control createContents(Composite parent) {
		Composite root = new Composite(parent,SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		layout.numColumns = 1;		
		root.setLayout(layout);
		Table usersTable = new Table(root, SWT.BORDER);
		usersTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Combo status = new Combo(root,SWT.READ_ONLY);
		status.add("Dostêpny");
		status.add("Zaraz wracam");
		status.add("Niewidoczny");
		status.add("Niedostêpny");
		status.addSelectionListener(new SelectionAdapter(){

			public void widgetSelected(SelectionEvent event) {
				switch(((Combo)event.getSource()).getSelectionIndex()){
					case 0:
						try {
							// zaraz wracam
							if (MainForm.this.connection == null){
								MainForm.this.connect();
							}else{
								MainForm.this.connection.changeStatus(new Status(Status.ON_LINE));
							}
						} catch (IOException e) {
						}
						break;
					case 1:
						try {
							// zaraz wracam
							if (MainForm.this.connection != null)
							MainForm.this.connection.changeStatus(new Status(Status.BUSY));
						} catch (IOException e) {
						}
						break;
					case 2:					
						try {
							// niewidoczny
							if (MainForm.this.connection != null)
							MainForm.this.connection.changeStatus(new Status(Status.NOT_VISIBLE));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					case 3:
						// off line
						MainForm.this.disconnect();
						break;
				}
			}
		});

		usersViewer = new TableViewer(usersTable);

		usersViewer.setContentProvider(new UsersContentProvider());
		usersViewer.setLabelProvider(new UsersLabelProvider());

		usersViewer.setSorter(new UsersSorter());

		usersViewer.setInput(usersData);

		addDoubleClick(usersViewer);
		createContextMenu(usersTable);
		return usersTable;
	}

	/**
	 * @see org.eclipse.jface.window.ApplicationWindow#createMenuManager()
	 */
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager();
		menuManager.add(createFileMenu());
		menuManager.add(createUserMenu());
		return menuManager;
	}
	
	

	/**
	 * @see org.eclipse.jface.window.Window#close()
	 */
	public boolean close() {
		disconnect();
		return super.close();
	}

	/**
	 * 
	 */
	void connect() {
		if (connection != null)
			disconnect();

		localUser =
			new LocalUser(
				Integer.parseInt(Config.getPreferenceStore().getString("user")),
				Config.getPreferenceStore().getString("password"));


		try {
			if (Config.getPreferenceStore().getBoolean("standard")){
				server = Server.getDefaultServer(localUser);
			}else{
				server =
					new Server(
						Config.getPreferenceStore().getString("host"),
						Integer.parseInt(
							Config.getPreferenceStore().getString("port")));
			}

			connection = new Connection(server, localUser);
			connection.addConnectionListener(this);
			connection.addMessageListener(this);
			connection.addUserListener(this);
			connection.connect();
		} catch (UnknownHostException e) {
			logger.error(e);
			showErrorDialog("Nie moge znaleŸæ hosta");
			connection = null;
		} catch (IOException e) {
			logger.error(e);
			showErrorDialog("Nie moge siê po³¹czyæ");
			connection = null;
		}
	}

	/**
	 * 
	 */
	private void disconnect() {
		try {
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * CHAT FORMS METHODS 
	 */
	public void chatFormClosed(IUser user) {
		usersChatForms.remove(user);
	}

	public void sendMessage(IMessage message) {
		try {
			connection.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			logger.error("You're not connected");
			showErrorDialog("Nie jesteœ po³¹czony");
		}
	}

	/*
	 * LISTENERS METHODS
	 */
	/**
	 * @see pl.mn.gadu.ConnectionListener#connectionEstablished()
	 */
	public void connectionEstablished() {
		logger.debug("ConnectionEstablished");
	}

	/**
	 * @see pl.mn.gadu.ConnectionListener#disconnected()
	 */
	public void disconnected() {

	}

	/**
	 * @see pl.mn.gadu.ConnectionListener#connectionError(java.lang.String)
	 */
	public void connectionError(String error) {

	}

	/**
	 * @see pl.mn.gadu.UserListener#userEntered(int)
	 */
	public void userEntered(int userNumber) {

	}

	/**
	 * @see pl.mn.gadu.UserListener#userLeaved(int)
	 */
	public void userLeaved(int userNumber) {

	}

	/**
	 * @see pl.mn.gadu.MessageListener#messageArrived(pl.mn.gadu.Message)
	 */
	public void messageArrived(final IMessage message) {
		logger.debug("Message arrived: " + message.getUser() + ":" + message.getText());
		getShell().getDisplay().syncExec(new Runnable() {
		   public void run() {
				ChatForm form = (ChatForm)usersChatForms.get(new User(message.getUser(),""+message.getUser(),false));
		   		if (form != null)
		   			form.messageArrived(message);
		   		else{
		   			User user = new User(message.getUser(),""+message.getUser(),false);
		   			form = new ChatForm(MainForm.this.getShell(),MainForm.this,user);	
		   			usersChatForms.put(user,form);
		   			form.open();
		   			form.messageArrived(message);
		   		}
		   }
		});
	}

	/*
	 * GUI METHODS
	 */

	/**
	 * Method createUserMenu.
	 * @return MenuManager
	 */
	private MenuManager createUserMenu() {
		MenuManager menu = new MenuManager("U¿ytkownicy", "Id02");
		menu.add(new AddUserAction(this));
		return menu;
	}

	/**
	 * Method createMenu.
	 * @return MenuManager
	 */
	private MenuManager createFileMenu() {
		MenuManager menu = new MenuManager("Plik", "Id01");
		//menu.add(new ConnectAction(this));
		menu.add(new ConfigAction(this));
		menu.add(new AboutAction(this));
		menu.add(new ExitAction(this));
		return menu;
	}
	
	/**
	 * @param table
	 */
	private void createContextMenu(Table table) {
		MenuManager menuMgr = new MenuManager("#context");
		menuMgr.add(new EditUserAction(this));
		menuMgr.add(new DeleteUserAction(this));
		menuMgr.add(new ChatUserAction(this));

		Menu menu = menuMgr.createContextMenu(table);
		table.setMenu(menu);
	}

	private void showErrorDialog(String error) {
		String[] tab = { IDialogConstants.OK_LABEL };
		MessageDialog dialog =
			new MessageDialog(
				getShell(),
				"Bl¹d",
				null,
				error,
				MessageDialog.ERROR,
				tab,
				0);
		dialog.open();
	}

	private void addDoubleClick(final TableViewer viewer) {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				startChatWithSelectedUser();
			}
		});
	}

	void editSelectedUser(){
		IStructuredSelection sel =
			(IStructuredSelection) usersViewer.getSelection();
		IUser first = (IUser) sel.getFirstElement();
		if (first != null) {
			UserAddForm form = new UserAddForm(getShell(), first);
			form.open();
			if (form.getReturnCode() != Window.CANCEL) {
				first = form.getUser();
				usersViewer.refresh();
				usersData.saveUsers();
			}
		}
	}
	
	void deleteSelectedUser(){
		IStructuredSelection sel =
			(IStructuredSelection) usersViewer.getSelection();
		User first = (User) sel.getFirstElement();
		if (first != null) {
			usersData.removeUser(first);
			usersViewer.refresh();
			usersData.saveUsers();
		}
	}
	
	void startChatWithSelectedUser(){
		IStructuredSelection sel =
			(IStructuredSelection) usersViewer.getSelection();
		User first = (User) sel.getFirstElement();
		if (first != null) {
			ChatForm chatForm = (ChatForm) usersChatForms.get(first);

			if (chatForm == null) {
				// nie ma jeszcze takiej rozmowy	
				ChatForm form =
					new ChatForm(getShell(), MainForm.this, first);
				usersChatForms.put(first, form);
				form.open();
			} else {
			}
		}
	}

	void addUser() {
		UserAddForm userForm = new UserAddForm(getShell());
		userForm.open();
		if (userForm.getReturnCode() != Window.CANCEL) {
			usersData.addUser(userForm.getUser());
			usersViewer.refresh();
			usersData.saveUsers();
		}
	}

	protected ToolBarManager createToolBarManager(int arg0) {
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT | SWT.WRAP);
		//toolBarManager.add(new ConnectAction(this));
		toolBarManager.add(new ConfigAction(this));
		toolBarManager.add(new AddUserAction(this));
		toolBarManager.add(new ExitAction(this));
		return toolBarManager;
	}

}
