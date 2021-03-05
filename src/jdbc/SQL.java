package jdbc;
import java.sql.*;

public class SQL implements SQLInterface{

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		try {
			//Open database connection
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:./db/company.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			
			// Here is where I do things with the database
			
			//Close database connection
			c.close();
			System.out.println("Database connection closed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void create(Connection c) throws SQLException {
		// TODO table workers, 
		Statement stmt1 = c.createStatement();
		String sql1 = "CREATE TABLE patients "
				   + "(id       INTEGER  PRIMARY KEY,"
				   + " name     TEXT     NOT NULL, "
				   + " surname     TEXT     NOT NULL, "
				   + " gender     TEXT     NOT NULL, "
				   + " blood type     TEXT     NOT NULL, "
				   + " allergies     TEXT     NOT NULL, "
				   + " birthdate     DATE     NOT NULL, "
				   + " address  TEXT	 NOT NULL,"
				   + "a)";
		stmt1.executeUpdate(sql1);
		stmt1.close();
		Statement stmt2 = c.createStatement();
		String sql2 = "CREATE TABLE employees "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " dob      DATE	 NOT NULL, "
				   + " address  TEXT, "
				   + " salary   REAL,"
				   + " photo	BLOB,"
				   + " dep_id	INTEGER REFERENCES departments(id) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt2.executeUpdate(sql2);
		stmt2.close();
		Statement stmt3 = c.createStatement();
		String sql3 = "CREATE TABLE reports "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " content  TEXT  	NOT NULL, "
				   + " date		DATE)";
		stmt3.executeUpdate(sql3);
		stmt3.close();
		Statement stmt4 = c.createStatement();
		String sql4 = "CREATE TABLE authors "
				   + "(report_id     INTEGER  REFERENCES reports(id) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " employee_id   INTEGER  REFERENCES employees(id) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " PRIMARY KEY (report_id,employee_id))";
		stmt4.executeUpdate(sql4);
		stmt4.close();
		System.out.println("Tables created.");
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop (Connection c) throws SQLException {
		// TODO Auto-generated method stub
		Statement stmt1 = c.createStatement();
		String sql1 = "DROP TABLE worker";
		stmt1.executeUpdate(sql1);
		stmt1.close();
		Statement stmt2 = c.createStatement();
		String sql2 = "DROP TABLE patient";
		stmt2.executeUpdate(sql2);
		stmt2.close();
		Statement stmt3 = c.createStatement();
		String sql3 = "DROP TABLE ambulance";
		stmt3.executeUpdate(sql3);
		stmt3.close();
		Statement stmt4 = c.createStatement();
		String sql4 = "DROP TABLE medicalTest";
		stmt4.executeUpdate(sql4);
		stmt4.close();
	}
	

	
}
