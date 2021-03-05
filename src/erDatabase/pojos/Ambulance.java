package erDatabase.pojos;

public class Ambulance {

	private short typeId;
	private boolean aviable;
	
	
	public Ambulance(short typeId, boolean aviable) {
		super();
		this.typeId = typeId;
		this.aviable = aviable;
	}

//Getters + Setter
	//ID
	public short getTypeId() {
		return typeId;
	}
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
	//Availability
	public boolean isAviable() {
		return aviable;
	}
	public void setAviable(boolean aviable) {
		if(aviable==true) {
			System.out.println("Ambulance available");
			this.aviable = aviable;
		} else {
			System.out.println("Ambulance not available");
			this.aviable = aviable;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aviable ? 1231 : 1237);
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
		if (aviable != other.aviable)
			return false;
		if (typeId != other.typeId)
			return false;
		return true;
	}
}
