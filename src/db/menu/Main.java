package db.menu;

import java.util.*;
import db.jdbc.*;
import db.pojos.Patient;

public class Main {
	static String username;
	static String password;
	static int option = 1;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
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
			System.out.println("Choose an option[1-]:");
			System.out.println("1.");
			
		}if(username.equalsIgnoreCase("p")&&password.equalsIgnoreCase("p")) {
			System.out.println("Choose an option[1-]:");
			System.out.println("1.");
			
		}else {
			
		}
		
sc.close();
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
