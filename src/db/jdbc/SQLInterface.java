package db.jdbc;

import java.rmi.NotBoundException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import db.pojos.*;

public interface SQLInterface {
	
	public void connect() throws SQLException, ClassNotFoundException;
	public void disconnect() throws SQLException;
	
	
	public void create() throws SQLException;
	
	
	public void addPatient( Patient p) throws SQLException;
	public void addWorker( Worker w) throws SQLException;
	public void addMedicalTest( MedicalTest medtest) throws SQLException;
	public void addTreatment( Treatment treatment) throws SQLException;
	public void addShift( Shift s) throws SQLException;
	
	
	public List<Treatment> searchPatientsTreatment( Patient patient, String order) throws SQLException, Exception;
	public List<MedicalTest> searchMedicalTestByMedCardNumber( Integer medCardNumber) throws SQLException, Exception;
	public List<Shift> searchShiftByWorkerId ( Integer workerId) throws SQLException, Exception;
	public List<Shift> searchShiftByDate ( Integer workerId, Date date) throws SQLException, Exception;
	public List<Patient> searchPatient( String surname) throws SQLException, NotBoundException;
	public List<Worker> searchWorker( String surname) throws SQLException, NotBoundException;
	public List<Treatment> searchTreatmentByMed(Patient patient, String med) throws Exception;
	public List<Treatment> searchTreatmentsByMedCard( Integer medCard) throws Exception;
	
	
	public Patient selectPatient( Integer medCard) throws SQLException, NotBoundException;
	public Worker selectWorker( Integer workerId) throws SQLException, NotBoundException;
	public Shift selectShift( Integer shiftId) throws SQLException, Exception;
	public Treatment selectTreatment( Integer id) throws Exception;
	
	
	public Treatment editTreatment( Integer id, String diagnosis, String medication, Date startDate, Integer duration, String recommendation) throws Exception;
	public Shift editShift( Integer shiftId, Integer workerId, String shift, Integer room, Date date) throws Exception;
	public Patient editPatient( Integer medCardNumber, String name, String surname, String gender, String bloodType, String allergies, String address, Date bdate, Date checkIn, boolean hosp) throws Exception;
	
	
	public void deletePatientByMedicalCardId( int medCardNumber) throws SQLException;
	public void deleteWorkerById( int workerId) throws SQLException;
	public void deleteTreatmentById( int treatmentId) throws SQLException;
	public void deleteShiftById( int shiftId) throws SQLException;
	
	
	public void createLinkDoctorPatient( int medCardNumber, int workerId) throws SQLException;
	public void createLinkUserPatient( int userId, int medCardNumber) throws Exception;
	public void createLinkUserWorker( int userId, int workerId) throws Exception;
	
	
	public Worker selectWorkerByUserId( Integer userID) throws Exception;
	public Patient selectPatientByUserId( Integer userId) throws SQLException, NotBoundException;
	
}
