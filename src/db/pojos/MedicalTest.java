package db.pojos;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import javax.persistence.*;
import java.io.*;


public class MedicalTest implements Serializable{
	
	private static final long serialVersionUID = 7210218883507746083L;

	private Integer medicalTestId;
	//Unique for each patient - cannot be repeated for another patient.
	
	private Date dateMedTest;
	private String testType;
	private String testResult; 
	private byte[] testImage;
	private Integer patientId;

	public MedicalTest() {
			super();
	}

	public MedicalTest(Date date, String tType, String tResult, int patientId) {
		super();

		this.testType = tType;
		this.testResult = tResult;
		this.patientId = patientId;
	}
	public MedicalTest(int id, Date date, String type, String result, byte[] img, int patientId) {
		super();
		this.medicalTestId = id;
		this.dateMedTest = date;
		this.testType = type;
		this.testImage = img;
		this.testResult = result;
		this.patientId = patientId;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((medicalTestId == null) ? 0 : medicalTestId.hashCode());
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
		if (medicalTestId == null) {
			if (other.medicalTestId != null)
				return false;
		} else if (!medicalTestId.equals(other.medicalTestId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "MedicalTest [medicalTestId=" + medicalTestId + ", dateMedTest=" + dateMedTest + ", testType=" + testType
				+ ", testResult=" + testResult + ", testImage=" + Arrays.toString(testImage) + ", patientId=" + patientId
				+ "]";
	}
	/**
	 * Used to get the id of the medical test
	 * @return [Integer] The medical test's id
	 */
	public Integer getMedicalTestId() {
		return medicalTestId;
	}
	/**
	 * Used to set the ID of the medical test.
	 * @param medicalTestId - The id of the medical test requested.
	 */
	public void setMedicalTestId(Integer medicalTestId) {
		this.medicalTestId = medicalTestId;
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
	public String getTestType() {
		return testType;
	}
	/**
	 * Used to set the type of medical test.
	 * @param testType - The name of the medical test requested.
	 */
	public void setTestType(String testType) {
		this.testType = testType;
	}
	/**
	 * Used to get the result of the medical test
	 * @return [Blob] The result obtained from the medical test
	 */
	public String getTestResult() {
		return testResult;
	}
	/**
	 * Used to set the result of the medical test
	 * @param testResult - The result from the medical test
	 */
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	/**
	 * Used to get the image (result) of the medical test.
	 * @return [byte[]] The image obtained from the medical test
	 */
	public byte[]  getTestImage() {
		return testImage;
	}
	/**
	 * Used to set the result of the medical test
	 * @param testImage- The image resulted from the medical test
	 */
	public void setTestImage(byte[] testImage) {
		this.testImage = testImage;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
}
