package db.menu;
 
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.sql.Date;
import java.time.chrono.ThaiBuddhistDate;
import java.util.*;

import db.pojos.*;
import db.jdbc.*;

public class Main {
	static String username;
	static String password;
	static int option = 1;
	
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
			while (option!=0) {
			System.out.println("Choose an option[0-3]:");
			System.out.println(" 1. Register new patient \n 2. Check ambulance availability\n 3. Acces to a patient's profile \n 0. Exit");
			option = sc.nextInt();
			switch (option) {
			case 0:
				System.exit(0);
			case 1: 
				System.out.println("Register new patient");
				createPatient();
				break;
			case 2: 
				System.out.println("Check ambulance availability");
				checkAmbulancceAvailability();
				break;
			case 3: {
				System.out.println("Acces to a patient's profile");
				adAccessToPatientsProfile();
				break;
			}
			}
			
		}if(username.equalsIgnoreCase("p")&&password.equalsIgnoreCase("p")) {
			System.out.println("Consult my tratment");
			System.out.println("Here you can see all your treatments ordered by date");
			ShowPatientsTreatments();			
			
		}else {
		}
		
		sc.close();
		}catch (Exception e) {
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
		System.out.print("Date of birth: yyyy-[m]m-[d]d");
		String birthdate = sc.next();
		Date bdate = Date.valueOf(birthdate);
		System.out.print("Check in date: yyyy-[m]m-[d]d"); //averiguar como meter horas y minutos
		String checkindate = sc.next();
		Date cdate = Date.valueOf(checkindate);
		System.out.print("Address: ");				
		String address = sc.next();
		System.out.print("Hospitalization [YES/NO]: ");
		Boolean hospitalized = sc.nextBoolean();
		Patient p= new Patient (name, surname, gender, bloodType, allergie, address, bdate, cdate, hospitalized, medCardNumber);
		return p;
	}
	
	public static void ShowPatientsTreatments(){
		String treatment;
		List<Treatment> treatmentsList = new ArrayList<Treatment>();
		while(treatmentsList.isEmpty()) {
			treatmentsList = sql.searchPatientsTreatment();//FUNCION NO CREADA debe buscar los tratamientos de un paciente, ordenarlos por fecha de inicio y devolver un string
		}
		while(treatment == null) {
			System.out.println("Here you can see all your treatments ordered by date: ");
			treatment = treatmentsList.toString();
			System.out.println(treatment);
		}
	}

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
	private static void checkAmbulancceAvailability() {
		Ambulance ambulance = null;
		while(ambulance == null) {
		System.out.println("Enter the ambulance's license plate (0000 XXX):");
		String licensePlate = sc.next();
		ambulance = SQL.searchAmbulance(licensePlate); //FUNCION NO CREADA debe buscar una ambulancia pasándole la matrícula y devolver la ambulancia correspondiente, si no existe, devolverá null
		if(ambulance.isAviable() == true) {
			System.out.println("The ambulance " + licensePlate + "is available");
		} else{
			System.out.println("The ambulance " + licensePlate + "is not available");
		}
		}
	}
	private static void adAccessToPatientsProfile() {
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
		System.out.println("Name: " + patient.getPatientName() + "\nSurname: " + patient.getPatientSurname() + "\nGender: " + patient.getGender() + "\nBirth date: " + patient.getBdate() + "\nBlood Type: " + patient.getBloodType() + "\nAllergies: " + patient.getAllergieType() + "\nCheck-in: " + patient.getCheckInDate());
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
}