package db.pojos;

import java.sql.*;
import java.time.LocalDate;

public class Shift {
	
	private Date date;
	private String shift;
	private Integer room;
	private Integer workerId;

	public Shift() {
	}
	
	public Shift(Date date, String shift, Integer room, Integer workerId) throws Exception {
		this.setDate(date);
		this.setShift(shift);
		this.setRoom(room);
		this.setWorkerId(workerId);
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
		return "Shift [date=" + date + ", shift=" + shift + ", room=" + room + "]";
	}
	
}