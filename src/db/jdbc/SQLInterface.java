package db.jdbc;

import java.rmi.NotBoundException;
import java.sql.*;
import java.util.*;
import db.pojos.*;

public interface SQLInterface {
	
	public Connection connect() throws SQLException, ClassNotFoundException;
	public void create(Connection c) throws SQLException;
	/**
	 * Used to add a new patient to the database
	 * @param connection - connection of the database
	 * @param patient - the patient to add to the database
	 * @throws SQLException
	 */
	public void addPatient(Connection connection, Patient patient) throws SQLException;
	/**
	 * Used to add a new treatment to the database
	 * @param connection - connection to the database
	 * @param treatment - the treatment that will be added to the database
	 * @param patientID - the patient id that's associated to the treatment.
	 * @throws SQLException
	 */
	public void addTreatment(Connection connection, Treatment treatment, Integer patientID) throws SQLException; 
	
	/**
	 * Used to return the treatments of a patient using the patient id from a patient object
	 * @param c - connetion to the database. 
	 * @param patient - used to get the medicalId and select all of the treatments associated with that id.
	 * @return Returns all of the patient treatments, ordered by their start date. ALl of the treatments are returned inside of a List.
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Treatment> searchPatientsTreatment(Connection c, Patient patient, String order) throws SQLException, Exception;

	/**
	 * 
	 * @param medCardNumber
	 * @return
	 */
	public List<MedicalTest> searchMedicalTestByMedCardNumber(Connection c, Integer medCardNumber) throws SQLException, Exception; 
	public List<Treatment> searchTreatmentsByMedCard(Connection c, Integer medCard) throws SQLException, NotBoundException, Exception;
	public Treatment searchTreatmentsByID(Connection c, Integer id) throws SQLException, NotBoundException, Exception;
	//FUNCION NO CREADA debe buscar un treatment por su id y devolverlo, si no hay devolver� null
	public List<Patient> searchPatient(Connection c, String surname) throws SQLException, NotBoundException; 
	//FUNCION NO CREADA debe buscar un paciente pasandole un string del apellido y devolviendo una lista de objetos de tipo paciente que estar� vac�a si no hay ning�n paciente con ese nombre;
	public Patient selectPatient(Connection c, Integer medCard) throws SQLException, NotBoundException;
	
	public Treatment editTreatment(Integer id, String diagnosis, String medication, Integer duration, String recommendation); //FUNCION NO CREADA debe hacer un update del treatment cuyo id se le pasa, SOLO DEBE CAMBIAR CADA PAR�METRO si la string que se le pasa no es igual a un 0, debe devolver el nuevo tratamiento
	public List<Treatment> searchTreatmentByMed(Connection c,Patient patient, String med);

}
