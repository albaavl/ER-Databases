package db.jpa;

import java.util.*;
import pojos.users.*;

public interface UMInterface {

	public void connect();
	public void disconnect();
	public void newUser(User u);
	public void newRole(Role r);
	public Role getRole(int id);
	public List<Role> getRoles();
	public User checkPassword(String username, String password);
	public Role getRoleByName(String name);
	public void deleteUser(User u);
	public User getUser(int userId);
	public void updateUser(User u, byte[] password);
	
}
