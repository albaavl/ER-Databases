package db.jdbc;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

	public void addPatient(Connection c, Patient p) throws SQLException{
        try {
			String sq1 = "INSERT INTO patient (name, surname, medical card number, gender, blood type, allergies, date of birth, check in date, address, hospitalization) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setString(1, p.getPatientName());
			preparedStatement.setString(2, p.getPatientSurname());
			preparedStatement.setInt(3, p.getMedicalCardId());
			preparedStatement.setString(4, p.getGender());
			preparedStatement.setString(5, p.getBloodType());
			preparedStatement.setString(6, p.getAllergieType());
			preparedStatement.setDate(7, p.getBdate());
			preparedStatement.setDate(8, p.getCheckInDate());
			preparedStatement.setString(9, p.getPatientAddress());
			preparedStatement.setBoolean(10, p.isHospitalized());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addWorker(Connection c, Worker w) throws SQLException{
        try {
			String sq1 = "INSERT INTO workers (name, surname, speciality, room assigned, type, shift) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setString(1, w.getWorkerName());
			preparedStatement.setString(2, w.getWorkerSurname());
			preparedStatement.setString(3, w.getSpecialtyId());
			preparedStatement.setInt(4, w.getRoomEr());
			preparedStatement.setString(5, w.getTypeWorker());
			preparedStatement.setString(6, w.getShift());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addMedicalTest(Connection c, MedicalTest medtest) throws SQLException{
	//medical_test id(int) type(s) image(b) result(s) patient_id(int) emp_id(int)
        try {
			String sq1 = "INSERT INTO medical_test (id, type, image, result, patient_id, emp_id) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setInt(1, medtest.getMedicalTestId());
			preparedStatement.setString(2, medtest.getTestType());
			preparedStatement.setBlob(3, medtest.getTestImage());
			preparedStatement.setBlob(4, medtest.getTestResult());
			preparedStatement.setInt(5, medtest.getIdPatient());
			preparedStatement.setInt(6, medtest.getIdDoctor());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void addTreatment(Connection c, Treatment treatment, Integer patientId) throws SQLException {
	//treatment id(int) medication(s) diagnosis(s) start_date(sqlDate) duration(int) advice(s) patient_id(int)
		try {
			String sq1 = "INSERT INTO treatment (id, medication, diagnosis, start_date, duration, advice, patient_id) VALUES ("+treatment.getTreatmentId()+", "+treatment.getMedication()+","+treatment.getDiagnosis()+", "+treatment.getDuration()+", "+treatment.getRecommendation()+", "+patientId+")";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setInt(1, treatment.getTreatmentId());
			preparedStatement.setString(2, treatment.getMedication());
			preparedStatement.setString(3, treatment.getDiagnosis());
			preparedStatement.setDate(4, treatment.getStartDate());
			preparedStatement.setInt(5, treatment.getDuration());
			preparedStatement.setString(6, treatment.getDiagnosis());
			preparedStatement.setInt(7, patientId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void drop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Treatment> searchPatientsTreatment(Connection c, Patient patient) throws SQLException, Exception {
		String sql = "SELECT * FROM treatment WHERE patient_id = ? ORDER BY start_date";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, patient.getMedicalCardId());
		ResultSet rs = p.executeQuery();
		List <Treatment> tList = new ArrayList<Treatment>();
		while(rs.next()){
			tList.add( new Treatment(rs.getInt("id"), rs.getString("medication"), rs.getString("diagnosis"), rs.getDate("start_date"), rs.getInt("duration"), rs.getString("advice")) );
		}
		p.close();
		rs.close();
		return tList;
	}

	@Override
	public List<MedicalTest> searchMedicalTestByMedCardNumber(Connection c, Integer medCardNumber) throws SQLException, Exception{
		String sql = "SELECT * FROM medical_test WHERE patient_id = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, medCardNumber);
		ResultSet rs = p.executeQuery();
		List <MedicalTest> tList = new ArrayList<MedicalTest>();
		while(rs.next()){
			tList.add( new MedicalTest(rs.getInt("id"), rs.getString("type"), rs.getBlob("image"), rs.getString("result"), medCardNumber, rs.getInt("emp_id"))  );
		}
		p.close();
		rs.close();
		return tList;
	}

	@Override
	public List<Patient> searchPatient(Connection c, String surname) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM patients WHERE surname = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setString(1,"%" + surname + "%");
		ResultSet rs = p.executeQuery();
		List <Patient> pList = new ArrayList<Patient>();
		while(rs.next()){
			pList.add( new Patient(rs.getString("name"), rs.getString("surname"), rs.getString("gender"), rs.getString("blood_type"), rs.getString("allergies"), rs.getString("address"), rs.getDate("birthdate"), rs.getDate("check_in"), rs.getBoolean("hospitalized"), rs.getInt("medical_card_number")) );
		}
		p.close();
		rs.close();
		return pList;
	}

	@Override
	public Patient selectPatient(Connection c, Integer medCard) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM patients WHERE medical_card_number = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,medCard);
		ResultSet rs = p.executeQuery();
		Patient patient = null;
		if(rs.next()){
			patient = new Patient(rs.getString("name"), rs.getString("surname"), rs.getString("gender"), rs.getString("blood_type"), rs.getString("allergies"), rs.getString("address"), rs.getDate("birthdate"), rs.getDate("check_in"), rs.getBoolean("hospitalized"), rs.getInt("medical_card_number")) );
		}
		p.close();
		rs.close();
		return patient;	
	}

	@Override
	public Treatment searchTreatmentsByID(Connection c, Integer id) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM treatment WHERE id = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,id);
		ResultSet rs = p.executeQuery();
		Treatment treatment = null;
		if(rs.next()){
			treatment = new Treatment(id, rs.getString("medication"), rs.getString("diagnosis"), rs.getDate("start_date"), rs.getInt("duration"), rs.getString("advice"));
		p.close();
		rs.close();
		return treatment;	
	}

	//Cosas a añadir: 
	//searchAmbulance(String licensePlate) - buscar una ambulancia pasandole la matricula y devolver la ambulancia correspondiente, si no existe, devolver null
	//searchTreatmentById() - buscar un treatment por su id y devolverlo, si no hay devolver null
	//searchTreatmentByMedCardNum() - buscar los tratamientos asociados a un paciente (se le pasa su medcard numnber) y devolver una lista con estos, null si no hay
	//editTreatment() - update del treatment cuyo id se le pasa, SOLO DEBE CAMBIAR CADA PARaMETRO si la string que se le pasa no es igual a un 0, debe devolver el nuevo tratamiento
	//		sql.editTreatment(t.getID(),diagnosis,medication,duration,recommendation)

}
	@Override
	public Treatment editTreatment(Integer id, String diagnosis, String medication, Integer duration,
			String recommendation) {
		// TODO Auto-generated method stub
		return null;
	}
