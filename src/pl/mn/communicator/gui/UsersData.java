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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.PreferenceStore;

import pl.mn.communicator.IUser;
import pl.mn.communicator.gadu.User;

/**
 * Wszyscy uzytkownicy aktywni i nieaktywni
 *
 * @version $Revision: 1.8 $
 * @author mnaglik
 */
public class UsersData{
	private static Logger logger = Logger.getLogger(UsersData.class);
	private ArrayList users = new ArrayList();
	private PreferenceStore store;
	
	private UsersData() {
		store = new PreferenceStore();
		store.setFilename("config.cfg");
		try {
			store.load();
			String users = store.getString("users");
			logger.debug("Users readed: "+users);
			addAllUsers(getUsersFromString(users));
		} catch (IOException e) {
			logger.error(e);
		} finally {
		}
	}

	public void addUser(IUser user) {
		users.add(user);
	}

	public void addAllUsers(Collection collection) {
		users.addAll(collection);	
	}
	
	public void removeUser(IUser user) {
		users.remove(user);
	}

	public void userEntered(IUser user) {
		int i = users.indexOf(user);
		if (i > 0){
			((User)users.get(i)).setOnLine(true);
		}
	}

	public void userLeaved(IUser user) {
		int i = users.indexOf(user);
		if (i > 0){
			((User)users.get(i)).setOnLine(false);
		}
	}

	public Iterator getIterator() {
		return users.iterator();
	}

	public int getUserCount() {
		return users.size();
	}

	public String getUserName(int number) {
		User user = new User(number,"",false);
		int pos;
		if ((pos = users.indexOf(user))<0){
			return ""+number;
		}else{
			return ((User)users.get(pos)).getName();
		}
	}

	public void saveUsers() {
		logger.debug("Saving users");
		String usersString = getStringFromUsers(users);
		store.setValue("users",usersString);
		try {
			store.save();
		} catch (IOException e) {
			logger.error("Error saving users "+e);
		}
	}
	private List getUsersFromString(String usersString){
		ArrayList users = new ArrayList();
		
		StringTokenizer tokenizer = new StringTokenizer(usersString,";");
		while(tokenizer.hasMoreTokens()){
			String userToken = tokenizer.nextToken();
			logger.debug("UserToken: "+userToken);
			StringTokenizer userTokenizer = new StringTokenizer(userToken,",");
			try{
				String userName = userTokenizer.nextToken();
				int userNo = Integer.parseInt(userTokenizer.nextToken());
				users.add(new User(userNo,userName,false));
			}catch(Exception e){
				logger.error("User parsing error: "+userToken+" "+e);
			}
		}
		return users;
	}

	private String getStringFromUsers(List users){
		StringBuffer usersString = new StringBuffer();
		for (Iterator iter = users.iterator(); iter.hasNext();) {
			User element = (User) iter.next();
			usersString.append(element.getName()+","+element.getNumber()+";");
		}
		return usersString.toString();
	}
	
	/*
	 * singleton
	 */
	private static UsersData instance;
	public static UsersData getInstance() {
		if (instance == null)
			instance = new UsersData();
		return instance;
	}
}
