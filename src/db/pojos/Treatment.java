package db.pojos;

import java.io.Serializable;
import java.lang.annotation.Inherited;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "treatment")
public class Treatment implements Serializable {

	private static final long serialVersionUID = 5733730131671999655L;
	@Id
	@GeneratedValue(generator="treatment")
	@TableGenerator(name="treatment", table="sqlite_sequence",
	pkColumnName="id", valueColumnName="seq",
	pkColumnValue="treatment")	
	private Integer treatmentId; //Unique for each treatment for each patient - can be repeated
	private String diagnosis;
	private String medication;
	private String recommendation;
	private Date startDate; //Date when the patient starts the treatment
	private Integer duration; //Duration of the treatment in days
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="patient_id")	
	private Patient patient;

	//Builders

	public Treatment() {
	}

	/**
	 * Full builder for a treatment.
	 * 
	 * @param diagnosis - Diagnosis of the patient (String)
	 * @param recommendation - Recommendations from the doctor (String)
	 * @param startDate - Start date of the treatment (SQL Date)
	 * @param medication - Medication prescribed to the patient (String)
	 * @param duration - How many days is the treatment going to last (Integer)
	 * @throws Exception 
	 */
	public Treatment(String diagnosis, String medication , Date startDate, String recommendation, Integer duration) throws Exception {
		super();
		setDiagnosis(diagnosis);
		setMedication(medication);
		setStartDate(startDate);
		setRecommendation(recommendation);
		setDuration(duration);
	}

	public Treatment(int treatmentId, String diagnosis, String medication, Date startDate, String recommendation, int duration) throws Exception {
		setTreatmentId(treatmentId);
		setDiagnosis(diagnosis);
		setMedication(medication);		
		setStartDate(startDate);		
		setRecommendation(recommendation);
		setDuration(duration);
	}
	
	public Treatment(Treatment t) throws Exception {
		setTreatmentId(t.treatmentId);
		setDiagnosis(t.diagnosis);
		setRecommendation(t.recommendation);
		setStartDate(t.startDate);
		setMedication(t.medication);
		setDuration(t.duration);
	}

	//Getters + Setters

	/**
	 * Used to set the treatment id
	 * @param treatmentId - the id of the treatment (int)
	 */
	public void setTreatmentId(int treatmentId) {	
		this.treatmentId = treatmentId;
	}
	/**
	 * Used to get the treatment id
	 * @return treatmentId (int)
	 */
	public int getTreatmentId() {
		return treatmentId;
	}
	/**
	 * Used to get the diagnosis of the patient.
	 *  @return the diagnosis of the patient
	 */ 
	public String getDiagnosis() {
		return diagnosis;
	}
	/**
	 * Used to set the diagnosis of the patient.
	 * @param diagnosis - String that contains the diagnosis of the patient.
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	/**
	 * Used to get the doctor's recommendations for the patient.
	 *  @return the doctor's recommendations for the patient.
	 */ 
	public String getRecommendation() {
		return recommendation;
	}
	/**
	 * Used to set the doctor's recommendations for the patient.
	 * @param recommendation - String that contains all of the doctor's recommendations for the patient.
	 */
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	/**
	 * Used to get the start date of the treatment
	 * @return Date of the start day (SQL Date)
	 */
	public Date getStartDate() {
			return startDate;
	}
	/**
	 * Used to set the start date of the treatment
	 * @param startDate - The start date of the patient's treatment (SQL Date)
	 * @throws Exception If the start date is after the local time.
	 */
	public void setStartDate(Date startDate) throws Exception {
		
		if(startDate.toLocalDate().isAfter(LocalDate.now())) {
			this.startDate = startDate;
		} else {
			throw new Exception("Not valid Date");
		}
	}
	/**
	 * Used to get the medication prescribed to the patient 
	 *  @return the medication prescribed to the patient
	 */ 
	public String getMedication() {
		return medication;
	}
	/**
	 * Used to prescribe the medication to the patient 
	 * @param medication - String that contains all the medication prescribed to the patient.
	 */
	public void setMedication(String medication) {
		this.medication = medication;
	}
	/**
	 * Used to get the duration of the treatment in days 
	 *  @return the duration of the treatment.
	 */ 
	public Integer getDuration() {
		return duration;
	}
	/**
	 * Used to set the treatment's duration in days
	 * @param duration - Integer that contains how long is going to last the treatment
	 */
	public void setDuration(Integer duration) throws Exception {
		if(duration>0) {
			this.duration = duration;
		} else if(duration==0){ 
			System.out.println("The patient has not received any treatment");
		} else {
			throw new Exception("Not valid Duration");
		}
	}

	//Methods

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((treatmentId == null) ? 0 : treatmentId.hashCode());
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
		Treatment other = (Treatment) obj;
		if (treatmentId == null) {
			if (other.treatmentId != null)
				return false;
		} else if (!treatmentId.equals(other.treatmentId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Treatment [treatmentId=" + treatmentId + ", diagnosis=" + diagnosis + ", recommendation="
				+ recommendation + ", medication=" + medication + ", startDate=" + startDate + ", duration=" + duration
				+ "]";
	}
}
