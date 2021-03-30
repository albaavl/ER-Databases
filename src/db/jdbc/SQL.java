package db.jdbc;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
	public void drop (Connection c) throws SQLException {
		// TODO Auto-generated method stub
		Statement stmt1 = c.createStatement();
		String sql1 = "DROP TABLE patients";
		stmt1.executeUpdate(sql1);
		stmt1.close();
		Statement stmt2 = c.createStatement();
		String sql2 = "DROP TABLE employees_types";
		stmt2.executeUpdate(sql2);
		stmt2.close();
		Statement stmt3 = c.createStatement();
		String sql3 = "DROP TABLE employees";
		stmt3.executeUpdate(sql3);
		stmt3.close();
		Statement stmt4 = c.createStatement();
		String sql4 = "DROP TABLE doctor-Patient";
		stmt4.executeUpdate(sql4);
		stmt4.close();
		Statement stmt5 = c.createStatement();
		String sql5 = "DROP TABLE medical_test";
		stmt5.executeUpdate(sql5);
		stmt5.close();
		Statement stmt6 = c.createStatement();
		String sql6 = "DROP TABLE treatment";
		stmt6.executeUpdate(sql6);
		stmt6.close();
		Statement stmt7 = c.createStatement();
		String sql7 = "DROP TABLE ambulance";
		stmt7.executeUpdate(sql7);
		stmt7.close();
	}
	public void insert (Connection c) throws SQLException {
		try {
		// Get the employee info from the command prompt
				System.out.println("Please, input the patient info:");
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Name: ");
				String name = reader.readLine();
				System.out.print("Surname: ");
				String surname = reader.readLine();
				System.out.print("Medical card number: ");
				Integer medCardNumber = reader.read();
				System.out.print("Gender: ");
				String gender = reader.readLine();
				System.out.print("Blood type: ");
				String bloodType = reader.readLine();
				System.out.print("Allergies: ");
				String allergie = reader.readLine();
				System.out.print("Date of birth: ");
				String bDate = reader.readLine(); //should it be a date here
				System.out.print("Check in date: ");
				String checkInDate = reader.readLine(); //should it be a date here
				System.out.print("Address: ");				
				String address = reader.readLine();
				System.out.print("Hospitalization [YES/NO]: ");
				Boolean hospitalized = reader.ready();

				// Insert new record: begin
				Statement stmt1 = c.createStatement();
				String sql = "INSERT INTO patient (name, surname, medical card number, gender, blood type, allergies, date of birth, check in date, address, hospitalization) "
							+ "VALUES ('" + name + "', '" + surname	+ "', '" + medCardNumber +"', '" + gender	+ "', '" + bloodType	+ "', '" + allergie	+ "', '" + bDate	+ "', '" + checkInDate	+ "', '" + address	+ "', '" + hospitalized	+ "');";
				stmt1.executeUpdate(sql);
				stmt1.close();
				System.out.println("Patient info processed");
				System.out.println("Records inserted.");

				// Get the worker info from the command prompt
				System.out.print("Name: ");
				String nameW = reader.readLine();
				System.out.print("Surname: ");
				String surnameW = reader.readLine();
				System.out.print("Specialty: ");
				String specialty = reader.readLine();
				System.out.print("Room assigned: ");
				Integer room = reader.read();
				System.out.print("Type of worker: ");
				String tyoeW = reader.readLine();
				System.out.print("Shift: ");
				String shift = reader.readLine();

				// Insert new record: begin
				Statement stmt2 = c.createStatement();
				String sq2 = "INSERT INTO workers (name, surname, speciality, room assigned, type, shift) "
						+ "VALUES ('" + nameW + "', '" + surnameW	+ "', '" + specialty +"', '" + room	+ "', '" + tyoeW + "', '" + shift + "');";
				stmt2.executeUpdate(sq2);
				stmt2.close();
				System.out.println("Worker info processed");
				System.out.println("Records inserted.");
				
				// Close database connection
				c.close();
				System.out.println("Database connection closed.");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
//SELECT treatment de un paciente 
	@Override
	public void drop() {
		// TODO Auto-generated method stub
		
	}

	
}
