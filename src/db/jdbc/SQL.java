package db.jdbc;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import db.pojos.*;


public class SQL implements SQLInterface{

	@Override
	public Connection connect() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection c = DriverManager.getConnection("jdbc:sqlite:./db/company.db");
		c.createStatement().execute("PRAGMA foreign_keys=ON");
		System.out.println("Database connection opened.");
		return c;
	}
	public void disconnect(Connection c) throws SQLException{
		c.close();
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
				   + "patient_id INTEGER REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL)";
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

	public void addPatient(Connection c, Patient p) throws SQLException{

	}

	public void addMedicalTest(Connection c, MedicalTest medtest) throws SQLException{
	//medical_test id(int) type(s) image(b) result(s) patient_id(int) emp_id(int)
        try {
			Statement statement1 = c.createStatement();
			Blob image = null;
			String result = null; // TODO
			String sq1 = "INSERT INTO medical_test (id, type, image, result, patient_id, emp_id) VALUES ("+medtest.getMedicalTestId()+", " +medtest.getTestType()+", " +image+ ", " +result+ ", " +medtest.getIdPatient()+ ", " +medtest.getIdDoctor()+")";
			statement1.executeUpdate(sq1);
			statement1.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void addTreatment(Connection c, Treatment treatment, Integer patientId) throws SQLException {
	//treatment id(int) medication(s) diagnosis(s) start_date(sqlDate) duration(int) advice(s) patient_id(int)
		try {
			Statement statement1 = c.createStatement();
			String sq1 = "INSERT INTO treatment (id, medication, diagnosis, start_date, duration, advice, patient_id, emp_id) VALUES ("+treatment.getTreatmentId()+", "+treatment.getMedication()+","+treatment.getDiagnosis()+", "+treatment.getDuration()+", "+treatment.getRecommendation()+", "+patientId+")";
			statement1.executeUpdate(sq1);
			statement1.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void drop() {
		// TODO Auto-generated method stub
		
	}

	//Cosas a añadir: 
	//searchPatientTreatment() - buscar los tratamientos de un paciente, ordenarlos por fecha de inicio y devolver una lista
	//searchAmbulance(String licensePlate) - buscar una ambulancia pasandole la matricula y devolver la ambulancia correspondiente, si no existe, devolver null
	//searchMedicalTestBtPatient() - buscar medical tests por el medical card number del paciente asociaod al test y devolver una lista con todos los test asociados a ese paciente ordenados por fecha, null si no hay ninguno
	//searchTreatmentById() - buscar un treatment por su id y devolverlo, si no hay devolver null
	//searchTreatmentByMedCardNum() - buscar los tratamientos asociados a un paciente (se le pasa su medcard numnber) y devolver una lista con estos, null si no hay
	//editTreatment() - update del treatment cuyo id se le pasa, SOLO DEBE CAMBIAR CADA PARaMETRO si la string que se le pasa no es igual a un 0, debe devolver el nuevo tratamiento
	//		sql.editTreatment(t.getID(),diagnosis,medication,duration,recommendation)
	//addTreatment() - añade un nuevo treatment recibiendo el medcard number del paciente al que va asociado y un objeto treatment, no devuelve nada
	//searchPatient(String surename) - buscar un paciente pasandole un string del apellido y devolviendo una lista de objetos de tipo paciente que estar vacia si no hay ningun paciente con ese nombre;
	//selectpatient(Integer medCard) - debe seleccionar un paciente por el medical card number y devolver un objeto paciente que debe ser null si no existe un paciente con ese medical card number;

}
