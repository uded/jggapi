package pl.mn.communicator.gui;

import java.util.ArrayList;
import java.util.Iterator;

import pl.mn.communicator.IUser;
import pl.mn.communicator.gadu.User;

/**
 * @author mnaglik
 *
 * Wszyscy uzytkownicy aktywni i nieaktywni
 */
public class UsersData{
	private ArrayList users = new ArrayList();

	public UsersData() {
		addUser(new User(1755689, "Autor", false));
	}

	public void addUser(IUser user) {
		users.add(user);
	}

	public void removeUser(IUser user) {
		users.remove(user);
	}

	public void userEntered(IUser user) {
		int i = users.indexOf(user);
		if (i > 0){
//			((User)users.get(i)).setOnLine(true);
		}
	}

	public void userLeaved(IUser user) {
		int i = users.indexOf(user);
		if (i > 0){
//			((User)users.get(i)).setOnLine(false);
		}
	}

	public Iterator getIterator() {
		return users.iterator();
	}

	public int getUserCount() {
		return users.size();
	}
}
