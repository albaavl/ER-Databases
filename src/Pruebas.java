import java.io.File;


import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db.jdbc.SQL;
import db.pojos.*;
import db.xml.XMLManager;

public class Pruebas {
	static Connection c ;
	static Scanner sc = new Scanner(System.in);
	static SQL jdbc = new SQL();
	public static int option = 0;
	
	public static void main(String[] args) throws Exception, SQLException {
		jdbc.connect();
		try{
			jdbc.create();			
		}catch(SQLException ex) {
			if(!ex.getMessage().contains("already exists")) {
				ex.printStackTrace();
			}
		}
		List<Shift> list = new ArrayList<Shift>();
		Date d = new Date(2021, 6, 6);
		Worker w1 = new Worker(2, "Juan", "Gomez", "Dermatology", list, "doctor", 2);
		Worker w2 = new Worker(3, "María", "Perez", "Oncoology", list, "doctor", 3);
		Shift s1 = new Shift(d, 2, "morning", w1, 1);
		Shift s2 = new Shift(d, 4, "afternoon", w2, 2);
		jdbc.addWorker(w1);
		jdbc.addWorker(w2);
		jdbc.addShift(s1);
		jdbc.addShift(s2);
		System.out.println(" Convert Worker to XML file");
		XMLManager.java2XmlWorker();
		System.out.println(" Convert Shift to XML file");
		XMLManager.java2XmlShift();

	}
}
