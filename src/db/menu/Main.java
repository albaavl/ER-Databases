package db.menu;
 
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
			Worker medStaff = new Worker();
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
			Worker adstaff = new Worker();
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
				break;
			case 3: {
				System.out.println("Acces to a patient's profile");
				adAccessToPatientsProfile();
				break;
			}
			}
			
		}if(username.equalsIgnoreCase("p")&&password.equalsIgnoreCase("p")) {
			Patient patient = new Patient();//deberia ser el patient que ha hecho el log in
			System.out.println("Consult my treatment");
			System.out.println("Here you can see all your treatments ordered by date");
			showPatientsTreatments(patient);			
			
		}
		
		sc.close();
		}
		}catch (Exception e) {
		// TODO: handle exception
	}
		}
	
	public static void createPatient () throws NotBoundException {
		
		Patient p = new Patient();
		System.out.println("Please, input the patient info:");
		System.out.print("Name: ");
		String name = sc.next();
		p.setPatientName(name);
		System.out.print("Surname: ");
		String surname = sc.next();
		p.setPatientSurename(surname);
		System.out.print("Medical card number: ");
		Integer medCardNumber = sc.nextInt();
		p.setMedicalCardId(medCardNumber);
		System.out.print("Gender: ");
		String gender = sc.next();
		p.setGender(gender);
		System.out.print("Blood type: ");
		String bloodType = sc.next();
		p.setBloodType(bloodType);
		System.out.print("Allergies: ");
		String allergie = sc.next();
		p.setAllergieType(allergie);
		System.out.print("Date of birth: yyyy-[m]m-[d]d");
		String birthdate = sc.next();
		Date bdate = Date.valueOf(birthdate);
		p.setBdate(bdate);
		System.out.print("Check in date: yyyy-[m]m-[d]d"); //averiguar como meter horas y minutos
		String checkindate = sc.next();
		Date cdate = Date.valueOf(checkindate);
		p.setCheckInDate(cdate);
		System.out.print("Address: ");				
		String address = sc.next();
		p.setPatientAddress(address);
		System.out.print("Hospitalization [YES/NO]: ");
		Boolean hospitalized = sc.nextBoolean();
		p.setHospitalized(hospitalized);
	}
	
	public static void showPatientsTreatments(Patient patient){
		String treatment;
		List<Treatment> treatmentsList = new ArrayList<Treatment>();
		treatmentsList = sql.searchPatientsTreatment();//FUNCION NO CREADA debe buscar los tratamientos de un paciente, ordenarlos por fecha de inicio y devolver una lista
		if(treatmentList.isEmpty()){
			System.out.println ("Ther is no treatment available for this patient");
		} else{
			System.out.println("Here you can see all your treatments ordered by date: ");
			System.out.println(treatmentList.toString);
		}
	}

	private static void accessToAPatientsProfile() {
		Patient patient = selectPatient();
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
	
	private static void adAccessToPatientsProfile() {
		Patient patient = selectPatient();
		System.out.println(patient.toString());
		}
	
	private static void consultShifts() {
		System.out.println("Your shifts for the next 15 days are: ");
		//terminar en funcion de lo que decidamos hacer con shifts
	}

	private static void consultMedicalTest() {
		Patient patient = selectPatient();
		List<MedicalTest> tests = new ArrayList<MedicalTest>();
		tests = sql.searchMedicalTestByPatient(patient.getMedicalCardId()); //FUNCIÓN NO CREADA debe buscar medical tests por el medical card number del paciente asociaod al test y devolver una lista con todos los test asociados a ese paciente ordenados por fecha, null si no hay ninguno
		if (tests == null) {
			System.out.println("No tests available for patient " + patient.getPatientName()+" "+ patient.getPatientSurname());
	} else {
		System.out.println("These are all the medical tests of "+patient.getPatientName()+" "+patient.getPatientSurname()+" ordered by date:");
		System.out.println(tests.toString());
	}
	}

	private static void editDiagnosisAndTreatment(Patient  patient) {
		List<Treatment> treatments = new ArrayList<Treatment>();
		treatments = sql.searchTreatmentsByMedCardNumber(patient.getMedicalCardId()); //FUNCION NO CREADA debe buscar los tratamientos asociados a un paciente (se le pasa su medcard numnber) y devolver una lista con estos, null si no hay
		if(treatments == null) {
			System.out.println("No treatments available for patient "+ patient.getPatientName()+" "+ patient.getPatientSurname());
		} else {
			System.out.println("These are all the treatments associated to patient "+patient.getPatientName()+" "+patient.getPatientSurname()+" ordered by date:");
			System.out.println(treatments.toString());
			Treatment t = null;
			while (t == null) {
			System.out.println("Enter the id of the treatment that you want to edit:");
			Integer id = sc.nextInt();
			t = new Treatment(searchTreatmentsByID(id));//FUNCION NO CREADA debe buscar un treatment por su id y devolverlo, si no hay devolverá null
			}
			System.out.println("This is the selected treatment:");
			System.out.println(t.toString());
			System.out.println("Enter the new diagnosis, if you don't want to edit it enter a 0:");
			String diagnosis = sc.next();
			System.out.println("Enter the new medication, if you don't want to edit it enter a 0:");
			String medication = sc.next();
			System.out.println("Enter the new duration, if you don't want to edit it enter a 0:");
			String duration = sc.next();
			System.out.println("Enter the new recommendations, if you don't want to edit them enter a 0:");
			String recommendation = sc.next();
			Treatment nuevo = new Treatment(sql.editTreatment(t.getID(),diagnosis,medication,duration,recommendation)); //FUNCION NO CREADA debe hacer un update del treatment cuyo id se le pasa, SOLO DEBE CAMBIAR CADA PARÁMETRO si la string que se le pasa no es igual a un 0, debe devolver el nuevo tratamiento
			System.out.println("This is the edited treatment:");
			System.out.println(nuevo.toString());
		}
			
	}

	private static void createDiagnosisAndTreatment(Patient patient) {
		System.out.println("Enter the diagnosis:");
		String diagnosis = sc.next();
		System.out.println("Enter the medication:");
		String medication = sc.next();
		System.out.println("Enter the start date in this format yyyy-mm-dd:");
		Date startDate = Date.valueOf(sc.next());
		System.out.println("Enter the duration(number of days):");
		Integer duration = Integer.parseInt(sc.next());
		System.out.println("Enter the recommendations");
		String recommendation = sc.next();
		Treatment t = new Treatment(diagnosis, recommendation, startDate, medication, duration);
		sql.addTreatment(patient.getMedicalCardId(), t); //FUNCION NO CREADA añade un nuevo treatment recibiendo el medcard number del paciente al que va asociado y un objeto treatment, no devuelve nada
		System.out.println("Treatment added");
	}

	private static void accessToClinicalHistory(Patient patient) {
		// TODO Auto-generated method stub
	}
	
	private static Patient selectPatient() {
		List<Patient> patientList = new ArrayList<Patient>();
		Patient patient = null;
		while(patientList.isEmpty()) {
		System.out.println("Enter the patient's surname:");
		String surname = sc.next();
		patientList = SQL.searchPatient(surname); //FUNCION NO CREADA debe buscar un paciente pasandole un string del apellido y devolviendo una lista de objetos de tipo paciente que estará vacía si no hay ningún paciente con ese nombre;
		}
		while(patient == null) {
		System.out.println(patientList.toString());
		System.out.println("Enter the medical card number of the chosen patient:");
		Integer medCard = Integer.parseInt(sc.next());
		patient = new Patient(SQL.selectPatient (medCard)); //FUNCION NO CREADA debe seleccionar un paciente por el medical card number y devolver un objeto paciente que debe ser null si no existe un paciente con ese medical card number;
		}
		return patient;
	}
}