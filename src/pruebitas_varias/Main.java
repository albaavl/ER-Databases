package pruebitas_varias;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import db.interfaces.UMInterface;
import db.jdbc.SQL;
import db.jpa.JPAUserManager;
import db.pojos.*;

public class Main {


	static Connection c ;
	static Scanner sc = new Scanner(System.in);
	static SQL jdbc = new SQL();

	public static void main(String[] args) {
		try {
			c= jdbc.connect();
			jdbc.addPatient(c, new Patient("nom-1", "ap5", "female", "O-","gluten", "adress", Date.valueOf("2001-07-07"), Date.valueOf("2010-07-07"), false, 01));
			jdbc.addPatient(c, new Patient("nom2", "ap3", "female", "O-","gluten", "adress", Date.valueOf("2001-07-07"), Date.valueOf("2010-07-07"), false, 02));
			jdbc.addPatient(c, new Patient("nom3", "ap2", "female", "O-","gluten", "adress", Date.valueOf("2001-07-07"), Date.valueOf("2010-07-07"), false, 03));
			jdbc.addWorker(c, new Worker("name", "surname", "specialty", 07, "normal"));
			List<Patient> Patients = new ArrayList<>();
			Patients.addAll(jdbc.searchPatient(c, "ap"));
			System.out.println(Patients.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


