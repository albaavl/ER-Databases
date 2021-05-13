package db.pojos;
import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

//import sample.db.pojos.Employee;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;



@Entity
@Table(name = "shifts")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Shift")
@XmlType(propOrder = {"date","shift","room","workerId"})
public class Shift implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3588643268907504481L;
	//Attributes

	@Id
	@GeneratedValue(generator="shifts")
	@TableGenerator(name="shifts", table="sqlite_sequence",
	    pkColumnName="workerId", valueColumnName="seq", pkColumnValue="shifts")
	
	//we make the id transient to be able to import data from a XML file
	@XmlTransient
	private Integer shiftId;
	@XmlElement
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date date;
	@XmlElement
	private String shift;
	@XmlElement
	private Integer room;
	@XmlElement
	private Integer workerId ;
	

	public Shift() {
	this.date = Date.valueOf(LocalDate.of(2001, 10, 1));
	}
	
	
	public Shift(Date date, String shift, Integer room, Integer workerId) throws Exception {
		super();
		this.setDate(date);
		this.setRoom(room);
		this.setWorkerId(workerId);	
	}
	
	public Shift(Date date, String shift, Integer room, Integer workerId, Integer shiftId) throws Exception {
		super();
		this.setDate(date);
		this.setRoom(room);
		this.setWorkerId(workerId);
		this.setShiftId(shiftId);
	}
	
	public Shift(Shift s) throws Exception {
		super();
		this.setDate(s.date);
		this.setRoom(s.room);
		this.setWorkerId(s.workerId);
		this.setShiftId(s.shiftId);
		
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 * @throws Exception 
	 */
	public void setDate(Date date) throws Exception {
		if(date.toLocalDate().isAfter(LocalDate.now())) {
			this.date = date;
		}
		else {
			throw new Exception ("Not valid date, needs to be after today's date");
		}
	}
	/**
	 * @return the shift
	 */
	public String getShift() {
		return shift;
	}
	/**
	 * @param shift the shift to set
	 * @throws Exception 
	 */
	
	
	public void setShift(String shift) throws Exception {
		if(shift.equalsIgnoreCase("morning")||shift.equalsIgnoreCase("afternoon")||shift.equalsIgnoreCase("night")){
			this.shift=shift;
		}
		else {
			throw new Exception("Shift not valid, choose between 'morning','afternoon or 'night'");
		}
	}
	/**
	 * @return the room
	 */
	public Integer getRoom() {
		return room;
	}
	/**
	 * @param room the room to set
	 */
	public void setRoom(Integer room) {
		this.room = room;
	}
	/**
	 * @return the worker id
	 */
	public Integer getWorkerId() {
		return workerId;
	}
	/**
	 * @param workerId the corresponding worker
	 */
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	
	public Integer getShiftId() {
		return shiftId;
	}

	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((shift == null) ? 0 : shift.hashCode());
		result = prime * result + ((workerId == null) ? 0 : workerId.hashCode());
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
		Shift other = (Shift) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (shift == null) {
			if (other.shift != null)
				return false;
		} else if (!shift.equals(other.shift))
			return false;
		if (workerId == null) {
			if (other.workerId != null)
				return false;
		} else if (!workerId.equals(other.workerId))
			return false;
		return true;
	}
	
	

	@Override 
	public String toString() {
		return "Shift [date=" + this.date + ", shift=" + this.shift + ", room=" + this.room + "]";
	}
	
}
//AQUI NO, EN MAIN
//create the JAXBContext
/*JAXBContext jaxbContext= JAXBContext=.newInstance(Shift.class)
//get the marshaller
Marshaller marshaller=jaxbContext.createMarshaller();

marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
marshaller.marshal(shift,file);*/