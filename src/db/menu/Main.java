package db.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.sql.Date;
import java.util.*;
import db.jdbc.*;
import db.pojos.*;

public class Main {
	static String username;
	static String password;
	static int option;
	
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		try {
		System.out.println("Welcome to the Quiron's ER");
		System.out.println("Please enter your username and password:");
		System.out.println("Username:");
		username = sc.next();
		System.out.println("Password:");
		password = sc.next();
		
		if(username.equalsIgnoreCase("ms")&&password.equalsIgnoreCase("ms")) {
			System.out.println("Choose an option[1-]:");
			System.out.println("1.");
			
		}if(username.equalsIgnoreCase("as")&&password.equalsIgnoreCase("as")) {
			System.out.println("Choose an option[1-4]:");
			option = sc.nextInt();
			while (option!=0) {
			switch (option) {
			case 1: {
				System.out.println("1. Register new patient");
				Patient newp = createPatient();
				break;
			}
			case 2: {
				System.out.println("2. Check ambulance availability");
				
			}
			case 3: {
				System.out.println("3. Acces to a patient's profile");
				
			}
			default:
				System.exit(0);
			}
			}
			
		}if(username.equalsIgnoreCase("p")&&password.equalsIgnoreCase("p")) {
			System.out.println("Consult my tratment");
			System.out.println("Here you can see all your treatments ordered by date");
			ShowPatientsTreatments();			
		}else {			
		}
		
sc.close();
	} catch (Exception e) {
		// TODO: handle exception
	}
	}
	public static Patient createPatient () throws NotBoundException {
		System.out.println("Please, input the patient info:");
		System.out.print("Name: ");
		String name = sc.next();
		System.out.print("Surname: ");
		String surname = sc.next();
		System.out.print("Medical card number: ");
		Integer medCardNumber = sc.nextInt();
		System.out.print("Gender: ");
		String gender = sc.next();
		System.out.print("Blood type: ");
		String bloodType = sc.next();
		System.out.print("Allergies: ");
		String allergie = sc.next();
		System.out.print("Date of birth: ");
		System.out.print("Day: ");
		int bday = sc.nextInt();
		System.out.print("Month: ");
		int bmonth = sc.nextInt();
		System.out.print("Year: ");
		int byear = sc.nextInt();
		@SuppressWarnings("deprecation")
		Date bdate = new Date(byear, bmonth, bday);
		System.out.print("Check in date: ");
		System.out.print("Day: ");
		int cday = sc.nextInt();
		System.out.print("Month: ");
		int cmonth = sc.nextInt();
		System.out.print("Year: ");
		int cyear = sc.nextInt();
		System.out.print("Hour: ");
		int chour = sc.nextInt();
		System.out.print("Hour: ");
		int cmin = sc.nextInt();
		@SuppressWarnings("deprecation")
		Date cdate = (Date) new java.util.Date(cyear, cmonth, cyear, chour, cmin);
		System.out.print("Check in date: ");
		System.out.print("Address: ");				
		String address = sc.next();
		System.out.print("Hospitalization [YES/NO]: ");
		Boolean hospitalized = sc.nextBoolean();
		Patient p= new Patient (name, surname, gender, bloodType, allergie, address, bdate, cdate, hospitalized, medCardNumber);
		return p;
	}
	
	public static void ShowPatientsTreatments(){
		String treatment;
		ArrayList<Treatment> l = new ArrayList<Treatment> ();
		// l = sql.searchPatientsTreatment()
		treatment = l.toString();	
		System.out.println(treatment);
	}
	
}
