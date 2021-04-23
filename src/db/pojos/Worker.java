package db.pojos;

import java.rmi.NotBoundException;
import java.util.*;
import javax.persistence.*;
import java.io.*;

@Entity
@Table(name = "workers")
public class Worker implements Serializable{
	
	private static final long serialVersionUID = 5053907057578582101L;
	
	@Id
	@GeneratedValue(generator = "workers")
	@TableGenerator(name = "workers", table = "sqlite_sequence",
		pkColumnName = "id", valueColumnName = "seq", pkColumnValue = "workers")
	
	private Integer workerId;
	//Unique for each doctor - cannot be repeated for another doctor.

	private String workerName;
	private String workerSurname;
	private String specialtyId;
	private int roomEr;
	private String typeWorker;
	//Can be doctor(1), nurse(2), administration staff(3), technician(4)
	
	//Relationship 1-to-1 with Shift
	@OneToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "workerId")
	private Shift shift;
	
	//Relationship 1-to-n with Treatment
	@OneToMany(mappedBy = "patient")
	private Treatment treatment;
	
	//Relationship n-to-n with Patient
	@ManyToMany(mappedBy = "doctors")
	private List<Patient> patients;
	
	public Worker() {
		super();
		this.patients = new ArrayList<Patient>();
	}
	
	public Worker(String workerName, String workerSurname, String specialtyId, int roomEr,
			String typeWorker, Shift shift, Treatment treatment) {
		super();
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.roomEr = roomEr;
		this.typeWorker = typeWorker;
		this.shift = shift;
		this.treatment = treatment;
		this.patients = new ArrayList<Patient>();
	}
	
	public Worker(String workerName, String workerSurname, String specialtyId, int roomEr,
			String typeWorker, Shift shift, Treatment treatment, List<Patient> patients) {
		super();
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.roomEr = roomEr;
		this.typeWorker = typeWorker;
		this.shift = shift;
		this.treatment = treatment;
		this.patients = patients;
	}
	
	public Worker(Integer workerId, String workerName, String workerSurname, String specialtyId, int roomEr,
			String typeWorker) {
		super();
		this.workerId = workerId;
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.roomEr = roomEr;
		this.typeWorker = typeWorker;
		this.patients = new ArrayList<Patient>();
	}
	
	public Worker(Integer workerId, String workerName, String workerSurname, String specialtyId, int roomEr,
			String typeWorker, Shift shift, Treatment treatment) {
		super();
		this.workerId = workerId;
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.roomEr = roomEr;
		this.typeWorker = typeWorker;
		this.shift = shift;
		this.treatment = treatment;
		this.patients = new ArrayList<Patient>();
	}
	
	//Hashcode uses workerId as it is the primary keys, since they are unique
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((workerId == null) ? 0 : workerId.hashCode());
		return result;
	}
	//Equals uses workerId as it is the primary keys, since they are unique
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Worker other = (Worker) obj;
		if (workerId == null) {
			if (other.workerId != null)
				return false;
		} else if (!workerId.equals(other.workerId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Worker [workerId=" + workerId + ", workerName=" + workerName + ", workerSurname=" + workerSurname
				+ ", specialtyId=" + specialtyId + ", roomEr=" + roomEr + ", typeWorker=" + typeWorker + ", treatment = " + treatment + ", shift = " + shift + "]";
	}
	
	//Getters + Setters
	/**
	 * Used to get the id of the worker
	 * @return [Integer] The worker's id
	 */
	public Integer getWorkerId() {
		return workerId;
	}
	/**
	 * Used to set the worker's id
	 * @param workerId - The id of the worker.
	 */
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	/**
	 * Used to get the name of the worker
	 * @return [String] The worker's name
	 */
	public String getWorkerName() {
		return workerName;
	}
	/**
	 * Used to set the worker's name
	 * @param doctorName - The name of the worker.
	 */
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	/**
	 * Used to get the surname of the worker.
	 * @return [String] The worker's surname
	 */
	public String getWorkerSurname() {
		return workerName;
	}
	/**
	 * Used to set the worker's surname.
	 * @param doSurename - The surname of the worker.
	 */
	public void setWorkerSurname(String workerSurname) {
		this.workerSurname = workerSurname;
	}
	/**
	 * Used to get the identification of the specialty of the worker.
	 * @return [Integer] The worker's specialty
	 */
	public String getSpecialtyId() {
		return specialtyId;
	}
	/**
	 * Used to set the worker's specialty
	 * @param specialtyId - The identification of the specialty of the worker.
	 */
	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}
	/**
	 * Used to get the room assigned to the worker.
	 * @return [Integer] The worker's room.
	 */
	public int getRoomEr() {
		return roomEr;
	}
	/**
	 * Used to set the worker's assigned room
	 * @param roomEr - The assigned room of the worker.
	 */
	public void setRoomEr(Integer roomEr) {
		this.roomEr = roomEr;
		//We could limitate it by knowing the number of ER rooms in the hospital
	}
	/**
	 * Used to get the type of worker.
	 * @return [Integer] The worker's type.
	 */
	public String getTypeWorker() {
		return typeWorker;
	}
	/**
	 * Used to set the type of worker
	 * @param typeWorker - Must be doctor, nurse, adStaff or technician.
	 */
	public void setTypeWorker(String typeWorker) throws NotBoundException {
		if(typeWorker.equalsIgnoreCase("DOCTOR")){
			this.typeWorker = typeWorker;
		} else if (typeWorker.equalsIgnoreCase("NURSE")){
			this.typeWorker = typeWorker;
		} else if (typeWorker.equalsIgnoreCase("ADMINISTRATION STAFF")|| typeWorker.equalsIgnoreCase("ADSTAFF")|| typeWorker.equalsIgnoreCase("AD STAFF")){
			this.typeWorker = typeWorker;
		} else if (typeWorker.equalsIgnoreCase("TECHNICIAN")){
			this.typeWorker = typeWorker;
		} else {
			throw new NotBoundException("Incorrect type of worker");
		}
	}
	/**
	 * Used to get the shift of a worker.
	 * @return [Shift] The worker's shift.
	 */
	public Shift getShift() {
		return shift;
	}
	/**
	 * Used to set the shift of a worker
	 * @param shift - The assigned room of the worker.
	 */
	public void setShift(Shift shift) {
		this.shift = shift;
	}
	/**
	 * Used to get the treatment assigned by a worker to a patient.
	 * @return [Treatment] A patient's treatment assigned by the doctor.
	 */
	public Treatment getTreatment() {
		return treatment;
	}
	/**
	 * Used to set the treatment of a patient assigned by a worker.
	 * @param treatment - The treatment assigned to a patient by a worker.
	 */
	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}
	/**
	 * Used to get the list of patients of a worker.
	 * @return [List<Patient>] The list of patients of a worker.
	 */
	public List<Patient> getPatients() {
		return patients;
	}
	public String getPatientstoString() {
		String str = "";
		for(Patient patient : patients) {
			str += patient.toString() + "\n";
		}
		return str;
	}
	/**
	 * Used to set the list of patients of a worker.
	 * @param patients - The list of patients of a worker.
	 */
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	/**
	 * Used to add new patient to the list of patients of the worker. If the patient is already in the list of patients of the worker, the patient will not be added and will notify the worker. 
	 * @param patient - The patient that will be included in the list.
	 */
	public void addPatient (Patient patient) {
		if(!patients.contains(patient)) {
			this.patients.add(patient);
		} else {
			System.out.println("The patient " + patient.getPatientName() + " " + patient.getPatientSurname() + " with medical card: " +patient.getMedicalCardId()+ " is already included.");
		}
	}
	/**
	 * Used to remove a patient from the list of patients of the worker. If the patient is not in the list of patients of the worker, the patient will not be removed and will notify the worker. 
	 * @param patient - The patient that will be removed from the list.
	 */
	public void removePatinet (Patient patient) {
		if(patients.contains(patient)) {
			this.patients.remove(patient);
		} else {
			System.out.println("Patient " + patient.getPatientName() + " " + patient.getPatientSurname() + " with medical card: " +patient.getMedicalCardId()+ " not found.");
		}
	}
	/**
	 * Used to show a list of patients of the worker. 
	 */
	public String showPatient() {
		String str = "";
		for(Patient patient : patients) {
			str += patient.toString() + "\n";
		}
		return str;
	}
}
