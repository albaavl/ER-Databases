package db.jdbc;
import java.io.File;
import java.rmi.NotBoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.pojos.*;


public class SQL implements SQLInterface{

	private static Connection c;

	@Override
	public void connect() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:./db/ER.db");
		c.createStatement().execute("PRAGMA foreign_keys=ON");
		System.out.println("Database connection opened.");
	}
	
	@Override
	public void disconnect() throws SQLException{
		c.close();
	}
	
	@Override
	public void create() throws SQLException {
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
				   + " hospitalized BOOLEAN,"
				   + " userId INTEGER REFERENCES users(USERID) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt1.executeUpdate(sql1);
		stmt1.close();
		Statement stmt3 = c.createStatement();
		String sql3 = "CREATE TABLE workers "
				   + "(workerId       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " workerName     TEXT     NOT NULL, "
				   + " workerSurname     TEXT     NOT NULL, "
				   + " specialtyId   TEXT, "
				   + " typeWorker TEXT NOT NULL,"
				   + " userId INTEGER REFERENCES users(userId) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt3.executeUpdate(sql3);
		stmt3.close();
		Statement stmt4 = c.createStatement();
		String sql4 = "CREATE TABLE doctor_patient "
				   + "(patient_id     INTEGER  REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " doctor_id   INTEGER  REFERENCES workers(workerId) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " PRIMARY KEY (patient_id,doctor_id))";
		stmt4.executeUpdate(sql4);
		stmt4.close();
		Statement stmt5 = c.createStatement();		
		String sql5 = "CREATE TABLE medical_tests "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " patient_id INTEGER REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL,"
				   + " type     TEXT     NOT NULL,"				   
				   + " date     DATE     NOT NULL,"				   
				   + " result TEXT  NULL)";
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
				   + " patient_id INTEGER REFERENCES patients(medical_card_number) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt6.executeUpdate(sql6);
		stmt6.close(); 
		Statement stmt7 = c.createStatement();
		String sql7 = "CREATE TABLE shifts "
				   + "(shiftId INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " date     DATE     NOT NULL, "
				   + " turn    TEXT  NOT NULL,"
				   + " room   INTEGER     NOT NULL,"
		 		   + " doctor_id   INTEGER  REFERENCES workers(workerId) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt7.executeUpdate(sql7);
		stmt7.close();		
		System.out.println("Tables created.");		
	}

	@Override
	public void addPatient(Patient p) throws SQLException{
		if (p.getAllergieType()==null) {
			String sq1 = "INSERT INTO patients ( medical_card_number, name, surname, gender, birthdate,  address, blood_type, check_in, hospitalized) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = c.prepareStatement(sq1);
			preparedStatement.setInt(1, p.getMedicalCardId());
			preparedStatement.setString(2, p.getPatientName());
			preparedStatement.setString(3, p.getPatientSurname());
			preparedStatement.setString(4, p.getGender());
			preparedStatement.setDate(5, p.getbDate());
			preparedStatement.setString(6, p.getPatientAddress());
			preparedStatement.setString(7, p.getBloodType());
			preparedStatement.setDate(8, p.getCheckInDate());
			preparedStatement.setBoolean(9, p.getHospitalized());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		}else {
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
	}

	@Override
	public void addWorker(Worker w) throws SQLException{
		String sq1 = "INSERT INTO workers (workerName, workerSurname, specialtyId, typeWorker, userId) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = c.prepareStatement(sq1);
		preparedStatement.setString(1, w.getWorkerName());
		preparedStatement.setString(2, w.getWorkerSurname());
		preparedStatement.setString(3, w.getSpecialtyId());
		preparedStatement.setString(4, w.getTypeWorker());
		preparedStatement.setInt(5, w.getUserId());
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}

	@Override
	public void addMedicalTest(MedicalTest medtest) throws SQLException{
		String sq1 = "INSERT INTO medical_tests (patient_id, type, date, result) VALUES (?, ?, ?, ?)";
		PreparedStatement preparedStatement = c.prepareStatement(sq1);
		preparedStatement.setInt(1, medtest.getPatientId());
		preparedStatement.setString(2, medtest.getTestType());
		preparedStatement.setString(3, medtest.getTestResult());
		preparedStatement.setDate(4, medtest.getDateMedTest());
		preparedStatement.executeUpdate();
		preparedStatement.close();
    }

	@Override
	public void addTreatment(Treatment treatment) throws SQLException {
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
	
@Override
	public void addShift(Shift s) throws SQLException{
		String sq1 = "INSERT INTO shifts (date, turn, room, doctor_id) VALUES (?,?,?,?)";
		PreparedStatement preparedStatement = c.prepareStatement(sq1);
		preparedStatement.setString(1, s.getTurn());
		preparedStatement.setDate(2, s.getDate());
		preparedStatement.setInt(3, s.getRoom());
		preparedStatement.setInt(4, s.getWorker().getWorkerId());
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}

	@Override
	public List<Treatment> searchPatientsTreatment(Patient patient, String order) throws SQLException, Exception {
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
			tList.add( new Treatment(rs.getInt("id"), rs.getString("diagnosis"), rs.getString("medication"), rs.getDate("start_date"), rs.getString("advice"), rs.getInt("duration"), patient.getMedicalCardId()) );
		}
		p.close();
		rs.close();
		return tList;
	}

	@Override 
	public List<MedicalTest> searchMedicalTestByMedCardNumber(Integer medCardNumber) throws SQLException, Exception{
		String sql = "SELECT * FROM medical_tests WHERE patient_id = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, medCardNumber);
		ResultSet rs = p.executeQuery();
		List <MedicalTest> mList = new ArrayList<MedicalTest>();
		while(rs.next()){
			mList.add( new MedicalTest(rs.getInt("id"), rs.getDate("date"), rs.getString("type"), rs.getString("result"),rs.getInt("patient_id")));
		}
		p.close();
		rs.close();
		return mList;
	}
	
	@Override
	public List<Shift> searchShiftByWorkerId (Integer workerId) throws SQLException, Exception {
		String sql = "SELECT * FROM shifts WHERE doctor_id = ? ";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, workerId);
		ResultSet rs = p.executeQuery();
		List<Shift> shifts = new ArrayList<>();
		Worker w = selectWorker(workerId);
		if(rs.next()){ 
			shifts.add(new Shift(rs.getDate("date"), rs.getInt("room"), rs.getString("turn"), w, rs.getInt("shiftId")));		
		}
		p.close();
		rs.close();
		return shifts;
	}
	
	@Override
	public List<Shift> searchShiftByDate (Integer workerId, Date date) throws SQLException, Exception {
		String sql = "SELECT * FROM shifts WHERE doctor_id = ? AND date = ? ";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, workerId);
		p.setDate(2, date);
		ResultSet rs = p.executeQuery();
		List<Shift> shifts = new ArrayList<>();
		if(rs.next()){ 
		shifts.add(new Shift(rs.getDate("date"), rs.getInt("room"), rs.getString("turn"), rs.getInt("doctor_id"))) ;		
		}
		p.close();
		rs.close();
		return shifts;
	}

	@Override
	public List<Patient> searchPatient(String surname) throws SQLException, NotBoundException {
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
	
	@Override
	public List<Worker> searchWorker(String surname) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM workers WHERE workerSurname LIKE ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setString(1,"%" + surname + "%");
		ResultSet rs = p.executeQuery();
		List <Worker> wList = new ArrayList<Worker>();
		while(rs.next()){ 
			wList.add( new Worker(rs.getInt("workerId"), rs.getString("workerName"), rs.getString("workerSurname"), rs.getString("specialtyId"), rs.getString("typeWorker")) );
		}
		p.close();
		rs.close();
		return wList;
	}

	@Override
	public Patient selectPatient(Integer medCard) throws SQLException, NotBoundException {
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

	@Override
	public Worker selectWorker(Integer workerId) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM workers WHERE workerId = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,workerId);
		ResultSet rs = p.executeQuery();
		Worker worker = null;
		if(rs.next()){
			worker = new Worker(rs.getInt("workerId"), rs.getString("workerName"), rs.getString("workerSurname"), rs.getString("specialtyId"), rs.getString("typeWorker"));
		}
		p.close();
		rs.close();
		return worker;	
	}
	
	@Override
	public Shift selectShift(Integer shiftId) throws SQLException, Exception {
		String sql = "SELECT * FROM shifts WHERE shiftId = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,shiftId);
		ResultSet rs = p.executeQuery();
		Shift shift = null;
		if(rs.next()){
			shift = new Shift(rs.getDate("date"), rs.getInt("room"), rs.getString("turn"), rs.getInt("shiftId"));
		}
		p.close();
		rs.close();
		return shift;
	}
	
	@Override
	public Treatment selectTreatment(Integer id) throws Exception {
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

	@Override
	public List<Treatment> searchTreatmentsByMedCard(Integer medCard) throws Exception {
		String sql = "SELECT * FROM treatments WHERE patient_id = ?"; 
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1, medCard);
		ResultSet rs = p.executeQuery();
		List<Treatment> rList = new ArrayList<Treatment>();
		while (rs.next()) {
			rList.add(new Treatment(rs.getString("diagnosis"), rs.getString("medication"), rs.getDate("start_date"), rs.getString("advice"), rs.getInt("duration"), medCard));
		}
		p.close();
		rs.close();
		return rList;	
	}

	@Override
	public Treatment editTreatment(Integer id, String diagnosis, String medication, Date startDate, Integer duration, String recommendation) throws Exception{
		
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
		Treatment t = new Treatment(this.selectTreatment(id));
		p.close();
		return t;
	}

	@Override
	public Shift editShift (Integer shiftId, Integer workerId, String shift, Integer room, Date date) throws Exception {
			String sql;
			PreparedStatement p = null;

			if(workerId != null){

				sql = "UPDATE shifts SET doctor_id = ? WHERE shiftId = ?";
				p = c.prepareStatement(sql);
				p.setInt(1, workerId);
				p.setInt(2, shiftId);
				p.executeUpdate();
		 
			}if(shift != null){

				sql = "UPDATE shifts SET shift = ? WHERE shiftId = ?";
				p = c.prepareStatement(sql);
				p.setString(1, shift);
				p.setInt(2, shiftId);
				p.executeUpdate();
		 
			}if(room != null){

				sql = "UPDATE shifts SET room = ? WHERE shiftId = ?";
				p = c.prepareStatement(sql);
				p.setInt(1, room);
				p.setInt(2, shiftId);
				p.executeUpdate();
		 
			}if(date != null){

				sql = "UPDATE shifts SET date = ? WHERE shiftId = ?";
				p = c.prepareStatement(sql);
				p.setDate(1, date);
				p.setInt(2, shiftId);
				p.executeUpdate();
		 
			}
		
			p.close();
			Shift s = new Shift(this.selectShift(shiftId));
			return s;

	}

	
	@Override
	public List<Treatment> searchTreatmentByMed(Patient patient, String med) throws Exception{
		String sql = "SELECT * FROM treatments WHERE medication LIKE '%?%' AND patient_id = ?";
		PreparedStatement prep = c.prepareStatement(sql);
		prep.setString(1, med);
		prep.setInt(2, patient.getMedicalCardId());
		ResultSet rs = prep.executeQuery();
		List<Treatment> treatments = new ArrayList<>();
		if(rs.next()) {
			treatments.add(new Treatment(rs.getInt("id"), rs.getString("diagnosis"), rs.getString("medication"), rs.getDate("start_date"), rs.getString("advice"), rs.getInt("duration"), patient.getMedicalCardId()));
		}
		prep.close();
		return treatments;
	}
	
	/**
	 * Deletes any patient whose id matches the given medCardNumber
	 * @param c - Database connection.
	 * @param medCardNumber - The id from the patient that will be deleted. (int)
	 * @throws SQLException
	 */
	
	@Override
	public void deletePatientByMedicalCardId(int medCardNumber) throws SQLException{
		String sql = "DELETE FROM patients WHERE medical_card_number = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setInt(1, medCardNumber);
		pStatement.executeUpdate();
		pStatement.close();
	}

	/**
	 * Deletes any worker with an id that matches the given id.
	 * @param c - Database connection.
	 * @param workerId - Id from the worker that will be deleted. (int)
	 * @throws SQLException
	 */
	
	@Override
	public void deleteWorkerById(int workerId) throws SQLException {
		String sql = "DELETE FROM workers WHERE workerId = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setInt(1, workerId);
		pStatement.executeUpdate();
		pStatement.close();
	}

	/**
	 * @param c - Database connection
	 * @param treatmentId - Id from the treatment that will be deleted. (int)
	 * @throws SQLException
	 */
	
	@Override
	public void deleteTreatmentById(int treatmentId) throws SQLException {
		String sql = "DELETE FROM treatments WHERE id = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setInt(1, treatmentId);
		pStatement.executeUpdate();
		pStatement.close();
	}

	/**
	 * @param c - Database connection.
	 * @param shiftId - Id from the shift that you want to delete. (int)
	 * @throws SQLException
	 */
	
	@Override
	public void deleteShiftById(int shiftId) throws SQLException {
		String sql = "DELETE FROM shifts WHERE shiftId = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setInt(1, shiftId);
		pStatement.executeUpdate();
		pStatement.close();
	}

	/**
	 * Creates a connection between a patient and a doctor. 
	 * @param c - Database connection.
	 * @param medCardNumber - the id of the patient that will be connected to a doctor. (int)
	 * @param workerId - the id of the doctor that will be linked to the patient. (int)
	 * @throws SQLException
	 */
	@Override
	public void createLinkDoctorPatient(int medCardNumber, int workerId) throws SQLException {
		String sql = "INSERT INTO doctor_patient (patient_id, doctor_id) VALUES (?,?)";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setInt(1, medCardNumber);
		pStatement.setInt(2, workerId);
		pStatement.executeUpdate();
		pStatement.close();
	}
	
	@Override
	public void createLinkUserPatient(int userId, int medCardNumber) throws Exception {
		String sql1 = "UPDATE patients SET userId = ? WHERE medical_card_number = ? ";
		PreparedStatement pStatement = c.prepareStatement(sql1);
		pStatement.setInt(1, userId);
		pStatement.setInt(2, medCardNumber);
		pStatement.executeUpdate();
		pStatement.close();
	}
	
	@Override
	public void createLinkUserWorker(int userId, int workerId) throws Exception {
		String sql1 = "UPDATE workers SET userId = ? WHERE workerId = ? ";
		PreparedStatement pStatement = c.prepareStatement(sql1);
		pStatement.setInt(1, userId);
		pStatement.setInt(2, workerId);
		pStatement.executeUpdate();
		pStatement.close();
	}
	
	@Override
	public Worker selectWorkerByUserId(Integer userID) throws Exception {
		String sql = "SELECT * FROM workers WHERE userId = ? ";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setInt(1, userID);
		ResultSet rs = pStatement.executeQuery();
		Worker worker = null;
		if(rs.next()){
			worker = new Worker(rs.getInt("workerId"), rs.getString("workerName"), rs.getString("workerSurname"), rs.getString("specialtyId"),  rs.getString("typeWorker"));
		}
		pStatement.close();
		rs.close();
		return worker;
	}
	
	@Override
	public Patient selectPatientByUserId(Integer userId) throws SQLException, NotBoundException {
		String sql = "SELECT * FROM patients userId = ?";
		PreparedStatement p = c.prepareStatement(sql);
		p.setInt(1,userId);
		ResultSet rs = p.executeQuery();
		Patient patient = null;
		if(rs.next()){
			patient = new Patient(rs.getString("name"), rs.getString("surname"), rs.getString("gender"), rs.getString("blood_type"), rs.getString("allergies"), rs.getString("address"), rs.getDate("birthdate"), rs.getDate("check_in"), rs.getBoolean("hospitalized"), rs.getInt("medical_card_number"));
		}
		p.close();
		rs.close();
		return patient;	
	}

	public void updatePatientName(int medCardNum, String name) throws SQLException {
		String sql = "UPDATE patients SET name = ? WHERE medical_card_number = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setString(1, name);
		pStatement.setInt(2, medCardNum);
		pStatement.executeUpdate();
		pStatement.close();
	}
	public void updatePatientSurname(int medCardNum, String surname) throws SQLException {
		String sql = "UPDATE patients SET surname = ? WHERE medical_card_number = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setString(1, surname);
		pStatement.setInt(2, medCardNum);
		pStatement.executeUpdate();
		pStatement.close();
	}
	public void updatePatientGender(int medCardNum, String gender) throws SQLException {
		String sql = "UPDATE patients SET gender = ? WHERE medical_card_number = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setString(1, gender);
		pStatement.setInt(2, medCardNum);
		pStatement.executeUpdate();
		pStatement.close();
	}
	public void updatePatientBloodType(int medCardNum, String bloodType) throws SQLException {
		String sql = "UPDATE patients SET blood_type = ? WHERE medical_card_number = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setString(1, bloodType);
		pStatement.setInt(2, medCardNum);
		pStatement.executeUpdate();
		pStatement.close();
	}
	public void updatePatientAllergies(int medCardNum, String allergies) throws SQLException {
		String sql = "UPDATE patients SET allergies = ? WHERE medical_card_number = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setString(1, allergies);
		pStatement.setInt(2, medCardNum);
		pStatement.executeUpdate();
		pStatement.close();
	}
	public void updatePatientBirthDate(int medCardNum, Date bDate) throws SQLException {
		String sql = "UPDATE patients SET birthdate = ? WHERE medical_card_number = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setDate(1, bDate);
		pStatement.setInt(2, medCardNum);
		pStatement.executeUpdate();
		pStatement.close();
	}
	public void updatePatientCheckInDate(int medCardNum, Date checkInDate) throws SQLException {
		String sql = "UPDATE patients SET check_in = ? WHERE medical_card_number = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setDate(1, checkInDate);
		pStatement.setInt(2, medCardNum);
		pStatement.executeUpdate();
		pStatement.close();
	}
	public void updatePatientAddress(int medCardNum, String address) throws SQLException {
		String sql = "UPDATE patients SET address = ? WHERE medical_card_number = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setString(1, address);
		pStatement.setInt(2, medCardNum);
		pStatement.executeUpdate();
		pStatement.close();
	}
	public void updatePatientHospitalization(int medCardNum, Boolean hospitalization) throws SQLException {
		String sql = "UPDATE patients SET hospitalized = ? WHERE medical_card_number = ?";
		PreparedStatement pStatement = c.prepareStatement(sql);
		pStatement.setBoolean(1, hospitalization);
		pStatement.setInt(2, medCardNum);
		pStatement.executeUpdate();
		pStatement.close();
	}

	@Override
	public Patient editPatient(Integer medCardNum, String name, String surname, String gender,
			String bloodType, String allergies, String address, Date bdate, Date checkInDate, boolean hosp)
			throws Exception {

		String sql;
		PreparedStatement pStatement;

		if (name != null) {
			sql = "UPDATE patients SET name = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, name);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();	
		} 
		if (surname != null) {
			sql = "UPDATE patients SET surname = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, surname);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();
		}
		if (gender != null) {
			sql = "UPDATE patients SET gender = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, gender);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();	
		}
		if (bloodType != null) {
			sql = "UPDATE patients SET blood_type = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, bloodType);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();
		}
		if (allergies != null) {
			sql = "UPDATE patients SET allergies = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setString(1, allergies);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();
		}
		if (bdate != null) {
			sql = "UPDATE patients SET birthdate = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setDate(1, bdate);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();
		}

		if (checkInDate != null) {
			sql = "UPDATE patients SET check_in = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setDate(1, checkInDate);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();
	
		}
		
		try {
			sql = "UPDATE patients SET hospitalized = ? WHERE medical_card_number = ?";
			pStatement = c.prepareStatement(sql);
			pStatement.setBoolean(1, hosp);
			pStatement.setInt(2, medCardNum);
			pStatement.executeUpdate();
	
		} catch (Exception e) {
		}
		
		return null;
	}
}
