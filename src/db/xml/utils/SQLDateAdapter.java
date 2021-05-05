package db.xml.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class SQLDateAdapter {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public String marshal(Date sqlDate) throws Exception {
		return sqlDate.toLocalDate().format(formatter);
	}

	public Date unmarshal(String string) throws Exception {
		LocalDate localDate = LocalDate.parse(string, formatter);
		return Date.valueOf(localDate);
	}
}
