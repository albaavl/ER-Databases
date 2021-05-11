package pojos.users;

import javax.persistence.*;

public class UserManager implements JPAInterface {

	private static final String PERSISTENCE_PROVIDER = "user-provider";
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
	//LIST METHODS JPA
	//UPDATE METHODS
	//DELETE METHODS JPA
	//SEARCH METHODS
}
