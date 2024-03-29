package db.jpa;


import java.security.*;
import java.util.List;
import javax.persistence.*;

import pojos.users.*;

public class JPAUserManager implements UMInterface {

	private EntityManager em;
	
	@Override
	public void connect() {
		em = Persistence.createEntityManagerFactory("ER-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		List<Role> existingRoles = this.getRoles();
		if(existingRoles.size() < 3) {
			this.newRole(new Role("patient")); 
			this.newRole(new Role("medstaff"));
			this.newRole(new Role("adStaff"));
		}
	}

	@Override
	public void disconnect() {
		em.close();
	}

	@Override
	public void newUser(User u) {
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
	}
	
	@Override
	public User getUser(int userId) {
		Query q = em.createNativeQuery("SELECT * FROM users WHERE userId = ?", User.class);
		q.setParameter(1, userId);
		return (User) q.getSingleResult();
	}
	
	@Override
	public void updateUser(User u, byte[] password) {
		Query q = em.createNativeQuery("SELECT * FROM users WHERE userId = ?", User.class);
		q.setParameter(1, u.getUserId());
		User userToUpdate = (User) q.getSingleResult();
		em.getTransaction().begin();
		userToUpdate.setUsername(u.getUsername());
		userToUpdate.setPassword(password);
		userToUpdate.setRole(u.getRole());
		em.getTransaction().commit();
	}
	
	@Override
	public void deleteUser(User u) {
		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();
	}

	@Override
	public void newRole(Role r) {
		em.getTransaction().begin();
		em.persist(r);
		em.getTransaction().commit();
	}

	@Override
	public Role getRole(int id) {
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE id = ?", Role.class);
		q.setParameter(1, id);
		return (Role) q.getSingleResult();
	}
	
	@Override
	public Role getRoleByName(String name) {
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE ROLE = ?", Role.class);
		q.setParameter(1, name);
		Role role = (Role) q.getSingleResult();
		return role;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Role> getRoles() {
		Query q = em.createNativeQuery("SELECT * FROM roles", Role.class);
		return (List<Role>) q.getResultList();
	}
	

	@Override
	public User checkPassword(String username, String password) {
		User user = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] hash = md.digest();
			Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1", User.class);
			q.setParameter(1, username);
			q.setParameter(2, hash);
			user = (User) q.getSingleResult();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch(NoResultException e) {
			user=null;
		}
		return user;
	}
	
}
