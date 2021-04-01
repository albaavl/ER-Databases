package db.jdbc;

import java.sql.*;

public interface SQLInterface {
	
	public Connection connect() throws SQLException, ClassNotFoundException;
	public void create(Connection c) throws SQLException;
	public void delete();
	public void drop();
	void drop(Connection c) throws SQLException;

}
