package db.pojos;

import java.sql.Date;
import java.time.LocalDate;

import java.io.*;


public class MedicalTest implements Serializable{
	
	private static final long serialVersionUID = 7210218883507746083L;

	private Integer id;
	//Unique for each patient - cannot be repeated for another patient.
	
	private Integer patient_id;
	private Date dateMedTest;
	private String type;
	private String result; 

	public MedicalTest() {
			super();
	}

	public MedicalTest(Date date, String tType, String tResult, int patientId) {
		super();
		this.dateMedTest = date;
		this.type = tType;
		this.result = tResult;
		this.patient_id = patientId;
	}
	
	public MedicalTest(int id, Date date, String type, String result, int patientId) {
		super();
		this.id = id;
		this.dateMedTest = date;
		this.type = type;
		//this.testImage = img;
		this.result = result;
		this.patient_id = patientId;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		MedicalTest other = (MedicalTest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "MedicalTest [medicalTestId=" + id + ", dateMedTest=" + dateMedTest + ", testType=" + type
				+ ", testResult=" + result + ", patientId=" + id
				+ "]";
	}
	/**
	 * Used to get the id of the medical test
	 * @return [Integer] The medical test's id
	 */
	public Integer getMedicalTestId() {
		return id;
	}
	/**
	 * Used to set the ID of the medical test.
	 * @param medicalTestId - The id of the medical test requested.
	 */
	public void setMedicalTestId(Integer medicalTestId) {
		this.id = medicalTestId;
	}
	/**
	 * Used to get the date when realized of the medical test
	 * @return [Date] The medical test's date
	 */
	public Date getDateMedTest() {
		return dateMedTest;
	}
	/**
	 * Used to set the date of the medical test.
	 * @param dateMedTest - The date of the medical test requested.
	 */
	public void setDateMedTest(Date dateMedTest) {
		this.dateMedTest = dateMedTest;
	}
	/**
	 * Additional method to use LocalDate objects
	 * @return [LocalDate] The medical test's date
	 */
	public LocalDate getLocalDateMedTest() {
		return this.dateMedTest.toLocalDate();
	}
	/**
	 * Used to set the date of the medical test using LocalDate objects.
	 * @param ldate - The date of the medical test requested.
	 */
	public void setLocalDateMedTest (LocalDate ldate) {
		this.dateMedTest = Date.valueOf(ldate);
	}
	/**
	 * Used to get the type of the medical test.
	 * @return [String] The medical test's type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * Used to set the type of medical test.
	 * @param testType - The name of the medical test requested.
	 */
	public void setType(String testType) {
		this.type = testType;
	}
	/**
	 * Used to get the result of the medical test
	 * @return [Blob] The result obtained from the medical test
	 */
	public String getResult() {
		return result;
	}
	/**
	 * Used to set the result of the medical test
	 * @param testResult - The result from the medical test
	 */
	public void setResult(String testResult) {
		this.result = testResult;
	}

	public Integer getPatientId() {
		return patient_id;
	}

	public void setPatientId(Integer patientId) {
		this.patient_id = patientId;
	}
}
