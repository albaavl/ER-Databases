package db.interfaces;

import java.util.*;
import pojos.users.*;

public interface UserManager {

	public void connect();
	public void disconnect();
	public void newUser(User u);
	public void newRole(Role r);
	public Role getRole(int id);
	public List<Role> getRoles();
	public User checkPassword(String username, String password);
	
}
