package erDatabase.pojos;

import java.sql.*;

public class Patient {

//Attributes
	
	private String patientName;
	private String gender;
	private String bloodType;
	private String allergieType;
	private Date bDate; 
	private dateHour checkInDate; // No 100% seguro ser� basura 
	private String patientAddress;
	private boolean hospitalized;
	
//Getters + Setters
	//NAME
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	//GENDER
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	//BLOOD TYPE
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		//A+-B+-AB+-
		if(bloodType.equalsIgnoreCase("A+")){

		} else if (bloodType.equalsIgnoreCase("A-")){
			this.bloodType
		} else if (bloodType.equalsIgnoreCase("B+")) {
			
		} else if (bloodType.equalsIgnoreCase("B-")) {
			
		} else if (bloodType.equalsIgnoreCase("AB+")) {

		} else if (bloodType.equalsIgnoreCase("AB-")) {

		} else if (bloodType.equalsIgnoreCase("O+") || bloodType.equalsIgnoreCase("0+")) {

		} else if (bloodType.equalsIgnoreCase("O-") || bloodType.equalsIgnoreCase("0-")) {
			
		} else {
			
		}
		
	}
	
	//TYPE OF ALLERGIES 
	public String getAllergieType() {
		return allergieType;
	}
	public void setAllergieType(String allergieType) {
		this.allergieType = allergieType;
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
	
	//PATIENT ADDRESS
	public String getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}

	//HOSPITALIZED�
	public boolean isHospitalized() {
		return hospitalized;
	}
	public void setHospitalized(boolean hospitalized) {
		this.hospitalized = hospitalized;
	}	

	
	
	
//builder
	
	
	
}
