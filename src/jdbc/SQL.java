package jdbc;
import java.sql.*;

public class SQL implements SQLInterface{

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Connection c) throws SQLException {
		// TODO table workers, 
		Statement stmt1 = c.createStatement();
		String sql1 = "CREATE TABLE patients "
				   + "(medical_card_number       INTEGER  PRIMARY KEY,"
				   + " name     TEXT     NOT NULL, "
				   + " surname     TEXT     NOT NULL, "
				   + " gender     TEXT     NOT NULL, "
				   + " birthdate     DATE     NOT NULL, "
				   + " address  TEXT	 NOT NULL,"
				   + " blood_type     TEXT     NOT NULL, "
				   + " allergies     TEXT,"
				   + " check_in    DATE    NOT NULL,"
				   + " hospitalized BOOLEAN,)";
		stmt1.executeUpdate(sql1);
		stmt1.close();
		Statement stmt2 = c.createStatement();
		String sql2 = "CREATE TABLE employees_types "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " type     TEXT     NOT NULL,) ";
		stmt2.executeUpdate(sql2);
		stmt2.close();
		Statement stmt3 = c.createStatement();
		String sql3 = "CREATE TABLE employees "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " surname     TEXT     NOT NULL, "
				   + " gender     TEXT     NOT NULL, "
				   + " speciality   TEXT, "
				   + " room_in_ER   TEXT, "
				   + " shift TEXT NOT NULL, "
				   + " emp_type INTEGER REFERENCES employees types(id) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt3.executeUpdate(sql3);
		stmt3.close();
		Statement stmt4 = c.createStatement();
		String sql4 = "CREATE TABLE doctor-Patient "
				   + "(patient_id     INTEGER  REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " doctor_id   INTEGER  REFERENCES employees(id) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " PRIMARY KEY (patient_id,doctor_id))";
		stmt4.executeUpdate(sql4);
		stmt4.close();
		Statement stmt5 = c.createStatement();
		String sql5 = "CREATE TABLE medical_test "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " type     TEXT     NOT NULL,"
				   + " image    BLOB,"
				   + " result TEXT NOT NULL,"
				   + "patient_id INTEGER REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + "emp_id     INTEGER REFERENCES employees(id) ON UPDATE CASCADE ON DELETE SET NULL) ";
		stmt5.executeUpdate(sql5);
		stmt5.close();
		Statement stmt6 = c.createStatement();
		String sql6 = "CREATE TABLE treatment "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " medication    TEXT     NOT NULL,"
				   + " diagnosis     TEXT     NOT NULL,"
				   + " start_date   DATE      NOT NULL,"
				   + " duration   INTEGER     NOT NULL"
				   + " advice       TEXT      NOT NULL,"
				   + "patient_id INTEGER REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL"
				   + "emp_id     INTEGER REFERENCES employees(id) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt6.executeUpdate(sql6);
		stmt6.close();
		Statement stmt7 = c.createStatement();
		String sql7 = "CREATE TABLE ambulance "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " type     TEXT     NOT NULL,"
				   + " available BOOLEAN NOT NULL,"
				   + "patient_id INTEGER REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL,)";
		stmt7.executeUpdate(sql7);
		stmt7.close();
		//faltan previous pathologies y ambulance staff como fk en employeees
		
		System.out.println("Tables created.");
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop() {
		// TODO Auto-generated method stub
		
	}
	

	
}
