package pl.mn.communicator.gui;

import java.util.ArrayList;
import java.util.Iterator;

import pl.mn.communicator.AbstractUser;
import pl.mn.communicator.gadu.User;

/**
 * @author Administrator
 *
 * Wszyscy uzytkownicy aktywni i nieaktywni
 */
public class UsersData{
	private ArrayList users = new ArrayList();

	public UsersData() {
		addUser(new User(1755689, "Autor", false));
	}

	public void addUser(AbstractUser user) {
		users.add(user);
	}

	public void removeUser(AbstractUser user) {
		users.remove(user);
	}

	public void userEntered(AbstractUser user) {
		int i = users.indexOf(user);
		if (i > 0){
//			((User)users.get(i)).setOnLine(true);
		}
	}

	public void userLeaved(AbstractUser user) {
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
