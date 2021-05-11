package db.pojos;

import java.rmi.NotBoundException;
import java.util.*;
import javax.persistence.*;
import java.io.*;


public class Worker implements Serializable{
	
	private static final long serialVersionUID = 5053907057578582101L;

	private Integer workerId;
	//Unique for each doctor - cannot be repeated for another doctor.

	private String workerName;
	private String workerSurname;
	private String specialtyId;
	private int roomEr;
	private String typeWorker;
	//Can be doctor(1), nurse(2), administration staff(3), technician(4)

	
	public Worker() {
		super();
	}
	
	public Worker(String workerName, String workerSurname, String specialtyId, int roomEr,
			String typeWorker) {
		super();
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.roomEr = roomEr;
		this.typeWorker = typeWorker;
	}
	
	public Worker(Worker w) throws Exception {
		super();
		this.workerName = w.workerName;
		this.workerSurname = w.workerSurname;
		this.specialtyId = w.specialtyId;
		this.roomEr = w.roomEr;
		this.typeWorker = w.typeWorker;
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
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((workerId == null) ? 0 : workerId.hashCode());
		return result;
	}
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
				+ ", specialtyId=" + specialtyId + ", roomEr=" + roomEr + ", typeWorker=" + typeWorker + "]";
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
}