package erDatabase.pojos;

import java.sql.Date;
import java.util.Calendar;

public class Treatment {

	private String diagnosis;
	private String recommendation;
	private Date startDate;
	private String medication;
	private Integer duration; //in days
	
	public Treatment(String diagnosis, String recommendation, Date startDate, String medication, Integer duration) {
		super();
		this.diagnosis = diagnosis;
		this.recommendation = recommendation;
		this.startDate = startDate;
		this.medication = medication;
		this.duration = duration;
	}

	//Getters + Setters
	//DIAGNOSIS
	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	//DOCTOR'S RECOMMENDATION
	public String getRecommendation() {
		return recommendation;
	}
	
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	//START DATE 
	public Date getStartDate() {
			return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		if(startDate.compareTo()>0) {
			this.startDate = startDate;
		} else {
			throw new Exception("Not valid Date");
		}
	}
	//MEDICATION
	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
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
	
	
	
}
