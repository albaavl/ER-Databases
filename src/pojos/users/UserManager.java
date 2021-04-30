package pojos.users;

import java.util.List;
import db.pojos.*;
import javax.persistence.*;

public class UserManager implements Interface {

	private static final String PERSISTENCE_PROVIDER = "project-provider";
	private static EntityManager entityManager;
	
	public UserManager() {
		super();
	}
	
	//CONNECTION METHOD JPA
	public boolean Connect() {
		try {
		entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_PROVIDER).createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		entityManager.getTransaction().commit();
		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//DISCONNECTION METHOD JPA
	public boolean Disconnect() {
		try {
			entityManager.close();
			return true;
		} catch (Exception close_error){
			close_error.printStackTrace();
			return false;
		}	
	}
	
	//INSERT METHODS JPA
	
	// Insert patient into the database
		public Patient InsertNewPatient(User user, Category category) {
			try {
				System.out.println("New client: " + entityManager.getTransaction().isActive());
				Patient patient = new Patient();
				patient.set
				client.setUser(user);
				client.setName(user.getUserName());
				entity_manager.getTransaction().begin();
				entity_manager.persist(client);
				entity_manager.getTransaction().commit();
				return client;
			} catch (EntityNotFoundException new_client_account_error) {
				new_client_account_error.printStackTrace();
				return null;
			}
		}
	//LIST METHODS JPA
	//UPDATE METHODS
	//DELETE METHODS JPA
	//SEARCH METHODS
}
