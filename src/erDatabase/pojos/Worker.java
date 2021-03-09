package erDatabase.pojos;

import java.rmi.NotBoundException;

public class Worker {
	
	private Integer workerId;
	//Unique for each doctor - cannot be repeated for another doctor.
	
	private String workerName;
	private String workerSurname;
	private String specialtyId;
	private Integer roomEr;
	private String typeWorker;
	//Can be doctor(1), nurse(2), administration staff(3), technician(4)
	private String shift;
	//Can be morning, afternoon or night
	
	/**
	 * Full builder for a worker.
	 * 
	 * @param wnam - Name of the worker (String)
	 * @param wsnam - Surname of the worker (String)
	 * @param spId - Identification of the specialty of the doctor (Integer)
	 * @param rEr - Blood type of the patient [Must be: A+,A-,B+,B-,AB+,AB-,O+,O-] (String)
	 * @param tWorker - Type of worker [doctor(1), nurse(2), adStaff(3), Technician(4)] (Integer)
	 * @param shift - Shift of the worker [Must be: morning, afternoon, night] (String)
	 */
	public Worker(String workerName, String workerSurname, String specialtyId, Integer roomEr, String typeWorker,
			String shift) {
		super();
		this.workerName = workerName;
		this.workerSurname = workerSurname;
		this.specialtyId = specialtyId;
		this.roomEr = roomEr;
		this.typeWorker = typeWorker;
		this.shift = shift;
	}

	//Getters + Setters
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
	public Integer getRoomEr() {
		return roomEr;
	}
	/**
	 * Used to set the worker's assigned room
	 * @param roomEr - The assigned room of the worker.
	 */
	public void setRoomEr(Integer roomEr) {
		this.roomEr = roomEr;
		//Podría limitarse sabiendo el número de salas de las que dispone el hospital
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
	 * Used to get the worker's shift
	 * @return [String] The worker's shift.
	 */
	public String getShift() {
		return shift;
	}
	/**
	 * Used to set the worker's shift
	 * @param shift - Must be morning, afternoon or night.
	 */
	public void setShift(String shift) throws NotBoundException {
		if(shift.equalsIgnoreCase("MORNING")){
			this.shift = shift;
		} else if (shift.equalsIgnoreCase("AFTERNOON")){
			this.shift = shift;
		} else if (shift.equalsIgnoreCase("NIGHT")){
			this.shift = shift;
		} else {
			throw new NotBoundException("Incorrect type of worker");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((workerName == null) ? 0 : workerName.hashCode());
		result = prime * result + ((workerSurname == null) ? 0 : workerSurname.hashCode());
		result = prime * result + roomEr;
		result = prime * result + ((shift == null) ? 0 : shift.hashCode());
		result = prime * result + ((specialtyId == null) ? 0 : specialtyId.hashCode());
		result = prime * result + ((typeWorker == null) ? 0 : typeWorker.hashCode());
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
		if (workerName == null) {
			if (other.workerName != null)
				return false;
		} else if (!workerName.equals(other.workerName))
			return false;
		if (workerSurname == null) {
			if (other.workerSurname != null)
				return false;
		} else if (!workerSurname.equals(other.workerSurname))
			return false;
		if (roomEr != other.roomEr)
			return false;
		if (shift == null) {
			if (other.shift != null)
				return false;
		} else if (!shift.equals(other.shift))
			return false;
		if (specialtyId == null) {
			if (other.specialtyId != null)
				return false;
		} else if (!specialtyId.equals(other.specialtyId))
			return false;
		if (typeWorker == null) {
			if (other.typeWorker != null)
				return false;
		} else if (!typeWorker.equals(other.typeWorker))
			return false;
		return true;
	}
	
}
