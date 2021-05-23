package db.pojos;

import java.rmi.NotBoundException;
import java.util.*;
import javax.persistence.*;
import java.io.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "workers")
@XmlRootElement(name = "Worker")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "specialtyId", "typeWorker"})
public class Worker implements Serializable{
	
	private static final long serialVersionUID = 5053907057578582101L;

	@Id
	@GeneratedValue(generator = "workers")
	@TableGenerator(name = "workers", table = "sqlite_sequence",
		pkColumnName = "workerName", valueColumnName = "seq", pkColumnValue = "workers")
	
	@XmlTransient
	private Integer workerId;
	//Unique for each doctor - cannot be repeated for another doctor.

	@XmlAttribute
	private String workerName;
	@XmlAttribute
	private String workerSurname;
	@XmlAttribute
	private Integer userId;
	@XmlElement
	private String specialtyId;
	@XmlElement
	private String typeWorker;
	//Can be doctor(1), nurse(2), administration staff(3), technician(4)
	@OneToMany(mappedBy="worker")
	@XmlElement(name = "Shift")
    @XmlElementWrapper(name = "Shifts")
	private List<Shift> shift;
	
	public Worker() {
		super();
		this.shift = new ArrayList<Shift>();
	}
	
	public Worker(String workerName, String workerSurname, String specialtyId,
			String typeWorker) {
		super();
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.typeWorker = typeWorker;
		this.shift = new ArrayList<Shift>();
	}
	
	public Worker(Worker w) throws Exception {
		super();
		this.workerId = w.workerId;
		this.workerName = w.workerName;
		this.workerSurname = w.workerSurname;
		this.specialtyId = w.specialtyId;
		this.shift = w.shift;
		this.typeWorker = w.typeWorker;
	}
	
	public Worker(Integer workerId, String workerName, String workerSurname, String specialtyId, List<Shift> shift,
			String typeWorker) {
		super();
		this.workerId = workerId;
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.shift = shift;
		this.typeWorker = typeWorker;
	}
	public Worker(Integer workerId, String workerName, String workerSurname, String specialtyId,
			String typeWorker) {
		super();
		this.workerId = workerId;
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.typeWorker = typeWorker;
		this.shift = new ArrayList<Shift>();
	}
	
	public Worker(String workerName, String workerSurname, String specialtyId,
			String typeWorker, Integer userId) {
		super();
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.typeWorker = typeWorker;
		this.userId = userId;
		this.shift = new ArrayList<Shift>();
	}

	public Worker(Integer workerId, String workerName, String workerSurname, String specialtyId, List<Shift> shift,
			String typeWorker, Integer userId) {
		super();
		this.workerId = workerId;
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.shift = shift;
		this.typeWorker = typeWorker;
		this.userId = userId;
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
		return "Worker [workerId=" + this.workerId + ", workerName=" + this.workerName + ", workerSurname=" + this.workerSurname
				+ ", specialtyId=" + this.specialtyId + ", shift" + this.shift + ", typeWorker=" + this.typeWorker + "]";
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
	
	

	public List<Shift> getShift() {
		return shift;
	}

	public void setShift(List<Shift> shift) {
		this.shift = shift;
	}
	
	public void addShift (Shift shifts) {
		if(!shift.contains(shifts)) {
			this.shift.add(shifts);
		}
	}
	public void removeShift (Shift shifts) {
		if(!shift.contains(shifts)) {
			this.shift.remove(shifts);
		}
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}