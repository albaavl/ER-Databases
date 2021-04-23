package db.pojos;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import javax.persistence.*;
import java.io.*;

@Entity
@Table(name = "medical_tests")
public class MedicalTest implements Serializable{
	
	private static final long serialVersionUID = 7210218883507746083L;

	@Id
	@GeneratedValue(generator = "medical_tests")
	@TableGenerator(name = "medical_tests", table = "sqlite_sequence",
		pkColumnName = "id", valueColumnName = "seq", pkColumnValue = "medical_tests")
	
	private Integer medicalTestId;
	//Unique for each patient - cannot be repeated for another patient.

	private Integer idDoctor;
	//Identification of the doctor who is asking for and receiving the medical test of a patient
	private Integer idPatient;
	//Identification of the patient who is getting done the medical test
	private String testType;
	private String testResult;
	private Date date; 
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private byte[] testImage;
	
	//Relationship 1-to-n with Patient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "patient_id")
	private Patient patient;

	public MedicalTest() {
			super();
	}
	
	public MedicalTest(Integer idD, Integer idP, String tType, String tResult) {
		super();
		this.idDoctor = idD;
		this.idPatient = idP;
		this.testType = tType;
		this.testResult = tResult;
	}
	public MedicalTest(int id, Integer medId, Integer patientId, String type, String result, Blob img) {
		this.medicalTestId = id;
		this.testType = type;
		this.testImage = img;
		this.testResult = result;
		this.idPatient = patientId;
		this.idDoctor = medId;
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
	public String getTestResult() {
		return testResult;
	}
	/**
	 * Used to set the result of the medical test
	 * @param testResult - The image resulted from the medical test
	 */
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public Blob getTestImage() {
		return testImage;
	}
	public void setTestImage(Blob testImage) {
		this.testImage = testImage;
	}
	public void setMedicalTestId(Integer medicalTestId) {
		this.medicalTestId = medicalTestId;
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
