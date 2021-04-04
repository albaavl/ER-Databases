package db.pojos;

import java.sql.Date;
import java.time.LocalDate;

public class Treatment {

	private int treatmentId;
	//Unique for each treatment for each patient - can be repeated
	
	private String diagnosis;
	private String recommendation;
	private Date startDate;
	//Date when the patient starts the treatment
	private String medication;
	private Integer duration; 
	//Duration of the treatment in days
	
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
	public Treatment(String diagnosis, String recommendation, Date startDate, String medication, Integer duration) throws Exception {
		super();
		setDiagnosis(diagnosis);
		setRecommendation(recommendation);
		setStartDate(startDate);
		setMedication(medication);
		setDuration(duration);
	}
	
	public Treatment(int treatmentId, String medication, String diagnosis, Date startDate, int duration, String recommendation) throws Exception {
		setTreatmentId(treatmentId);
		setMedication(medication);
		setDiagnosis(diagnosis);
		setStartDate(startDate);
		setDuration(duration);
		setRecommendation(recommendation);
	}

	public void setTreatmentId(int treatmentId) {	//TODO-HAY Q ORDENAR ESTO
		this.treatmentId = treatmentId;
	}

	public Treatment() {
	}
	
	public Treatment(Treatment t) throws Exception {
		setDiagnosis(t.diagnosis);
		setRecommendation(t.recommendation);
		setStartDate(t.startDate);
		setMedication(t.medication);
		setDuration(t.duration);
	}

	//Getters + Setters

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
	//START DATE 
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
	public int getTreatmentId() {
		return treatmentId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((diagnosis == null) ? 0 : diagnosis.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((medication == null) ? 0 : medication.hashCode());
		result = prime * result + ((recommendation == null) ? 0 : recommendation.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		if (diagnosis == null) {
			if (other.diagnosis != null)
				return false;
		} else if (!diagnosis.equals(other.diagnosis))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (medication == null) {
			if (other.medication != null)
				return false;
		} else if (!medication.equals(other.medication))
			return false;
		if (recommendation == null) {
			if (other.recommendation != null)
				return false;
		} else if (!recommendation.equals(other.recommendation))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Treatment [treatmentId=" + treatmentId + ", diagnosis=" + diagnosis + ", recommendation="
				+ recommendation + ", startDate=" + startDate + ", medication=" + medication + ", duration=" + duration
				+ "]";
	}
}