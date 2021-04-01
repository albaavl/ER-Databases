package db.pojos;

import java.rmi.NotBoundException;
import java.sql.*;

public class MedicalTest {
	
	//Attributes
	private Integer medicalTestId;
	//Unique for each patient - cannot be repeated for another patient.
	
	private Integer idDoctor;
	//Identification of the doctor who is asking for and receiving the medical test of a patient
	private Integer idPatient;
	//Identification of the patient who is getting done the medical test
	private String testType;
	private Blob testResult;
	//Result of the medical test as an image (for CT, X-rays, MRIs...)

	/**
	 * Full builder for a medical test.
	 * 
	 * @param idD - Identification of the doctor (Integer)
	 * @param idP - Identification of the patient (Integer)
	 * @param tType - Name of the medical test (String)
	 * @param tResult- Image obtained from the medical test (Blob)
	 */
	public MedicalTest(Integer idD, Integer idP, String tType, Blob tResult) {
		super();
		this.idDoctor = idD;
		this.idPatient = idP;
		this.testType = tType;
		this.testResult = tResult;
	}
	/**
	 * Used to get the id of the doctor
	 * @return [Integer] The doctor's id
	 */
	public Integer getIdDoctor() {
		return idDoctor;
	}
	/**
	 * Used to set the doctor's id
	 * @param idDoctor - The id of the doctor who is going to receive the results of the medical test
	 */
	public void setIdDoctor(int idDoctor) {
		this.idDoctor = idDoctor;
	}
	/**
	 * Used to get the id of the patient
	 * @return [Integer] The patient's id
	 */
	public Integer getIdPatient() {
		return idPatient;
	}
	/**
	 * Used to set the patient's id
	 * @param idPatient - The id of the patient who is getting done the medical test
	 */
	public void setIdPatient(int idPatient) {
		this.idPatient = idPatient;
	}
	/**
	 * Used to get the name of the medical test
	 * @return [String] The medical test's name
	 */
	public String getTestType() {
		return testType;
	}
	/**
	 * Used to set the type of medical test
	 * @param testType - The name of the medical test requested
	 */
	public void setTestType(String testType) {
		this.testType = testType;
	}
	/**
	 * Used to get the image (result) of the medical test
	 * @return [Blob] The image obtained from the medical test
	 */
	public Blob getTestResult() {
		return testResult;
	}
	/**
	 * Used to set the result of the medical test
	 * @param testResult - The image resulted from the medical test
	 */
	public void setTestResult(Blob testResult) {
		this.testResult = testResult;
	}

	public Integer getMedicalTestId() {
		return medicalTestId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idDoctor;
		result = prime * result + idPatient;
		result = prime * result + ((testResult == null) ? 0 : testResult.hashCode());
		result = prime * result + ((testType == null) ? 0 : testType.hashCode());
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
		if (idDoctor != other.idDoctor)
			return false;
		if (idPatient != other.idPatient)
			return false;
		if (testResult == null) {
			if (other.testResult != null)
				return false;
		} else if (!testResult.equals(other.testResult))
			return false;
		if (testType == null) {
			if (other.testType != null)
				return false;
		} else if (!testType.equals(other.testType))
			return false;
		return true;
	}
	
}
