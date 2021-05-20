package db.pojos;
import java.util.*;

import javax.persistence.*;
import java.io.Serializable;
import javax.xml.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import db.xml.utils.*;



@Entity
@Table(name = "shifts")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Shift")
@XmlType(propOrder = {"date","turn","room"})
public class Shift implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3588643268907504481L;
	//Attributes
/*
	@Id
	@GeneratedValue(generator="shifts")
	@TableGenerator(name="shifts", table="sqlite_sequence",
	    pkColumnName="workerId", valueColumnName="seq", pkColumnValue="shifts")
*/	
	//we make the id transient to be able to import data from a XML file
	@XmlTransient
	private Integer shiftId;
	@XmlElement
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date date;
	@XmlElement
	private String turn;
	@XmlElement
	private Integer room;
	@XmlTransient
	private Worker worker ;
	

	public Shift() {
	this.date = Date.valueOf(LocalDate.of(2001, 10, 1));
	}
	
	
	public Shift(Date date, Integer room, String turn, Worker worker) throws Exception {
		super();
		this.setDate(date);
		this.setRoom(room);
		this.setTurn(turn);
		this.setWorker(worker);	
	}
	public Shift(Date date, Integer room, String turn, Integer shiftId) throws Exception {
		super();
		this.setDate(date);
		this.setRoom(room);
		this.setTurn(turn);
		this.setShiftId(shiftId);
	}
	
	public Shift(Date date, Integer room, String turn, Worker worker, Integer shiftId) throws Exception {
		super();
		this.setDate(date);
		this.setRoom(room);
		this.setTurn(turn);
		this.setWorker(worker);
		this.setShiftId(shiftId);
	}

	public Shift(Shift s) throws Exception {
		super();
		this.setDate(s.date);
		this.setRoom(s.room);
		this.setTurn(s.turn);
		this.setWorker(s.worker);
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
	public String getTurn() {
		return turn;
	}
	/**
	 * @param shift the shift to set
	 * @throws Exception 
	 */
	
	
	public void setTurn(String t) throws Exception {
		if(t.equalsIgnoreCase("morning")||t.equalsIgnoreCase("afternoon")||t.equalsIgnoreCase("night")){
			this.turn = t;
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
	
	
	public Worker getWorker() {
		return worker;
	}


	public void setWorker(Worker worker) {
		this.worker = worker;
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
		result = prime * result + ((shiftId == null) ? 0 : shiftId.hashCode());
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
		if (shiftId == null) {
			if (other.shiftId != null)
				return false;
		} else if (!shiftId.equals(other.shiftId))
			return false;
		return true;
	}


	@Override 
	public String toString() {
		return "Shift [date=" + this.date + ", shift=" + this.turn + ", room=" + this.room + "]";
	}
	
}