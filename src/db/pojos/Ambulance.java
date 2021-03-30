package db.pojos;

public class Ambulance {

	private Integer licensePlateNumber;
	private String licensePlateLetters;
	private String licensePlate;
	private short typeId;
	//Can be either 1 (SVB) or 2 (SVA), nothing else.
	private boolean availability;
	//Whether the ambulance is available or not.
	
	/**
	 * Full builder for an ambulance
	 * 
	 * @param typeId - Type of ambulance (short)
	 * @param availability - Is the ambulance available? (boolean)
	 */
	public Ambulance(Integer licensePlateNumber, String licensePlateLetters, short typeId, boolean availability) {
		super();
		this.licensePlateNumber = licensePlateNumber;
		this.licensePlateLetters = licensePlateLetters;
		this.typeId = typeId;
		this.availability = availability;
	}

//Getters + Setter

	/**
	 * Used to get the ambulance's id
	 * @return - short with the ID
	 */
	public short getTypeId() {
		return typeId;
	}
	/**
	 * Used to set the ambulance's id, SVA or SVB
	 * @param typeID - short with the ID of the ambulance.
	 */
	public void setTypeId(short typeId) throws Exception {
		if (typeId == 1) {
			System.out.println("Type of ambulance: SVB");
			this.typeId = typeId;
		} else if (typeId == 2) {
			System.out.println("Type of ambulance: SVA");
			this.typeId = typeId;
		} else {
			throw new Exception("Not valid type. Select 1 for SVB or 2 for SVA");
		}
	}
	/**
	 * Used to get the ambulance's license plate number
	 * @return - Integer with the number of the license plate
	 */
	public Integer getLicensePlateNumber() {
		return licensePlateNumber;
	}
	/**
	 * Used to set the ambulance's license plate number
	 * @param licensePlateNumber - Integer with the number of the ambulance's license plate
	 */
	public void setLicensePlateNumber(Integer licensePlateNumber) throws Exception {
		if (licensePlateNumber.toString().length() == 4) {
		this.licensePlateNumber = licensePlateNumber;
		} else {
			throw new Exception("Not valid number. It should have 4 digits");
		}
	}
	/**
	 * Used to get the ambulance's license plate letter
	 * @return - String with the letters of the license plate
	 */
	public String getLicensePlateLetters() {
		return licensePlateLetters;
	}

	public void setLicensePlateLetters(String licensePlateLetters) throws Exception {
		if (licensePlateLetters.length() == 3) {
			this.licensePlateLetters = licensePlateLetters;
			} else {
				throw new Exception("Not valid characters. It should have 3 letters");
			}
	}
	/**
	 * Used to get the ambulance's liccense plate
	 * @return - String in the form of 0000 XXX
	 */
	public String getLicensePalete() {
		return licensePlate;
	}
	/**
	 * Used to set the ambulance's license plate
	 * @param licensePlateNumber - Integer of 4 digits with the number of the license plate
	 * @param licensePlateLetters - String of 3 characters with the letters of the license plate
	 * @throws Exception 
	 */
	public void setLicensePalete(Integer licensePlateNumber, String licensePlateLetters ) throws Exception {
		if (licensePlateNumber.toString().length() == 4 && licensePlateLetters.length() == 3) {
				this.licensePlate = licensePlateNumber + " " + licensePlateLetters;
		} else {
			throw new Exception("Not valid license plate. It should have 4 digits and 3 letters in the form 0000 XXX");
		}
	}
	/**
	 * Used to know if the ambulance is available
	 * @return True/False
	 */
	public boolean isAviable() {
		return availability;
	}
	/**
	 * Used to set the ambulance's availability
	 * @param availability - TRUE/FALSE
	 */
	public void setAviable(boolean availability) {
		if(availability==true) {
			System.out.println("Ambulance available");
			this.availability = availability;
		} else {
			System.out.println("Ambulance not available");
			this.availability = availability;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (availability ? 1231 : 1237);
		result = prime * result + typeId;
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
		Ambulance other = (Ambulance) obj;
		if (availability != other.availability)
			return false;
		if (typeId != other.typeId)
			return false;
		return true;
	}
}
