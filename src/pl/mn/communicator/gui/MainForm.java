package pl.mn.communicator.gui;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceManager;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import pl.mn.communicator.AbstractConnection;
import pl.mn.communicator.AbstractLocalUser;
import pl.mn.communicator.AbstractMessage;
import pl.mn.communicator.AbstractServer;
import pl.mn.communicator.AbstractUser;
import pl.mn.communicator.ConnectionListener;
import pl.mn.communicator.MessageListener;
import pl.mn.communicator.UserListener;
import pl.mn.communicator.gadu.Connection;
import pl.mn.communicator.gadu.LocalUser;
import pl.mn.communicator.gadu.Message;
import pl.mn.communicator.gadu.Server;
import pl.mn.communicator.gadu.User;

/**
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

			AbstractUser[] users = new User[u.getUserCount()];

			Iterator i = u.getIterator();
			int j = 0;
			while (i.hasNext()) {
				users[j++] = (AbstractUser) i.next();
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
			ImageData dataOn = new ImageData("img//on.bmp");
			ImageData dataOff = new ImageData("img//off.bmp");
			Display display = Display.getCurrent();
			on = new Image(display, dataOn);
			off = new Image(display, dataOff);
		}
		/**
		 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
		 */
		public Image getImage(Object element) {
			if (((AbstractUser) element).isOnLine())
				return on;
			else
				return off;
		}

		public String getText(Object element) {
			return ((AbstractUser) element).getName();
		}

	}

	private class UsersSorter extends ViewerSorter {
		// TODO duze i male litery sa zle sortowane
		/**
		 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public int compare(Viewer arg0, Object o1, Object o2) {
			AbstractUser u1 = (AbstractUser) o1;
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
		w.setShellStyle(SWT.CLOSE ^ SWT.RESIZE);
		w.setBlockOnOpen(true);
		w.addMenuBar();
		w.open();
		Display.getCurrent().dispose();
	}

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
		usersData = new UsersData();
		makeActions();
	}

	/**
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("JavaGG");
		shell.setSize(160, 600);
	}

	/**
	 * @see org.eclipse.jface.window.ApplicationWindow#createContents()
	 */
	protected Control createContents(Composite parent) {

		Table usersTable = new Table(parent, SWT.BORDER);
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
	private void connect() {
		if (connection != null)
			disconnect();
		// inicjalizuj dane polaczenia
		// TODO sprawdzanie parseInt przy parsowaniu
		// TODO zmiana danych po edycji w menu
		localUser =
			new LocalUser(
				Integer.parseInt(Config.getPreferenceStore().getString("user")),
				Config.getPreferenceStore().getString("password"));

		server =
			new Server(
				Config.getPreferenceStore().getString("host"),
				Integer.parseInt(
					Config.getPreferenceStore().getString("port")));
		connection = new Connection(server, localUser);
		connection.addConnectionListener(this);
		connection.addMessageListener(this);
		connection.addUserListener(this);
		try {
			connection.connect();
		} catch (UnknownHostException e) {
			showErrorDialog("Nie moge znaleŸæ hosta");
			connection = null;
			//e.printStackTrace();
		} catch (IOException e) {
			showErrorDialog("Nie moge siê po³¹czyæ");
			connection = null;
			//e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void disconnect() {
		try {
			if (connection != null) {
				connection.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * CHAT FORMS METHODS 
	 */
	public void chatFormClosed(AbstractUser user) {
		usersChatForms.remove(user);
	}

	public void sendMessage(AbstractMessage message) {
		try {
			connection.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * LISTENERS METHODS
	 */
	/**
	 * @see pl.mn.gadu.ConnectionListener#connectionEstablished()
	 */
	public void connectionEstablished() {
		System.out.println("connectionEstablished");
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
	public void messageArrived(final AbstractMessage message) {
		System.out.println(
			"Message arrived: " + message.getUser() + ":" + message.getText());
		// TODO czemu to kurwa nie dziala?
		/*
				Display.getCurrent().syncExec(new Runnable() {
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
		*/
	}

	/*
	 * GUI METHODS
	 */
	/**
	 * Method createAboutAction.
	 * @return Action
	 */
	private Action createAboutAction() {
		return new Action() {
			public String getText() {
				return "O programie";
			}

			public void run() {
				String[] tab = { IDialogConstants.OK_LABEL };
				MessageDialog dialog =
					new MessageDialog(
						getShell(),
						"Informacje o programie",
						null,
						"JavaGG (c) Marcin Naglik 2003",
						MessageDialog.INFORMATION,
						tab,
						0);
				dialog.open();
			}
		};
	}

	/**
	 * Method createUserMenu.
	 * @return MenuManager
	 */
	private MenuManager createUserMenu() {
		MenuManager menu = new MenuManager("U¿ytkownicy", "Id02");
		menu.add(new Action() {
			public String getText() {
				return "Dodaj...";
			}
			public void run() {
				UserAddForm userForm = new UserAddForm(getShell());
				userForm.open();
				if (userForm.getReturnCode() != Window.CANCEL) {
					usersData.addUser(userForm.getUser());
					usersViewer.refresh();
				}
			}

		});

		menu.add(new Action() {
			public String getText() {
				return "Wyœlij...";
			}
			public void run() {
				try {
					connection.sendMessage(
						new Message(
							2569522,
							"Dupa blada. £¹ka jest piêkna dziœ."));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		return menu;
	}

	/**
	 * Method createMenu.
	 * @return MenuManager
	 */
	private MenuManager createFileMenu() {
		MenuManager menu = new MenuManager("Plik", "Id01");
		menu.add(new Action() {
			public String getText() {
				return "Po³¹cz...";
			}
			public void run() {
				connect();
			}
		});
		menu.add(new Action() {
			public String getText() {
				return "Ustawienia";
			}
			public void run() {
				ConfigForm config =
					new ConfigForm(getShell(), new PreferenceManager());
				config.open();
			}
		});
		menu.add(createAboutAction());
		menu.add(new Action() {
			public String getText() {
				return "Wyjœcie";
			}
			public void run() {
				close();
			}
		});
		return menu;
	}

	/**
	 * @param table
	 */
	private void createContextMenu(Table table) {
		MenuManager menuMgr = new MenuManager("#context");
		menuMgr.add(new Action() {
			public String getText() {
				return "Edytuj...";
			}
			public void run() {
				IStructuredSelection sel =
					(IStructuredSelection) usersViewer.getSelection();
				AbstractUser first = (AbstractUser) sel.getFirstElement();
				if (first != null) {
					UserAddForm form = new UserAddForm(getShell(), first);
					form.open();
					if (form.getReturnCode() != Window.CANCEL) {
						first = form.getUser();
						usersViewer.refresh();
					}
				}
			}
		});

		menuMgr.add(new Action() {
			public String getText() {
				return "Usuñ...";
			}
			public void run() {
				IStructuredSelection sel =
					(IStructuredSelection) usersViewer.getSelection();
				User first = (User) sel.getFirstElement();
				if (first != null) {
					usersData.removeUser(first);
					usersViewer.refresh();
				}
			}
		});

		menuMgr.add(new Action() {
			public String getText() {
				return "Rozmawiaj...";
			}
			public void run() {
				chatStarted.run();
			}
		});

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
				chatStarted.run();
			}
		});
	}

	private void makeActions() {
		chatStarted = new Action() {
			public void run() {
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
		};

	}
}
