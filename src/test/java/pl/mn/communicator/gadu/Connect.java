package pl.mn.communicator.gadu;
/*
 * Created on Oct 3, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.util.Collection;
import java.util.Iterator;

import pl.mn.communicator.IConnectionService;
import pl.mn.communicator.IContactListService;
import pl.mn.communicator.IIncommingMessage;
import pl.mn.communicator.ILoginService;
import pl.mn.communicator.IMessageService;
import pl.mn.communicator.IOutgoingMessage;
import pl.mn.communicator.IPublicDirectoryService;
import pl.mn.communicator.ISession;
import pl.mn.communicator.LocalUser;
import pl.mn.communicator.LoginContext;
import pl.mn.communicator.MessageStatus;
import pl.mn.communicator.OutgoingMessage;
import pl.mn.communicator.PersonalInfo;
import pl.mn.communicator.PublicDirSearchQuery;
import pl.mn.communicator.PublicDirSearchReply;
import pl.mn.communicator.Server;
import pl.mn.communicator.SessionFactory;
import pl.mn.communicator.SessionState;
import pl.mn.communicator.event.ConnectionListener;
import pl.mn.communicator.event.ContactListListener;
import pl.mn.communicator.event.LoginListener;
import pl.mn.communicator.event.MessageListener;
import pl.mn.communicator.event.PublicDirListener;
import pl.mn.communicator.event.SessionStateListener;



/**
 * @author mehul
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */



/*  COMMENTS:-================================
 
 have a look at the method X() which actually has code to add the users to the list as well as export it
 
 ==============================================
 */

public class Connect {
	
	ILoginService loginService;
	IConnectionService connectionService;
	ISession session;
	IContactListService contactListService;
	LoginContext loginContext;
	public IPublicDirectoryService publicdirService;	
	public PublicDirSearchQuery searchQuery; 	
	public Collection ulist;
	
	
	public void E(){
		try{
			
			
			loginContext = new LoginContext(1038285, "test");
			
			
			session = SessionFactory.createSession();		
			
			session.addSessionStateListener(new SessionStateListener() {
				public void sessionStateChanged(SessionState oldSessionState, SessionState newSessionState) {
					
					System.out.println(""+newSessionState.toString());
					
					if(newSessionState.toString().equals("connected")){
						
						//F();
						
						
					}
					
					if(newSessionState.toString().equals("authentication_awaiting")){
						
						F();	
					}	
					
					if(newSessionState.toString().equals("logged_in")){
						
						try{
							//connectionService.disconnect();
							System.out.println("IN HERE 1");
							contactListService= session.getContactListService();
							System.out.println("IN HERE 2");	
							contactListService.importContactList();
							System.out.println("IN HERE 3");
							contactListService.addContactListListener(new ContactListListener(){
								
								public void contactListExported(){
									
									System.out.println("Contact list exported: ");
								}
								
								public void contactListReceived(Collection users){
									ulist= users;
									
									System.out.println("contact list recieved successfully: "+users+" length is:"+users.size());	
									
									Iterator it= users.iterator();
									
									Object obj;
									while(it.hasNext()){
										
										obj= it.next();    		 
										System.out.println("Object is ====="+obj);
										LocalUser lusr=(pl.mn.communicator.LocalUser)obj;
										System.out.println("The details are: ===========\nUIN: "+lusr.getUin()+"\nDisplay Name: "+lusr.getDisplayName()+"\n=======================");
										
									}
									
									X();		
								}
								
								
							});
							//X();		

						}catch(Exception e){System.out.println("Exception while getting USER_LIST: "+e);}
						
						
						try{
							IMessageService messageService = session.getMessageService();
							
							messageService.addMessageListener(new MessageListener(){
								
								public void messageArrived(IIncommingMessage incommingMessage){
									
									System.out.println("================\nMessage Body: "+incommingMessage.getMessageBody()+"\nMessage ID: "+incommingMessage.getMessageID()+"\nFrom ID: "+incommingMessage.getRecipientUin()+"\n========================");
									
								} 								
								public void messageDelivered(int uin, int messageID, MessageStatus deliveryStatus){}
								public void messageSent(IOutgoingMessage outgoingmessage){} 	
								
								
							});
							
							
							
							
							OutgoingMessage outMessage = OutgoingMessage.createNewMessage(5914398, "helo hello hello");
							messageService.sendMessage(outMessage);		
							
							
							
							
						}catch(Exception e){
							
							System.out.println("Exception while sending the message: "+e);	
						}
						
					}	
					
					
					
				}
			});
			
			
			/////
			
			connectionService = session.getConnectionService();
			
			connectionService.addConnectionListener(new ConnectionListener.Stub() {
				public void connectionEstablished() {
					System.out.println("Connection established.");
					
				}
				
				public void connectionClosed() {
					System.out.println("Connection closed.");
				}
				
				public void connectionError(Exception ex) {
					System.out.println("Connection Error: "+ex.getMessage());
				}
			});
			//connectionService.connect();
			
			connectionService.connect(new Server("217.17.41.93",8074));
			
			
			
			
			
		}catch(Exception e){
			System.out.println("Exception in Connect: "+e);
		}	
		
		
		
		
	}
	
	public void F(){
		
		try{
			loginService = session.getLoginService();
			//loginService.login();
			loginService.login(loginContext);
			loginService.addLoginListener(new LoginListener.Stub() {
				public void loginOK() {
					System.out.println("Login OK.");
					
				}
				
				public void loginFailed() {
					System.out.println("Login Failed.");
				}
			});
			
		}catch(Exception e){System.out.println("Exception in F(): "+e);}
		
		
		
	}
	
//	=====================================ADDED NOW FOR LIST EXPORT TEST
	public void X(){
		
		System.out.println("Inside X()");
		publicdirService = session.getPublicDirectoryService();
		
		publicdirService.addPublicDirListener(new PublicDirListener(){
			
			public void onPublicDirectoryRead(int queryID, PersonalInfo pubDirReply) {}
			
			public void onPublicDirectoryUpdated(int queryID) {
				
				System.out.println("List Updated to the server");
			}
			
			public void onPublicDirectorySearchReply(int queryID, PublicDirSearchReply publicDirSearchReply) {}
			
		});
		
		LocalUser luser=null;
		
		String[] fname = {"january","february","march","april","may","june","july","August","sebptember","octomber","november","december","sunday","monday","tuesday","wednesday","thursday","friday","saturday","ripley","jonathanMores","RickyMartin","SubramaniamSwamy","recardo","Christiano","senthilchengalvarayan","rathisMacGuire","ChristopherReeves","Imissyouverymuchdoyou","santasinghbantasingh","requirethisornot","thisshouldexceed2400mark","theprogramshouldfail","newyorknewyork","chicagorensays","dreaming4u","someoneNowhere","OughttobeOneAmongYOu"};
		try{
			
			int j=10101;
			for(int i=0; i<37; i++){
				luser=new LocalUser();
				luser.setUin(10101+i);
				luser.setFirstName(fname[i]);
				luser.setDisplayName(fname[i]);
				luser.setNickName(fname[i]);
				luser.setEmailAddress(fname[i]+"@month.com");
				luser.setTelephone(fname[i]);
				System.out.println("Before adding USER to ULIST COllection"+i);
				ulist.add(luser);
				
			}
			
			contactListService.exportContactList(ulist);
			
		}catch(Exception e){
			System.out.println("Exception while adding user to the list:"+e);
			
		}
		
	}
	
//	=====================================
	
	
	
	public static void main(String args[]){
		Connect a =new Connect();
		a.E();
		
	}
	
}