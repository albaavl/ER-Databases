package db.pojos;

import java.sql.*;
import java.time.LocalDate;

public class Shift {
	
	private Date date;
	private String shift;
	private Integer room;
	

	public Shift() {
	}
	
	public Shift(Date date, String shift, Integer room) throws Exception {
		this.setDate(date);
		this.setShift(shift);
		this.setRoom(room);
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
		if(shift.equalsIgnoreCase("morning")||shift.equalsIgnoreCase("afternoon")||shift.equalsIgnoreCase("night")) {
			this.shift = shift;
		}
		else {
			throw new Exception("Shift not valid, choose between 'morning', 'afternoon' or 'night'");
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
	
	
	
	
}
