package pojos.users;

import java.io.*;
import java.util.*;
import javax.persistence.*;



@Entity
@Table(name = "roles")
public class Role implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5005077952154120857L;
	@Id
	@GeneratedValue(generator="roles")
	@TableGenerator(name="roles", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq", pkColumnValue="roles")
	private Integer id;
	private String type;
	@OneToMany(mappedBy = "role")
	private List<User> users;
	
	
	
	public Role(String role) {
		super();
		this.type = role;
		this.users = new ArrayList<User>();
	}

	public Role() {
		super();
		this.users = new ArrayList<User>();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return type;
	}
	public void setRole(String role) {
		this.type = role;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + type + "]";
	}
}
