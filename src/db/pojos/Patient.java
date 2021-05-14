package db.pojos;

import java.io.*;
import java.rmi.*;
import java.sql.Date;
import java.util.*;
import javax.persistence.*;


public class Patient implements Serializable{

	private static final long serialVersionUID = -2589974670411322131L;
	
	private Integer medical_card_number;
		//Unique for each patient - cannot be repeated for another patient.
	private String patientName;
	private String patientSurname;
	private String gender;
		//Can be either Male or female, nothing else.
	private String bloodType;
		//Can be A+,A-,B+,B-,AB+,AB-,O+,O-
	private String allergieType;
	private Date bDate; 
		//Birthday of the patient.
	private Date checkInDate; 
		//Date and hour when patient got in the ER.
	private String patientAddress;
		//Home address of the patient.
	private boolean hospitalized;
		//Whether the patient is hospitalized or not.
	private Integer userId;

//builder

	/**
	 * Empty builder - shouldn't be used for anything.
	 */
	public Patient() {
	}
	
	/**
	 * Full builder for a patient.
	 * 
	 * @param pnam - Name of the patient (String)
	 * @param psnam - Surname of the patient (String)
	 * @param pgen - Gender of the patient [Must be Male or Female] (String)
	 * @param btype - Blood type of the patient [Must be: A+,A-,B+,B-,AB+,AB-,O+,O-] (String)
	 * @param allerg - String with the allergies of the patient. 
	 * @param paddress - String with the home address of the patient.
	 * @param bdat - Birthday of the patient (SQL Date)
	 * @param cIndat - Check in date of the patient (SQL Date)
	 * @param hosp - Is the patient hospitalized? (boolean)
	 * @param medCardId - The medical card id of the patient (int) [Cannot be changed once it's created]
	 * @throws NotBoundException if Gender or Blood type provided isnt one of the previous mentioned.
	 */
	public Patient(String pnam, String psnam, String pgen, String btype, String allerg, String paddress, Date bdat, Date cIndat, boolean hosp, Integer medCardId ) throws NotBoundException{
		this.setAllergieType( allerg);
		this.setbDate(bdat);
		this.setBloodType(btype);
		this.setCheckInDate(cIndat);
		this.setGender(pgen);
		this.setHospitalized(hosp); 
		this.setPatientName(pnam);
		this.setPatientSurname(psnam);
		this.setPatientAddress(paddress);
		this.medical_card_number = medCardId;
	}
	
	public Patient(String pnam, String psnam, String pgen, String btype, String allerg, String paddress, Date bdat, Date cIndat, boolean hosp, Integer medCardId, Integer userId ) throws NotBoundException{
		this.setAllergieType( allerg);
		this.setbDate(bdat);
		this.setBloodType(btype);
		this.setCheckInDate(cIndat);
		this.setGender(pgen);
		this.setHospitalized(hosp); 
		this.setPatientName(pnam);
		this.setPatientSurname(psnam);
		this.setPatientAddress(paddress);
		this.userId= userId;
		this.medical_card_number = medCardId;
	}
	
	public Patient(Patient p) throws NotBoundException {
		this.setAllergieType(p.allergieType);
		this.setbDate(p.bDate);
		this.setBloodType(p.bloodType);
		this.setCheckInDate(p.checkInDate);
		this.setGender(p.gender);
		this.setHospitalized(p.hospitalized); 
		this.setPatientName(p.patientName);
		this.setPatientSurname(p.patientSurname);
		this.setPatientAddress(p.patientAddress);
		this.medical_card_number = p.medical_card_number;
	}

//Getters + Setters

	/**
	 * Used to get the name of the patient
	 * @return [String] The patient name
	 */
	public String getPatientName() {
		return patientName;
	}
	
	/**
	 * Used to set the patient's name
	 * @param patientName - The name of the patient.
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	/**
	 * Used to get the surname of the patient.
	 * @return [String] The patient surname
	 */
	public String getPatientSurname() {
		return patientSurname;
	}
	/**
	 * Used to set the patient's surname.
	 * @param patientSurename - The surname of the patient
	 */
	public void setPatientSurname(String patientSurname) {
		this.patientSurname = patientSurname;
	}
	
	/**
	 * @return the bDate
	 */
	public Date getbDate() {
		return bDate;
	}

	/**
	 * @param bDate the bDate to set
	 */
	public void setbDate(Date bDate) {
		this.bDate = bDate;
	}

	/**
	 * Used to get the gender of the patient.
	 *  @return the gender of the patient (Male / Female)
	 */ 
	public String getGender() {
		return gender;
	}
	/**
	 * Sets the gender of the patient.
	 * 
	 * @param gender - Must be Male or Female.
 	 * @throws NotBoundException if not a correct gender
	 */
	public void setGender(String gender) throws NotBoundException {
		if(gender.equalsIgnoreCase("Male")) {
			this.gender = gender;
		} else if(gender.equalsIgnoreCase("Female")){
			this.gender = gender;		
		} else {
			throw new NotBoundException("Not a gender.") ;
		}
	}

	/**
	 * Returns the patient blood type as a string.
	 */
	public String getBloodType() {
		return bloodType;
	}
	/**
	 * Sets the blood type of the patient.
	 * 
	 * @param bloodType Must be one of the following: 
	 * @throws NotBoundException if blood type doesnt exist
	 */
	public void setBloodType(String bloodType) throws NotBoundException {

		if(bloodType.equalsIgnoreCase("A+")){
			this.bloodType = bloodType;
		} else if (bloodType.equalsIgnoreCase("A-")){
			this.bloodType = bloodType;
		} else if (bloodType.equalsIgnoreCase("B+")) {
			this.bloodType = bloodType;
		} else if (bloodType.equalsIgnoreCase("B-")) {
			this.bloodType = bloodType;
		} else if (bloodType.equalsIgnoreCase("AB+")) {
			this.bloodType = bloodType;
		} else if (bloodType.equalsIgnoreCase("AB-")) {
			this.bloodType = bloodType;
		} else if (bloodType.equalsIgnoreCase("O+") || bloodType.equalsIgnoreCase("0+")) {
			this.bloodType = bloodType;
		} else if (bloodType.equalsIgnoreCase("O-") || bloodType.equalsIgnoreCase("0-")) {
			this.bloodType = bloodType;
		} else {
			throw new NotBoundException("Incorrect blood type");
		}
		
	}
	
	/**
	 * Used to get the patient allergies
	 * @return A string with all of the patient allergies.
	 */
	public String getAllergieType() {
		return allergieType;
	}
	/**
	 * Used to set the patient allergies.
	 * @param allergieType - String that contains all of the patient allergies.
	 */
	public void setAllergieType(String allergieType) {
		this.allergieType = allergieType;
	}
		
	/**
	 * Used to get the check in date of the patient into the ER.
	 * @return Date of check in (SQL Date)
	 */
	public Date getCheckInDate() {
		return checkInDate;
	}
	/**
	 * Used to set the check in date of the patient
	 * @param checkInDate - The check in date of the patient in the ER (SQL Date)
	 */
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	
	/**
	 * Used to get the home address of the patient.
	 * @return - String with the address
	 */
	public String getPatientAddress() {
		return patientAddress;
	}
	/**
	 * Used to set the patient address
	 * @param patientAddress - String with the home address of the patient.
	 */
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}

	/**
	 * Used to know if the patient is hospitalized
	 * @return True/False
	 */
	public boolean getHospitalized() {
		return hospitalized;
	}
	/**
	 * Used to set the 
	 */
	public void setHospitalized(boolean hospitalized) {
		this.hospitalized = hospitalized;
	}	

	/**
	 * Used to obtain the medical card id of the patient
	 * @return - the medical card id of the patient [int]
	 */
	public Integer getMedicalCardId() {
		return medical_card_number;
	}
	
	public void setMedicalCardId (Integer medicalCardId) {
		this.medical_card_number = medicalCardId;
	}	

	//Methods

	@Override
	public String toString() {
		return "MedicalCardNumber: "+this.medical_card_number+ "Name: " + this.patientName + ", Surname: " + this.patientSurname + ", Gender: " + this.gender + ", Blood type: " + this.bloodType
		+ ", Birthdate:"+this.getbDate().toString()+", Address: "+this.getPatientAddress()+", Check in date: " + this.checkInDate.toString() + ", Allergies: " +  this.allergieType + ", Hospitalized: "+this.getHospitalized();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((medical_card_number == null) ? 0 : medical_card_number.hashCode());
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
		Patient other = (Patient) obj;
		if (medical_card_number == null) {
			if (other.medical_card_number != null)
				return false;
		} else if (!medical_card_number.equals(other.medical_card_number))
			return false;
		return true;
	}
}
