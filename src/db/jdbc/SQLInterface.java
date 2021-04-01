package db.jdbc;

import java.sql.*;
import java.util.*;
import db.pojos.*;

public interface SQLInterface {
	
	public Connection connect() throws SQLException, ClassNotFoundException;
	public void create(Connection c) throws SQLException;
	public void delete();
	public void drop();
	void drop(Connection c) throws SQLException;
	public Treatment searchPatientsTreatment(Patient patient);//FUNCION NO CREADA debe buscar los tratamientos de un paciente, ordenarlos por fecha de inicio y devolver una lista
	public void addPatient(Patient patient);
	public List<MedicalTest> searchMedicalTestByMedCardNumber(Integer medCardNumber); //FUNCIÓN NO CREADA debe buscar medical tests por el medical card number del paciente asociaod al test y devolver una lista con todos los test asociados a ese paciente ordenados por fecha, vacia si no hay ninguno
	public List<Treatment> searchTreatmentsByMedCardNumber(Integer medCardNumber); //FUNCION NO CREADA debe buscar los tratamientos asociados a un paciente (se le pasa su medcard numnber) y devolver una lista con estos, vacia si no hay
	public Treatment searchTreatmentsByID(Integer id);//FUNCION NO CREADA debe buscar un treatment por su id y devolverlo, si no hay devolverá null
	public Treatment editTreatment(Integer id, String diagnosis, String medication, Integer duration, String recommendation); //FUNCION NO CREADA debe hacer un update del treatment cuyo id se le pasa, SOLO DEBE CAMBIAR CADA PARÁMETRO si la string que se le pasa no es igual a un 0, debe devolver el nuevo tratamiento
	public void addTreatment(Integer patientID, Treatment treatment); //FUNCION NO CREADA añade un nuevo treatment recibiendo el medcard number del paciente al que va asociado y un objeto treatment, no devuelve nada
	public List<Patient> searchPatient(String surname); //FUNCION NO CREADA debe buscar un paciente pasandole un string del apellido y devolviendo una lista de objetos de tipo paciente que estará vacía si no hay ningún paciente con ese nombre;
	public Patient selectPatient(Integer medCard); //FUNCION NO CREADA debe seleccionar un paciente por el medical card number y devolver un objeto paciente que debe ser null si no existe un paciente con ese medical card number;
	
}
