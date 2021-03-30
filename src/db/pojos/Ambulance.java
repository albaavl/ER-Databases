package db.pojos;

public class Ambulance {

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
	public Ambulance(short typeId, boolean availability) {
		super();
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
