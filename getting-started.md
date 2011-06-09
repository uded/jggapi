Introduction
============

JGGApi is a library that was designed to be used by high-level developers. It handles low-level binary Gadu-Gadu 
protocol implementation. High-level developers can use well described interfaces to interact with Gadu-Gadu server 
as most of complicated protocol details is hidden.

One of the most important class in JGGApi is a Session class. It is an entry point to so-called JGGApi services:

**JGGApi services:**

* IConnectionService - connecting and disconnecting.
* ILoginService - logging and logging out.
* IMessageService - sending and receiving messages.
* IPresenceService - updating own status and listening for changes of statuses of users from buddy list.
* IPublicDirectoryService - searching in public directory service, reading and writing personal information.
* IContactListService - importing and exporting of contact list.
* IRegistrationService - registering and unregistering new account, changing password, sending forgotten password by e-mail.

Session lifecycle
=================

Session can be in various states. We can check current SessionState by invoking getSessionState() method on Session object.

	ISession session = ...
	SessionState sessionState = session.getSessionState();				

				
List of all available session states:
* SessionState.CONNECTION_AWAITING
* SessionState.CONNECTING
* SessionState.CONNECTED
* SessionState.AUTHENTICATION_AWAITING
* SessionState.LOGGED_IN
* SessionState.DISCONNECTING
* SessionState.DISCONNECTED
* SessionState.LOGGED_OUT
* SessionState.CONNECTION_ERROR

After session creation, Session instance is in SessionState.CONNECTION_AWAITING. When connect() method is invoked 
session state changes to SessionState.CONNECTING. After connection to Gadu-Gadu server is established the session state 
changes to SessionState.CONNECTED. Next phase is the authentication of user, therefore session state is in 
SessionState.AUTHENTICATION_AWAITING. After logging in session changes to SessionState.LOGGED_IN. In SessionState.LOGGED_IN 
it is possible to send messages, change status, import, export contact list and search information in public directory. 
Basically, it means session is ready for interaction with Gadu-Gadu server. When there is an unexpected error that occurs 
during interaction with Gadu-Gadu server session state changes to SessionState.CONNECTION_ERROR and this means you can 
disconnect from server. If you want to be notified of session state changes you can register as SessionStateLister in Session 
object. Code snippet:

	ISession session = ...
	session.addSessionStateListener(new SessionStateListener() {
		public void sessionStateChanged(final SessionState oldSessionState, final SessionState newSessionState) {
			//add handling here
		}
	});

				
Connecting and disconnecting
============================

Create Session object by invoking static method on SessionFactory. After that create a LoginContext instance. 
LoginContext contains various information that are of high importance in logging process. It holds Gadu-Gadu 
uin and password for it. In addition, it has information about initial status, maximal image size that the user 
accepts and initial list of monitored users. Note that monitored users list also includes list of users that are 
banned, ie. the ones from which we do not wish to receive any notification and messages. In order to connect to 
Gadu-Gadu server you have to use ConnectionService that can be referenced from previously created Session object.	 

After that add ConnectionListener to listen for connection related events and finally invoke connect() method on 
ConnectionService. Code snippet:

	final ISession session = SessionFactory.createSession();
	final LoginContext loginContext = new LoginContext(1336843, "dupadupa");
	session.getConnectionService().addConnectionListener(new ConnectionListener.Stub() {
		public void connectionEstablished() throws GGException {
			System.out.println("Connection established.");
		}
	});
	session.getConnectionService().connect();

The code above code snippet establishes socket connection to Gadu-Gadu server. It is important to note that it 
does NOT log in user. When connection to server is successful, connectionEstablished() method from connection handler 
class is invoked and SessionState changes to SessionState.AUTHENTICATION_AWAITING. In order to disconnect from Gadu-Gadu 
server you have to invoke disconnect() method on ConnectionService. After that connection handler class that implements 
ConnectionListener interface is notified and connectionClosed() method is invoked. After listener method is triggered 
the SessionState changes to DISCONNECTED. It is important to notice that disconnect method can always be invoked, 
regardless of the current session state. Code snippet:

	final ISession session = SessionFactory.createSession();
	session.getConnectionService().addConnectionListener(new ConnectionListener.Stub() {
		public void connectionClosed() throws GGException {
			System.out.println("Connection closed.");
		}
	});
	session.getConnectionService().disconnect();
				
Logging in and logging out
==========================

In order to log in connection to Gadu-Gadu server must be established. Session state must be in 
SessionState.AUTHENTICATION_AWAITING mode. In order to log in you will have to use LoginService previously obtained from 
Session instance. In addition, you will need to create a LoginContext instance. LoginContext contains various information 
that are of high importance in logging process. It holds Gadu-Gadu uin and password for it. What is more, it has information 
about initial status, maximal image size that the user accepts and initial list of monitored users. Note that monitored users 
list also includes list of users that are banned, ie. the ones from which we do not wish to receive any notification or messages.

You can add LoginListener to be notified of login related events. If your uin and password match and you will be successfuly 
authenticated, to let you know about this, loginOK() method will be triggered. In case authentication process was not successful,
you will be notified of this through invocation of loginFailed() method on LoginListener. The LoginFailedEvent object contains 
cause of unsuccessful login.

Code snippet:

	ISession session = ...
	ILoginService loginService = session.getLoginService();
	loginService.addLoginListener(new LoginListener.Stub() {
		public void loginOK() throws GGException {
			System.out.println("Login OK.");
		}
		public void loginFailed(final LoginFailedEvent loginFailedEvent) throws GGException {
			System.out.println("Login Failed.");
		}
	});
				
If you want to log out from Gadu-Gadu server you have to invoke logout() method on LoginService. After doing that status will 
be set to unavailable and there will be no way to send messages or interact with Gadu-Gadu server whatsoever. The session state 
will change in such situation to SessionState.LOGGED_OUT.

Alternatively, you can invoke logout(java.lang.String description, java.lang.Date returnDate) passing your description that will 
be set when status is unavailable and/or your anticipated return time. If you do not wish to pass your return time you are free 
to pass null as a second parameter.

	ISession session = ...
	ILoginService loginService = session.getLoginService();
	loginService.logout();

Alternative approach:

	ISession session = ...
	ILoginService loginService = session.getLoginService();
	loginService.logout("Vegetarians rocks! Wake up, stop killing animals!", null);

Sending and receiving messages
==============================

In order to send and receive messages you have to use MessageService that similarly to previous services can be obtained from 
Session object. Before sending, an outgoing message has to be created. There is a OutgoingMessage class that has three static 
methods to create it's instance. The most common static method is: OutgoingMessage.createNewMessage(int, java.lang.String).

The above method simply creates a new OutgoingMessage instance. The first parameter is the number of recipient and the second 
one is the body of the message to be send.

After creating OutgoingMessage object you can send it through MessageService. In order to do it you have to invoke 
sendMessage(IOutgoingMessage outgoingMessage) method from MessageService.

	ISession session = ...
	IMessageService messageService = session.getMessageService();
	OutgoingMessage outMessage = OutgoingMessage.createNewMessage(176798, "body");
	messageService.sendMessage(outMessage);

If you want to be notified of message related events you have to register MessageListener in MessageService.

	final ISession session = ...
	final IMessageService messageService = session.getMessageService();
	messageService.addMessageListener(new MessageListener.Stub() {
		public void messageArrived(IIncommingMessage incommingMessage) {
			System.out.println("MessageArrived, from user: "+incommingMessage.getUin());
			System.out.println("MessageBody: "+incommingMessage.getMessageBody());
			System.out.println("MessageID: "+incommingMessage.getMessageID());
			System.out.println("MessageStatus: "+incommingMessage.getMessageClass());
			System.out.println("MessageTime: "+incommingMessage.getMessageDate());
		}
	});

There is also possibilty to receive confirmation whether a certain outgoing message was delivered. In order to do that 
you have to register as MessageListener in MessageService. When message is delivered JGGApi will notify you though invocation 
of messageDelivered(int uin, int messageID, MessageStatus deliveryStatus) method in MessageListener The message can be identified 
by an unique messageID which is generated when you creates an instance of OutgoingMessage.

	ISession session = ...
	IMessageService messageService = session.getMessageService();
	messageService.addMessageListener(new MessageListener.Stub() {
		public void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus) {
			System.out.println("MessageDelivered, fromUser: "+String.valueOf(uin));
			System.out.println("MessageDelivered, messageID: "+String.valueOf(messageID));
			System.out.println("MessageDelivered, messageStatus: "+deliveryStatus);
		}
	});
	OutgoingMessage outMessage = OutgoingMessage.createNewMessage(176798, "body");
	messageService.sendMessage(outMessage);
