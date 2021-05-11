package db.jdbc;
import java.rmi.NotBoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.pojos.*;


public class SQL implements SQLInterface{

	@Override
	public Connection connect() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection c = DriverManager.getConnection("jdbc:sqlite:./db/ER.db");
		c.createStatement().execute("PRAGMA foreign_keys=ON");
		System.out.println("Database connection opened.");
		return c;
	}
	public void disconnect(Connection c) throws SQLException{
		c.close();
	}
	
	@Override
	public void create(Connection c) throws SQLException {
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
				   + " hospitalized BOOLEAN)";
		stmt1.executeUpdate(sql1);
		stmt1.close();
		Statement stmt3 = c.createStatement();
		String sql3 = "CREATE TABLE workers "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " surname     TEXT     NOT NULL, "
				   + " specialty   TEXT, "
				   + " role TEXT NOT NULL,"
				   + " room_in_ER   TEXT)";
		stmt3.executeUpdate(sql3);
		stmt3.close();
		Statement stmt4 = c.createStatement();
		String sql4 = "CREATE TABLE doctor_patient "
				   + "(patient_id     INTEGER  REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " doctor_id   INTEGER  REFERENCES workers(id) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " PRIMARY KEY (patient_id,doctor_id))";
		stmt4.executeUpdate(sql4);
		stmt4.close();
		Statement stmt5 = c.createStatement();		
		String sql5 = "CREATE TABLE medical_tests "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + "patient_id INTEGER REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " type     TEXT     NOT NULL,"				   
				   + " result TEXT  NULL,"
				   + " image    BLOB)";
		stmt5.executeUpdate(sql5);
		stmt5.close();
		Statement stmt6 = c.createStatement();
		String sql6 = "CREATE TABLE treatments "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " diagnosis     TEXT     NOT NULL,"
				   + " medication    TEXT     NOT NULL,"				   
				   + " start_date   DATE      NOT NULL,"
				   + " advice       TEXT      NOT NULL,"
				   + " duration   INTEGER     NOT NULL,"	
				   + " doctor_id   INTEGER  REFERENCES workers(id) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + "patient_id INTEGER REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt6.executeUpdate(sql6);
		stmt6.close(); 
		Statement stmt7 = c.createStatement();
		String sql7 = "CREATE TABLE shifts "
				   + "(id INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + "shift    TEXT  NOT NULL,"
				   + " date     DATE     NOT NULL, "
				   + " room   INTEGER     NOT NULL,"
		 		   + " doctor_id   INTEGER  REFERENCES workers(id) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt7.executeUpdate(sql7);
		stmt7.close();		
		System.out.println("Tables created.");		
	}


	public void addPatient(Connection c, Patient p) throws SQLException{
			String sq1 = "INSERT INTO patients ( medical_card_number, name, surname, gender, birthdate,  address, blood_type, allergies, check_in, hospitalized) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setInt(1, p.getMedicalCardId());
			preparedStatement.setString(2, p.getPatientName());
			preparedStatement.setString(3, p.getPatientSurname());
			preparedStatement.setString(4, p.getGender());
			preparedStatement.setDate(5, p.getbDate());
			preparedStatement.setString(6, p.getPatientAddress());
			preparedStatement.setString(7, p.getBloodType());
			preparedStatement.setString(8, p.getAllergieType());
			preparedStatement.setDate(9, p.getCheckInDate());
			preparedStatement.setBoolean(10, p.getHospitalized());
			preparedStatement.executeUpdate();
			preparedStatement.close();
	}


	public void addWorker(Connection c, Worker w) throws SQLException{
			String sq1 = "INSERT INTO workers (name, surname, specialty, role, room_in_ER) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setString(1, w.getWorkerName());
			preparedStatement.setString(2, w.getWorkerSurname());
			preparedStatement.setString(3, w.getSpecialtyId());
			preparedStatement.setString(4, w.getTypeWorker());
			preparedStatement.setInt(5, w.getRoomEr());
			preparedStatement.executeUpdate();
			preparedStatement.close();
	}


	public void addMedicalTest(Connection c, MedicalTest medtest) throws SQLException{
			String sq1 = "INSERT INTO medical_tests (patient_id, type, result, image) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setInt(1, medtest.getPatientId());
			preparedStatement.setString(2, medtest.getTestType());
			preparedStatement.setString(3, medtest.getTestResult());
			preparedStatement.setBytes(4, medtest.getTestImage());
			preparedStatement.executeUpdate();
			preparedStatement.close();
    }

	public void addTreatment(Connection c, Treatment treatment) throws SQLException {
		String sq1 = "INSERT INTO treatments (diagnosis, medication, start_date, advice, duration, patient_id) VALUES (?,?,?,?,?,?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setString(1, treatment.getDiagnosis());
			preparedStatement.setString(2, treatment.getMedication());			
			preparedStatement.setDate(3, treatment.getStartDate());			
			preparedStatement.setString(4, treatment.getRecommendation());
			preparedStatement.setInt(5, treatment.getDuration());
			preparedStatement.setInt(6, treatment.getPatientId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
	}
	
	public void addShift(Connection c, Shift s) throws SQLException{
		String sq1 = "INSERT INTO shifts (shift, date, room, doctor_id) VALUES (?,?,?,?)";
		PreparedStatement preparedStatement = c.prepareStatement(sq1);
		preparedStatement.setString(1, s.getShift());
		preparedStatement.setDate(2, s.getDate());
		preparedStatement.setInt(3, s.getRoom());
		preparedStatement.setInt(4, s.getWorkerId());
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}

	@Override
	public List<Treatment> searchPatientsTreatment(Connection c, Patient patient, String order) throws SQLException, Exception {
		String sql = "SELECT * FROM treatments WHERE patient_id = ? ORDER BY ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, patient.getMedicalCardId());
		switch (order) {
			case "date":
				p.setString(2, "start_date");
				break;
			case "med":
				p.setString(2, "medication");
				break;
			case "duration":
				p.setString(2, "duration");
				break;

		}
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
		String sql = "SELECT * FROM medical_tests WHERE patient_id = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, medCardNumber);
		ResultSet rs = p.executeQuery();
		List <MedicalTest> mList = new ArrayList<MedicalTest>();
		while(rs.next()){
			mList.add( new MedicalTest(rs.getInt("id"), rs.getDate("date"), rs.getString("type"), rs.getString("result"), rs.getBytes("image"),rs.getInt("patient_id")));
		}
		p.close();
		rs.close();
		return mList;
	}
	
	public List<Shift> searchShiftByWorkerId (Connection c, Integer workerId) throws SQLException, Exception {
		String sql = "SELECT * FROM shifts WHERE workerId = ? ";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, workerId);
		ResultSet rs = p.executeQuery();
		List<Shift> shifts = new ArrayList<>();
		if(rs.next()){ 
			shifts.add(new Shift(rs.getDate("date"), rs.getString("shift"), rs.getInt("room"), workerId));		
		}
		p.close();
		rs.close();
		return shifts;
	}
	
	public List<Shift> searchShiftByDate (Connection c, Integer workerId, Date date) throws SQLException, Exception {
		//TODO hay que editar la funcion o mas bien crear una nueva que busque por id del shift
		String sql = "SELECT * FROM shifts WHERE workerId = ? AND date = ? ";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, workerId);
		p.setDate(2, date);
		ResultSet rs = p.executeQuery();
		List<Shift> shifts = new ArrayList<>();
		if(rs.next()){ 
		shifts.add(new Shift(rs.getDate("date"), rs.getString("shift"), rs.getInt("room"), rs.getInt("doctor_id"))) ;		
		}
		p.close();
		rs.close();
		return shifts;
	}

	@Override
	public List<Patient> searchPatient(Connection c, String surname) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM patients WHERE surname LIKE ?";
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
		String sql = "SELECT * FROM workers WHERE surname LIKE ?";
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
		String sql = "SELECT * FROM patients WHERE medical_card_number = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,medCard);
		ResultSet rs = p.executeQuery();
		Patient patient = null;
		if(rs.next()){
			patient = new Patient(rs.getString("name"), rs.getString("surname"), rs.getString("gender"), rs.getString("blood_type"), rs.getString("allergies"), rs.getString("address"), rs.getDate("birthdate"), rs.getDate("check_in"), rs.getBoolean("hospitalized"), rs.getInt("medical_card_number"));
		}
		p.close();
		rs.close();
		return patient;	
	}

	public Worker selectWorker(Connection c, Integer workerId) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM workers WHERE id = ?";
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
	
	public Shift selectShift(Connection c, Integer shiftId) throws SQLException, Exception {
		String sql = "SELECT * FROM shifts WHERE id = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,shiftId);
		ResultSet rs = p.executeQuery();
		Shift shift = null;
		if(rs.next()){
			shift = new Shift(rs.getDate("date"), rs.getString("shift"), rs.getInt("room"), rs.getInt("doctor_id"), rs.getInt("id"));
		}
		p.close();
		rs.close();
		return shift;
	}
	
	@Override
	public Treatment selectTreatment(Connection c, Integer id) throws Exception {
		String sql = "SELECT * FROM treatments WHERE id = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,id);
		ResultSet rs = p.executeQuery();
		Treatment treatment = null;
		if(rs.next()){
			treatment =  new Treatment(id, rs.getString("diagnosis"), rs.getString("medication"), rs.getDate("start_date"), rs.getString("advice"), rs.getInt("duration"));
		}
		p.close();
		rs.close();
		return treatment;	
	}

	public List<Treatment> searchTreatmentsByMedCard(Connection c, Integer medCard) throws Exception {
		String sql = "SELECT * FROM treatments WHERE patient_id = ?"; 
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, medCard);
		ResultSet rs = p.executeQuery();
		List<Treatment> rList = new ArrayList<Treatment>();
		while (rs.next()) {
			rList.add(new Treatment(rs.getString("diagnosis"), rs.getString("medication"), rs.getDate("start_date"), rs.getString("advice"), rs.getInt("duration")));
		}
		p.close();
		rs.close();
		return rList;	
	}
	

	@Override
	public Treatment editTreatment(Connection c, Integer id, String diagnosis, String medication, Date startDate, Integer duration, String recommendation) throws Exception{
		
		String sql;
		PreparedStatement p = null;

		if(diagnosis != null){

			sql = "UPDATE treatments SET diagnosis = ? WHERE id = ?";
			p = c.prepareStatement(sql);
			p.setString(1, diagnosis);
			p.setInt(2, id);
			p.executeUpdate();
	 
		}
		if(medication != null){

			sql = "UPDATE treatments SET medication = ? WHERE id = ?";
			p = c.prepareStatement(sql);
			p.setString(1, medication);
			p.setInt(2, id);
			p.executeUpdate();
			
		}
		if(duration != null){

			sql = "UPDATE treatments SET duration = ? WHERE id = ?";
			p = c.prepareStatement(sql);
			p.setInt(1, duration);
			p.setInt(2,id);
			p.executeUpdate();

		}
		if(startDate != null){

			sql = "UPDATE treatments SET start_date = ? WHERE id = ?";
			p = c.prepareStatement(sql);
			p.setDate(1, startDate);
			p.setInt(2, id);
			p.executeUpdate();
		
		}
		if(recommendation != null){

			sql = "UPDATE treatments SET advice = ? WHERE id = ?";
			p = c.prepareStatement(sql);
			p.setString(1, recommendation);
			p.setInt(2, id);
			p.executeUpdate();

		}
		Treatment t = new Treatment(this.selectTreatment(c, id));
		p.close();
		return t;
	}

	
	
	public Shift editShift (Connection c,int shiftId, Integer workerId, String shift, Integer room, Date date) throws Exception {
			String sql;
			PreparedStatement p = null;

			if(workerId != null){

				sql = "UPDATE shifts SET workerId = ? WHERE id = ?";
				p = c.prepareStatement(sql);
				p.setInt(1, workerId);
				p.setInt(2, shiftId);
				p.executeUpdate();
		 
			}if(shift != null){

				sql = "UPDATE shifts SET shift = ? WHERE id = ?";
				p = c.prepareStatement(sql);
				p.setString(1, shift);
				p.setInt(2, shiftId);
				p.executeUpdate();
		 
			}if(room != null){

				sql = "UPDATE shifts SET room = ? WHERE id = ?";
				p = c.prepareStatement(sql);
				p.setInt(1, room);
				p.setInt(2, shiftId);
				p.executeUpdate();
		 
			}if(date != null){

				sql = "UPDATE shifts SET date = ? WHERE id = ?";
				p = c.prepareStatement(sql);
				p.setDate(1, date);
				p.setInt(2, shiftId);
				p.executeUpdate();
		 
			}
		
			p.close();
			Shift s = new Shift(this.selectShift(c, shiftId));
			return s;

	}

	
	@Override
	public List<Treatment> searchTreatmentByMed(Connection c,Patient patient, String med) throws Exception{
		String sql = "SELECT * FROM treatments WHERE medication LIKE '%"+ med +"%' AND patient_id = "+patient.getMedicalCardId();
		PreparedStatement prep = c.prepareStatement(sql);		
		ResultSet rs = prep.executeQuery();
		List<Treatment> treatments = new ArrayList<>();
		if(rs.next()) {
			treatments.add(new Treatment(rs.getInt("id"), rs.getString("diagnosis"), rs.getString("medication"), rs.getDate("start_date"), rs.getString("advice"), rs.getInt("duration")));
		}
		prep.close();
		return treatments;
	}
	
	
	}
