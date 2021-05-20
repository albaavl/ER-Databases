import java.io.File;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import db.jdbc.SQL;
import db.xml.XMLManager;

public class Pruebas {
	static Connection c ;
	static Scanner sc = new Scanner(System.in);
	static SQL jdbc = new SQL();
	public static int option = 0;
	
	public static void main(String[] args) throws Exception, SQLException {
			
		System.out.println(" Convert Worker to XML file");
		XMLManager.java2XmlWorker();

	}
}
