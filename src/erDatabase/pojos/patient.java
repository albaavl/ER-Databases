package erDatabase.pojos;

import java.sql.*;

public class patient {

//Atributes
	
	private String patientName;
	private short genderId; //Cambiar todos estos por strings limitados
	private short bloodTypeId;// ...
	private short allergiesId;// ...
	private Date bDate; 
	private dateHour checkInDate; // No 100% seguro
	private boolean ingresed;
	
//Getters + Setters
	//NAME
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	//GENDER
	public short getGenderId() {
		return genderId;
	}
	public void setGenderId(short genderId) {
		this.genderId = genderId;
	}
	//BIRTHDAY
	public Date getBdate() {
		return bDate;
	}
	public void setBdate(Date bdate) {
		this.bDate = bdate;
	}
	//CHECKINDATE
	public dateHour getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(dateHour checkInDate) {
		this.checkInDate = checkInDate;
	}
	//INGRESED¿
	public boolean isIngresed() {
		return ingresed;
	}
	public void setIngresed(boolean ingresed) {
		this.ingresed = ingresed;
	}
	
	//TBD
	
	public String toString() {
		return ;
	}
	
	
//builder
	
	
	
}
