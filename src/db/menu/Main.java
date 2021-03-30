package db.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.sql.Date;
import java.util.*;
import db.jdbc.*;
<<<<<<< HEAD
import db.pojos.*;
=======
import db.pojos.Patient;
>>>>>>> branch 'master' of https://github.com/albaavl/ER-Databases

public class Main {
	static String username;
	static String password;
<<<<<<< HEAD
	static int option;
	
	static Scanner sc = new Scanner(System.in);
=======
	static int option = 1;
>>>>>>> branch 'master' of https://github.com/albaavl/ER-Databases

	public static void main(String[] args) {
		try {
		System.out.println("Welcome to the Quiron's ER");
		System.out.println("Please enter your username and password:");
		System.out.println("Username:");
		username = sc.next();
		System.out.println("Password:");
		password = sc.next();
		
		if(username.equalsIgnoreCase("ms")&&password.equalsIgnoreCase("ms")) {
			while(option != 0) {
			System.out.println("Choose an option[0-3]:");
			System.out.println(" 1.Access to a patient's profile \n 2.Consult medical test\n 3.Consult my shifts \n 0. Exit");
			option = sc.nextInt();
			switch(option) {
			case 0:
				System.exit(0);
			case 1:
				accessToAPatientsProfile();
				break;
			case 2:
				consultMedicalTest();
				break;
			case 3:
				consultShifts();
				break;
			}
			}
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
<<<<<<< HEAD
			System.out.println("Consult my tratment");
			System.out.println("Here you can see all your treatments ordered by date");
			ShowPatientsTreatments();			
		}else {			
=======
			System.out.println("Choose an option[1-]:");
			System.out.println("1.");
			
		}else {
			
>>>>>>> branch 'master' of https://github.com/albaavl/ER-Databases
		}
		
sc.close();
	} catch (Exception e) {
		// TODO: handle exception
	}
<<<<<<< HEAD
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
	
=======

	private static void accessToAPatientsProfile() {
		List<Patient> patientList = new ArrayList<Patient>();
		Patient patient = null;
		while(patientList.isEmpty()) {
		System.out.println("Enter the patient's name:");
		String name = sc.next();
		patientList = SQL.searchPatient(name); //FUNCION NO CREADA debe buscar un paciente pasandole un string del nombre y devolviendo una lista de objetos de tipo paciente que estará vacía si no hay ningún paciente con ese nombre;
		}
		while(patient == null) {
		System.out.println(patientList.toString());
		System.out.println("Enter the medical card number of the chosen patient:");
		Integer medCard = Integer.parseInt(sc.next());
		patient = SQL.selectPatient (medCard); //FUNCION NO CREADA debe seleccionar un paciente por el medical card number y devolver un objeto paciente que debe ser null si no existe un paciente con ese medical card number;
		}
		System.out.println("Choose an option:");
		System.out.println(" 1.Access to a clinical history \n 2.Create diagnosis and treatment\n 3.Edit diagnosis and treatment\n ");
		switch(option) {
		case 1:
			accessToClinicalHistory(patient);
			break;
		case 2:
			createDiagnosisAndTreatment(patient);
			break;
		case 3:
			editDiagnosisAndTreatment(patient);
			break;
		}
		
	}

	private static void consultShifts() {
		// TODO Auto-generated method stub
		
	}

	private static void consultMedicalTest() {
		// TODO Auto-generated method stub
		
	}

	private static void editDiagnosisAndTreatment(Patient  patient) {
		// TODO Auto-generated method stub
		
	}

	private static void createDiagnosisAndTreatment(Patient patient) {
		// TODO Auto-generated method stub
		
	}

	private static void accessToClinicalHistory(Patient patient) {
		// TODO Auto-generated method stub
		
	}

>>>>>>> branch 'master' of https://github.com/albaavl/ER-Databases
}
