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
		// TODO 
		Statement stmt1 = c.createStatement();
		String sql1 = "CREATE TABLE patient "
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
		String sql3 = "CREATE TABLE worker "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " surname     TEXT     NOT NULL, "
				   + " specialty   TEXT, "
				   + " room_in_ER   TEXT, "
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
				   + "emp_id     INTEGER REFERENCES employees(id) ON UPDATE CASCADE ON DELETE SET NULL) "
				   + "patient_id INTEGER REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " type     TEXT     NOT NULL,"				   
				   + " result TEXT  NULL,"
				   + " image    BLOB,";
		stmt5.executeUpdate(sql5);
		stmt5.close();
		Statement stmt6 = c.createStatement();
		String sql6 = "CREATE TABLE treatment "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " diagnosis     TEXT     NOT NULL,"
				   + " medication    TEXT     NOT NULL,"				   
				   + " start_date   DATE      NOT NULL,"
				   + " advice       TEXT      NOT NULL,"
				   + " duration   INTEGER     NOT NULL"				   
				   + "patient_id INTEGER REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt6.executeUpdate(sql6);
		stmt6.close(); 
		Statement stmt7 = c.createStatement();
		String sql7 = "CREATE TABLE shift "
				   + "(shift       TEXT  PRIMARY KEY,"
				   + " date     DATE     NOT NULL, "
				   + " room   INTEGER     NOT NULL"
		 		   + " doctor_id   INTEGER  REFERENCES employees(id) ON UPDATE CASCADE ON DELETE SET NULL";
		stmt7.executeUpdate(sql7);
		stmt7.close();		
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
		String sql1 = "DROP TABLE patient";
		stmt1.executeUpdate(sql1);
		stmt1.close();
		Statement stmt2 = c.createStatement();
		String sql2 = "DROP TABLE employees_types";
		stmt2.executeUpdate(sql2);
		stmt2.close();
		Statement stmt3 = c.createStatement();
		String sql3 = "DROP TABLE worker";
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
		String sql7 = "DROP TABLE shift";
		stmt7.executeUpdate(sql7);
		stmt7.close();
	}

	public void addPatient(Connection c, Patient p) throws SQLException{
        try {
			String sq1 = "INSERT INTO patient ( medical card number, name, surname, gender, date of birth,  address, blood type, allergies, check in date, hospitalization) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			String sq1 = "INSERT INTO worker (id, name, surname, specialty, room_in_ER, type, emp_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setInt(1, w.getWorkerId());
			preparedStatement.setString(2, w.getWorkerName());
			preparedStatement.setString(3, w.getWorkerSurname());
			preparedStatement.setString(4, w.getSpecialtyId());
			preparedStatement.setInt(5, w.getRoomEr());
			preparedStatement.setString(6, w.getTypeWorker());
			preparedStatement.setString(7, w.getTypeWorker());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addMedicalTest(Connection c, MedicalTest medtest) throws SQLException{
        try {
			String sq1 = "INSERT INTO medical_test (id, emp_id,  patient_id, type, result, image) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setInt(1, medtest.getMedicalTestId());
			preparedStatement.setInt(2, medtest.getIdDoctor());
			preparedStatement.setInt(3, medtest.getIdPatient());
			preparedStatement.setString(4, medtest.getTestType());
			preparedStatement.setString(5, medtest.getTestResult());
			preparedStatement.setBlob(6, medtest.getTestImage());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void addTreatment(Connection c, Treatment treatment, Integer patientId) throws SQLException {
		try {
			String sq1 = "INSERT INTO treatment (id, diagnosis, medication, start_date, advice, duration, patient_id) VALUES ("+treatment.getTreatmentId()+", "+treatment.getDiagnosis()+", "+treatment.getMedication()+", "+treatment.getRecommendation()+", "+treatment.getDuration()+", "+patientId+")";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setInt(1, treatment.getTreatmentId());
			preparedStatement.setString(2, treatment.getDiagnosis());
			preparedStatement.setString(3, treatment.getMedication());			
			preparedStatement.setDate(4, treatment.getStartDate());			
			preparedStatement.setString(5, treatment.getDiagnosis());
			preparedStatement.setInt(6, treatment.getDuration());
			preparedStatement.setInt(7, patientId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Treatment> searchPatientsTreatment(Connection c, Patient patient) throws SQLException, Exception {
		String sql = "SELECT * FROM treatment WHERE patient_id = ? ORDER BY start_date";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, patient.getMedicalCardId());
		ResultSet rs = p.executeQuery();
		List <Treatment> tList = new ArrayList<Treatment>();
		while(rs.next()){
			tList.add( new Treatment(rs.getInt("id"), rs.getString("diagnosis"), rs.getString("medication"), rs.getDate("start_date"), rs.getString("advice"), rs.getInt("duration")) );
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
		List <MedicalTest> mList = new ArrayList<MedicalTest>();
		while(rs.next()){
			mList.add( new MedicalTest(rs.getInt("id"), rs.getInt("emp_id"), medCardNumber, rs.getString("type"), rs.getString("result"), rs.getBlob("image"))  );
		}
		p.close();
		rs.close();
		return mList;
	}
	
	public Shift searchShiftByWorkerId (Connection c, Integer workerId) throws SQLException, Exception {
		String sql = "SELECT * FROM shift WHERE workerId = ? ";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, workerId);
		ResultSet rs = p.executeQuery();
		Shift s = null;
		if(rs.next()){ 
			s = new Shift(rs.getDate("date"), rs.getString("shift"), rs.getInt("room"), workerId);		
		}
		p.close();
		rs.close();
		return s;
	}
	
	public Shift searchShiftByDate (Connection c, Integer workerId, Date date) throws SQLException, Exception {
		String sql = "SELECT * FROM shift WHERE workerId = ?, date = ? ";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, workerId);
		p.setDate(2, date);
		ResultSet rs = p.executeQuery();
		Shift s = null;
		if(rs.next()){ 
			s = new Shift(rs.getDate("date"), rs.getString("shift"), rs.getInt("room"), rs.getInt("doctor_id"));		
		}
		p.close();
		rs.close();
		return s;
	}

	@Override
	public List<Patient> searchPatient(Connection c, String surname) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM patient WHERE surname = ?";
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
	
	public List<Worker> searchWorker(Connection c, String surname) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM worker WHERE surname = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setString(1,"%" + surname + "%");
		ResultSet rs = p.executeQuery();
		List <Worker> wList = new ArrayList<Worker>();
		while(rs.next()){ 
			wList.add( new Worker(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("specialty"), rs.getInt("room_in_ER"), rs.getString("type")) );
		}
		p.close();
		rs.close();
		return wList;
	}

	@Override
	public Patient selectPatient(Connection c, Integer medCard) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM patient WHERE medical_card_number = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,medCard);
		ResultSet rs = p.executeQuery();
		Patient patient = null;
		if(rs.next()){
			patient = new Patient(rs.getString("name"), rs.getString("surname"), rs.getString("gender"), rs.getString("blood_type"), rs.getString("allergies"), rs.getString("address"), rs.getDate("birthdate"), rs.getDate("check_in"), rs.getBoolean("hospitalized"), rs.getInt("medical_card_number"));
		//creo que sobra el getmedicalcard, pues es como lo busca, no le hace falta verlo de nuevo. Si es así hay que crear un nuevo constructor sin la medical card
		}
		p.close();
		rs.close();
		return patient;	
	}

	public Worker selectWorker(Connection c, Integer workerId) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM worker WHERE id = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,workerId);
		ResultSet rs = p.executeQuery();
		Worker worker = null;
		if(rs.next()){
			worker = new Worker(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("specialty"), rs.getInt("room_in_ER"), rs.getString("type"));
		}
		p.close();
		rs.close();
		return worker;	
	}
	
	@Override
	public Treatment searchTreatmentsByID(Connection c, Integer id) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM treatment WHERE id = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,id);
		ResultSet rs = p.executeQuery();
		Treatment treatment = null;
		if(rs.next()){
			try {
				treatment =  new Treatment(id, rs.getString("diagnosis"), rs.getString("medication"), rs.getDate("start_date"), rs.getString("advice"), rs.getInt("duration"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		p.close();
		rs.close();
		return treatment;	
	}

	public Treatment searchTreatmentsByMedCard(Connection c, Integer medCard) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM patients WHERE medical_card_number = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,medCard);
		ResultSet rs = p.executeQuery();
		Treatment treatment = null;
		if(rs.next()){
			try {
				treatment = new Treatment(rs.getString("diagnosis"), rs.getString("medication"), rs.getDate("start_date"), rs.getString("advice"), rs.getInt("duration"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//TENEMOS QUE REVISAR TODO EL TEMA DE EXCEPCIONES
			}
		}
		p.close();
		rs.close();
		return treatment;	
	}
	
	//igual habría que hacer un edit para cada cosa del treatment, sino se cambiarían todos
	public void editTreatment(Connection c, int id, String diagnosis, String medication, Date startDate, Integer duration, String recommendation) {
		try {
			String sql = "UPDATE treatment SET diagnosis = ?, medication = ?, start_date = ?, advice = ?, duration = ? ";
			Treatment t = searchTreatmentsByID(c, id);
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, diagnosis);
			prep.setString(2, medication);
			prep.setDate(3, startDate);			
			prep.setInt(4, duration);		
			prep.setString(5, recommendation);	
			prep.setInt(6, t.getDuration());
			prep.executeUpdate();
			prep.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void editShift (Connection c, int workerId, String shift, int room, Date date) {
		try {
			String sql = "UPDATE treatment SET shift = ?, room = ? WHERE doctor_id = ?, date = ? ";
			Shift s = searchShiftByDate(c, workerId, date);
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, shift);
			prep.setInt(2, room);
			prep.setInt(3, workerId);			
			prep.setDate(4, date);		
			prep.executeUpdate();
			prep.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
	@Override
	public Treatment editTreatment(Integer id, String diagnosis, String medication, Integer duration,
			String recommendation) {
		// TODO Auto-generated method stub
		return null;
	}
