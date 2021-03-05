package jdbc;

import java.sql.*;

public interface SQLInterface {
	
	public void connect();
	public void create(Connection c) throws SQLException;
	public void delete();
	public void drop();

}
