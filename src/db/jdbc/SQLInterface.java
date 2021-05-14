package db.jdbc;

import java.rmi.NotBoundException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import db.pojos.*;

public interface SQLInterface {
	public Connection connect() throws SQLException, ClassNotFoundException;
	public void disconnect(Connection c) throws SQLException;
	
	
	public void create(Connection c) throws SQLException;
	
	
	public void addPatient(Connection c, Patient p) throws SQLException;
	public void addWorker(Connection c, Worker w) throws SQLException;
	public void addMedicalTest(Connection c, MedicalTest medtest) throws SQLException;
	public void addTreatment(Connection c, Treatment treatment) throws SQLException;
	public void addShift(Connection c, Shift s) throws SQLException;
	
	
	public List<Treatment> searchPatientsTreatment(Connection c, Patient patient, String order) throws SQLException, Exception;
	public List<MedicalTest> searchMedicalTestByMedCardNumber(Connection c, Integer medCardNumber) throws SQLException, Exception;
	public List<Shift> searchShiftByWorkerId (Connection c, Integer workerId) throws SQLException, Exception;
	public List<Shift> searchShiftByDate (Connection c, Integer workerId, Date date) throws SQLException, Exception;
	public List<Patient> searchPatient(Connection c, String surname) throws SQLException, NotBoundException;
	public List<Worker> searchWorker(Connection c, String surname) throws SQLException, NotBoundException;
	public List<Treatment> searchTreatmentByMed(Connection c,Patient patient, String med) throws Exception;
	public List<Treatment> searchTreatmentsByMedCard(Connection c, Integer medCard) throws Exception;
	
	
	public Patient selectPatient(Connection c, Integer medCard) throws SQLException, NotBoundException;
	public Worker selectWorker(Connection c, Integer workerId) throws SQLException, NotBoundException;
	public Shift selectShift(Connection c, Integer shiftId) throws SQLException, Exception;
	public Treatment selectTreatment(Connection c, Integer id) throws Exception;
	
	
	public Treatment editTreatment(Connection c, Integer id, String diagnosis, String medication, Date startDate, Integer duration, String recommendation) throws Exception;
	public Shift editShift (Connection c,Integer shiftId, Integer workerId, String shift, Integer room, Date date) throws Exception;
	public Patient editPatient(Connection c, Integer medCardNumber, String name, String surname, String gender, String bloodType, String allergies, String address, Date bdate, Date checkIn, boolean hosp) throws Exception;
	
	
	public void deletePatientByMedicalCardId(Connection c, int medCardNumber) throws SQLException;
	public void deleteWorkerById(Connection c, int workerId) throws SQLException;
	public void deleteTreatmentById(Connection c, int treatmentId) throws SQLException;
	public void deleteShiftById(Connection c, int shiftId) throws SQLException;
	
	
	public void createLinkDoctorPatient(Connection c, int medCardNumber, int workerId) throws SQLException;
	public void createLinkUserPatient(Connection c, int userId, int medCardNumber) throws Exception;
	public void createLinkUserWorker(Connection c, int userId, int workerId) throws Exception;
	
	
	public Worker selectWorkerByUserId(Connection c, Integer userID) throws Exception;
	public Patient selectPatientByUserId(Connection c, Integer userId) throws SQLException, NotBoundException;
	
}
